package io.github.indicode.fabric.worlddata.mixin;

import io.github.indicode.fabric.worlddata.Test;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelGeneratorOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

/**
 * @author Indigo Amann
 */
@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {


    @Shadow public abstract File getRunDirectory();

    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadNBT(String string_1, long long_1, LevelGeneratorOptions levelGeneratorOptions_1, CallbackInfo ci) {
        //Test
        //WorldDataLib.addIOCallback(new Test());

        WorldDataLib.Internals.setGameDir(this.getRunDirectory());
        WorldDataLib.Internals.setWorldDir(new File(getRunDirectory() + "/" + string_1));
        WorldDataLib.getIOCallbacks().forEach(callback -> callback.onWorldLoad(new File(getRunDirectory() + "/" + string_1), getRunDirectory()));
    }
}
