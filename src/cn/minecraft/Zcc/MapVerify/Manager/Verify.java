package cn.minecraft.Zcc.MapVerify.Manager;

import cn.minecraft.Zcc.MapVerify.ConfigLang;
import cn.minecraft.Zcc.MapVerify.Main;
import cn.minecraft.Zcc.MapVerify.Thread.TimeOutThread;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class Verify {
    public static void goVerify(Player player){
        if((Boolean) ConfigLang.getConfig("join_1_Verify")){
            if (getWritePlayer(player)){return;}
        }
        //=============
        Main.playerStringHashMap.put(player,"");
        Main.playerItemStackHashMap.put(player,player.getItemInHand().clone());
        Main.playerSwapItemStackHasMap.put(player,player.getInventory().getItem(40).clone());
        player.getInventory().setItem(40,new ItemStack(Material.AIR));
        if (Main.version.contains("v1_7_") || Main.version.contains("v1_8_") || Main.version.contains("v1_9_") || Main.version.contains("v1_10_") || Main.version.contains("v1_11_") |Main.version.contains("v1_12_")){
            try {
                Class<?> c = Class.forName("org.bukkit.Material");
                for (Object enumConstant : c.getEnumConstants()) {
                    if (enumConstant.toString().equals("EMPTY_MAP")){
                        ItemStack itemStack = new ItemStack((Material) enumConstant,1);
                        player.getInventory().setItemInHand(itemStack);
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("获得物品Mete异常 BukkitMain.version:" + Bukkit.getVersion()+"错误："+e.getMessage());
            }
        }

        if (Main.version.contains("v1_13_") || Main.version.contains("v1_14_") || Main.version.contains("v1_15_") || Main.version.contains("v1_16_") |Main.version.contains("v1_17_")){
            try {
                Class<?> c = Class.forName("org.bukkit.Material");
                for (Object enumConstant : c.getEnumConstants()) {
                    if (enumConstant.toString().equals("LEGACY_EMPTY_MAP")){
                        ItemStack itemStack = new ItemStack((Material) enumConstant,1);
                        player.getInventory().setItemInHand(itemStack);
                        break;
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("获得物品Mete异常 BukkitMain.version:" +Bukkit.getVersion()+"错误："+e.getMessage());
            }
        }

        if ((Boolean) ConfigLang.getConfig("Time_Out.timeOutKick")){
            new TimeOutThread(player,ConfigLang.getInterval()).start();//开启倒计时
        }
    }
    public static void writePlayer(Player player){
        File file = new File(Main.plugin.getDataFolder(),"writePlayer");
        file.mkdir();
        File filePlayer = new File(file,player.getName());
        try {
            filePlayer.createNewFile();
        } catch (IOException e) {
            System.out.println("无法保存玩家本地验证数据 异常:"+e.getMessage());
        }
    }
    public static boolean getWritePlayer(Player player){
        File file = new File(Main.plugin.getDataFolder(),"writePlayer");
        file.mkdir();
        File filePlayer = new File(file,player.getName());
        return filePlayer.exists();
    }
    public static void VerifyNot(Player player){
        if (getVerify(player).equals("0")) {
            player.sendMessage(ConfigLang.getLang(ConfigLang.Lang.right_title));
            return;
        }
        player.sendMessage(ConfigLang.getLang(ConfigLang.Lang.verify_not));
    }
    public static void VerifyOk(Player player){
        backToItem(player);
        Main.playerStringHashMap.remove(player);
        Main.playerItemStackHashMap.remove(player);
        player.sendMessage(ConfigLang.getLang(ConfigLang.Lang.verify_ok));
        if((Boolean) ConfigLang.getConfig("join_1_Verify")){
            writePlayer(player);
        }
    }
    public static String getVerify(Player player){
        return Main.playerStringHashMap.get(player);
    }
    public static void backToItem(Player player){
        if (Main.playerItemStackHashMap.get(player)!=null) { player.getInventory().setItemInHand(Main.playerItemStackHashMap.get(player)); }
        if (Main.playerSwapItemStackHasMap.get(player)!=null) { player.getInventory().setItem(40,Main.playerSwapItemStackHasMap.get(player)); }
    }
}
