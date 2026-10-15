package main.java.me.dniym.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

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

    @ParameterizedTest
    @EnumSource(value = Protections.class, names = {
            "PreventRailDupe", "PreventTripwireDupe", "VillagerTradeCheesing"
    })
    void multiVersionProtectionsRemainRelevantToMinecraft262(Protections protection) {
        assertTrue(protection.isRelevantToVersion("v26_2_R1"));
        assertTrue(protection.isRelevantToVersion("26.2"));
        assertTrue(protection.isRelevantToVersion("v1_21_R1"));
        assertFalse(protection.isRelevantToVersion("v26_20_R1"));
        assertFalse(protection.isRelevantToVersion("v26_3_R1"));
    }
}
