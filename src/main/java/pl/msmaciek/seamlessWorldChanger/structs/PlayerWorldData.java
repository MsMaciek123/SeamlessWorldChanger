package pl.msmaciek.seamlessWorldChanger.structs;

import com.github.retrooper.packetevents.protocol.world.dimension.DimensionType;

public record PlayerWorldData(DimensionType dimension, String worldName) {}
