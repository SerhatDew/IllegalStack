# IllegalStack
A Paper plugin dedicated to fixing glitches and exploits in Minecraft, updated for Paper 26.2 and Java 25.

## Links
(NOTE: CONCERNING CUSTOM OR UNRELEASED OR SPECIAL BUILDS OR JARS.  THE ONLY PLACE YOU SHOULD EVER DOWNLOAD THIS OR ANY OTHER PLUGIN IS DIRECTLY FROM THE AUTHORS LINKS.  NEVER TRUST SOMEONE THAT RANDOMLY MESSAGES YOU OR JOINS YOUR SERVER CLAIMING TO HAVE A NEW VERSION AS IT WILL LIKELY CONTAIN MALICIOUS CODE TO TAKE OVER OR DESTROY YOUR SERVER!)
- [Spigot](https://www.spigotmc.org/resources/dupe-fixes-illegal-stack-remover.44411/) <- OFFICIAL DOWNLOAD LINK
- [Wiki](https://github.com/dniym/IllegalStack/wiki/FAQ)
- [Discord](https://discord.gg/Gsx4QaT)

## Building this project

Build requirements: JDK 25, Gradle 9.8.0, and Paper API 26.2. The local source bundle includes Gradle wrapper support; repository checkouts can use an installed Gradle 9.8.0 because binary JAR files are intentionally not tracked.

Run `gradle clean check build jacocoTestReport` from the project directory. In this local workspace, `./gradlew` or `gradlew.bat` can be used instead. This runs the tests and coverage verification and builds `/build/libs/IllegalStack.jar`.

The JaCoCo report is written to `/build/reports/jacoco/test/html/index.html`.
