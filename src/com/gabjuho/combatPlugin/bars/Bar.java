package com.gabjuho.combatPlugin.bars;

import com.gabjuho.combatPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Bar {

    private int taskID=-1;
    private final Main plugin;
    private HashMap<UUID,BossBar> combatList;
    private HashMap<UUID,Boolean> isCombatList;

    public Bar(Main plugin)
    {
        this.plugin = plugin;
    }

    public void addPlayer(Player player)
    {
        combatList.get(player.getUniqueId()).addPlayer(player);
    }

    public HashMap<UUID, BossBar> getBarList()
    {
        return combatList;
    }

    public HashMap<UUID, Boolean> getIsCombatList()
    {
        return isCombatList;
    }

    public void cast()
    {
        setTaskID(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            HashMap<UUID,BossBar> tempBarList;
            HashMap<UUID,Integer> tempTimeList;

            @Override
            public void run() {

                double time = 1.0 / 10;

                for(UUID id: combatList.keySet())
                {
                    Double progress = combatList.get(id).getProgress();

                    if(progress>0 && isCombatList.get(id).equals(true))
                    {
                        progress -= time;
                        combatList.get(id).setProgress(progress);
                        combatList.get(id).setTitle(format("&c전투 중 ") + (int) (progress * 10) + "초");
                    }
                    else
                    {
                        combatList.get(id).removeAll();
                        combatList.remove(id);
                    }
                }
            }
        },0,20));
    }

    private String format(String msg)
    {
        return ChatColor.translateAlternateColorCodes('&',msg);
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

}
