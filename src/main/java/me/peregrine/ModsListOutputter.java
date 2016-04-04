package me.peregrine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

@Mod(name="ModsList Outputter", modid = "me.peregrine.modslistoutputter", version = "MC1.7.x_1.0a")
public class ModsListOutputter
{
	public boolean isActive = true;
    public String format = "{id}({name}:{version}[{displayVersion}]):{source}";
    public boolean isActivatedOnly = false;

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
    	Configuration configuration = new Configuration(event.getSuggestedConfigurationFile());
        try {

			configuration.load();

			System.out.println("コンフィグ読み込み");
			this.isActive = configuration.getBoolean("isActive", "General", this.isActive, "Whether this mod is valid.");
			this.format = configuration.getString("format", "Style", this.format, "Output format");
			this.isActivatedOnly = configuration.getBoolean("isActivatedOnly", "General", this.isActivatedOnly,
					"Write only Active Mods.");

		} finally {
			configuration.save();
			System.out.println("コンフィグセーブ");
		}
    }

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event)
    {

    if(isActive)
    {
    	System.out.println("アクティブだった");
    	StringBuilder sb = new StringBuilder();
        List<ModContainer> mods = Loader.instance().getModList();
        if(this.isActivatedOnly)
            mods = Loader.instance().getActiveModList();


        for(ModContainer mod : mods)
        {
            String buf = this.format;
            buf = buf.replace("{id}", mod.getModId());
            buf = buf.replace("{name}", mod.getName());
            buf = buf.replace("{version}", mod.getVersion());
            buf = buf.replace("{displayVersion}", mod.getDisplayVersion());
            buf = buf.replace("{source}", mod.getSource().getName());
            sb.append(buf);
            sb.append("\n");
        }
    

        try
        {
        	System.out.println("アクティブだった2");
        	//StringBuilder sb2 = new StringBuilder();
            File file = new File(Minecraft.getMinecraft().mcDataDir, "mods/ModsList.txt");
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")));
            pw.write(sb.toString());
            pw.close();
            fos.close();
        }
        catch (Exception e){}
    }
    }
}
