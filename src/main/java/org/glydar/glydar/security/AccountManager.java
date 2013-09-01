package org.glydar.glydar.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import com.google.common.base.Joiner;
import com.google.common.hash.Hashing;

import org.glydar.glydar.GServer;
import org.glydar.glydar.models.GPlayer;
import org.glydar.paraglydar.configuration.file.YamlConfiguration;

public class AccountManager {
	private final GServer s;
	private File dir;
	private File admins;
	private YamlConfiguration accounts;
	
	public AccountManager(GServer s){
		this.s = s;
	}
	
	//These sacred commands are kept seperate to avoid tampering
	public boolean manageCommands(GPlayer ply, String message){
		if (message.startsWith("/login")){
			if (message.split(" ").length > 1){
				String[] split = message.split(" ");
				String[] passArray = Arrays.copyOfRange(split, 1, split.length);
				String password = Joiner.on(" ").join(passArray);
				manageLoginCommand(ply, password);
			} else {
				ply.sendMessage("Usage: /login [password]");
			}
			return true;
		} else if (message.startsWith("/register")) {
			if (message.split(" ").length > 1){
				String[] split = message.split(" ");
				String[] passArray = Arrays.copyOfRange(split, 1, split.length);
				String password = Joiner.on(" ").join(passArray);
				manageRegisterCommand(ply, password);
			} else {
				ply.sendMessage("Usage: /register [password]");
			}
			return true;
		}
		
		return false;
	}
	
	public void manageLoginCommand(GPlayer ply, String pass){
		String id = createUniqueIdentifier(ply);
		String ip = ply.getIp();
		String password = Hashing.sha512().hashString(pass).toString();
		
		boolean existing = false;
		for (String s: accounts.getConfigurationSection(ip).getKeys(false)){
			if (s.equalsIgnoreCase(id)){
				existing = true;
			}
		}
		if (!existing){
			ply.sendMessage("Sorry, you don't have an account. Please do /register [password]");
			return;
		}
		
		if (accounts.getString(ip + "." + id).equals(password)){
			ply.playerVerified();
			ply.sendMessage("You have sucessfully verified!");
			return;
		} else {
			ply.sendMessage("Wrong password. Please try again.");
			return;
		}
	}
	
	public void manageRegisterCommand(GPlayer ply, String pass){
		String id = createUniqueIdentifier(ply);
		String ip = ply.getIp();
		String password = Hashing.sha512().hashString(pass).toString();
		
		boolean existing = false;
		for (String s: accounts.getConfigurationSection(ip).getKeys(false)){
			if (s.equalsIgnoreCase(id)){
				existing = true;
			}
		}
		if (existing){
			ply.sendMessage("Sorry, you already have an account. Please do /register [password]");
			return;
		}
		
		accounts.set(ip + "." + id, password);
		ply.playerVerified();
		ply.sendMessage("You have sucessfully registered!");
		return;
	}
	
	public String createUniqueIdentifier(GPlayer ply){
		StringBuilder id = new StringBuilder();
		
		Long race = ply.getEntityData().getEntityType();
		String idRace;
		if (race.toString().length() == 1){
			idRace = "00" + race;
		} else if (race.toString().length() == 2) {
			idRace = "0" + race;
		} else {
			idRace = race.toString();
		}
		id.append(idRace);
		
		byte classType = ply.getEntityData().getClassType();
		id.append(classType);
		
		byte specialization = ply.getEntityData().getSpecialization();
		id.append(specialization);
		
		Integer head = ply.getEntityData().getApp().getHeadModel();
		String idHead;
		if (head.toString().length() == 1){
			idHead = "000" + head;
		} else if (head.toString().length() == 2) {
			idHead = "00" + head;
		} else if (head.toString().length() == 3) {
			idHead = "0" + head;
		} else {
			idHead = head.toString();
		}
		id.append(idHead);
		
		Integer hair = ply.getEntityData().getApp().getHairModel();
		String idHair;
		if (hair.toString().length() == 1){
			idHair = "000" + hair;
		} else if (hair.toString().length() == 2) {
			idHair = "00" + hair;
		} else if (hair.toString().length() == 3) {
			idHair = "0" + hair;
		} else {
			idHair = hair.toString();
		}
		id.append(idHair);
		
		Byte red = ply.getEntityData().getApp().getHairR();
		String idRed;
		if (red.toString().length() == 1){
			idRed = "00" + red;
		} else if (red.toString().length() == 2) {
			idRed = "0" + red;
		} else {
			idRed = red.toString();
		}
		id.append(idRed);
		
		Byte green = ply.getEntityData().getApp().getHairG();
		String idGreen;
		if (green.toString().length() == 1){
			idGreen = "00" + green;
		} else if (green.toString().length() == 2) {
			idGreen = "0" + green;
		} else {
			idGreen = green.toString();
		}
		id.append(idGreen);
		
		Byte blue = ply.getEntityData().getApp().getHairR();
		String idBlue;
		if (blue.toString().length() == 1){
			idBlue = "00" + blue;
		} else if (blue.toString().length() == 2) {
			idBlue = "0" + blue;
		} else {
			idBlue = blue.toString();
		}
		id.append(idBlue);
		
		return id.toString();
	}

	public void loadAdminFile(){
		makeAccountsDir();
		
		File adminsFile = new File(dir, "admins.txt");
        List<String> adminsList = new ArrayList<>();
        if (!adminsFile.isFile()) {
            try {
                adminsFile.createNewFile();
            } catch (Exception e) {
                s.getLogger().log(Level.SEVERE, "Could not create admins file.");
            }
        } else {
            try {
                Scanner scanner = new Scanner(adminsFile);
                while (scanner.hasNext()) {
                    String line = scanner.next();
                    if (line == null || line.equals("")){
                    } else {
                        adminsList.add(line.trim());
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                s.getLogger().log(Level.SEVERE, "Couldn't find admins file.");
            }
        }
        s.setAdmins(adminsList);
        
        this.admins = adminsFile;
	}
	
	public void loadAccountFile(){
		makeAccountsDir();
		
		File accountsFile = new File(dir, "accounts.yml");
		if (!accountsFile.isFile()) {
            try {
            	accountsFile.createNewFile();
            } catch (Exception e) {
                s.getLogger().log(Level.SEVERE, "Could not create accounts file.");
            }
		}
		
		accounts = YamlConfiguration.loadConfiguration(accountsFile);
	}
	
	private void makeAccountsDir(){
		File accountsDir = new File("accounts");
		if (!accountsDir.isDirectory()){
			try{
				accountsDir.mkdir();
			} catch (Exception e) {
				s.getLogger().log(Level.SEVERE, "Could not create accounts directory.");
			}
		}
		dir = accountsDir;
	}
	
}
