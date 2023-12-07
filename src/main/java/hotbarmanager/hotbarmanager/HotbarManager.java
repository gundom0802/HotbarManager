package hotbarmanager.hotbarmanager;

import hotbarmanager.hotbarmanager.commands.HotbarCommand;
import hotbarmanager.hotbarmanager.handlers.HotbarHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class HotbarManager extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("hotbar").setExecutor(new HotbarCommand(this));

        new HotbarHandler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
