package pl.msmaciek.seamlessWorldChanger.listeners;

import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerJoinGame;
import pl.msmaciek.seamlessWorldChanger.SeamlessWorldChanger;
import pl.msmaciek.seamlessWorldChanger.structs.PlayerWorldData;

import java.util.UUID;

public class JoinGamePacketListener implements PacketListener {
    @Override
    public void onPacketSend(PacketSendEvent event) {

        if (event.getPacketType() != PacketType.Play.Server.JOIN_GAME)
            return;

        var packet = new WrapperPlayServerJoinGame(event);

        UUID uuid = event.getUser().getUUID();
        SeamlessWorldChanger.getInstance().getPlayerWorldData().put(
            uuid,
            new PlayerWorldData(packet.getDimensionType(), packet.getWorldName())
        );
    }
}