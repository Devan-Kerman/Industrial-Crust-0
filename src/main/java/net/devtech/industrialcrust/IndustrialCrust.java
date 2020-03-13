package net.devtech.industrialcrust;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.AbstractBlock;
import net.devtech.asyncore.items.CustomItem;
import net.devtech.asyncore.items.CustomItemFactory;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.industrialcrust.blocks.machines.batteries.BatteryBocs;
import net.devtech.industrialcrust.blocks.machines.cables.CopperCable;
import net.devtech.industrialcrust.blocks.machines.casings.BasicMachineCasing;
import net.devtech.industrialcrust.blocks.machines.generators.CoalGenerator;
import net.devtech.industrialcrust.blocks.ores.CopperOreBlock;
import net.devtech.industrialcrust.blocks.ores.LeadOreBlock;
import net.devtech.industrialcrust.blocks.ores.TinOreBlock;
import net.devtech.industrialcrust.blocks.ores.UraniumOreBlock;
import net.devtech.industrialcrust.hwla.HWWLAManager;
import net.devtech.industrialcrust.items.battery.BasicBattery;
import net.devtech.industrialcrust.items.ingots.*;
import net.devtech.industrialcrust.items.interfaces.CanConsume;
import net.devtech.industrialcrust.items.materials.Resin;
import net.devtech.industrialcrust.items.materials.Scrap;
import net.devtech.industrialcrust.items.plates.*;
import net.devtech.industrialcrust.items.tools.BukkitHammer;
import net.devtech.industrialcrust.items.tools.TreeTap;
import net.devtech.industrialcrust.persistent.EmptyBlockPersistent;
import net.devtech.industrialcrust.persistent.GsonPersistent;
import net.devtech.utilib.functions.ThrowingSupplier;
import net.devtech.yajslib.persistent.AnnotatedPersistent;
import net.devtech.yajslib.persistent.Persistent;
import net.devtech.yajslib.persistent.PersistentRegistry;
import net.devtech.yajslib.persistent.util.EmptyPersistent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.lang.reflect.Constructor;

public final class IndustrialCrust extends JavaPlugin implements Listener {
	public static IndustrialCrust instance;
	private final HWWLAManager hwwlaManager = new HWWLAManager();

	@Override
	public void onEnable() {
		instance = this;
		this.getServer().getPluginManager().registerEvents(this, this);
		try {
			AsynCore.itemEventManager
			.register(PlayerItemConsumeEvent.class, PlayerItemConsumeEvent::getItem, i -> i instanceof CanConsume,
			          CanConsume::consume, EventPriority.NORMAL, false);
			// ores
			registerEmptyBlock(CopperOreBlock.class, 492309209L);
			registerEmptyBlock(LeadOreBlock.class, 4309209309L);
			registerEmptyBlock(TinOreBlock.class, 9538949209309L);
			registerEmptyBlock(UraniumOreBlock.class, 6092094309L);
			// materials
			registerEmpty(Resin.class, 582093209L);
			registerEmpty(Scrap.class, 48393209209L);
			// tools
			registerEmpty(TreeTap.class, 39020910L);
			registerEmpty(BukkitHammer.class, 5093092091209L);
			// ingots
			registerEmpty(BronzeIngot.class, 9032094093209L);
			registerEmpty(CopperIngot.class, 4832093209L);
			registerEmpty(LeadIngot.class, 389209209);
			registerEmpty(RefinedIronIngot.class, 5934092309L);
			registerEmpty(SilverIngot.class, 5893093209209290L);
			registerEmpty(TinIngot.class, 5409430932090932L);
			// plates
			registerEmpty(BronzePlate.class, 493092920L);
			registerEmpty(CopperPlate.class, 48939299009L);
			registerEmpty(GoldPlate.class, 80909320939L);
			registerEmpty(IronPlate.class, 439209032L);
			registerEmpty(RefinedIronIngot.class, 89483828989328L);
			registerEmpty(TinPlate.class, 9040930939209L);
			// machines
			registerEmptyBlock(BasicMachineCasing.class, 5438922932L);
			registerAnnotatedBlock(BatteryBocs.class, 3435434544343433L);
			registerAnnotatedBlock(CopperCable.class, 54564634563487548L);
			registerAnnotatedBlock(CoalGenerator.class, 895695095093409409L);
			// batteries
			registerGson(BasicBattery.class, 490328323289892L);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		// hwla manager
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.hwwlaManager::tick, 2, 8);
		// Plugin startup logic
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		this.give(player, BatteryBocs.class);
		this.give(player, CopperCable.class);
		this.give(player, CoalGenerator.class);
		this.give(player, BasicBattery.class);
	}

	private void give(Player player, Class<? extends CustomItem> item) {
		ItemStack stack = CustomItemFactory.createNew(AsynCore.persistentRegistry, item);
		System.out.println((Object) CustomItemFactory.from(stack, AsynCore.persistentRegistry));
		player.getInventory().addItem(stack);
	}

	private static <T extends AbstractBlock> void registerEmptyBlock(Class<T> type, long versionHash) throws NoSuchMethodException {
		Constructor<T> constructor = type.getDeclaredConstructor(PersistentRegistry.class, ServerAccess.class);
		if (!constructor.isAccessible())
			constructor.setAccessible(true);
		register(type, new EmptyBlockPersistent<>(constructor::newInstance, versionHash));
	}

	private static <T> void registerAnnotatedBlock(Class<T> type, long versionHash) throws NoSuchMethodException {
		Constructor<T> constructor = type.getDeclaredConstructor(PersistentRegistry.class, ServerAccess.class);
		if (!constructor.isAccessible())
			constructor.setAccessible(true);
		register(type, new AnnotatedPersistent<>(() -> constructor
		                                               .newInstance(AsynCore.persistentRegistry,
		                                                            AsynCore.mainWorldAccess), type, versionHash));
	}

	private static <T> void registerEmpty(Class<T> type, long versionHash) {
		register(type, new EmptyPersistent<>(versionHash, (ThrowingSupplier<T>) type::newInstance));
	}

	private static <T> void register(Class<T> type, Persistent<T> persistent) {
		AsynCore.persistentRegistry.register(type, persistent);
	}

	private static <T> void registerGson(Class<T> type, long versionHash) {
		AsynCore.persistentRegistry.register(type, new GsonPersistent<>(versionHash, type));
	}
}
