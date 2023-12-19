package hotbarmanager.hotbarmanager.util;

import java.util.Set;
import java.util.UUID;
import java.util.HashSet;

public class HotbarSetUtil {
	public final static Set<UUID> HOTBARLOCK_PLAYERS = new HashSet<UUID>();

	public static boolean checkPlayerInSet(UUID u) {
		return HOTBARLOCK_PLAYERS.contains(u);
	}

	public static void addPlayer(UUID u) {
		HOTBARLOCK_PLAYERS.add(u);
	}

	public static void removePlayer(UUID u) {
		HOTBARLOCK_PLAYERS.remove(u);
	}
}
