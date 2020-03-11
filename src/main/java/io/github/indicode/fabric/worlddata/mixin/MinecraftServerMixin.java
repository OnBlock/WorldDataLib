package io.github.indicode.fabric.worlddata.mixin;

import com.google.gson.JsonElement;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.class_4952;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelGeneratorType;
import org.spongepowered.asm.mixin.Final;
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
    @Shadow @Final private File gameDir;

    @Inject(method = "loadWorld", at = @At("RETURN"))
    private void loadNBT(String string_1, String string_2, long long_1, class_4952 class_4952_1, CallbackInfo ci) {
        WorldDataLib.Internals.setGameDir(gameDir);
        WorldDataLib.Internals.setWorldDir(new File(gameDir + "/" + string_1));
        WorldDataLib.getIOCallbacks().forEach(callback -> callback.onWorldLoad(new File(gameDir + "/" + string_1), gameDir));
    }
}
