package net.devtech.industrialcrust.util;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.Iterator;

public class Positions {
	public static Iterable<Location> touching(Location location) {
		return () -> new Iterator<Location>() {
			private int current;
			@Override
			public boolean hasNext() {
				return this.current < 6; // 6 faces on a cube. yes.
			}

			@Override
			public Location next() {
				switch (this.current++) {
					case 0:
						return location.clone().add(1, 0, 0); // up
					case 1:
						return location.clone().add(0, 1, 0); // left
					case 2:
						return location.clone().add(0, 0, 1); // up
					case 3:
						return location.clone().add(-1, 0, 0); // left
					case 4:
						return location.clone().add(0, -1, 0); // up
					case 5:
						return location.clone().add(0, 0, -1); // left
				}
				throw new IllegalStateException("no");
			}
		};
	}

	public static Iterable<BlockFace> getFaces() {
		return Arrays.asList(BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}
}
