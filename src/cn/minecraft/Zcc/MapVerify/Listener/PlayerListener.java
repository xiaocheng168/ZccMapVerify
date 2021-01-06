package cn.minecraft.Zcc.MapVerify.Listener;

import cn.minecraft.Zcc.MapVerify.Main;
import cn.minecraft.Zcc.MapVerify.Manager.Verify;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;


public class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onTab(PlayerChatTabCompleteEvent A){
        System.out.println(A.getChatMessage());
    }
    @EventHandler(ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent A) {
        if (Bukkit.getPluginManager().getPlugin("AuthMe") == null){
            Verify.goVerify(A.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent A){
        Player player =A.getPlayer();
        if (!isPlay(player)){
            A.setCancelled(true);
            Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (Main.playerStringHashMap.get(player).equals(A.getMessage())) {
                        Verify.VerifyOk(player);
                    }else{
                        Verify.VerifyNot(player);
                    }
                }
            });
        }
    }
    @EventHandler(ignoreCancelled = true)
    public void onChat(PlayerChatEvent A){
        Player player =A.getPlayer();
        if (!isPlay(player)){
            A.setCancelled(true);
            Bukkit.getScheduler().runTask(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    if (Main.playerStringHashMap.get(player).equals(A.getMessage())) {
                        Verify.VerifyOk(player);
                    }else{
                        Verify.VerifyNot(player);
                    }
                }
            });
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent A){
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent A){
        Player player = A.getPlayer();
        Verify.backToItem(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent A){
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    public static boolean isPlay(Player player){
        return Main.playerStringHashMap.get(player)==null;
    }

    @EventHandler(ignoreCancelled = true)
    public void onSolt(PlayerItemHeldEvent A) {
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent A){
        if (!isPlay(A.getPlayer())){
            if ((A.getTo().getX() != A.getFrom().getX()) || ((A.getTo().getZ() != A.getFrom().getZ()) || ((A.getTo().getY() != A.getFrom().getY())))) {
                A.setTo(A.getFrom());
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent A){
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent A){
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onInMain(PlayerSwapHandItemsEvent A){
        A.setCancelled(!isPlay(A.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent A){
        A.setCancelled(!isPlay((Player) A.getWhoClicked()));
    }

}
