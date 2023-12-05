package hotbarlock.hotbarlock;

import hotbarlock.hotbarlock.commands.HotbarCommand;
import hotbarlock.hotbarlock.handlers.HotbarHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class HotbarLock extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("hotbar").setExecutor(new HotbarCommand());

        new HotbarHandler(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
