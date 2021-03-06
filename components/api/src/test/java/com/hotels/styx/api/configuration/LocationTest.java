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
package com.hotels.styx.api.configuration;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static com.hotels.styx.support.ClassPathResourceUtils.getResource;
import static com.hotels.styx.support.matchers.IsOptional.isPresent;
import static java.lang.System.setProperty;
import static org.hamcrest.MatcherAssert.assertThat;

public class LocationTest {

    private static final String CONFIG_PATH = "CONFIG_PATH";

    @AfterTest
    protected void clearProperty() {
        System.clearProperty(CONFIG_PATH);
    }

    @Test(expectedExceptions = ConfigurationException.class)
    public void failsIfTheLocationSpecifiedIsNotReadableFilePath() {
        setProperty(CONFIG_PATH, "/path/that/does/not/exist");
        SystemSettings.Location location = new SystemSettings.Location(CONFIG_PATH);
        location.value();
    }

    @Test
    public void returnsTheConfiguredLocationPath() {
        setProperty(CONFIG_PATH, logPath("/conf/logback/logback.xml"));
        SystemSettings.Location location = new SystemSettings.Location(CONFIG_PATH);
        assertThat(location.value(), isPresent());
    }

    private String logPath(String name) {
        return getResource(LocationTest.class, name);
    }
}