package io.github.indicode.fabric.worlddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Indigo Amann
 */
public abstract class NBTWorldData implements WorldIOCalback, NBTSavableData {

    public abstract File getSaveFile(File worldDirectory, File rootDirectory, boolean backup);

    @Override
    public void onWorldSave(File worldDirectory, File rootDirectory) {
        File backupFile = getSaveFile(worldDirectory, rootDirectory, true);
        File file = getSaveFile(worldDirectory, rootDirectory, false);
        if (file.exists() && backupFile != null) {
            if (backupFile.exists()) {
                if (!backupFile.delete()) throw new RuntimeException(new IOException("Could not delete backup file: " + backupFile.getAbsolutePath()));
            }
            if (!backupFile.getParentFile().isDirectory()) {
                if (!backupFile.mkdirs()) throw new RuntimeException(new IOException("Could not create directory " + backupFile.getAbsolutePath() + " for data backup."));
            }
            if (!file.renameTo(backupFile)) throw new RuntimeException(new IOException("Could not rename data file " + file.getAbsolutePath() + " to backup file: " + backupFile.getAbsolutePath()));
        }
        if (file.exists()) {
            if (!file.delete()) throw new RuntimeException(new IOException("Could not delete data file: " + file.getAbsolutePath()));
        }
        if (!file.getParentFile().isDirectory()) {
            if (!file.mkdirs()) throw new RuntimeException(new IOException("Could not create directory " + file.getAbsolutePath() + " for data."));
        }
        if (!file.isFile()) {
            try {
                if (!file.createNewFile()) throw new RuntimeException(new IOException("Could not create data file " + file.getAbsolutePath()));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
        CompoundTag tag = toNBT(new CompoundTag());
        if (tag == null) throw new RuntimeException(new NullPointerException("Null tag was passed for " + file.getAbsolutePath()));
        try {
            NbtIo.writeCompressed(tag, new FileOutputStream(file));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void onWorldLoad(File worldDirectory, File rootDirectory) {
        File backupFile = getSaveFile(worldDirectory, rootDirectory, true);
        File file = getSaveFile(worldDirectory, rootDirectory, false);
        if (!file.exists()) {
            if (backupFile == null || !backupFile.exists()) {
                return;
            }
            file = backupFile;
        }
        try {
            CompoundTag tag = NbtIo.readCompressed(new FileInputStream(file));
            if (tag == null) throw new RuntimeException(new NullPointerException("Null tag was passed for " + file.getAbsolutePath()));
            fromNBT(tag);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
