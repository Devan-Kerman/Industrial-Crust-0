package net.devtech.industrialcrust.items.battery;

import net.devtech.asyncore.items.CustomItem;
import net.devtech.industrialcrust.items.interfaces.ItemPowerHolder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import java.util.Collections;

public class BasicBattery implements CustomItem, ItemPowerHolder {
	private static final ItemStack BASIC_BATTERY = new ItemStack(Material.POTION);
	static {
		PotionMeta meta = (PotionMeta) BASIC_BATTERY.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE+"Basic Battery");
		meta.setLore(Collections.singletonList(getChargeFormat(0)));
		meta.setColor(Color.BLACK);
		BASIC_BATTERY.setItemMeta(meta);
	}

	private int power;

	@Override
	public ItemStack createBaseStack() {
		return BASIC_BATTERY;
	}

	@Override
	public int getMaxPower() {
		return 1023;
	}

	@Override
	public int getPower() {
		return this.power;
	}

	@Override
	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public void transform(ItemStack stack) {
		PotionMeta meta = (PotionMeta) stack.getItemMeta();
		meta.setColor(Color.fromRGB(255-this.power/4, this.power/4, 0));
		meta.setLore(Collections.singletonList(getChargeFormat(this.power/((float)this.getMaxPower()))));
		stack.setItemMeta(meta);
	}

	private static final String CHARGE_FORMAT = "%sCharge: %3.3f%%";
	protected static String getChargeFormat(float percentage) {
		ChatColor color;
		if(percentage < .25) {
			color = ChatColor.RED;
		} else if(percentage < .5) {
			color = ChatColor.GOLD;
		} else if(percentage < .75) {
			color = ChatColor.YELLOW;
		} else
			color = ChatColor.GREEN;
		return String.format(CHARGE_FORMAT, color, percentage*100);
	}
}
