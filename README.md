# IllegalStack

A Paper plugin dedicated to fixing glitches, item duplication bugs, and exploits in Minecraft.

This fork updates the upstream IllegalStack project for **Paper 26.2** running on **Java 25**.

## Compatibility

- **Server:** Paper 26.2
- **Java:** Java 25 (JDK 25)

For older Minecraft versions (1.13 - 1.21), please use the upstream releases from the official links below.

## Releases

[Download IllegalStack 2.9.12b](https://github.com/SerhatDew/IllegalStack/releases/tag/v2.9.12b)

Place `IllegalStack.jar` in the server's `plugins` directory and restart the server. Replace the previous JAR when upgrading.

### 2.9.12b

- Restored clickable protection menus and staff notification links on startup, without requiring `/istack reload`.
- Enabled rail/carpet dupe, tripwire dupe, and villager trade cheesing protections for Paper 26.2. Legacy-only protections remain excluded.


## Changes in this Fork

- Updated compilation target and Gradle toolchain to Java 25.
- Updated server dependency to Paper API 26.2.
- Updated `ServerVersion` detection logic to support 2-digit major versions (26.2).
- Migrated legacy BungeeCord chat components to Kyori Adventure API.
- Updated deprecated Bukkit API references (enchantment names, potion data).
- Updated listener bindings to Bukkit `EntityMountEvent`.
- Reduced configuration log verbosity during first-time startup.
- Added JUnit 5 tests and JaCoCo coverage verification for version parsing and protections.

## Building from Source

Requirements:
- JDK 25
- Gradle 9.8.0 (or Gradle wrapper)

To build the plugin JAR:
```bash
gradle clean check build jacocoTestReport
```

The compiled plugin will be generated at `build/libs/IllegalStack.jar`.

## Upstream Links

This project is a fork of IllegalStack by dNiym.
- Official Spigot Page: https://www.spigotmc.org/resources/dupe-fixes-illegal-stack-remover.44411/
- Wiki / Documentation: https://github.com/dniym/IllegalStack/wiki/FAQ
- Discord: https://discord.gg/Gsx4QaT
- Original Source: https://github.com/dniym/IllegalStack
