package src.net.jadiefication.lobby;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import src.net.jadiefication.Commands.SurvivalCommand;
import src.net.jadiefication.Database.Database;
import src.net.jadiefication.Gui.NavigationGUI;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public final class Lobby extends JavaPlugin implements Listener {

    private NavigationGUI navigationGUI;
    private List<String> ignore;

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "velocity:main");
        Bukkit.getPluginManager().registerEvents(this, this);
        getDataFolder().mkdirs(); // This will create the directory if it doesn't exist
        Path ignorePath = Path.of(getDataFolder().getPath(), "ignore.db");
        if (!Files.exists(ignorePath)) {
            try {
                Files.createFile(ignorePath);
            } catch (IOException e) {
                getLogger().severe("Failed to create ignore.db file: " + e.getMessage());
            }
        }
        Database.initDatabase("ignore", getDataPath().toString());
        ignore = Database.getIgnoreList();
        try {
            navigationGUI = new NavigationGUI(this);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        registerCommand();
    }

    private void registerCommand() {
        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register("survivals", new SurvivalCommand(this));
        });
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "minecraft:tp " + player.getName() + " -1 150 27 180 0");
        UUID playerUUID = player.getUniqueId();
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1);
        meta.displayName(Component.text("§6§lɴᴀᴠɪɢᴀᴛɪᴏɴ"));
        meta.lore(List.of(Component.text("§7ᴜꜱᴇ ᴛʜɪꜱ ᴛᴏ ɴᴀᴠɪɢᴀᴛᴇ ᴛʜᴇ ɴᴇᴛᴡᴏʀᴋ")));
        item.setItemMeta(meta);
        if (!ignore.contains(playerUUID.toString())) {
            player.getInventory().clear();
        }
        if (!player.getInventory().contains(item)) {
            player.getInventory().setItem(4 ,item);
        }
    }

    @EventHandler
    public void onInventoryUse(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().equals(navigationGUI.getInventory())) {
            event.setCancelled(true);
            if (event.getSlot() == 22) {
                player.performCommand("survivals");
            }
        }
    }

    @EventHandler
    public void onNavigationUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item != null && item.getType() == Material.AMETHYST_SHARD) {
            ItemMeta meta = item.getItemMeta();

            // Check if the compass has the correct CustomModelData for the custom texture
            if (meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 1) {
                // Cancel the event (prevent other actions)
                event.setCancelled(true);
                player.openInventory(navigationGUI.getInventory());
            }
        }
    }

    @Override
    public void onDisable() {
        Database.closeDatabase();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "velocity:main");
        Bukkit.getPluginManager().disablePlugin(this);
    }
}
