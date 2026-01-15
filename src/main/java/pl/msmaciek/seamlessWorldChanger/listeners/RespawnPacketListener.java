package pl.msmaciek.seamlessWorldChanger.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import com.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRespawn;
import pl.msmaciek.seamlessWorldChanger.SeamlessWorldChanger;
import pl.msmaciek.seamlessWorldChanger.structs.PlayerWorldData;

import java.util.UUID;

public class RespawnPacketListener implements PacketListener {
    private final SeamlessWorldChanger instance = SeamlessWorldChanger.getInstance();

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.RESPAWN)
            return;

        var packet = new WrapperPlayServerRespawn(event);

        UUID uuid = event.getUser().getUUID();

        PlayerWorldData previousWorldData = instance.getPlayerWorldData().getOrDefault(uuid,
                new PlayerWorldData(DimensionTypes.OVERWORLD, "minecraft:overworld"));
        DimensionType previousDimension = previousWorldData.dimension();
        DimensionType newDimension = packet.getDimensionType();

        // Bring back old world name in order to avoid issue with sound stopping when changing world (sometimes by respawning)
        // do that only if dimension is the same
        String previousWorldName = previousWorldData.worldName();
        if(previousDimension.equals(newDimension))
            packet.setWorldName(previousWorldName);

        instance.getPlayerWorldData().put(uuid, new PlayerWorldData(newDimension, previousWorldName));

        if(instance.getRealRespawnedPlayers().remove(uuid))
            return;

        if(!previousDimension.equals(newDimension))
            return;

        event.setCancelled(true);
    }
}
