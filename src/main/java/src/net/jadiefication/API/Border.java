package src.net.jadiefication.API;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class Border {

    public static void setGuiBorder(@NotNull Inventory inventory) {
        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(" "));
        item.setItemMeta(meta);

        int size = inventory.getSize();
        int height = size / 9;
        // Top border
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }

        // Bottom border
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }

        // Left and right borders
        for (int i = 1; i < height - 1; i++) {
            inventory.setItem(i * 9, item); // Left border
            inventory.setItem(i * 9 + 8, item); // Right border
        }
    }
}
