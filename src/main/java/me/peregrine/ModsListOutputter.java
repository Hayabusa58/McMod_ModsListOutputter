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

/*
 *
 * The main class of ModslistOutPutter
 * @auther Hayabusa
 *
 */
@Mod(name = "ModsList Outputter", modid = "me.peregrine.modslistoutputter", version = "MC1.7.x_1.0a")
public class ModsListOutputter
{
	@Instance("me.peregrine.modslistoutputter")
	public static ModsListOutputter MLO;

	//public static boolean isIDFound = false;

	public static boolean istheresecondarydistribution = false;

	@EventHandler
    public void preLoad(FMLPreInitializationEvent event){
	    File file = new File(event.getModConfigurationDirectory(), "ModsListOutPutter.cfg");
	    MLOConfig.preLoad(file);
    }

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event){

    if(MLOConfig.isactive){
    	StringBuilder sb = new StringBuilder();
        List<ModContainer> mods = Loader.instance().getModList();

        if(MLOConfig.isactivatedonly){
            mods = Loader.instance().getActiveModList();
        }

        for(ModContainer mod : mods){
        	ModsListOutputter.DebugMessage("mod情報取得");
        	//コンフィグからフォーマットを取得して置換
            String buf = MLOConfig.format;
            buf = buf.replace("{modid}", mod.getModId());
            buf = buf.replace("{name}", mod.getName());
            buf = buf.replace("{version}", mod.getVersion());
            buf = buf.replace("{displayVersion}", mod.getDisplayVersion());
            buf = buf.replace("{source}", mod.getSource().getName());
            sb.append(buf);
            sb.append("\n");

        }try{
        	this.GenerateModslist(sb);
        	/*if(MLOConfig.isBBCodemodeactive){
        		this.UpdateforBBCode();
        	}*/


        } catch (Exception e){
        	e.printStackTrace();
        }

     }

    }
    /*デバッグメッセージ*/
    public static void DebugMessage(String pText, Object... pData){
    	System.out.println(String.format("ModsListOutPutter + " + pText, pData));
    }


    public File GenerateModslist(StringBuilder sb)
    {
    	try
        {
            File modslist = new File(MLOConfig.listdir, "Modslist.txt");
            modslist.createNewFile();
            System.out.println("リスト生成");
            FileOutputStream fos = new FileOutputStream(modslist);
            PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos, "UTF-8")));
            if(MLOConfig.isBBCodemodeactive){
            	pw.write("[spoiler]");
            	pw.write(sb.toString());
            	pw.write("[/spoiler]");
            	pw.close();
            	fos.close();
            }else{
            	pw.write(sb.toString());
            	pw.close();
            	fos.close();
            }
            return modslist;
        } catch (Exception e){
        	return null;
        }

    }
    /* うまくいかなかったので未使用. いつか実装したい*/
    public boolean isThereSecondaryDistribution(String str)
    {
    	if(str.matches(".*1.7.10-Mod.*|.*1.8-Mod*.|*.1.8.9-Mod.*|*.1.9-Mod.*")){
    		istheresecondarydistribution = true;
    		return true;
    	}else{
    		return false;
    	}

    }

   /* public void UpdateforBBCode(){
    	if(MLOConfig.isBBCodemodeactive){
    		File file = new File(MLOConfig.listdir, "Modslist.txt");
    		File dummyfile = new File(MLOConfig.listdir, "Modslist2.txt");
        	if(file.exists()){
        		try{
        			System.out.println("OK!!!");

        			FileReader fr = new FileReader(file);
        			BufferedReader br = new BufferedReader(fr);

        			FileWriter fw = new FileWriter(dummyfile);
        			PrintWriter pr = new PrintWriter(fw);

        			String line;
        			int i = 0;

        			while((line = br.readLine())!= null){
        				pr.println(line);
        				i++;
        				if(i == 1){
        					pr.println("[spoiler]");
        				}
        				pr.println("[/spoiler]");

        				br.close();
        				pr.close();
        			}
        		} catch (Exception e){

        		}
        	}
        }*/


    }





