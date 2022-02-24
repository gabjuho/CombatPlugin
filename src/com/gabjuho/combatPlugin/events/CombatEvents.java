package com.gabjuho.combatPlugin.events;

import com.gabjuho.combatPlugin.bars.Bar;
import com.gabjuho.combatPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatEvents implements Listener {

    private Bar bar = new Bar(Main.getPlugin(Main.class));

    @EventHandler
    void onHitPlayer(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            Player damager = (Player) event.getDamager(); // 데미지를 받은 사람
            Player hiter = (Player) event.getEntity(); // 데미지를 준 사람

            if(!bar.getBarList().containsKey(damager.getUniqueId()) && !bar.getBarList().containsKey(hiter.getUniqueId()))
            {
                BossBar bossbar = Bukkit.createBossBar("전투 중", BarColor.BLUE, BarStyle.SOLID);
                bar.getBarList().put(damager.getUniqueId(),bossbar);
                bar.getBarList().put(hiter.getUniqueId(),bossbar);
                bar.getIsCombatList().put(damager.getUniqueId(),true);
                bar.getIsCombatList().put(hiter.getUniqueId(),true);
                bar.addPlayer(damager);
                bar.addPlayer(hiter);
            }

        }
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if(bar.getIsCombatList().containsKey(player.getUniqueId())) {
            if (bar.getIsCombatList().get(player.getUniqueId()).equals(true)) {
                player.setHealth(0);
                bar.getIsCombatList().put(player.getUniqueId(), false);
            }
        }
    }


}
