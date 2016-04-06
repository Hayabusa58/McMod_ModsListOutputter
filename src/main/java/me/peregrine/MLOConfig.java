package me.peregrine;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class MLOConfig {

	public static boolean isActive = true;
    public static String format = "{id}({name}:{version}[{displayVersion}]):{source}";
    public static boolean isActivatedOnly = false;

	public static void preLoad(File file)
	{
		Configuration configuration = new Configuration(file, "1.0");

		try {

			configuration.load();

			System.out.println("コンフィグ読み込み");
			isActive = configuration.getBoolean("1.isActive", "General", isActive, "Whether this mod is valid.");
			format = configuration.getString("1.format", "Style", format, "Output format");
			isActivatedOnly = configuration.getBoolean("2.isActivatedOnly", "General", isActivatedOnly,
					"Write only Active Mods.");

		} finally {
			configuration.save();
			System.out.println("コンフィグセーブ");
		}
	}
}
