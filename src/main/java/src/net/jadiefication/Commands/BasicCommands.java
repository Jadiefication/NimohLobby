package src.net.jadiefication.Commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import src.net.jadiefication.lobby.Lobby;

public class BasicCommands implements BasicCommand {
    protected final Lobby plugin;


    public BasicCommands(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull CommandSourceStack commandSourceStack, @NotNull String[] strings) {

    }

    @Override
    public boolean canUse(@NotNull CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public @Nullable String permission() {
        return "survival." + getClass().getSimpleName().toLowerCase().replace("command", "");
    }

    protected void sendNoPermissionMessage(Player player) {
        player.sendMessage("You don't have permission to use this command.");
    }
}
