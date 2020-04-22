package io.github.indicode.fabric.worlddata.mixin;

import io.github.indicode.fabric.worlddata.Test;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.class_5219;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.nio.file.Path;

@Mixin(LevelStorage.class)
public abstract class LevelStorageMixin {

    @Inject(method = "readLevelProperties(Ljava/io/File;)Lnet/minecraft/class_5219;", at = @At("HEAD"))
    private void onReadLevelProperties(File file_1, CallbackInfoReturnable<class_5219> cir) {
        String currentDir = System.getProperty("user.dir");

        WorldDataLib.Internals.setGameDir(new File(currentDir));
        WorldDataLib.Internals.setWorldDir(new File(currentDir).toPath().resolve("world").toFile());
        WorldDataLib.getIOCallbacks().forEach(WorldDataLib::triggerCallbackSave);
    }

}
