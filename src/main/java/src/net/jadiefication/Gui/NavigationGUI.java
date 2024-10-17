package src.net.jadiefication.Gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import src.net.jadiefication.API.Border;
import src.net.jadiefication.API.Heads;
import src.net.jadiefication.lobby.Lobby;

import java.net.MalformedURLException;

public class NavigationGUI implements InventoryHolder {

    private final Inventory inventory;

    public NavigationGUI(Lobby plugin) throws MalformedURLException {
        this.inventory = plugin.getServer().createInventory(this, 54, Component.text("Navigation"));
        Border.setGuiBorder(this.inventory);
        this.inventory.setItem(22, Heads.createComingSoonHead());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }
}
