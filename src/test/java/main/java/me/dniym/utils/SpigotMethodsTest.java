package main.java.me.dniym.utils;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SpigotMethodsTest {

    @Test
    void legacyTextPreservesSectionColorCodes() {
        assertThat(LegacyComponentSerializer.legacySection().serialize(SpigotMethods.legacyText("§3text")))
                .isEqualTo("§3text");
    }

    @Test
    void wordWrapPreservesEveryWord() {
        assertThat(SpigotMethods.wordWrap("alpha beta gamma", 10))
                .isEqualTo("alpha beta\ngamma");
    }

    @Test
    void longWordSplitsWithoutDroppingCharacters() {
        assertThat(SpigotMethods.longWord("abcdefgh", 3))
                .isEqualTo("abc\ndef\ngh");
    }
}
