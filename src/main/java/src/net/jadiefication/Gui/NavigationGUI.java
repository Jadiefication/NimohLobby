package src.net.jadiefication.Gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import src.net.jadiefication.API.Border;
import src.net.jadiefication.API.Heads;
import src.net.jadiefication.lobby.Lobby;

import java.net.MalformedURLException;
import java.util.List;

public class NavigationGUI implements InventoryHolder {

    private final Inventory inventory;
    private final ItemStack item = Heads.createHead("98daa1e3ed94ff3e33e1d4c6e43f024c47d78a57ba4d38e75e7c9264106");
    private final ItemMeta meta = item.getItemMeta();

    public NavigationGUI(Lobby plugin) throws MalformedURLException {
        meta.displayName(Component.text("§aꜱᴜʀᴠɪᴠᴀʟ"));
        meta.lore(List.of(Component.text("§7Join us on a wonderful journey on Nimoh!"), Component.text("§7This gamemode is still in beta, stuff is bound to change!")));
        item.setItemMeta(meta);
        this.inventory = plugin.getServer().createInventory(this, 54, Component.text("Navigation"));
        Border.setGuiBorder(this.inventory);
        this.inventory.setItem(22, item);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
