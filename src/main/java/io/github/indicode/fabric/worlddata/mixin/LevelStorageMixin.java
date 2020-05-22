package io.github.indicode.fabric.worlddata.mixin;

import com.mojang.datafixers.DataFixer;
import io.github.indicode.fabric.worlddata.Test;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.function.BiFunction;

@Mixin(LevelStorage.class)
public abstract class LevelStorageMixin {

    @Inject(method = "Lnet/minecraft/world/level/storage/LevelStorage;readLevelProperties(Ljava/io/File;Ljava/util/function/BiFunction;)Ljava/lang/Object;", at = @At("HEAD"))
    private <T> void onReadLevelProperties(File file_1, BiFunction<File, DataFixer, T> biFunction_1, CallbackInfoReturnable<T> cir) {
        String currentDir = System.getProperty("user.dir");

        //TODO: Comment before compiling
        new Test();

        WorldDataLib.Internals.setGameDir(new File(currentDir));
        WorldDataLib.Internals.setWorldDir(new File(currentDir).toPath().resolve("world").toFile());
        WorldDataLib.getIOCallbacks().forEach(WorldDataLib::triggerCallbackSave);
    }

}
