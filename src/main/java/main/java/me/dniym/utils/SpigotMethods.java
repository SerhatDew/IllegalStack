package main.java.me.dniym.utils;

import main.java.me.dniym.enums.Protections;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class SpigotMethods {

    static Component legacyText(String text) {
        return LegacyComponentSerializer.legacySection().deserialize(text);
    }

    private static Component interactive(String text, String hover, String command, ClickEvent.Action action) {
        Component component = legacyText(text).hoverEvent(HoverEvent.showText(legacyText(hover)));
        if (command != null) {
            ClickEvent clickEvent = action == ClickEvent.Action.RUN_COMMAND
                    ? ClickEvent.runCommand(command)
                    : ClickEvent.suggestCommand(command);
            component = component.clickEvent(clickEvent);
        }
        return component;
    }

    public static Component makeParentText(Protections protections, String status, boolean children, int catId) {
        Component result;
        if (!protections.isList()) {
            String tag = ChatColor.DARK_AQUA + "[" + status + ChatColor.DARK_AQUA + "]";
            result = interactive(tag, "Toggle This Protection On/Off", "/istack toggle " + protections.name(),
                    ClickEvent.Action.RUN_COMMAND);
        } else {
            String command = "/istack value add " + protections.name();
            result = interactive(ChatColor.GRAY + "[" + ChatColor.GREEN + "A", "Add a value to this list",
                    command, ClickEvent.Action.SUGGEST_COMMAND);
            if (!protections.getTxtSet().isEmpty()) {
                result = result.append(interactive(ChatColor.RED + "R", "Remove a value from this list",
                        "/istack value remove " + protections.name(), ClickEvent.Action.SUGGEST_COMMAND));
            } else {
                result = result.append(Component.text(" "));
            }
            result = result.append(interactive(ChatColor.AQUA + "L", ChatColor.AQUA + protections.findValue(), null, null));
            result = result.append(legacyText(ChatColor.GRAY + "] "));
        }

        result = result.append(legacyText(ChatColor.GOLD + " " + protections.getDisplayName()));
        Component info = legacyText(ChatColor.GREEN + " *");
        if (!protections.getDescription().isEmpty()) {
            String description = protections.getDescription() + ChatColor.AQUA + " " + protections.getVersion() + ChatColor.RESET;
            info = info.hoverEvent(HoverEvent.showText(legacyText(description)));
        }
        result = result.append(info);

        if (children) {
            result = result.append(interactive(ChatColor.YELLOW + "(more options)", "Click to show/hide more options",
                    "/istack prot " + catId + " " + protections.getProtId(), ClickEvent.Action.RUN_COMMAND));
        }
        return result;
    }

    public static Component makeCategoryText(String category, Boolean show, int catId) {
        Component result = legacyText(ChatColor.AQUA + "-     " + category + "     -");
        String text = show
                ? ChatColor.GRAY + " (" + ChatColor.RED + "HIDE" + ChatColor.GRAY + ")"
                : ChatColor.GRAY + " (" + ChatColor.GREEN + "SHOW" + ChatColor.GRAY + ")";

        String command = "/istack prot ";
        if (!show) {
            if (catId == 1 || catId == 2) {
                command += catId;
            }
        }
        return result.append(interactive(text, ChatColor.GRAY + "Click to activate", command,
                ClickEvent.Action.RUN_COMMAND));
    }

    public static Component makeChildText(Protections protection, int catId) {
        Component result = legacyText(ChatColor.GRAY + "  -> ")
                .hoverEvent(HoverEvent.showText(legacyText(protection.getDescription())));

        if (protection.isList()) {
            Component addOption;
            if (protection == Protections.EnchantedItemWhitelist) {
                addOption = interactive(ChatColor.GOLD + " [" + ChatColor.GREEN + "A",
                        "Click to toggle adding EnchantedItemWhitelist add mode on/off",
                        "/istack enchantwhitelistmode", ClickEvent.Action.RUN_COMMAND);
            } else {
                addOption = interactive(ChatColor.GOLD + " [" + ChatColor.GREEN + "A",
                        "Click to add a value to this list.", "/istack value add " + protection.name(),
                        ClickEvent.Action.SUGGEST_COMMAND);
            }
            result = result.append(addOption);
            if (!protection.getTxtSet().isEmpty()) {
                result = result.append(interactive(ChatColor.RED + "R", "Click to remove a value from this list.",
                        "/istack value remove " + protection.name(), ClickEvent.Action.SUGGEST_COMMAND));
            } else {
                result = result.append(Component.text(" "));
            }
            result = result.append(interactive(ChatColor.GRAY + "L" + ChatColor.GOLD + "] ", protection.findValue(), null, null));
        } else if (protection.getIntValue() >= 0) {
            int value = protection.getConfigValue() instanceof Integer ? (Integer) protection.getConfigValue() : 0;
            String padding = value <= 9 ? "   " : value <= 99 ? " " : "";
            String command = "/istack value set " + protection.name();
            result = result.append(interactive(ChatColor.GRAY + " [" + protection.findValue() + padding + "] ",
                    ChatColor.GREEN + "(Click to change)", command, ClickEvent.Action.SUGGEST_COMMAND));
        } else if (!protection.getTxtValue().isEmpty()) {
            String command = "/istack value set " + protection.name() + " " + protection.getConfigValue();
            result = result.append(interactive(ChatColor.GRAY + " [" + ChatColor.GREEN + "TXT" + ChatColor.GRAY + "] ",
                    ChatColor.GRAY + protection.findValue() + ChatColor.GREEN + " (Click to change)", command,
                    ClickEvent.Action.SUGGEST_COMMAND));
        } else {
            String status = protection.isEnabled() ? ChatColor.GREEN + " ON" : ChatColor.DARK_RED + "OFF";
            result = result.append(interactive(ChatColor.GOLD + " [" + status + ChatColor.GOLD + "] ",
                    "Click to toggle", "/istack toggle " + protection.name() + " " + catId,
                    ClickEvent.Action.RUN_COMMAND));
        }

        result = result.append(legacyText(ChatColor.DARK_AQUA + protection.getDisplayName()));
        if (!protection.getDescription().isEmpty()) {
            result = result.hoverEvent(HoverEvent.showText(legacyText(protection.getDescription())));
        }
        return result;
    }

    public static String wordWrap(String text, int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Line length must be positive");
        }
        if (text.length() <= length) {
            return text;
        }

        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        for (String word : text.trim().split("\\s+")) {
            if (word.length() > length) {
                if (!currentLine.isEmpty()) {
                    lines.add(currentLine.toString());
                    currentLine.setLength(0);
                }
                lines.addAll(List.of(longWord(word, length).split("\n")));
            } else if (currentLine.isEmpty()) {
                currentLine.append(word);
            } else if (currentLine.length() + 1 + word.length() <= length) {
                currentLine.append(' ').append(word);
            } else {
                lines.add(currentLine.toString());
                currentLine.setLength(0);
                currentLine.append(word);
            }
        }
        if (!currentLine.isEmpty()) {
            lines.add(currentLine.toString());
        }
        return String.join("\n", lines);
    }

    public static String longWord(String text, int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Line length must be positive");
        }

        StringBuilder result = new StringBuilder(text.length() + text.length() / length);
        for (int start = 0; start < text.length(); start += length) {
            if (!result.isEmpty()) {
                result.append('\n');
            }
            result.append(text, start, Math.min(start + length, text.length()));
        }
        return result.toString();
    }

    public static boolean isNPC(LivingEntity entity) {
        return entity != null && (entity.hasMetadata("shopkeeper") || entity.hasMetadata("NPC"));
    }
}
