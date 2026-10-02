package main.java.me.dniym.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ServerVersion {

    v1_8_R1,
    v1_8_R2,
    v1_8_R3,
    v1_9_R1,
    v1_9_R2,
    v1_10_R1,
    v1_11_R1,
    v1_12_R1,
    v1_13_R1,
    v1_13_R2,
    v1_14_R1,
    v1_15_R1,
    v1_16_R1,
    v1_16_R2,
    v1_16_R3,
    v1_17_R1,
    v1_18_R1,
    v1_18_R2,
    v1_19_R1,
    v1_19_R2,
    v1_19_R3,
    v1_20_R1,
    v1_20_R2,
    v1_20_R3,
    v1_20_R4,
    v1_20_R5,
    v1_21_R1,
    v26_2_R1;

    private static final Pattern MINECRAFT_VERSION_PATTERN =
            Pattern.compile("^(\\d+)\\.(\\d+)(?:[-+].*)?$");

    /**
     * Resolves the Minecraft version reported by the Bukkit API to the
     * corresponding version supported by this build.
     *
     * @param minecraftVersion version returned by Bukkit.getMinecraftVersion()
     * @return the matching server version
     * @throws IllegalArgumentException if the value is malformed or unsupported
     */
    public static ServerVersion fromMinecraftVersion(String minecraftVersion) {
        if (minecraftVersion == null) {
            throw new IllegalArgumentException("Minecraft version must not be null");
        }

        Matcher matcher = MINECRAFT_VERSION_PATTERN.matcher(minecraftVersion);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Minecraft version: " + minecraftVersion);
        }

        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        if (major == 26 && minor == 2) {
            return v26_2_R1;
        }

        throw new IllegalArgumentException("Unsupported Minecraft version: " + minecraftVersion);
    }

    public boolean serverVersionEqual(ServerVersion version) {
        return this.equals(version);
    }

    public boolean serverVersionGreaterThanOrEqual(ServerVersion version) {
        return this.ordinal() >= version.ordinal();
    }


    public boolean serverVersionGreaterThan(ServerVersion version1, ServerVersion version2) {
        return version1.ordinal() > version2.ordinal();
    }

    public boolean serverVersionLessThan(ServerVersion version1, ServerVersion version2) {
        return version1.ordinal() < version2.ordinal();
    }

    public String getServerVersionName() {
        return this.name();
    }

    public int getOrdinalServerVersionNumber() {
        return this.ordinal();
    }

    public int getServerMajorVersionNumber() {
        String[] versionParts = this.name().split("_");
        int leadingVersion = Integer.parseInt(versionParts[0].substring(1));
        return leadingVersion == 1
                ? Integer.parseInt(versionParts[1])
                : leadingVersion;
    }
}
