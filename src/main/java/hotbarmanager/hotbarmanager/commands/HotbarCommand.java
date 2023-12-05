package hotbarlock.hotbarlock.commands;

import hotbarlock.hotbarlock.util.HotbarSaveUtil;
import hotbarlock.hotbarlock.util.HotbarSetUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HotbarCommand implements CommandExecutor {
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
				if (!HotbarSaveUtil.checkPlayerInMap(target.getUniqueId())) {
					for (int i = 1; i < 8; i++) {
						HotbarSaveUtil.setSavedItem(target.getUniqueId(), target.getInventory().getItem(i));
					}
					sender.sendMessage("Inventory saved for " + target.getName());
				}
				else {
					sender.sendMessage(target.getName() + "already has their inventory saved");
				}
				break;
			case "load":
				if (HotbarSaveUtil.checkPlayerInMap(target.getUniqueId())) {
					for (int i = 0; i < 7; i++) {
						if (HotbarSaveUtil.getSavedItem(target.getUniqueId(), i) == null) {
							target.getInventory().setItem(i + 1, new ItemStack(Material.AIR));
						}
						target.getInventory().setItem(i + 1, HotbarSaveUtil.getSavedItem(target.getUniqueId(), i));
					}
					HotbarSaveUtil.removePlayer(target.getUniqueId());
					sender.sendMessage("Inventory loaded for " + target.getName());
				}
				else {
					sender.sendMessage(target.getName() + " does not have their inventory saved");
				}
				break;
			default:
				sender.sendMessage("Action not valid. Please use the following actions: lock, unlock");

		}
		return true;
	}
}
