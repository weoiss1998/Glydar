package org.glydar.glydar.netty.data;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;

import org.glydar.glydar.Glydar;
import org.glydar.glydar.util.Bitops;
import org.glydar.paraglydar.data.Appearance;
import org.glydar.paraglydar.data.EntityData;
import org.glydar.paraglydar.data.Item;
import org.glydar.paraglydar.data.Vector3;

import sun.security.util.BitArray;

/* Structures and data discovered by mat^2 (http://github.com/matpow2) */

@SuppressWarnings("restriction")
public class GEntityData implements BaseData, EntityData {

	//TODO: This is temporary xD
	public static byte[] FULL_BITMASK;

	private long id;
	private byte[] bitmask = new byte[8];

	private GVector3<Long> position;

	private float roll;
	private float pitch;
	private float yaw;

	private GVector3<Float> velocity;

	private GVector3<Float> accel;

	private GVector3<Float> extraVel;

	private float lookPitch;
	private long physicsFlags; //Uint
	private byte hostileType;
	private long entityType; //Uint
	private byte currentMode;
	private long lastShootTime; //Uint
	private long hitCounter; //Uint
	private long lastHitTime; //Uint
	private GAppearance app;
	private byte flags1;
	private byte flags2;
	private long rollTime; //Uint
	private int stunTime;
	private long slowedTime; //Uint
	private long makeBlueTime; //Uint
	private long speedUpTime; //Uint
	private float slowPatchTime;
	private byte classType;
	private byte specialization;
	private float chargedMP;

	private GVector3<Float> rayHit;

	private float HP;
	private float MP;

	private float blockPower;
	private float maxHPMultiplier;
	private float shootSpeed;
	private float damageMultiplier;
	private float armorMultiplier;
	private float resistanceMultiplier;
	private long level;  //Uint
	private long currentXP; //Uint
	private GItem itemData;
	private GItem[] equipment;

	private long iceBlockFour; //Uint
	private long[] skills;
	private String name;

	private long na1; //Uint
	private long na2; // |
	private byte na3;
	private long na4;
	private long na5;
	private long nu1;
	private long nu2;
	private long nu3;
	private long nu4;
	private long nu5;
	private long nu6;
	private byte nu7;
	private byte nu8;
	private long parentOwner;
	private long nu11;
	private long nu12;
	private long nu13;
	private long nu14;
	private long nu15;
	private long nu16;
	private long nu17;
	private long nu18;
	private long nu20;
	private long nu21;
	private long nu22;
	private byte nu19;

	private int debugCap;

	public GEntityData() {
		bitmask = new byte[8];
		position = new GVector3<Long>();
		velocity = new GVector3<Float>();
		accel = new GVector3<Float>();
		extraVel = new GVector3<Float>();
		rayHit = new GVector3<Float>();
		app = new GAppearance();
		itemData = new GItem();
		equipment = new GItem[13];
		for (int i = 0; i < 13; i++)
			equipment[i] = new GItem();

		skills = new long[11];
	}

