package net.devtech.industrialcrust.items.interfaces;

import org.bukkit.event.player.PlayerItemConsumeEvent;

public interface CanConsume {
	void consume(PlayerItemConsumeEvent event);
}
