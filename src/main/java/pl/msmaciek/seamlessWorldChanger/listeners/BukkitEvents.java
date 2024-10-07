package pl.msmaciek.seamlessWorldChanger.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import pl.msmaciek.seamlessWorldChanger.SeamlessWorldChanger;
import pl.msmaciek.seamlessWorldChanger.configs.ConfigLoader;
import pl.msmaciek.seamlessWorldChanger.configs.MainConfig;

public class BukkitEvents implements Listener {
    private final String METADATA_KEY = "seamlessWorldChanger_teleporting";
    private final ConfigLoader configLoader = SeamlessWorldChanger.getInstance().getConfigLoader();

    @EventHandler
    public void onTeleportEvent(PlayerTeleportEvent e) {
        // TODO: Add another option to delay world change. It can also be made by delaying some packets, however it will seem like a lag.
        int delay = configLoader.getMainConfig().worldChangeDelay;

        if(delay < 1) return;
        if(e.getFrom().getWorld() == e.getTo().getWorld()) return;

        if(!e.getPlayer().getMetadata(METADATA_KEY).isEmpty()) {
            e.getPlayer().removeMetadata(METADATA_KEY, SeamlessWorldChanger.getInstance());
            return;
        }

        MainConfig mainConfig = SeamlessWorldChanger.getInstance().getConfigLoader().getMainConfig();
        if(mainConfig.showTitle)
            e.getPlayer().showTitle(mainConfig.shownTitle);

        e.setCancelled(true);

        Bukkit.getScheduler().runTaskLater(SeamlessWorldChanger.getInstance(), () -> {
            e.getPlayer().setMetadata(METADATA_KEY, new FixedMetadataValue(SeamlessWorldChanger.getInstance(), true));
            e.getPlayer().teleport(e.getTo(), e.getCause());
        }, delay);
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        SeamlessWorldChanger.getInstance().getRealRespawnedPlayers().add(
            event.getPlayer().getUniqueId()
        );
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e) {
        SeamlessWorldChanger.getInstance().getPlayerDimension().remove(
            e.getPlayer().getUniqueId()
        );
    }
}
