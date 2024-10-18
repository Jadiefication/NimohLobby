package src.net.jadiefication.Commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import jdk.jfr.Experimental;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import src.net.jadiefication.lobby.Lobby;

public class SurvivalCommand extends BasicCommands{

    public SurvivalCommand(Lobby plugin) {
        super(plugin);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "velocity:main");
    }

    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {

        Player player = (Player) commandSourceStack.getSender();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("Connect");
        out.writeUTF("survival");

        player.sendPluginMessage(plugin, "velocity:main", out.toByteArray());
    }

    @Override
    public @Nullable String permission() {
        return "lobby.survival";
    }
}
