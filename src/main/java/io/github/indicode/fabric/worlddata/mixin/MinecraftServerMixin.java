package io.github.indicode.fabric.worlddata.mixin;

import com.google.gson.JsonElement;
import io.github.indicode.fabric.worlddata.WorldDataLib;
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

    @Shadow public abstract boolean isDedicated();

    @Inject(method = "loadWorld", at = @At("RETURN"))
    private void loadNBT(String string_1, String string_2, long long_1, LevelGeneratorType levelGeneratorType_1, JsonElement jsonElement_1, CallbackInfo ci) {
        WorldDataLib.getIOCallbacks().forEach(callback -> callback.onWorldLoad(new File(gameDir + "/" + string_1), gameDir));
    }
}