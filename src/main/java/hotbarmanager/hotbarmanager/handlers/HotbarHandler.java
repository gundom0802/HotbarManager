package hotbarmanager.hotbarmanager.handlers;

import hotbarmanager.hotbarmanager.HotbarManager;
import hotbarmanager.hotbarmanager.util.HotbarSetUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HotbarHandler implements Listener {
	public HotbarHandler(HotbarManager plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	public boolean checkPlace(InventoryClickEvent event) {
		return event.getAction().equals(InventoryAction.PLACE_ALL) || event.getAction().equals(InventoryAction.PLACE_SOME) || event.getAction().equals(InventoryAction.PLACE_ONE);
	}
	public boolean checkPickup(InventoryClickEvent event) {
		return event.getAction().equals(InventoryAction.PICKUP_ALL) || event.getAction().equals(InventoryAction.PICKUP_SOME) || event.getAction().equals(InventoryAction.PICKUP_HALF) || event.getAction().equals(InventoryAction.PICKUP_ONE);
	}
	@EventHandler
	public void onHotbarClick(InventoryClickEvent event) {
		if (HotbarSetUtil.checkPlayerInSet(event.getWhoClicked().getUniqueId())) {
			if (event.getClick().equals(ClickType.NUMBER_KEY) || checkPickup(event) || checkPlace(event)) {
				if ((event.getHotbarButton() >= 1 && event.getHotbarButton() < 8) || (event.getSlot() >= 1 && event.getSlot() < 8)) {
					event.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void onPickup(EntityPickupItemEvent event) {
		if (!(event.getEntity() instanceof Player pl)) {
			return;
		}
		if (HotbarSetUtil.checkPlayerInSet(pl.getUniqueId())) {
			event.setCancelled(true);
			for (int i = 9; i < 36; i++) {
				if (pl.getInventory().getItem(i) == null) {
					continue;
				}
				if ((event.getItem().getItemStack().getType() == pl.getInventory().getItem(i).getType()) && pl.getInventory().getItem(i).getMaxStackSize() > pl.getInventory().getItem(i).getAmount()) {
					event.setCancelled(false);
				}
			}
			for (int i = 9; i < 36; i++) {
				if (pl.getInventory().getItem(i) == null) {
					pl.getInventory().setItem(i, event.getItem().getItemStack());
					event.getItem().remove();
					break;
				}
			}
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (HotbarSetUtil.checkPlayerInSet(event.getPlayer().getUniqueId())) {
			event.setCancelled(true);
		}
	}
}
