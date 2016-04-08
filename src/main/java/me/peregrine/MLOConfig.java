package me.peregrine;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MLOConfig {

	public static boolean isActive = true;
    public static String format = "Modid:{modid} Modname:{name} Modversion:{version}[{displayVersion}] Filename:{source}";
    public static boolean isActivatedOnly = false;

	public static void preLoad(File file)
	{
		Configuration cfg = new Configuration(file, "1.0");

		try {

			cfg.load();

			System.out.println("コンフィグ読み込み");
			isActive = cfg.getBoolean("1.isActive", "General", isActive, "Whether this mod is valid.");
			format = cfg.getString("1.format", "Style", format, "Output format");
			isActivatedOnly = cfg.getBoolean("2.isActivatedOnly", "General", isActivatedOnly,
					"Write only Active Mods.");

		} finally {
			cfg.save();
			System.out.println("コンフィグセーブ");
		}
	}
}
