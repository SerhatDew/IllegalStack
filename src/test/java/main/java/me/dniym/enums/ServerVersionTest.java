package main.java.me.dniym.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerVersionTest {

    @Test
    void parsesMinecraftVersion() {
        assertEquals(ServerVersion.v26_2_R1, ServerVersion.fromMinecraftVersion("26.2"));
    }

    @Test
    void parsesPaperVersionSuffix() {
        assertEquals(ServerVersion.v26_2_R1,
                ServerVersion.fromMinecraftVersion("26.2-R0.1-SNAPSHOT"));
    }

    @Test
    void reportsCalendarVersionMajor() {
        assertEquals(26, ServerVersion.v26_2_R1.getServerMajorVersionNumber());
    }

    @Test
    void rejectsUnsupportedVersion() {
        assertThrows(IllegalArgumentException.class,
                () -> ServerVersion.fromMinecraftVersion("not-a-version"));
    }

    @Test
    void rejectsWellFormedButUnsupportedVersion() {
        assertThrows(IllegalArgumentException.class,
                () -> ServerVersion.fromMinecraftVersion("26.3"));
    }

    @Test
    void rejectsNullVersion() {
        assertThrows(IllegalArgumentException.class,
                () -> ServerVersion.fromMinecraftVersion(null));
    }

    @Test
    void preservesVersionComparisonAndDisplayHelpers() {
        assertTrue(ServerVersion.v26_2_R1.serverVersionEqual(ServerVersion.v26_2_R1));
        assertTrue(ServerVersion.v26_2_R1.serverVersionGreaterThanOrEqual(ServerVersion.v1_21_R1));
        assertTrue(ServerVersion.v26_2_R1.serverVersionGreaterThan(ServerVersion.v26_2_R1,
                ServerVersion.v1_21_R1));
        assertTrue(ServerVersion.v1_21_R1.serverVersionLessThan(ServerVersion.v1_21_R1,
                ServerVersion.v26_2_R1));
        assertEquals("v26_2_R1", ServerVersion.v26_2_R1.getServerVersionName());
        assertEquals(ServerVersion.v26_2_R1.ordinal(), ServerVersion.v26_2_R1.getOrdinalServerVersionNumber());
        assertEquals(21, ServerVersion.v1_21_R1.getServerMajorVersionNumber());
    }
}
