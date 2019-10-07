package io.github.indicode.fabric.worlddata;

import net.minecraft.nbt.CompoundTag;

/**
 * @author Indigo Amann
 */
public interface NBTSavableData {
    CompoundTag toNBT(CompoundTag tag);
    void fromNBT(CompoundTag tag);
}
