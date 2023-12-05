package hotbarlock.hotbarlock.util;

import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HotbarSaveUtil {

	public static final Map<UUID, List<ItemStack>> PLAYER_SAVEDHOTBAR = new HashMap<>();

	public static boolean checkPlayerInMap(UUID u) {
		return PLAYER_SAVEDHOTBAR.containsKey(u);
	}

	public static ItemStack getSavedItem(UUID u, int i) {
		return PLAYER_SAVEDHOTBAR.get(u).get(i);
	}

	public static void setSavedItem(UUID u, ItemStack it) {
		if (!PLAYER_SAVEDHOTBAR.containsKey(u)) {
			PLAYER_SAVEDHOTBAR.put(u, new ArrayList<>());
		}
		PLAYER_SAVEDHOTBAR.get(u).add(it);
	}

	public static void removePlayer(UUID u) {
		PLAYER_SAVEDHOTBAR.remove(u);
	}

}
