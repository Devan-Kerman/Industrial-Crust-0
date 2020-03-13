package net.devtech.industrialcrust.hwla;

import net.devtech.asyncore.AsynCore;
import net.devtech.industrialcrust.util.HotbarMessager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class HWWLAManager {
	public void tick() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Block block = player.getTargetBlock(null, 5);
			if (block != null) {
				Object object = AsynCore.mainWorldAccess.get(block.getLocation());
				if (object instanceof HasHWWLAName) {
					HotbarMessager.sendHotBarMessage(player, "IC0: " + ((HasHWWLAName) object).getName());
				}
			}
		}
	}
}
