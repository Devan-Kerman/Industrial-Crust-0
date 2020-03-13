package net.devtech.industrialcrust.persistent;

import com.google.gson.Gson;
import net.devtech.yajslib.io.PersistentInput;
import net.devtech.yajslib.io.PersistentOutput;
import net.devtech.yajslib.persistent.Persistent;
import java.io.IOException;
import java.lang.reflect.Type;

public class GsonPersistent<T> implements Persistent<T> {
	private static final Gson GSON = new Gson();
	private final long versionHash;
	private final Class<T> type;

	public GsonPersistent(long hash, Class<T> type) {
		this.versionHash = hash;
		this.type = type;
	}

	@Override
	public long versionHash() {
		return this.versionHash;
	}

	@Override
	public void write(T t, PersistentOutput output) throws IOException {
		output.writePersistent(GSON.toJson(t));
	}

	@Override
	public T read(PersistentInput input) throws IOException {
		return GSON.fromJson((String) input.readPersistent(), (Type) this.type);
	}

	@Override
	public T blank() {
		return GSON.fromJson("{}", this.type);
	}
}
