package net.devtech.industrialcrust.gui;

import net.devtech.asyncore.gui.components.AComponent;
import net.devtech.asyncore.gui.components.APanel;
import net.devtech.asyncore.gui.graphics.InventoryGraphics;
import net.devtech.asyncore.gui.graphics.NestedGraphics;
import net.devtech.asyncore.util.Size2i;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.awt.Point;
import java.util.Arrays;

public class ABackgroundPanel extends APanel {
	private final boolean[] drawn;
	private final ItemStack background;
	public ABackgroundPanel(Size2i size2i, ItemStack background) {
		super(size2i);
		this.drawn = new boolean[size2i.getWidth() * size2i.getHeight()];
		this.background = background;
	}

	public ABackgroundPanel(Size2i size, Material material) {
		this(size, new ItemStack(material));
	}

	@Override
	public void draw(InventoryGraphics inventory) {
		for (int x = 0; x < this.size.getWidth(); x++) {
			for (int y = 0; y < this.size.getHeight(); y++) {
				inventory.setItem(this.background, x, y);
			}
		}
		for(int i = 0; i < this.components.size(); ++i) {
			AComponent component = this.components.get(i);
			Point bounds = this.locations.get(i);
			InventoryGraphics graphics = new NestedGraphics(inventory, bounds.x, bounds.y);
			Size2i size = component.getSize();
			for (int x = 0; x < size.getWidth(); x++) { // undraw before draw
				for (int y = 0; y < size.getHeight(); y++) {
					graphics.setItem(null, x, y);
				}
			}
			component.draw(graphics);
		}
	}
}
