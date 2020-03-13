package net.devtech.industrialcrust.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * simplified and optimized
 * https://bukkit.org/threads/send-hotbar-messages.440664/
 */
public class HotbarMessager {

	/**
	 * These are the Class instances. Use these to get fields or methods for
	 * classes.
	 */
	private static final Class<?> CRAFTPLAYERCLASS;

	private static final Field PLAYERCONNECTION;
	private static final Method GETHANDLE, SENDPACKET;


	/**
	 * These are the constructors for those classes. You need these to create
	 * new objects.
	 */
	private static final Constructor<?> PACKET_PLAYER_CHAT_CONSTRUCTOR,
	CHATMESSAGE_CONSTRUCTOR;
	/**
	 * Used in 1.12+. Bytes are replaced with this enum
	 */
	private static Object chatMessageType;

	/**
	 * This is the server version. This is how we know the server version.
	 */
	private static final String SERVER_VERSION;

	static {
		// This gets the server version.
		String name = Bukkit.getServer().getClass().getName();
		name = name.substring(name.indexOf("craftbukkit.")
		                      + "craftbukkit.".length());
		name = name.substring(0, name.indexOf("."));
		SERVER_VERSION = name;

		try {
			// This here sets the class fields.
			CRAFTPLAYERCLASS = Class.forName("org.bukkit.craftbukkit."
			                                 + SERVER_VERSION + ".entity.CraftPlayer");
			Class<?> packetPlayerChatClass = Class.forName("net.minecraft.server."
			                                               + SERVER_VERSION + ".PacketPlayOutChat");
			Class<?> packetClass = Class.forName("net.minecraft.server."
			                                     + SERVER_VERSION + ".Packet");
			Class<?> textComponent = Class.forName("net.minecraft.server." + SERVER_VERSION
			                                       + ".IChatBaseComponent");
			GETHANDLE = CRAFTPLAYERCLASS.getMethod("getHandle");
			PLAYERCONNECTION = GETHANDLE.getReturnType()
			                            .getField("playerConnection");
			SENDPACKET = PLAYERCONNECTION.getType().getMethod("sendPacket", packetClass);

			Constructor<?> playerChatConstructor;
			try {
				playerChatConstructor = packetPlayerChatClass
				                        .getConstructor(textComponent, byte.class);
			} catch (NoSuchMethodException e) {
				Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server."
				                                              + SERVER_VERSION + ".ChatMessageType");
				chatMessageType = chatMessageTypeClass
				                  .getEnumConstants()[2];

				playerChatConstructor = packetPlayerChatClass
				                        .getConstructor(textComponent, chatMessageTypeClass);
			}

			PACKET_PLAYER_CHAT_CONSTRUCTOR = playerChatConstructor;

			Class<?> chatMessage = Class.forName("net.minecraft.server."
			                                     + SERVER_VERSION + ".ChatMessage");

			CHATMESSAGE_CONSTRUCTOR = chatMessage.getConstructor(
			String.class, Object[].class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sends the hotbar message 'message' to the player 'player'
	 *
	 * @param player the player
	 * @param message the message
	 */
	public static void sendHotBarMessage(Player player, String message) {
		try {
			// This creates the IChatComponentBase instance
			Object icb = CHATMESSAGE_CONSTRUCTOR.newInstance(message,
			                                                 new Object[0]);
			// This creates the packet
			Object packet;
			try {
				packet = PACKET_PLAYER_CHAT_CONSTRUCTOR.newInstance(icb,
				                                                    (byte) 2);
			} catch (Exception e) {
				packet = PACKET_PLAYER_CHAT_CONSTRUCTOR.newInstance(icb,
				                                                    chatMessageType);
			}
			// This casts the player to a craftplayer
			Object craftplayerInst = CRAFTPLAYERCLASS.cast(player);
			// This invokes the method above.
			Object methodhHandle = GETHANDLE.invoke(craftplayerInst);
			// This gets the player's connection
			Object playerConnection = PLAYERCONNECTION.get(methodhHandle);
			// This sends the packet.
			SENDPACKET
			.invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}