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
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(name = "ModsList Outputter", modid = "me.peregrine.modslistoutputter", version = "MC1.7.x_1.0a")
public class ModsListOutputter
{
	@Instance("me.peregrine.modslistoutputter")
	public static ModsListOutputter MLO;

	public static boolean isIDFound = false;

	@EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
	    File file = new File(event.getModConfigurationDirectory(), "ModsListOutPutter.cfg");
	    MLOConfig.preLoad(file);
    	//MLOConfig.preLoad(event.getSuggestedConfigurationFile());

    }

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event)
    {

    if(MLOConfig.isActive)
    {
    	//System.out.println("アクティブだった");
    	//ModsListOutputter.DebugMessage("コンフィグ読み込み");
    	StringBuilder sb = new StringBuilder();
        List<ModContainer> mods = Loader.instance().getModList();

        if(MLOConfig.isActivatedOnly)
        {
            mods = Loader.instance().getActiveModList();
        }

        for(ModContainer mod : mods)
        {
        	ModsListOutputter.DebugMessage("mod情報取得");
            String buf = MLOConfig.format;
            buf = buf.replace("{modid}", mod.getModId());
            buf = buf.replace("{name}", mod.getName());
            buf = buf.replace("{version}", mod.getVersion());
            buf = buf.replace("{displayVersion}", mod.getDisplayVersion());
            buf = buf.replace("{source}", mod.getSource().getName());
            sb.append(buf);
            sb.append("\n");
        }

        try
        {
        	//System.out.println("アクティブだった2");
        	//StringBuilder sb2 = new StringBuilder();
            File modslist = new File(MLOConfig.listdir, "Modslist.txt");
            modslist.createNewFile();
        	//File modslist = new File(Minecraft.getMinecraft().mcDataDir, "mods/ModsList.txt");
            ModsListOutputter.DebugMessage("リスト生成");
            FileOutputStream fos = new FileOutputStream(modslist);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")));
            pw.write(sb.toString());
            pw.close();
            fos.close();

        }
        catch (Exception e){



        }
    }
    }
    /*public void ChangeConfigName() throws IOException
    {
    	File oldcfg = new File(Minecraft.getMinecraft().mcDataDir, "/config/me.peregrine.modslistoutputter.cfg");
    	File newcfg = new File(Minecraft.getMinecraft().mcDataDir, "/config/ModsListOutPutter.cfg");
    if(oldcfg.exists())
    	oldcfg.renameTo(newcfg);
    }
*/
    public static void DebugMessage(String pText, Object... pData)
    {
    	System.out.println(String.format("ModsListOutPutter +" + pText, pData));
    }
}
