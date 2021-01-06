package cn.minecraft.Zcc.MapVerify;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ConfigLang {
    public static Object getConfig(String s){
        File file = new File(Main.plugin.getDataFolder(),"config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.get(s);
    }
    public static String getLang(ConfigLang.Lang lang){
        File file = new File(Main.plugin.getDataFolder(),"config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return (config.getString("lang.perfix")+config.getString(lang.getLang())).replace("&","§").replace("%interval%",String.valueOf(getInterval()));
    }
    public static int getInterval(){
        File file = new File(Main.plugin.getDataFolder(),"config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("Time_Out.interval",30);
    }
    public enum Lang{
        prefix("lang.perfix"),
        time_out("lang.time-out"),
        verify_ok("lang.verify-ok"),
        verify_not("lang.verify-not"),
        right_title("lang.right-title")
        ;
        String lang;
        Lang(String s){
            this.lang = s;
        }

        public String getLang() {
            return lang;
        }
    }
    public static String getVersion(){
        return "1.1";
    }
    /*
    * @args s
    *
    * */

    public static String getNewVersion(){
        URL url = null;
        String data = "";
        int b;
        try {
            url = new URL("https://www.mcbbs.net/thread-1142613-1-1.html");
            URLConnection urlConnection = url.openConnection();
            while((b = urlConnection.getInputStream().read())!=-1){
                data +=(char)b;
            }
        } catch (MalformedURLException e) {
            System.out.println("无法解析MCBBSWEB页面,无法检测最新版本请自行前往查看");
            System.out.println("Webstie: https://www.showdoc.com.cn/ZccMapVerify");
        } catch (IOException e) {
            System.out.println("无法解析MCBBSWEB页面,无法检测最新版本请自行前往查看");
            System.out.println("Webstie: https://www.showdoc.com.cn/ZccMapVerify");
        }

        return subString(data,"|{","}|");
    }

    public static String subString(String s,String z,String y){
        return s.substring(s.indexOf(z)+z.getBytes().length,s.indexOf(y));
    }
}
