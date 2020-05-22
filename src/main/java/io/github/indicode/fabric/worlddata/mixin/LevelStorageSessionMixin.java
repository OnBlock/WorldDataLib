package io.github.indicode.fabric.worlddata.mixin;

import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelStorage.Session.class)
public abstract class LevelStorageSessionMixin {

    @Inject(method = "method_27426", at = @At("HEAD"))
    private void onSaveWorld(SaveProperties saveProperties_1, CompoundTag compoundTag_1, CallbackInfo ci) {
        WorldDataLib.getIOCallbacks().forEach(WorldDataLib::triggerCallbackSave);
    }

}