	public GEntityData(EntityData e) {
		this.id = e.getId();
		this.bitmask = e.getBitmask();
		this.position = new GVector3<Long>(e.getPosition());
		this.roll = e.getRoll();
		this.pitch = e.getPitch();
		this.yaw = e.getYaw();
		this.velocity = new GVector3<Float>(e.getVelocity());
		this.accel = new GVector3<Float>(e.getAccel());
		this.extraVel = new GVector3<Float>(e.getExtraVel());
		this.lookPitch = e.getLookPitch();
		this.physicsFlags = e.getPhysicsFlags();
		this.hostileType = e.getHostileType();
		this.entityType = e.getEntityType();
		this.currentMode = e.getCurrentMode();
		this.lastShootTime = e.getLastShootTime();
		this.hitCounter = e.getHitCounter();
		this.lastHitTime = e.getLastHitTime();
		this.app = new GAppearance(e.getApp());
		this.flags1 = e.getFlags1();
		this.flags2 = e.getFlags2();
		this.rollTime = e.getRollTime();
		this.stunTime = e.getStunTime();
		this.slowedTime = e.getSlowedTime();
		this.makeBlueTime = e.getMakeBlueTime();
		this.speedUpTime = e.getSpeedUpTime();
		this.slowPatchTime = e.getSlowPatchTime();
		this.classType = e.getClassType();
		this.specialization = e.getSpecialization();
		this.chargedMP = e.getChargedMP();
		this.rayHit = new GVector3<Float>(e.getRayHit());
		HP = e.getHP();
		MP = e.getMP();
		this.blockPower = e.getBlockPower();
		this.maxHPMultiplier = e.getMaxHPMultiplier();
		this.shootSpeed = e.getShootSpeed();
		this.damageMultiplier = e.getDamageMultiplier();
		this.armorMultiplier = e.getArmorMultiplier();
		this.resistanceMultiplier = e.getResistanceMultiplier();
		this.level = e.getLevel();
		this.currentXP = e.getCurrentXP();
		this.itemData = new GItem(e.getItemData());
		this.equipment = new GItem[e.getEquipment().length];
		for (int j = 0; j < e.getEquipment().length; j++) {
			this.equipment[j] = new GItem(e.getEquipment()[j]);
		}
		this.iceBlockFour = e.getIceBlockFour();
		this.skills = e.getSkills();
		this.name = e.getName();
		this.na1 = e.getNa1();
		this.na2 = e.getNa2();
		this.na3 = e.getNa3();
		this.na4 = e.getNa4();
		this.na5 = e.getNa5();
		this.nu1 = e.getNu1();
		this.nu2 = e.getNu2();
		this.nu3 = e.getNu3();
		this.nu4 = e.getNu4();
		this.nu5 = e.getNu5();
		this.nu6 = e.getNu6();
		this.nu7 = e.getNu7();
		this.nu8 = e.getNu8();
		this.parentOwner = e.getParentOwner();
		this.nu11 = e.getNu11();
		this.nu12 = e.getNu12();
		this.nu13 = e.getNu13();
		this.nu14 = e.getNu14();
		this.nu15 = e.getNu15();
		this.nu16 = e.getNu16();
		this.nu17 = e.getNu17();
		this.nu18 = e.getNu18();
		this.nu20 = e.getNu20();
		this.nu21 = e.getNu21();
		this.nu22 = e.getNu22();
		this.nu19 = e.getNu19();
		this.debugCap = e.getDebugCap();
	}

