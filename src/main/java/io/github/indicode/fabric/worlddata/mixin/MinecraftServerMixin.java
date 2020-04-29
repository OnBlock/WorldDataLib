package io.github.indicode.fabric.worlddata.mixin;

import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(method = "save", at = @At(value = "RETURN", target = "Lnet/minecraft/server/MinecraftServer;save(ZZZ)Z"))

    private void modify(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
        WorldDataLib.getIOCallbacks().forEach(WorldDataLib::triggerCallbackSave);
    }

}
