package cn.minecraft.Zcc.MapVerify.Listener;

import cn.minecraft.Zcc.MapVerify.Main;
import net.minecraft.server.v1_8_R3.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.*;

import java.util.Random;

public class MapListener implements Listener {
    @EventHandler
    public void onMap(MapInitializeEvent A){
        A.getMap().setWorld(Bukkit.getWorld("world_nether"));
        String verify = String.valueOf(new Random().nextInt(99999));
        A.getMap().addRenderer(new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                if (!PlayerListener.isPlay(player)){
                    Main.playerStringHashMap.put(player,verify);
                    mapCanvas.drawText(0,0, MinecraftFont.Font,verify);
                }
            }
        });
    }
}
