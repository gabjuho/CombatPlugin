package com.gabjuho.combatPlugin.bars;

import com.gabjuho.combatPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Bar {

    private int taskID=-1;
    private final Main plugin;
    private final HashMap<UUID,BossBar> combatList = new HashMap<>();
    private final HashMap<UUID,Boolean> isCombatList = new HashMap<>();
    private final BossBar[] bossBars = new BossBar[2000];
    private static int bossBarSize = 0;

    public Bar(Main plugin)
    {
        this.plugin = plugin;
        cast();
    }

    public void addPlayer(Player player)
    {
        combatList.get(player.getUniqueId()).addPlayer(player);
    }

    public void addNewBossBar(BossBar bar)
    {
        bossBars[bossBarSize] = bar;
        bossBarSize++;
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

            @Override
            public void run() {

                double time = 1.0 / 20;

                if(!combatList.isEmpty())
                {
                    for(int i=0;i<bossBarSize;i++)
                    {
                        double progress = bossBars[i].getProgress();
                        progress-=time;
                        if(progress > 0)
                        {
                            bossBars[i].setTitle(format("&c전투 중 "+ (int)(progress * 20)+"초"));
                            bossBars[i].setProgress(progress);
                        }
                        for(UUID id: combatList.keySet()) {
                            if (combatList.get(id).equals(bossBars[i]))
                            {
                                if(progress <=0 || isCombatList.get(id).equals(false))
                                {
                                    bossBars[i].setVisible(false);
                                    isCombatList.put(id, false);
                                    combatList.remove(id);
                                }
                            }
                        }
                    }
                }
            }
        },0,20));
    }

    public String format(String msg)
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
