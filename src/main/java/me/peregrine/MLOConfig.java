package me.peregrine;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

public class MLOConfig {

	public static boolean isActive = true;
    public static String format = "Modid:{modid} Modname:{name} Modversion:{version}[{displayVersion}] Filename:{source}";
    public static String listdir;
    //public static String listname;
    public static boolean isActivatedOnly = false;

	public static void preLoad(File file)
	{
		//File isFileflag = new File(Minecraft.getMinecraft().mcDataDir, "config/ModsListOutPutter.cfg");
		
		
 		
		Configuration cfg = new Configuration(file, "1.0");
		ModsListOutputter.DebugMessage("コンフィグ生成");
		
		

		try {

			cfg.load();

			listdir = new File(Minecraft.getMinecraft().mcDataDir, "mods/ModsList.txt").getParent();

			//String mcdir = mc.getPath();

			ModsListOutputter.DebugMessage("コンフィグ読み込み");
			//System.out.println("コンフィグ読み込み");
			System.out.println(listdir);
			//System.out.println(listdir);
			isActive = cfg.getBoolean("1.isActive", "General", isActive, "Whether this mod is valid.");
			format = cfg.getString("1.format", "Style", format, "Output format");
			listdir = cfg.getString("3.GenerateDirectory", "General", listdir , "Directory of generate modslist file. Default directory is [../Gamedirectory/mods].");
			isActivatedOnly = cfg.getBoolean("2.isActivatedOnly", "General", isActivatedOnly,
					"Write only Active Mods.");
			System.out.println(listdir);

		} finally {
			cfg.save();
			ModsListOutputter.DebugMessage("コンフィグセーブ");
		}
		}
	
	
}