	@Override
	@SuppressWarnings("restriction")
	public void decode(ByteBuf buf) {
		id = buf.readLong();
		buf.readBytes(bitmask);
		BitArray bitArray = new BitArray(8 * bitmask.length, Bitops.flipBits(bitmask)); //Size in bits, byte[]
		//BitArray bitArray = new BitArray(bitmask);

		if (bitArray.get(0)) {
			position.decode(buf, Long.class);
		}
		if (bitArray.get(1)) {
			pitch = buf.readFloat();
			roll = buf.readFloat();
			yaw = buf.readFloat();
		}
		if (bitArray.get(2)) {
			velocity.decode(buf, Float.class);
		}
		if (bitArray.get(3)) {
			accel.decode(buf, Float.class);
		}
		if (bitArray.get(4)) {
			extraVel.decode(buf, Float.class);
		}
		if (bitArray.get(5)) {
			lookPitch = buf.readFloat();
		}
		if (bitArray.get(6)) {
			physicsFlags = buf.readUnsignedInt();
		}
		if (bitArray.get(7)) {
			hostileType = buf.readByte();
		}
		if (bitArray.get(8)) {
			entityType = buf.readUnsignedInt();
		}
		if (bitArray.get(9)) {
			currentMode = buf.readByte();
		}
		if (bitArray.get(10)) {
			lastShootTime = buf.readUnsignedInt();
		}
		if (bitArray.get(11)) {
			hitCounter = buf.readUnsignedInt();
		}
		if (bitArray.get(12)) {
			lastHitTime = buf.readUnsignedInt();
		}
		if (bitArray.get(13)) {
			app.decode(buf);
		}
		if (bitArray.get(14)) {
			flags1 = buf.readByte();
			flags2 = buf.readByte();
		}
		if (bitArray.get(15)) {
			rollTime = buf.readUnsignedInt();
		}
		if (bitArray.get(16)) {
			stunTime = buf.readInt();
		}
		if (bitArray.get(17)) {
			slowedTime = buf.readUnsignedInt();
		}
		if (bitArray.get(18)) {
			makeBlueTime = buf.readUnsignedInt();
		}
		if (bitArray.get(19)) {
			speedUpTime = buf.readUnsignedInt();
		}
		if (bitArray.get(20)) {
			slowPatchTime = buf.readFloat();
		}
		if (bitArray.get(21)) {
			classType = buf.readByte();
		}
		if (bitArray.get(22)) {
			specialization = buf.readByte();
		}
		if (bitArray.get(23)) {
			chargedMP = buf.readFloat();
		}
		if (bitArray.get(24)) {
			nu1 = buf.readUnsignedInt();
			nu2 = buf.readUnsignedInt();
			nu3 = buf.readUnsignedInt();
		}
		if (bitArray.get(25)) {
			nu4 = buf.readUnsignedInt();
			nu5 = buf.readUnsignedInt();
			nu6 = buf.readUnsignedInt();
		}
		if (bitArray.get(26)) {
			rayHit.decode(buf, Float.class);
		}
		if (bitArray.get(27)) {
			HP = buf.readFloat();
		}
		if (bitArray.get(28)) {
			MP = buf.readFloat();
		}
		if (bitArray.get(29)) {
			blockPower = buf.readFloat();
		}
		if (bitArray.get(30)) {
			maxHPMultiplier = buf.readFloat();
			shootSpeed = buf.readFloat();
			damageMultiplier = buf.readFloat();
			armorMultiplier = buf.readFloat();
			resistanceMultiplier = buf.readFloat();
		}
		if (bitArray.get(31)) {
			nu7 = buf.readByte();
		}
		if (bitArray.get(32)) {
			nu8 = buf.readByte();
		}
		if (bitArray.get(33)) {
			level = buf.readUnsignedInt();
		}
		if (bitArray.get(34)) {
			currentXP = buf.readUnsignedInt();
		}
		if (bitArray.get(35)) {
			parentOwner = buf.readLong();
		}
		if (bitArray.get(36)) {
			na1 = buf.readUnsignedInt();
			na2 = buf.readUnsignedInt();
		}
		if (bitArray.get(37)) {
			na3 = buf.readByte();
		}
		if (bitArray.get(38)) {
			na4 = buf.readUnsignedInt();
		}
		if (bitArray.get(39)) {
			na5 = buf.readUnsignedInt();
			nu11 = buf.readUnsignedInt();
			nu12 = buf.readUnsignedInt();
		}
		if (bitArray.get(40)) {
			nu13 = buf.readUnsignedInt();
			nu14 = buf.readUnsignedInt();
			nu15 = buf.readUnsignedInt();
			nu16 = buf.readUnsignedInt();
			nu17 = buf.readUnsignedInt();
			nu18 = buf.readUnsignedInt();
		}
		if (bitArray.get(41)) {
			nu20 = buf.readUnsignedInt();
			nu21 = buf.readUnsignedInt();
			nu22 = buf.readUnsignedInt();
		}
		if (bitArray.get(42)) {
			nu19 = buf.readByte();
		}
		if (bitArray.get(43)) {
			itemData.decode(buf);
		}
		if (bitArray.get(44)) {
			for (int i = 0; i < 13; i++) {
				GItem item = new GItem();
				item.decode(buf);
				equipment[i] = item;
			}
		}
		if (bitArray.get(45)) {
			name = new String(buf.readBytes(16).array(), Charsets.US_ASCII).trim();
		}
		if (bitArray.get(46)) {
			for (int i = 0; i < 11; i++) {
				skills[i] = buf.readUnsignedInt();
			}
		}
		if (bitArray.get(47)) {
			iceBlockFour = buf.readUnsignedInt();
		}

		debugCap = buf.capacity();
		buf.resetReaderIndex();
		buf.resetWriterIndex();

	}


