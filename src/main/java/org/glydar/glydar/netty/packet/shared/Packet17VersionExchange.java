package org.glydar.glydar.netty.packet.shared;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.models.GPlayer;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet15Seed;
import org.glydar.glydar.netty.packet.server.Packet16Join;
import org.glydar.glydar.util.Versioning;

@CubeWorldPacket.Packet(id = 17)
public class Packet17VersionExchange extends CubeWorldPacket {
	int version;

	public Packet17VersionExchange() {

	}

	public Packet17VersionExchange(int serverVersion) {
		this.version = serverVersion;
	}

	@Override
	protected void internalDecode(ByteBuf buf) {
		version = buf.readInt();
	}

	@Override
	public void receivedFrom(GPlayer ply) {
		ply.sendPacket(new Packet16Join(ply.entityID));
		ply.sendPacket(new Packet15Seed(ply.getWorld().getSeed()));
		ply.sendMessage("Server powered by Glydar " + Versioning.getParaGlydarVersion());
	}

	@Override
	protected void internalEncode(ByteBuf buf) {
		buf.writeInt(version);
	}
}
