package org.glydar.glydar.models;

import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.netty.data.GEntityData;
import org.glydar.glydar.netty.data.GVector3;
import org.glydar.glydar.netty.packet.CubeWorldPacket;
import org.glydar.glydar.netty.packet.server.Packet15Seed;
import org.glydar.glydar.netty.packet.shared.Packet0EntityUpdate;
import org.glydar.glydar.netty.packet.shared.Packet10Chat;
import org.glydar.glydar.security.Account;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.permissions.Permission;
import org.glydar.paraglydar.permissions.PermissionAttachment;

public class GPlayer extends GEntity implements Player {
	private boolean connected = false;
	private boolean joined = false;
	private ChannelHandlerContext channelCtx;
	private final Account account;

	public GPlayer() {
		super();
		this.account = new Account(this);
	}

	public void setChannelContext(ChannelHandlerContext ctx) {
		this.channelCtx = ctx;
	}

	public ChannelHandlerContext getChannelContext() {
		return this.channelCtx;
	}

	public void sendPacket(CubeWorldPacket packet) {
		packet.sendTo(this);
	}

	@Override
	public Collection<Player> getPlayers() {
		Collection<Player> ret = new HashSet<Player>();
		ret.add(this);
		return ret;
	}
	
	public Account getAccount(){
		return account;
	}

	public void playerConnected(){
		connected = true;
	}
	
	public boolean isConnected(){
		return connected;
	}
	
	public boolean isJoined(){
		return joined;
	}
	
	public void playerJoined(){
		joined = true;
	}

	public void playerVerified() {
		Glydar.getServer().addEntity(entityID, this);
		account.setVerified(true);
	}

	public void playerLeft() {
		Glydar.getServer().removeEntity(entityID);
		world.removeEntity(entityID);
		forceUpdateData();
		channelCtx.close();
	}

	public String getIp() {
		return ((InetSocketAddress) channelCtx.channel().remoteAddress()).getAddress().getHostAddress();
	}

	public void sendMessage(String message) {
		this.sendPacket(new Packet10Chat(message, 0));
	}

	public void kickPlayer(String message) {
		sendMessage(message);
		playerLeft();
	}

	public void kickPlayer() {
		sendMessage("You have been kicked!");
		playerLeft();
	}
	
	@Override
	public void changeWorld(World w){
		//Temporary(?) way of removing all current models in players client!
		for (Entity e : w.getWorldEntities()) {
			GEntityData ed = new GEntityData(e.getEntityData());
			ed.setHostileType((byte) 2);
			GVector3<Long> v = new GVector3<Long>();
			v.setX((long) 0);
			v.setY((long) 0);
			v.setZ((long) 0);
			ed.setPosition(v);
			new Packet0EntityUpdate(ed).sendTo(this);
		}
		super.changeWorld(w);
		new Packet15Seed(w.getSeed()).sendTo(this);
		for (Entity e : this.getWorld().getWorldEntities()) {
			if (((GEntity) e).entityID == this.entityID) {
				continue;
			}
			this.sendPacket(new Packet0EntityUpdate(e.getEntityData()));
		}
	}

	@Override
	public boolean hasPermission(String permission) {
		return hasPermission(new Permission(permission, Permission.PermissionDefault.FALSE));
	}

	@Override
	public boolean hasPermission(Permission permission) {
		if (getAttachments() == null || getAttachments().isEmpty()) {
			switch (permission.getPermissionDefault()) {
				case TRUE:
					return true;
				case FALSE:
					return false;
				case ADMIN:
					return account.isAdmin();
				case NON_ADMIN:
					return (!account.isAdmin());
			}
		}
		for (PermissionAttachment attachment : getAttachments()) {
			if (attachment.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	public List<PermissionAttachment> getAttachments() {
		return PermissionAttachment.getAttachments(this);
	}

	public void addAttachment(PermissionAttachment attachment) {
		PermissionAttachment.addAttachment(attachment);
	}

	@Override
	public String getName() {
		return this.data.getName();
	}
}
