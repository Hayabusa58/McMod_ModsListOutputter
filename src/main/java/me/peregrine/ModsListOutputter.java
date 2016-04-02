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
    public String format = "{id}({name}:{version}[{displayVersion}]):{source}";
    public boolean isActivatedOnly = false;

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
        Configuration configuration = new Configuration(event.getSuggestedConfigurationFile());
        configuration.load();

        this.format = configuration.getString("format", "mods", this.format, "Output format");
        this.isActivatedOnly = configuration.getBoolean("isActivatedOnly", "mods", this.isActivatedOnly, "Write only Active Mods.");

        configuration.save();
    }

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event)
    {
        List<ModContainer> mods = Loader.instance().getModList();
        if(this.isActivatedOnly)
            mods = Loader.instance().getActiveModList();

        StringBuilder sb = new StringBuilder();
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
