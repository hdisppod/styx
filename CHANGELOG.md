# Change Log

## [styx-0.7.4](https://github.com/HotelsDotCom/styx/tree/styx-0.7.4) (2018-01-11)
[Full Changelog](https://github.com/HotelsDotCom/styx/compare/styx-0.7.3...styx-0.7.4)

**Implemented enhancements:**

- Choose supported TLS protocol versions for styx client [\#28](https://github.com/HotelsDotCom/styx/issues/28)

**Merged pull requests:**

- Tidy up tests: Use new FullHttpRequest/Response message API [\#42](https://github.com/HotelsDotCom/styx/pull/42) ([mikkokar](https://github.com/mikkokar))
- Add initial changelog file and make rule for changelog generation [\#41](https://github.com/HotelsDotCom/styx/pull/41) ([alobodzki](https://github.com/alobodzki))
- Add FullHttpRequest and FullHttpResponse classes to Styx API [\#40](https://github.com/HotelsDotCom/styx/pull/40) ([kvosper](https://github.com/kvosper))
- Pretty print admin interface pages by default. [\#35](https://github.com/HotelsDotCom/styx/pull/35) ([mikkokar](https://github.com/mikkokar))
- Don't try to parse the inbound request paths [\#15](https://github.com/HotelsDotCom/styx/pull/15) ([taer](https://github.com/taer))

## [styx-0.7.3](https://github.com/HotelsDotCom/styx/tree/styx-0.7.3) (2017-11-27)
[Full Changelog](https://github.com/HotelsDotCom/styx/compare/styx-0.7.1...styx-0.7.3)

**Closed issues:**

- Out of direct memory exception from Netty is sometimes mapped to 502 Bad Gateway [\#24](https://github.com/HotelsDotCom/styx/issues/24)
- Provide a metric for direct memory monitoring [\#17](https://github.com/HotelsDotCom/styx/issues/17)

**Merged pull requests:**

- Added developers section in parent pom [\#39](https://github.com/HotelsDotCom/styx/pull/39) ([alobodzki](https://github.com/alobodzki))
- Version bumped up to 0.7.3-SNAPSHOT [\#38](https://github.com/HotelsDotCom/styx/pull/38) ([alobodzki](https://github.com/alobodzki))
- Maven central publishing profile added [\#37](https://github.com/HotelsDotCom/styx/pull/37) ([alobodzki](https://github.com/alobodzki))
- Change file headers to expected format [\#34](https://github.com/HotelsDotCom/styx/pull/34) ([kvosper](https://github.com/kvosper))
- Issue28: Specify TLS protocol version for backend services [\#33](https://github.com/HotelsDotCom/styx/pull/33) ([mikkokar](https://github.com/mikkokar))
- Create ISSUE\_TEMPLATE.md [\#31](https://github.com/HotelsDotCom/styx/pull/31) ([kainee](https://github.com/kainee))
- Add link to styx-user Google Groups forum. [\#30](https://github.com/HotelsDotCom/styx/pull/30) ([mikkokar](https://github.com/mikkokar))
- Implement Wiremock API on top of Styx/Netty HTTP servers. [\#29](https://github.com/HotelsDotCom/styx/pull/29) ([mikkokar](https://github.com/mikkokar))
- Styx server tls protocol [\#27](https://github.com/HotelsDotCom/styx/pull/27) ([mikkokar](https://github.com/mikkokar))
- Map OutOfDirectMemoryError to 500 Internal Server Error status code. [\#26](https://github.com/HotelsDotCom/styx/pull/26) ([mikkokar](https://github.com/mikkokar))
- Enable Maven quality profile for Travis builds [\#25](https://github.com/HotelsDotCom/styx/pull/25) ([mikkokar](https://github.com/mikkokar))
- Set of metrics that are tracking netty pooled memory allocator [\#23](https://github.com/HotelsDotCom/styx/pull/23) ([alobodzki](https://github.com/alobodzki))
- Added code of conduct [\#22](https://github.com/HotelsDotCom/styx/pull/22) ([massdosage](https://github.com/massdosage))
- Log file changes when file backed registry fails at notifying listene… [\#21](https://github.com/HotelsDotCom/styx/pull/21) ([kvosper](https://github.com/kvosper))
- Change styx version format. [\#20](https://github.com/HotelsDotCom/styx/pull/20) ([mikkokar](https://github.com/mikkokar))
- Add styx load test script [\#18](https://github.com/HotelsDotCom/styx/pull/18) ([mikkokar](https://github.com/mikkokar))
- Add getOptional to Http context [\#16](https://github.com/HotelsDotCom/styx/pull/16) ([taer](https://github.com/taer))
- Make sure we don't end up with 0 threads [\#14](https://github.com/HotelsDotCom/styx/pull/14) ([taer](https://github.com/taer))
- fix documentation [\#13](https://github.com/HotelsDotCom/styx/pull/13) ([ggaeta1](https://github.com/ggaeta1))
- Update pom files [\#12](https://github.com/HotelsDotCom/styx/pull/12) ([mikkokar](https://github.com/mikkokar))

## [styx-0.7.1](https://github.com/HotelsDotCom/styx/tree/styx-0.7.1) (2017-10-19)
**Merged pull requests:**

- Release styx-0.7.1. [\#11](https://github.com/HotelsDotCom/styx/pull/11) ([mikkokar](https://github.com/mikkokar))
- Improve distribution zip file: [\#10](https://github.com/HotelsDotCom/styx/pull/10) ([mikkokar](https://github.com/mikkokar))
- Adding guides to get started with Styx quickly [\#7](https://github.com/HotelsDotCom/styx/pull/7) ([kvosper](https://github.com/kvosper))
- Adding sections of pom requested by central repository [\#6](https://github.com/HotelsDotCom/styx/pull/6) ([alobodzki](https://github.com/alobodzki))
- Reduce test logging [\#5](https://github.com/HotelsDotCom/styx/pull/5) ([mikkokar](https://github.com/mikkokar))
- Apache license shield added [\#4](https://github.com/HotelsDotCom/styx/pull/4) ([alobodzki](https://github.com/alobodzki))
- Add Travis build badge to README.md. [\#3](https://github.com/HotelsDotCom/styx/pull/3) ([mikkokar](https://github.com/mikkokar))
- Add .travis.yml. [\#2](https://github.com/HotelsDotCom/styx/pull/2) ([mikkokar](https://github.com/mikkokar))
- Fixed image reference to Styx overview diagram. [\#1](https://github.com/HotelsDotCom/styx/pull/1) ([iamchrisrice](https://github.com/iamchrisrice))



\* *This Change Log was automatically generated by [github_changelog_generator](https://github.com/skywinder/Github-Changelog-Generator)*