package cn.minecraft.Zcc.MapVerify.Listener;

import cn.minecraft.Zcc.MapVerify.Manager.Verify;
import fr.xephi.authme.events.LoginEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AuthmeLogin implements Listener {
    @EventHandler
    public void Login(LoginEvent A){
        Verify.goVerify(A.getPlayer());
    }
}
