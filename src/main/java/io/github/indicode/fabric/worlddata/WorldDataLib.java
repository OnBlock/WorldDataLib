package io.github.indicode.fabric.worlddata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Indigo Amann
 */
public class WorldDataLib {
    private static List<WorldIOCalback> callbacks = new ArrayList<>();
    public static void addSaveCallback(WorldIOCalback callback) {
        callbacks.add(callback);
    }
}
