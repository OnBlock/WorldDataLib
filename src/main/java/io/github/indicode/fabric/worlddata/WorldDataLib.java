package io.github.indicode.fabric.worlddata;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Indigo Amann
 */
public class WorldDataLib {
    private static List<WorldIOCalback> callbacks = new ArrayList<>();
    public static void addIOCallback(WorldIOCalback callback) {
        callbacks.add(callback);
    }
    public static ImmutableList<WorldIOCalback> getIOCallbacks() {
        return ImmutableList.copyOf(callbacks);
    }
}
