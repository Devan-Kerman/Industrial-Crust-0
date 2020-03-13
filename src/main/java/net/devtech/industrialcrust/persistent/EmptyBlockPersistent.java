package net.devtech.industrialcrust.persistent;

import net.devtech.asyncore.AsynCore;
import net.devtech.asyncore.blocks.AbstractBlock;
import net.devtech.asyncore.world.server.ServerAccess;
import net.devtech.utilib.functions.ThrowingBiFunction;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.Persistent;
import net.devtech.yajslib.persistent.PersistentRegistry;
import org.bukkit.Location;
import java.io.IOException;
import java.util.function.BiFunction;

public class EmptyBlockPersistent<T extends AbstractBlock> implements Persistent<T> {
	private final long versionHash;
	private final BiFunction<PersistentRegistry, ServerAccess<Object>, T> function;

	public EmptyBlockPersistent(ThrowingBiFunction<PersistentRegistry, ServerAccess<Object>, T> function, long hash) {
		this.versionHash = hash;
		this.function = function;
	}

	@Override
	public long versionHash() {
		return this.versionHash;
	}

	@Override
	public void write(T t, PersistentOutput output) throws IOException {
		output.writePersistent(t.getLocation());
	}

	@Override
	public T read(PersistentInput input) throws IOException {
		T blank = this.blank();
		blank.setLocation((Location) input.readPersistent());
		return blank;
	}

	@Override
	public T blank() {
		return this.function.apply(AsynCore.persistentRegistry, AsynCore.mainWorldAccess);
	}
}
