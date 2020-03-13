package net.devtech.industrialcrust.gui;

import net.devtech.asyncore.gui.components.AComponent;
import net.devtech.asyncore.gui.graphics.InventoryGraphics;
import net.devtech.asyncore.util.Size2i;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;

public class AEnergyBar implements AComponent {
	private final ItemStack fill;
	private final ItemStack empty;
	private final ItemStack icon;
	private final int rows;
	private final Size2i size2i;
	private final IntSupplier energy;
	private final IntSupplier max;

	public AEnergyBar(ItemStack fill, ItemStack empty, @Nullable ItemStack icon, IntSupplier energy, IntSupplier max, int rows) {
		this.fill = fill;
		this.empty = empty;
		this.icon = icon;
		this.rows = rows;
		this.energy = energy;
		this.max = max;
		this.size2i = new Size2i(rows, 1);
	}

	public double getCurrentProgress() {
		return this.energy.getAsInt() / (double) this.max.getAsInt();
	}

	@Override public void draw(InventoryGraphics inventory) {
		int len = this.rows;
		if (this.icon != null) {
			--len;
		}

		double progress = this.energy.getAsInt() / (double) this.max.getAsInt();
		int filled = Math.min((int) Math.round((double) len * progress), this.rows);
		ItemStack fillClone = this.displayProgress(this.fill);

		for (int i = 0; i < filled; ++i) {
			inventory.setItem(fillClone, i, 0);
		}

		ItemStack emptyClone = this.displayProgress(this.empty);

		for (int i = filled; i < len; ++i) {
			inventory.setItem(emptyClone, i, 0);
		}

		if (this.icon != null) {
			inventory.setItem(this.displayProgress(this.icon), this.rows - 1, 0);
		}

	}

	@Override public boolean attemptAdd(Point point, ItemStack add) {
		return true;
	}

	@Override public boolean attemptTake(Point point, ItemStack stack) {
		return true;
	}

	@Override public boolean attemptSwap(Point point, ItemStack add, ItemStack take) {
		return true;
	}

	@Override public Size2i getSize() {
		return this.size2i;
	}

	protected ItemStack displayProgress(ItemStack stack) {
		double progress = this.energy.getAsInt() / (double) this.max.getAsInt();
		ItemStack clone = stack.clone();
		ItemMeta meta = clone.getItemMeta();
		List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList();
		lore.add(String.format("%s%d/%d", getColor(progress), this.energy.getAsInt(), this.max.getAsInt()));
		if (progress > 0.75D) {
			clone.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		}

		meta.setLore(lore);
		clone.setItemMeta(meta);
		return clone;
	}

	protected static ChatColor getColor(double progress) {
		if (progress < 0.25D) {
			return ChatColor.RED;
		} else if (progress < 0.5D) {
			return ChatColor.GOLD;
		} else if (progress < 0.75D) {
			return ChatColor.YELLOW;
		} else {
			return progress < 1.0D ? ChatColor.GREEN : ChatColor.WHITE;
		}
	}
}
