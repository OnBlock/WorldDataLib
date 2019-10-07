package io.github.indicode.fabric.worlddata;

import net.minecraft.nbt.CompoundTag;

import java.io.File;

/**
 * @author Indigo Amann
 */
public abstract class NBTWorldData implements WorldIOCalback, NBTSavableData {
    protected boolean backup;

    public NBTWorldData(boolean backup) {

    }

    public abstract File getSaveFile(File worldDirectory, File rootDirectory);
    
    @Override
    public void onWorldSave(File worldDirectory, File rootDirectory) {
        File file = getSaveFile(worldDirectory, rootDirectory);
    }

    @Override
    public void onWorldLoad(File worldDirectory, File rootDirectory) {
        File file = getSaveFile(worldDirectory, rootDirectory);
    }
}
