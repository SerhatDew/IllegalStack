package main.java.me.dniym.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class IllegalStackCommandTest {

    @Test
    void protectionMenuOffersClickableCategoriesWithoutReload() {
        List<ClickEvent<?>> commands = new ArrayList<>();
        Player player = (Player) Proxy.newProxyInstance(Player.class.getClassLoader(), new Class<?>[]{Player.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("hasPermission")) {
                        return true;
                    }
                    if (method.getName().equals("sendMessage")) {
                        if (args[0] instanceof Component component) {
                            collectCommands(component, commands);
                        }
                        return null;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });

        new IllegalStackCommand().onCommand(player, null, "istack", new String[]{"protections"});

        assertThat(commands).containsExactly(ClickEvent.runCommand("/istack prot 1"),
                ClickEvent.runCommand("/istack prot 2"));
    }

    private static void collectCommands(Component component, List<ClickEvent<?>> commands) {
        ClickEvent<?> click = component.clickEvent();
        if (click != null && click.action() == ClickEvent.Action.RUN_COMMAND) {
            commands.add(click);
        }
        for (Component child : component.children()) {
            collectCommands(child, commands);
        }
    }
}
