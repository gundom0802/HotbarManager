package hotbarmanager.hotbarmanager.util;

import com.google.common.io.ByteStreams;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class HotbarSaveUtil {

	public static byte[] serializeHotbarItems(List<ItemStack> items) {
		ByteArrayDataOutput stream = ByteStreams.newDataOutput();
		ItemStack[] itemarray = items.toArray(new ItemStack[0]);

		for (ItemStack item : itemarray) {
			byte[] data = item == null || item.getType() == Material.AIR ? new byte[]{} : item.serializeAsBytes();

			stream.writeInt(data.length);
			stream.write(data);
		}

		stream.writeInt(-1);
		return stream.toByteArray();
	}

	public static ItemStack[] deserializeHotbarItems(byte[] bytes) {
		List<ItemStack> items = new ArrayList<>();
		ByteArrayDataInput inputStream = ByteStreams.newDataInput(bytes);
		int length = inputStream.readInt();

		while (length != -1) {
			if (length == 0) {
				items.add(null);
			} else {
				byte[] data = new byte[length];

				inputStream.readFully(data, 0, data.length);
				items.add(ItemStack.deserializeBytes(data));
			}

			length = inputStream.readInt();
		}

		return items.toArray(new ItemStack[0]);
	}

}