	@SuppressWarnings("restriction")
	@Override
	public void encode(ByteBuf buf) {
		buf.writeLong(id); //Ulong but whatever
		BitArray bitArray = new BitArray(8 * bitmask.length, bitmask); //Size in bits, byte[]
		buf.writeBytes(bitmask);

		if (bitArray.get(0)) {
			position.encode(buf, Long.class);
		}
		if (bitArray.get(1)) {
			buf.writeFloat(pitch);
			buf.writeFloat(roll);
			buf.writeFloat(yaw);
		}
		if (bitArray.get(2)) {
			velocity.encode(buf, Float.class);
		}
		if (bitArray.get(3)) {
			accel.encode(buf, Float.class);
		}
		if (bitArray.get(4)) {
			extraVel.encode(buf, Float.class);
		}
		if (bitArray.get(5)) {
			buf.writeFloat(lookPitch);
		}
		if (bitArray.get(6)) {
			buf.writeInt((int) physicsFlags);
		}
		if (bitArray.get(7)) {
			buf.writeByte(hostileType);
		}
		if (bitArray.get(8)) {
			buf.writeInt((int) entityType);
		}
		if (bitArray.get(9)) {
			buf.writeByte(currentMode);
		}
		if (bitArray.get(10)) {
			buf.writeInt((int) lastShootTime);
		}
		if (bitArray.get(11)) {
			buf.writeInt((int) hitCounter);
		}
		if (bitArray.get(12)) {
			buf.writeInt((int) lastHitTime);
		}
		if (bitArray.get(13)) {
			app.encode(buf);
		}
		if (bitArray.get(14)) {
			buf.writeByte(flags1);
			buf.writeByte(flags2);
		}
		if (bitArray.get(15)) {
			buf.writeInt((int) rollTime);
		}
		if (bitArray.get(16)) {
			buf.writeInt(stunTime);
		}
		if (bitArray.get(17)) {
			buf.writeInt((int) slowedTime);
		}
		if (bitArray.get(18)) {
			buf.writeInt((int) makeBlueTime);
		}
		if (bitArray.get(19)) {
			buf.writeInt((int) speedUpTime);
		}
		if (bitArray.get(20)) {
			buf.writeFloat(slowPatchTime);
		}
		if (bitArray.get(21)) {
			buf.writeByte(classType);
		}
		if (bitArray.get(22)) {
			buf.writeByte(specialization);
		}
		if (bitArray.get(23)) {
			buf.writeFloat(chargedMP);
		}
		if (bitArray.get(24)) {
			buf.writeInt((int) nu1);
			buf.writeInt((int) nu2);
			buf.writeInt((int) nu3);
		}
		if (bitArray.get(25)) {
			buf.writeInt((int) nu4);
			buf.writeInt((int) nu5);
			buf.writeInt((int) nu6);
		}
		if (bitArray.get(26)) {
			rayHit.encode(buf, Float.class);
		}
		if (bitArray.get(27)) {
			buf.writeFloat(HP);
		}
		if (bitArray.get(28)) {
			buf.writeFloat(MP);
		}
		if (bitArray.get(29)) {
			buf.writeFloat(blockPower);
		}
		if (bitArray.get(30)) {
			buf.writeFloat(maxHPMultiplier);
			buf.writeFloat(shootSpeed);
			buf.writeFloat(damageMultiplier);
			buf.writeFloat(armorMultiplier);
			buf.writeFloat(resistanceMultiplier);
		}
		if (bitArray.get(31)) {
			buf.writeByte(nu7);
		}
		if (bitArray.get(32)) {
			buf.writeByte(nu8);
		}
		if (bitArray.get(33)) {
			buf.writeInt((int) level);
		}
		if (bitArray.get(34)) {
			buf.writeInt((int) currentXP);
		}
		if (bitArray.get(35)) {
			buf.writeLong(parentOwner);
		}
		if (bitArray.get(36)) {
			buf.writeInt((int) na1);
			buf.writeInt((int) na2);
		}
		if (bitArray.get(37)) {
			buf.writeByte(na3);
		}
		if (bitArray.get(38)) {
			buf.writeInt((int) na4);
		}
		if (bitArray.get(39)) {
			buf.writeInt((int) na5);
			buf.writeInt((int) nu11);
			buf.writeInt((int) nu12);
		}
		if (bitArray.get(40)) {
			buf.writeInt((int) nu13);
			buf.writeInt((int) nu14);
			buf.writeInt((int) nu15);
			buf.writeInt((int) nu16);
			buf.writeInt((int) nu17);
			buf.writeInt((int) nu18);
		}
		if (bitArray.get(41)) {
			buf.writeInt((int) nu20);
			buf.writeInt((int) nu21);
			buf.writeInt((int) nu22);
		}
		if (bitArray.get(42)) {
			buf.writeByte(nu19);
		}
		if (bitArray.get(43)) {
			itemData.encode(buf);
		}
		if (bitArray.get(44)) {
			for (int i = 0; i < 13; i++) {
				GItem item = equipment[i];
				item.encode(buf);
			}
		}
		if (bitArray.get(45)) {
			byte[] utf8 = name.getBytes(Charsets.UTF_8);
			buf.writeBytes(new byte[4]); //TODO But why?
			buf.writeBytes(utf8);
			buf.writeBytes(new byte[16 - name.length()]);

		}
		if (bitArray.get(46)) {
			for (int i = 0; i < 11; i++) {
				buf.writeInt((int) skills[i]);
			}
		}
		if (bitArray.get(47)) {
			buf.writeInt((int) iceBlockFour);
		}

		buf.capacity(buf.writerIndex() + 1);
		if (buf.readerIndex() > 0) {
			Glydar.getServer().getLogger().warning("Data read during encode.");
		}
	}

