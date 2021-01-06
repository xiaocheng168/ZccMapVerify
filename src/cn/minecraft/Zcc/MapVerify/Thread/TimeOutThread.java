package cn.minecraft.Zcc.MapVerify.Thread;

import cn.minecraft.Zcc.MapVerify.ConfigLang;
import cn.minecraft.Zcc.MapVerify.Listener.PlayerListener;
import cn.minecraft.Zcc.MapVerify.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TimeOutThread extends Thread{
    Player player;
    Integer time = 0;
    Integer interval;
    public TimeOutThread(Player player,Integer interval){
        this.player = player;
        this.interval = interval;
    }
    @Override
    public void run() {
        while (true){
            time++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!player.isOnline()){return;}

            if (Main.playerStringHashMap.get(player) !=null) {
                player.sendMessage(ConfigLang.getLang(ConfigLang.Lang.right_title));
            }

            if (time >= interval) {
                Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (!PlayerListener.isPlay(player)) {
                            player.kickPlayer(ConfigLang.getLang(ConfigLang.Lang.time_out));
                        }
                    }
                });
                break;
            }
        }

    }
}
