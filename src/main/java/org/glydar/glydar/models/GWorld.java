package org.glydar.glydar.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.protocol.server.Packet4ServerUpdate;
import org.glydar.glydar.protocol.shared.Packet10Chat;
import org.glydar.paraglydar.models.Entity;
import org.glydar.paraglydar.models.Player;
import org.glydar.paraglydar.models.World;
import org.glydar.paraglydar.models.WorldTarget;

public class GWorld implements World {
	final long worldId;
	private String name;
	private int seed;
	private boolean pvp;
	public Packet4ServerUpdate worldUpdatePacket = new Packet4ServerUpdate();;
	
	private HashMap<Long,Entity> worldEntities = new HashMap<Long,Entity>();
	
	private static long WORLD_ID = 1;
	public static long getNewWorldId() {
		return WORLD_ID++;
	}
	
	public GWorld(String name, int seed){
		this.name = name;
		this.seed = seed;
		pvp = false;
		worldId = GWorld.getNewWorldId();
		Glydar.getServer().addWorld(this);
	}
	
	public long getWorldId(){
		return worldId;
	}
	
	public int getSeed(){
		return seed;
	}
	
	//Not sure if this should be used :)
	/*public void setSeed(int seed){
		this.seed = seed;
		new Packet15Seed(seed).sendTo(new WorldTarget(this));
	}*/
	
	public String getName(){
		return name;
	}
	
	public void addEntity(long entityID, Entity e) {
		if (!worldEntities.containsKey(entityID)) {
			worldEntities.put(entityID, e);
		}
		if (pvp && e.getEntityData().getHostileType() < 3){
			e.getEntityData().setFlags1((byte) 32);
		} else {
			e.getEntityData().setFlags1((byte) 64);
		}
		e.forceUpdateData(true);
	}

	public void removeEntity(long entityID) {
		worldEntities.remove(entityID);
	}
	
	public Collection<Player> getWorldPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Entity e : worldEntities.values()) {
			if (e instanceof Player) {
				players.add((Player) e);
			}
		}
		return players;
	}

	public Collection<Entity> getWorldEntities() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		for (Entity e : worldEntities.values()) {
			entities.add(e);
		}
		return entities;
	}
	
	public void broadcastMessage(String message) {
		new Packet10Chat(message, 0).sendTo(new WorldTarget(this));
	}

	@Override
	public boolean isPVPAllowed() {
		return this.pvp;
	}
	
	public void setPVPAllowed(boolean allowPVP) {
		if (allowPVP){
			for (Entity e : getWorldEntities()){
				e.getEntityData().setFlags1((byte) 32);
				e.forceUpdateData(true);
			}
		} else {
			for (Entity e : getWorldEntities()){
				e.getEntityData().setFlags1((byte) 64);
				e.forceUpdateData(true);
			}
		}
		this.pvp = allowPVP;
	}
}
