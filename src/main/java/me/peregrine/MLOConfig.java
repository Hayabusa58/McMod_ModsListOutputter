package me.peregrine;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

public class MLOConfig {

	public static boolean isactive = true;
    public static String format = "Modid:{modid} Modname:{name} Modversion:{version}[{displayVersion}] Filename:{source}";
    public static String listdir;
    public static boolean isactivatedonly = false;
    public static boolean isBBCodemodeactive = false;
    public static boolean iscustummodeactive = false;

	public static void preLoad(File file)
	{
		Configuration cfg = new Configuration(file, "1.0");
		ModsListOutputter.DebugMessage("コンフィグ生成");

		try {

			cfg.load();

			listdir = new File(Minecraft.getMinecraft().mcDataDir, "mods/ModsList.txt").getParent();

			String generalcate = "General";
			String formatcate = "Format";

			ModsListOutputter.DebugMessage("コンフィグ読み込み");
			System.out.println(listdir);
			/*generalカテゴリの項目*/
			isactive = cfg.getBoolean("1.isActive", generalcate, isactive, "Whether this mod is valid.");
			isactivatedonly = cfg.getBoolean("2.isActivatedOnly", generalcate, isactivatedonly,
					"Write only Active Mods.");
			listdir = cfg.getString("3.GenerateDirectory", generalcate, listdir , "Directory of generate modslist file. Default directory is [../Gamedirectory/mods].");
			isBBCodemodeactive = cfg.getBoolean("4.isBBCodeModeActive", generalcate, isBBCodemodeactive, "Whether to generate optimized mod list to BBCode.");
			iscustummodeactive = cfg.getBoolean("5.isCustumModeActive", generalcate, iscustummodeactive, "Whether generate a mod list in a custom format.");
			/*formatカテゴリの項目*/
			format = cfg.getString("1.format", formatcate, format, "Output format");

			//System.out.println(listdir);

		} finally {
			cfg.save();
			//ModsListOutputter.DebugMessage("コンフィグセーブ");
		}
	}
}
