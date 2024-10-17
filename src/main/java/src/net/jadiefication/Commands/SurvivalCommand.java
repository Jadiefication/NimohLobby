package src.net.jadiefication.Commands;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import src.net.jadiefication.lobby.Lobby;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class SurvivalCommand extends BasicCommands{

    public SurvivalCommand(Lobby plugin) {
        super(plugin);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "bungeecord:main");
    }

    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {

        Player player = (Player) commandSourceStack.getSender();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(outputStream);

        try {
            out.writeUTF("Connect");
            out.writeUTF("survival");
        } catch (Exception e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(plugin, "bungeecord:main", outputStream.toByteArray());
    }

    @Override
    public @Nullable String permission() {
        return "lobby.survival";
    }
}
