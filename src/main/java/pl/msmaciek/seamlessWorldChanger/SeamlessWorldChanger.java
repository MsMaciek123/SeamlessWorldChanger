package pl.msmaciek.seamlessWorldChanger;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerCommon;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.msmaciek.seamlessWorldChanger.configs.ConfigLoader;
import pl.msmaciek.seamlessWorldChanger.listeners.BukkitEvents;
import pl.msmaciek.seamlessWorldChanger.listeners.JoinGamePacketListener;
import pl.msmaciek.seamlessWorldChanger.listeners.RespawnPacketListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class SeamlessWorldChanger extends JavaPlugin {

    @Getter private static SeamlessWorldChanger instance;
    @Getter private ConfigLoader configLoader;

    @Getter private final ArrayList<UUID> realRespawnedPlayers = new ArrayList<>();
    @Getter private final HashMap<UUID, DimensionType> playerDimension = new HashMap<>();

    private final ArrayList<PacketListenerCommon> packetListeners = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        configLoader = new ConfigLoader();
        configLoader.loadConfigs();

        packetListeners.add(
            PacketEvents.getAPI().getEventManager().registerListener(
                new JoinGamePacketListener(),
                PacketListenerPriority.LOW
            )
        );

        packetListeners.add(
            PacketEvents.getAPI().getEventManager().registerListener(
                new RespawnPacketListener(),
                PacketListenerPriority.LOW
            )
        );

        getServer().getPluginManager().registerEvents(new BukkitEvents(), this);
    }

    @Override
    public void onDisable() {
        PacketEvents.getAPI().getEventManager().unregisterListeners(packetListeners.toArray(new PacketListenerCommon[0]));
    }
}
