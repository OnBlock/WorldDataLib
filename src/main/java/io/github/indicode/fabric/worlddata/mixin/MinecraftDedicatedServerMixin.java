package io.github.indicode.fabric.worlddata.mixin;

import io.github.indicode.fabric.worlddata.Test;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(MinecraftDedicatedServer.class)
public abstract class MinecraftDedicatedServerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Util;getMeasuringTimeNano()J", ordinal = 1), method = "setupServer") // Does not work
    private void oky$setupServer$ready(CallbackInfoReturnable<Boolean> cir) {
        String currentDir = System.getProperty("user.dir");

        //TODO: Comment before compiling
        //WorldDataLib.addIOCallback(new Test());

        WorldDataLib.Internals.setGameDir(new File(currentDir));
        WorldDataLib.Internals.setWorldDir(new File(currentDir).toPath().resolve("world").toFile());
        WorldDataLib.getIOCallbacks().forEach(WorldDataLib::triggerCallbackLoad);
    }
}
