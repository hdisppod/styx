/**
 * Copyright (C) 2013-2018 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.styx.proxy.backends.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.annotations.VisibleForTesting;
import com.hotels.styx.api.Environment;
import com.hotels.styx.api.configuration.Configuration;
import com.hotels.styx.api.configuration.ConfigurationException;
import com.hotels.styx.api.service.spi.AbstractStyxService;
import com.hotels.styx.client.applications.BackendService;
import com.hotels.styx.client.applications.BackendServices;
import com.hotels.styx.infrastructure.FileBackedRegistry;
import com.hotels.styx.infrastructure.Registry;
import com.hotels.styx.infrastructure.YamlReader;
import com.hotels.styx.proxy.backends.file.FileChangeMonitor.FileMonitorSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Throwables.propagate;
import static com.hotels.styx.api.io.ResourceFactory.newResource;
import static com.hotels.styx.client.applications.BackendServices.newBackendServices;
import static com.hotels.styx.infrastructure.Registry.Outcome.FAILED;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * File backed {@link com.hotels.styx.client.applications.BackendService} registry.
 */
public class FileBackedBackendServicesRegistry extends AbstractStyxService implements Registry<BackendService>, FileChangeMonitor.Listener {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileBackedBackendServicesRegistry.class);
    private final FileBackedRegistry<BackendService> fileBackedRegistry;
    private final FileMonitor fileChangeMonitor;

    @VisibleForTesting
    FileBackedBackendServicesRegistry(FileBackedRegistry<BackendService> fileBackedRegistry) {
        this(fileBackedRegistry, FileMonitor.DISABLED);
    }

    @VisibleForTesting
    FileBackedBackendServicesRegistry(FileBackedRegistry<BackendService> fileBackedRegistry, FileMonitor fileChangeMonitor) {
        super(format("FileBackedBackendServiceRegistry(%s)", fileBackedRegistry.fileName()));
        this.fileBackedRegistry = requireNonNull(fileBackedRegistry);
        this.fileChangeMonitor = requireNonNull(fileChangeMonitor);
    }

    public static FileBackedBackendServicesRegistry create(String originsFile) {
        return create(originsFile, FileMonitor.DISABLED);
    }

    static FileBackedBackendServicesRegistry create(String originsFile, FileMonitor fileChangeMonitor) {
        FileBackedRegistry<BackendService> fileBackedRegistry = new FileBackedRegistry<>(
                newResource(originsFile),
                new YAMLBackendServicesReader());
        return new FileBackedBackendServicesRegistry(fileBackedRegistry, fileChangeMonitor);
    }

    @Override
    public Registry<BackendService> addListener(ChangeListener<BackendService> changeListener) {
        return this.fileBackedRegistry.addListener(changeListener);
    }

    @Override
    public Registry<BackendService> removeListener(ChangeListener<BackendService> changeListener) {
        return this.fileBackedRegistry.removeListener(changeListener);
    }

    @Override
    public CompletableFuture<ReloadResult> reload() {
        return this.fileBackedRegistry.reload()
                .thenApply(outcome -> logReloadAttempt("Admin Interface", outcome));
    }

    @Override
    public Iterable<BackendService> get() {
        return this.fileBackedRegistry.get();
    }

    @Override
    protected CompletableFuture<Void> startService() {
        try {
            fileChangeMonitor.start(this);
        } catch (Exception e) {
            CompletableFuture<Void> x = new CompletableFuture<>();
            x.completeExceptionally(e);
            return x;
        }
        return this.fileBackedRegistry.reload()
                .thenApply(result -> logReloadAttempt("Initial load", result))
                .thenAccept(result -> {
                    if (result.outcome() == FAILED) {
                        throw new RuntimeException(result.cause().orElse(null));
                    }
                });
    }

    @Override
    public CompletableFuture<Void> stop() {
        return super.stop();
    }

    @VisibleForTesting
    FileMonitor monitor() {
        return fileChangeMonitor;
    }

    @Override
    public void fileChanged() {
        this.fileBackedRegistry.reload()
                .thenApply(outcome -> logReloadAttempt("File Monitor", outcome));
    }

    private ReloadResult logReloadAttempt(String reason, ReloadResult outcome) {
        String fileName = this.fileBackedRegistry.fileName();
        if (outcome.outcome() == Outcome.RELOADED || outcome.outcome() == Outcome.UNCHANGED) {
            LOGGER.info("Backend services reloaded. reason='{}', {}, file='{}'", new Object[]{reason, outcome.message(), fileName});
        } else if (outcome.outcome() == FAILED) {
            LOGGER.error("Backend services reload failed. reason='{}', {}, file='{}'",
                    new Object[]{reason, outcome.message(), fileName, outcome.cause().get()});
        }
        return outcome;
    }

    /**
     * Factory for creating a {@link FileBackedBackendServicesRegistry}.
     */
    public static class Factory implements Registry.Factory<BackendService> {

        @Override
        public Registry<BackendService> create(Environment environment, Configuration registryConfiguration) {
            String originsFile = registryConfiguration.get("originsFile", String.class)
                    .map(Factory::requireNonEmpty)
                    .orElseThrow(() -> new ConfigurationException(
                            "missing [services.registry.factory.config.originsFile] config value for factory class FileBackedBackendServicesRegistry.Factory"));

            FileMonitorSettings monitorSettings = registryConfiguration.get("monitor", FileMonitorSettings.class)
                    .orElse(new FileMonitorSettings());

            return registry(originsFile, monitorSettings);
        }

        private static Registry<BackendService> registry(String originsFile, FileMonitorSettings monitorSettings) {
            requireNonEmpty(originsFile);

            FileBackedRegistry<BackendService> fileBackedRegistry = new FileBackedRegistry<>(
                    newResource(originsFile),
                    new YAMLBackendServicesReader());

            FileMonitor monitor = monitorSettings.enabled() ? new FileChangeMonitor(originsFile) : FileMonitor.DISABLED;
            return new FileBackedBackendServicesRegistry(fileBackedRegistry, monitor);
        }

        private static String requireNonEmpty(String originsFile) {
            if (originsFile.isEmpty()) {
                throw new ConfigurationException("empty [services.registry.factory.config.originsFile] config value for factory class FileBackedBackendServicesRegistry.Factory");
            } else {
                return originsFile;
            }
        }
    }

    @VisibleForTesting
    static class YAMLBackendServicesReader implements FileBackedRegistry.Reader<BackendService> {
        private final YamlReader<List<BackendService>> delegate = new YamlReader<>();

        @Override
        public Iterable<BackendService> read(byte[] content) {
            try {
                return readBackendServices(content);
            } catch (Exception e) {
                throw propagate(e);
            }
        }

        private BackendServices readBackendServices(byte[] content) throws Exception {
            return newBackendServices(delegate.read(content, new TypeReference<List<BackendService>>() {
            }));
        }
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("originsFileName", fileBackedRegistry.fileName())
                .toString();
    }
}