	/**
	 * Updates this EntityData with another EntityData via bitmask
	 *
	 * @param changes Bitmasked EntityData with changes.
	 */
	@SuppressWarnings("restriction")
	public void updateFrom(EntityData changes) {
		if (changes.getId() != this.id) {
			Glydar.getServer().getLogger().warning("Tried to update entity ID " + this.id + " with changes from ID " + changes.getId() + "!");
			return;
		}

		BitArray bitArray = new BitArray(8 * changes.getBitmask().length, Bitops.flipBits(changes.getBitmask())); //Size in bits, byte[]

		if (bitArray.get(0)) {
			this.position = (GVector3<Long>) changes.getPosition();
		}
		if (bitArray.get(1)) {
			this.pitch = changes.getPitch();
			this.roll = changes.getRoll();
			this.yaw = changes.getYaw();
		}
		if (bitArray.get(2)) {
			this.velocity = (GVector3<Float>) changes.getVelocity();
		}
		if (bitArray.get(3)) {
			this.accel = (GVector3<Float>) changes.getAccel();
		}
		if (bitArray.get(4)) {
			this.extraVel = (GVector3<Float>) changes.getExtraVel();
		}
		if (bitArray.get(5)) {
			this.lookPitch = changes.getLookPitch();
		}
		if (bitArray.get(6)) {
			this.physicsFlags = changes.getPhysicsFlags();
		}
		if (bitArray.get(7)) {
			this.hostileType = changes.getHostileType();
		}
		if (bitArray.get(8)) {
			this.entityType = changes.getEntityType();
		}
		if (bitArray.get(9)) {
			this.currentMode = changes.getCurrentMode();
		}
		if (bitArray.get(10)) {
			this.lastShootTime = changes.getLastShootTime();
		}
		if (bitArray.get(11)) {
			this.hitCounter = changes.getHitCounter();
		}
		if (bitArray.get(12)) {
			this.lastHitTime = changes.getLastHitTime();
		}
		if (bitArray.get(13)) {
			this.app = (GAppearance) changes.getApp();
		}
		if (bitArray.get(14)) {
			this.flags1 = changes.getFlags1();
			this.flags2 = changes.getFlags2();
		}
		if (bitArray.get(15)) {
			this.rollTime = changes.getRollTime();
		}
		if (bitArray.get(16)) {
			this.stunTime = changes.getStunTime();
		}
		if (bitArray.get(17)) {
			this.slowedTime = changes.getSlowedTime();
		}
		if (bitArray.get(18)) {
			this.makeBlueTime = changes.getMakeBlueTime();
		}
		if (bitArray.get(19)) {
			this.speedUpTime = changes.getSpeedUpTime();
		}
		if (bitArray.get(20)) {
			this.slowPatchTime = changes.getSlowPatchTime();
		}
		if (bitArray.get(21)) {
			this.classType = changes.getClassType();
		}
		if (bitArray.get(22)) {
			this.specialization = changes.getSpecialization();
		}
		if (bitArray.get(23)) {
			this.chargedMP = changes.getChargedMP();
		}
		if (bitArray.get(24)) {
			this.nu1 = changes.getNu1();
			this.nu2 = changes.getNu2();
			this.nu3 = changes.getNu3();
		}
		if (bitArray.get(25)) {
			this.nu4 = changes.getNu4();
			this.nu5 = changes.getNu5();
			this.nu6 = changes.getNu6();
		}
		if (bitArray.get(26)) {
			this.rayHit = (GVector3<Float>) changes.getRayHit();
		}
		if (bitArray.get(27)) {
			this.HP = changes.getHP();
		}
		if (bitArray.get(28)) {
			this.MP = changes.getMP();
		}
		if (bitArray.get(29)) {
			this.blockPower = changes.getBlockPower();
		}
		if (bitArray.get(30)) {
			this.maxHPMultiplier = changes.getMaxHPMultiplier();
			this.shootSpeed = changes.getShootSpeed();
			this.damageMultiplier = changes.getDamageMultiplier();
			this.armorMultiplier = changes.getArmorMultiplier();
			this.resistanceMultiplier = changes.getResistanceMultiplier();
		}
		if (bitArray.get(31)) {
			this.nu7 = changes.getNu7();
		}
		if (bitArray.get(32)) {
			this.nu8 = changes.getNu8();
		}
		if (bitArray.get(33)) {
			this.level = changes.getLevel();
		}
		if (bitArray.get(34)) {
			this.currentXP = changes.getCurrentXP();
		}
		if (bitArray.get(35)) {
			this.parentOwner = changes.getParentOwner();
		}
		if (bitArray.get(36)) {
			this.na1 = changes.getNa1();
			this.na2 = changes.getNa2();
		}
		if (bitArray.get(37)) {
			this.na3 = changes.getNa3();
		}
		if (bitArray.get(38)) {
			this.na4 = changes.getNa4();
		}
		if (bitArray.get(39)) {
			this.na5 = changes.getNa5();
			this.nu11 = changes.getNu11();
			this.nu12 = changes.getNu12();
		}
		if (bitArray.get(40)) {
			this.nu13 = changes.getNu13();
			this.nu14 = changes.getNu14();
			this.nu15 = changes.getNu15();
			this.nu16 = changes.getNu16();
			this.nu17 = changes.getNu17();
			this.nu18 = changes.getNu18();
		}
		if (bitArray.get(41)) {
			this.nu20 = changes.getNu20();
			this.nu21 = changes.getNu21();
			this.nu22 = changes.getNu22();
		}
		if (bitArray.get(42)) {
			this.nu19 = changes.getNu19();
		}
		if (bitArray.get(43)) {
			this.itemData = (GItem) changes.getItemData();
		}
		if (bitArray.get(44)) {
			this.equipment = (GItem[]) changes.getEquipment();
		}
		if (bitArray.get(45)) {
			this.name = changes.getName();
		}
		if (bitArray.get(46)) {
			this.skills = changes.getSkills();
		}
		if (bitArray.get(47)) {
			this.iceBlockFour = changes.getIceBlockFour();
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getBitmask() {
		return bitmask;
	}

	public void setBitmask(byte[] mask) {
		this.bitmask = mask;
	}

	public Vector3<Long> getPosition() {
		return position;
	}

	public void setPosition(Vector3<Long> pos) {
		this.position = (GVector3<Long>) pos;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public Vector3<Float> getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector3<Float> velocity) {
		this.velocity = (GVector3<Float>) velocity;
	}

	public Vector3<Float> getAccel() {
		return accel;
	}

	public void setAccel(Vector3<Float> accel) {
		this.accel = (GVector3<Float>) accel;
	}

	public Vector3<Float> getExtraVel() {
		return extraVel;
	}

	public void setExtraVel(Vector3<Float> extraVel) {
		this.extraVel = (GVector3<Float>) extraVel;
	}

	public float getLookPitch() {
		return lookPitch;
	}

	public void setLookPitch(float lookPitch) {
		this.lookPitch = lookPitch;
	}

	public long getPhysicsFlags() {
		return physicsFlags;
	}

	public void setPhysicsFlags(long physicsFlags) {
		this.physicsFlags = physicsFlags;
	}

	public byte getHostileType() {
		return hostileType;
	}

	public void setHostileType(byte hostileType) {
		this.hostileType = hostileType;
	}

	public long getEntityType() {
		return entityType;
	}

	public void setEntityType(long entityType) {
		this.entityType = entityType;
	}

	public byte getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(byte currentMode) {
		this.currentMode = currentMode;
	}

	public long getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(long lastShootTime) {
		this.lastShootTime = lastShootTime;
	}

	public long getHitCounter() {
		return hitCounter;
	}

	public void setHitCounter(long hitCounter) {
		this.hitCounter = hitCounter;
	}

	public long getLastHitTime() {
		return lastHitTime;
	}

	public void setLastHitTime(long lastHitTime) {
		this.lastHitTime = lastHitTime;
	}

	public Appearance getApp() {
		return app;
	}

	public void setApp(Appearance app) {
		this.app = (GAppearance) app;
	}

	public byte getFlags1() {
		return flags1;
	}

	public void setFlags1(byte flags1) {
		this.flags1 = flags1;
	}

	public byte getFlags2() {
		return flags2;
	}

	public void setFlags2(byte flags2) {
		this.flags2 = flags2;
	}

	public long getRollTime() {
		return rollTime;
	}

	public void setRollTime(long rollTime) {
		this.rollTime = rollTime;
	}

	public int getStunTime() {
		return stunTime;
	}

	public void setStunTime(int stunTime) {
		this.stunTime = stunTime;
	}

	public long getSlowedTime() {
		return slowedTime;
	}

	public void setSlowedTime(long slowedTime) {
		this.slowedTime = slowedTime;
	}

	public long getMakeBlueTime() {
		return makeBlueTime;
	}

	public void setMakeBlueTime(long makeBlueTime) {
		this.makeBlueTime = makeBlueTime;
	}

	public long getSpeedUpTime() {
		return speedUpTime;
	}

	public void setSpeedUpTime(long speedUpTime) {
		this.speedUpTime = speedUpTime;
	}

	public float getSlowPatchTime() {
		return slowPatchTime;
	}

	public void setSlowPatchTime(float slowPatchTime) {
		this.slowPatchTime = slowPatchTime;
	}

	public byte getClassType() {
		return classType;
	}

	public void setClassType(byte classType) {
		this.classType = classType;
	}

	public byte getSpecialization() {
		return specialization;
	}

	public void setSpecialization(byte specialization) {
		this.specialization = specialization;
	}

	public float getChargedMP() {
		return chargedMP;
	}

	public void setChargedMP(float chargedMP) {
		this.chargedMP = chargedMP;
	}

	public Vector3<Float> getRayHit() {
		return rayHit;
	}

	public void setRayHit(Vector3<Float> rayHit) {
		this.rayHit = (GVector3<Float>) rayHit;
	}

	public float getHP() {
		return HP;
	}

	public void setHP(float hP) {
		HP = hP;
	}

	public float getMP() {
		return MP;
	}

	public void setMP(float mP) {
		MP = mP;
	}

	public float getBlockPower() {
		return blockPower;
	}

	public void setBlockPower(float blockPower) {
		this.blockPower = blockPower;
	}

	public float getMaxHPMultiplier() {
		return maxHPMultiplier;
	}

	public void setMaxHPMultiplier(float maxHPMultiplier) {
		this.maxHPMultiplier = maxHPMultiplier;
	}

	public float getShootSpeed() {
		return shootSpeed;
	}

	public void setShootSpeed(float shootSpeed) {
		this.shootSpeed = shootSpeed;
	}

	public float getDamageMultiplier() {
		return damageMultiplier;
	}

	public void setDamageMultiplier(float damageMultiplier) {
		this.damageMultiplier = damageMultiplier;
	}

	public float getArmorMultiplier() {
		return armorMultiplier;
	}

	public void setArmorMultiplier(float armorMultiplier) {
		this.armorMultiplier = armorMultiplier;
	}

	public float getResistanceMultiplier() {
		return resistanceMultiplier;
	}

	public void setResistanceMultiplier(float resistanceMultiplier) {
		this.resistanceMultiplier = resistanceMultiplier;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public long getCurrentXP() {
		return currentXP;
	}

	public void setCurrentXP(long currentXP) {
		this.currentXP = currentXP;
	}

	public GItem getItemData() {
		return itemData;
	}

	public void setItemData(Item itemData) {
		this.itemData = (GItem) itemData;
	}

	public GItem[] getEquipment() {
		return equipment;
	}

	public void setEquipment(Item[] equipment) {
		this.equipment = (GItem[]) equipment;
	}

	public long getIceBlockFour() {
		return iceBlockFour;
	}

	public void setIceBlockFour(long iceBlockFour) {
		this.iceBlockFour = iceBlockFour;
	}

	public long[] getSkills() {
		return skills;
	}

	public void setSkills(long[] skills) {
		this.skills = skills;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getNa1() {
		return na1;
	}

	public void setNa1(long na1) {
		this.na1 = na1;
	}

	public long getNa2() {
		return na2;
	}

	public void setNa2(long na2) {
		this.na2 = na2;
	}

	public byte getNa3() {
		return na3;
	}

	public void setNa3(byte na3) {
		this.na3 = na3;
	}

	public long getNa4() {
		return na4;
	}

	public void setNa4(long na4) {
		this.na4 = na4;
	}

	public long getNa5() {
		return na5;
	}

	public void setNa5(long na5) {
		this.na5 = na5;
	}

	public long getNu1() {
		return nu1;
	}

	public void setNu1(long nu1) {
		this.nu1 = nu1;
	}

	public long getNu2() {
		return nu2;
	}

	public void setNu2(long nu2) {
		this.nu2 = nu2;
	}

	public long getNu3() {
		return nu3;
	}

	public void setNu3(long nu3) {
		this.nu3 = nu3;
	}

	public long getNu4() {
		return nu4;
	}

	public void setNu4(long nu4) {
		this.nu4 = nu4;
	}

	public long getNu5() {
		return nu5;
	}

	public void setNu5(long nu5) {
		this.nu5 = nu5;
	}

	public long getNu6() {
		return nu6;
	}

	public void setNu6(long nu6) {
		this.nu6 = nu6;
	}

	public byte getNu7() {
		return nu7;
	}

	public void setNu7(byte nu7) {
		this.nu7 = nu7;
	}

	public byte getNu8() {
		return nu8;
	}

	public void setNu8(byte nu8) {
		this.nu8 = nu8;
	}

	public long getParentOwner() {
		return parentOwner;
	}

	public void setParentOwner(long parentOwner) {
		this.parentOwner = parentOwner;
	}

	public long getNu11() {
		return nu11;
	}

	public void setNu11(long nu11) {
		this.nu11 = nu11;
	}

	public long getNu12() {
		return nu12;
	}

	public void setNu12(long nu12) {
		this.nu12 = nu12;
	}

	public long getNu13() {
		return nu13;
	}

	public void setNu13(long nu13) {
		this.nu13 = nu13;
	}

	public long getNu14() {
		return nu14;
	}

	public void setNu14(long nu14) {
		this.nu14 = nu14;
	}

	public long getNu15() {
		return nu15;
	}

	public void setNu15(long nu15) {
		this.nu15 = nu15;
	}

	public long getNu16() {
		return nu16;
	}

	public void setNu16(long nu16) {
		this.nu16 = nu16;
	}

	public long getNu17() {
		return nu17;
	}

	public void setNu17(long nu17) {
		this.nu17 = nu17;
	}

	public long getNu18() {
		return nu18;
	}

	public void setNu18(long nu18) {
		this.nu18 = nu18;
	}

	public long getNu20() {
		return nu20;
	}

	public void setNu20(long nu20) {
		this.nu20 = nu20;
	}

	public long getNu21() {
		return nu21;
	}

	public void setNu21(long nu21) {
		this.nu21 = nu21;
	}

	public long getNu22() {
		return nu22;
	}

	public void setNu22(long nu22) {
		this.nu22 = nu22;
	}

	public byte getNu19() {
		return nu19;
	}

	public void setNu19(byte nu19) {
		this.nu19 = nu19;
	}

	public int getDebugCap() {
		return debugCap;
	}

	public void setDebugCap(int debugCap) {
		this.debugCap = debugCap;
	}
}
