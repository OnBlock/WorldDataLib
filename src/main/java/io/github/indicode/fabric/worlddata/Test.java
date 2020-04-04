package io.github.indicode.fabric.worlddata;

import net.minecraft.nbt.CompoundTag;

import java.io.File;
import java.util.Date;

public class Test extends NBTWorldData {

    @Override
    public File getSaveFile(File worldDirectory, File rootDirectory, boolean backup) {
        return rootDirectory.toPath().resolve("test.dat" + (backup ? "_old" : "")).toFile();
    }

    @Override
    public CompoundTag toNBT(CompoundTag tag) {
        CompoundTag compound = new CompoundTag();
        System.out.println("[WorldDataLib Test] Writing to NBT");
        compound.putString("curr", new Date().toString());
        return compound;
    }

    @Override
    public void fromNBT(CompoundTag tag) {
        if (tag.contains("curr")) {
            System.out.println("[WorldDataLib Test] Test superseded " + tag.getString("curr"));
        } else {
            System.out.println("[WorldDataLib Test] Test Failed: Failed to load");
        }
    }
}
