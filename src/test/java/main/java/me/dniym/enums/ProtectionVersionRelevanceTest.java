package main.java.me.dniym.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProtectionVersionRelevanceTest {

    @Test
    void oldVersionOnlyProtectionIsNotRelevantToMinecraft262() {
        assertFalse(Protections.isVersionRelevant("< 1.21", "v26_2"));
        assertFalse(Protections.isVersionRelevant("< 1.20", "26.2"));
    }

    @Test
    void oldVersionOnlyProtectionRemainsRelevantBelowItsCutoff() {
        assertTrue(Protections.isVersionRelevant("< 1.21", "v1_20_R6"));
        assertTrue(Protections.isVersionRelevant("< 1.20", "1.19.4"));
    }

    @Test
    void openEndedProtectionsRemainRelevantToNewerMinecraft() {
        assertTrue(Protections.isVersionRelevant("> 1.12", "v26_2"));
        assertTrue(Protections.isVersionRelevant("ALL", "v26_2"));
    }
}
