package hotbarmanager.hotbarmanager.commands;

import hotbarmanager.hotbarmanager.HotbarManager;
import hotbarmanager.hotbarmanager.util.HotbarSaveUtil;
import hotbarmanager.hotbarmanager.util.HotbarSetUtil;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class HotbarCommand implements CommandExecutor {

	private final HotbarManager plugin;

	public HotbarCommand(HotbarManager plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (args.length != 2) {
			sender.sendMessage("Invalid usage. Usage: /hotbar <action> <player>");
			return true;
		}

		Player target = Bukkit.getPlayerExact(args[1]);

		if (target == null) {
			sender.sendMessage(args[0] + "is not online");
			return true;
		}

		final PersistentDataContainer data = target.getPersistentDataContainer();
		final NamespacedKey key = new NamespacedKey(plugin, "hotbaritems");

		switch (args[0]) {
			case "lock":
				if (!HotbarSetUtil.checkPlayerInSet(target.getUniqueId())) {
					HotbarSetUtil.addPlayer(target.getUniqueId());
					sender.sendMessage("Hotbar locked");
				}
				else {
					sender.sendMessage(args[1] + "already has their hotbar locked");
				}
				break;
			case "unlock":
				if (HotbarSetUtil.checkPlayerInSet(target.getUniqueId())) {
					HotbarSetUtil.removePlayer(target.getUniqueId());
					sender.sendMessage("Hotbar unlocked");
				}
				else {
					sender.sendMessage(args[1] + "already has their hotbar unlocked");
				}
				break;
			case "save":
				List<ItemStack> list = new ArrayList<>();
				for (int i = 1; i < 8; i++) {
					list.add(target.getInventory().getItem(i));
				}
				data.set(key, PersistentDataType.BYTE_ARRAY, HotbarSaveUtil.serializeHotbarItems(list));
				sender.sendMessage("Inventory saved for " + args[1]);
				break;
			case "load":
				if (data.get(key, PersistentDataType.BYTE_ARRAY) == null) {
					sender.sendMessage(args[1] + " does not have their inventory saved");
					break;
				}
				ItemStack[] items = HotbarSaveUtil.deserializeHotbarItems(data.get(key, PersistentDataType.BYTE_ARRAY));
				for (int i = 0; i < 7; i++) {
					target.getInventory().setItem(i + 1, items[i]);
				}
				sender.sendMessage("Inventory loaded for " + args[1]);
				break;
			default:
				sender.sendMessage("Action not valid. Please use the following actions: lock, unlock");

		}
		return true;
	}
}
