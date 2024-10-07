package pl.msmaciek.seamlessWorldChanger.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.protocol.world.dimension.DimensionType;
import com.github.retrooper.packetevents.protocol.world.dimension.DimensionTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerRespawn;
import pl.msmaciek.seamlessWorldChanger.SeamlessWorldChanger;

import java.util.UUID;

public class RespawnPacketListener implements PacketListener {
    private final SeamlessWorldChanger instance = SeamlessWorldChanger.getInstance();

    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.RESPAWN)
            return;

        var packet = new WrapperPlayServerRespawn(event);

        UUID uuid = event.getUser().getUUID();

        DimensionType previousDimension = instance.getPlayerDimension().getOrDefault(uuid, DimensionTypes.OVERWORLD);
        DimensionType newDimension = packet.getDimensionType();
        instance.getPlayerDimension().put(uuid, newDimension);

        if(instance.getRealRespawnedPlayers().remove(uuid))
            return;

        if(!previousDimension.equals(newDimension))
            return;

        event.setCancelled(true);
    }
}
