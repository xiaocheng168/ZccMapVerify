package cn.minecraft.Zcc.MapVerify;

import cn.minecraft.Zcc.MapVerify.Listener.AuthmeLogin;
import cn.minecraft.Zcc.MapVerify.Listener.MapListener;
import cn.minecraft.Zcc.MapVerify.Listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import sun.net.www.protocol.http.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.logging.Level;

public class Main extends JavaPlugin  {
    public static HashMap<Player,String> playerStringHashMap = new HashMap<>();//存放玩家验证码
    public static HashMap<Player, ItemStack> playerItemStackHashMap = new HashMap<>();//存放玩家手中物品
    public static HashMap<Player, ItemStack> playerSwapItemStackHasMap = new HashMap<>();//存放副手手中物品
    public static String version = "v"+Bukkit.getBukkitVersion().replace(".","_");
    public static Plugin plugin;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        if (getConfig().getBoolean("Auto-Updata",true)){
            getLogger().info("正在异步检测版本中");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String newVersion = ConfigLang.getNewVersion();
                    if (!newVersion.equals(ConfigLang.getVersion())){
                        getLogger().log(Level.WARNING,"检测到新版本:"+newVersion);
                        getLogger().log(Level.WARNING,"请前往查看:https://www.showdoc.com.cn/ZccMapVerify");
                        getLogger().log(Level.WARNING,"MCBBS:https://www.mcbbs.net/thread-1142613-1-1.html");
                    }else{
                        getLogger().info("已经是最新版本了");
                    }
                }
            }).start();
        }
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getPluginManager().registerEvents(new MapListener(),this);
        if (Bukkit.getPluginManager().getPlugin("AuthMe") != null){
            Bukkit.getPluginManager().registerEvents(new AuthmeLogin(),this);
        }
        getLogger().info("图形验证已载入 版本"+ConfigLang.getVersion());
        getLogger().info("插件运行在:"+version);
        getLogger().info("开发者:Zcc");
        getLogger().info("QQ:3513312668");
        getLogger().info("有BUG请反馈 谢谢！");
        bStat();
    }

    @Override
    public void onDisable() {
        getLogger().info("图形验证插件已卸载，欢迎下次使用");
        getLogger().info("开发者:Zcc");
        getLogger().info("QQ:3513312668");
    }

    public void bStat(){
        new cn.minecraft.Zcc.MapVerify.Metrics(this,9895);
    }
}
