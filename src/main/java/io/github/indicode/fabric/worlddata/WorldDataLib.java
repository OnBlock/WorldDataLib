package io.github.indicode.fabric.worlddata;

import com.google.common.collect.ImmutableList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Indigo Amann
 */
public class WorldDataLib {
    private static File worldDir, gameDir;
    private static List<WorldIOCalback> callbacks = new ArrayList<>();
    public static void addIOCallback(WorldIOCalback callback) {
        callbacks.add(callback);
    }
    public static ImmutableList<WorldIOCalback> getIOCallbacks() {
        return ImmutableList.copyOf(callbacks);
    }
    public static void triggerCallbackLoad(WorldIOCalback callback) {
        checkDirsValid();
        callback.onWorldLoad(worldDir, gameDir);
    }
    public static void triggerCallbackSave(WorldIOCalback callback) {
        checkDirsValid();
        callback.onWorldSave(worldDir, gameDir);
    }
    private static void checkDirsValid() {
        if (worldDir == null || gameDir == null) throw new RuntimeException(new IllegalStateException("Cannot trigger a callback until a world is loaded."));
    }

    /**
     * Don't touch
     */
    public static class Internals {
        public static void setGameDir(File file) {
            WorldDataLib.gameDir = file;
        }
        public static void setWorldDir(File file) {
            WorldDataLib.worldDir = file;
        }
        public static void clearDirs() {
            setGameDir(null);
            setWorldDir(null);
        }
    }
}
