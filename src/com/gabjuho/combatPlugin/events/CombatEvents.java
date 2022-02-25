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
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class CombatEvents implements Listener {

    private final Bar bar = new Bar(Main.getPlugin(Main.class));

    @EventHandler
    void onHitPlayer(EntityDamageByEntityEvent event)
    {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player)
        {
            Player damager = (Player) event.getDamager(); // 데미지를 받은 사람
            Player hiter = (Player) event.getEntity(); // 데미지를 준 사람

            if(!bar.getBarList().containsKey(damager.getUniqueId()) && !bar.getBarList().containsKey(hiter.getUniqueId()))
            {
                BossBar bossbar = Bukkit.createBossBar(bar.format("&c전투 중 20초"), BarColor.BLUE, BarStyle.SOLID);
                bossbar.setVisible(true);

                bar.addNewBossBar(bossbar);

                bar.getBarList().put(damager.getUniqueId(),bossbar);
                bar.getBarList().put(hiter.getUniqueId(),bossbar);
                bar.getIsCombatList().put(damager.getUniqueId(),true);
                bar.getIsCombatList().put(hiter.getUniqueId(),true);
                bar.addPlayer(damager);
                bar.addPlayer(hiter);
            }
            else if(bar.getBarList().containsKey(damager.getUniqueId()) && !bar.getBarList().containsKey(hiter.getUniqueId()))
            {
                BossBar bossBar = bar.getBarList().get(damager.getUniqueId());
                bar.getBarList().put(hiter.getUniqueId(),bossBar);
                bar.getIsCombatList().put(hiter.getUniqueId(),true);
                bar.addPlayer(hiter);
            }
            else if(!bar.getBarList().containsKey(damager.getUniqueId()) && bar.getBarList().containsKey(hiter.getUniqueId()))
            {
                BossBar bossBar = bar.getBarList().get(hiter.getUniqueId());
                bar.getBarList().put(damager.getUniqueId(),bossBar);
                bar.getIsCombatList().put(damager.getUniqueId(),true);
                bar.addPlayer(damager);
            }

        }
    }

    @EventHandler
    void onProjectileHit(ProjectileHitEvent event)
    {
        if(event.getHitEntity() instanceof Player && event.getEntity().getShooter() instanceof Player)
        {
            Player shooter = (Player)event.getEntity().getShooter();
            Player damager = (Player)event.getHitEntity();

            if(!bar.getBarList().containsKey(damager.getUniqueId()) && !bar.getBarList().containsKey(shooter.getUniqueId()))
            {
                BossBar bossbar = Bukkit.createBossBar(bar.format("&c전투 중 20초"), BarColor.BLUE, BarStyle.SOLID);
                bossbar.setVisible(true);

                bar.addNewBossBar(bossbar);

                bar.getBarList().put(damager.getUniqueId(),bossbar);
                bar.getBarList().put(shooter.getUniqueId(),bossbar);
                bar.getIsCombatList().put(damager.getUniqueId(),true);
                bar.getIsCombatList().put(shooter.getUniqueId(),true);
                bar.addPlayer(damager);
                bar.addPlayer(shooter);
            }
            else if(bar.getBarList().containsKey(damager.getUniqueId()) && !bar.getBarList().containsKey(shooter.getUniqueId()))
            {
                BossBar bossBar = bar.getBarList().get(damager.getUniqueId());
                bar.getBarList().put(shooter.getUniqueId(),bossBar);
                bar.getIsCombatList().put(shooter.getUniqueId(),true);
                bar.addPlayer(shooter);
            }
            else if(!bar.getBarList().containsKey(damager.getUniqueId()) && bar.getBarList().containsKey(shooter.getUniqueId()))
            {
                BossBar bossBar = bar.getBarList().get(shooter.getUniqueId());
                bar.getBarList().put(damager.getUniqueId(),bossBar);
                bar.getIsCombatList().put(damager.getUniqueId(),true);
                bar.addPlayer(damager);
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

                for(UUID id: bar.getBarList().keySet())
                {
                    if(bar.getBarList().get(player.getUniqueId()).equals(bar.getBarList().get(id)))
                    {
                        bar.getIsCombatList().put(id,false);
                    }
                }

            }
        }
    }


}
