package io.github.indicode.fabric.worlddata.mixin;

import com.mojang.datafixers.DataFixer;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.level.LevelProperties;
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
@Mixin(WorldSaveHandler.class)
public class WorldSaveHandlerMixin {
    private MinecraftServer server;
    @Inject(method = "<init>", at = @At("RETURN"))
    private void setServer(File file_1, String string_1, MinecraftServer minecraftServer_1, DataFixer dataFixer_1, CallbackInfo ci) {
        server = minecraftServer_1;
    }
    @Inject(method = "saveWorld(Lnet/minecraft/world/level/LevelProperties;Lnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    private void onSaveNBT(LevelProperties levelProperties_1, CompoundTag compoundTag_1, CallbackInfo ci) {
        WorldDataLib.getIOCallbacks().forEach(callback -> callback.onWorldSave(worldDir, server.isDedicated() ? worldDir.getParentFile() : worldDir.getParentFile().getParentFile()));
    }
    @Final
    @Shadow
    private File worldDir;
}
