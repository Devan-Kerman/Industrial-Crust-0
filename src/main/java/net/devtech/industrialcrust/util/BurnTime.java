package net.devtech.industrialcrust.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BurnTime {
	public static int getBurnTime(Material stack) {
		switch (stack) {
			case LAVA_BUCKET:
				return 20000;
			case COAL_BLOCK:
				return 16000;
			case BLAZE_ROD:
				return 2400;
			case COAL:
				return 1600;
			case BOAT_BIRCH:
			case BOAT_ACACIA:
			case BOAT_JUNGLE:
			case BOAT_SPRUCE:
			case BOAT_DARK_OAK:
			case BOAT:
				return 1200;
			case WOOD:
			case LOG:
			case LOG_2:
			case WOOD_PLATE:
			case FENCE:
			case FENCE_GATE:
			case WOOD_STAIRS:
			case BRICK_STAIRS:
			case ACACIA_STAIRS:
			case DARK_OAK_STAIRS:
			case BIRCH_WOOD_STAIRS:
			case JUNGLE_WOOD_STAIRS:
			case SPRUCE_WOOD_STAIRS:
			case TRAP_DOOR:
			case WORKBENCH:
			case BOOKSHELF:
			case CHEST:
			case TRAPPED_CHEST:
			case DAYLIGHT_DETECTOR:
			case DAYLIGHT_DETECTOR_INVERTED:
			case JUKEBOX:
			case NOTE_BLOCK:
			case HUGE_MUSHROOM_1:
			case HUGE_MUSHROOM_2:
			case BANNER:
			case BOW:
			case FISHING_ROD:
			case LADDER:
			case WOOD_BUTTON:
			case WOOD_DOUBLE_STEP:
				return 300;
			case WOODEN_DOOR:
			case WOOD_AXE:
			case WOOD_HOE:
			case WOOD_DOOR:
			case WOOD_STEP:
			case WOOD_SPADE:
			case WOOD_SWORD:
			case WOOD_PICKAXE:
			case BOWL:
				return 200;
			case SAPLING:
			case STICK:
			case WOOL:
				return 100;
			case CARPET:
				return 67;
			default:
				return 0;
		}
	}
}
