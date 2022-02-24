package com.gabjuho.combatPlugin;
import com.gabjuho.combatPlugin.commands.CombatCommands;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable()
    {
        CombatCommands commands = new CombatCommands();
        getServer().getPluginManager().registerEvents(this,this);

        getCommand("test").setExecutor(commands);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Combat] 플러그인이 활성화 되었습니다!");
    }
    @Override
    public void onDisable()
    {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Combat] 플러그인이 비활성화 되었습니다!");
    }
}