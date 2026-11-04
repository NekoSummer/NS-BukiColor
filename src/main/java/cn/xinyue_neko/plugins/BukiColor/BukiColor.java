package cn.xinyue_neko.plugins.BukiColor;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class BukiColor extends JavaPlugin implements Listener {

    private static final String TITLE = "[BukiColor] ";

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        getServer().getConsoleSender().sendMessage(TITLE + "plugin: " + getDescription().getName() + " v" + getDescription().getVersion());
        getServer().getConsoleSender().sendMessage(TITLE + "Created by" + ChatColor.AQUA + getDescription().getAuthors() + ChatColor.RESET);
        getServer().getConsoleSender().sendMessage(TITLE + ChatColor.AQUA + "This plugin is a backward-compatible version of \"AnvilColors\"");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onAnvilUSE(PrepareAnvilEvent ev) {
        HumanEntity human = ev.getView().getPlayer();
        if (!(human instanceof Player)) return;
        Player pl = (Player) human;

        AnvilInventory anvil = ev.getInventory();
        String renameText = anvil.getRenameText();

        ItemStack res = ev.getResult();
        if (res == null) return;
        if (renameText == null || renameText.trim().isEmpty()) return;
        if (!renameText.contains("&")) return;

        if (
                !pl.hasPermission("bukicolor.uses")
                && getConfig().getBoolean("enablePermission", true)
        ) return;

        ItemStack out = res.clone();
        ItemMeta meta = out.getItemMeta();
        if (meta == null) return;

        String colored = ChatColor.translateAlternateColorCodes('&', renameText);
        meta.setDisplayName(ChatColor.RESET + colored);
        out.setItemMeta(meta);

        ev.setResult(out);
    }
}
