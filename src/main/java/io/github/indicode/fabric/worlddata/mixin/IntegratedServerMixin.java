package io.github.indicode.fabric.worlddata.mixin;

import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import io.github.indicode.fabric.worlddata.WorldDataLib;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.UserCache;
import net.minecraft.world.level.LevelGeneratorOptions;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

/**
 * @author Indigo Amann
 */
@Mixin(IntegratedServer.class)
public abstract class IntegratedServerMixin extends MinecraftServer {

    @Shadow public abstract File getRunDirectory();
    private final File worldStorage = getRunDirectory().toPath().resolve("world").toFile();

    public IntegratedServerMixin(LevelStorage.class_5143 levelStorage$class_5143_1, Proxy proxy_1, DataFixer dataFixer_1, CommandManager commandManager_1, MinecraftSessionService minecraftSessionService_1, GameProfileRepository gameProfileRepository_1, UserCache userCache_1, WorldGenerationProgressListenerFactory worldGenerationProgressListenerFactory_1) {
        super(levelStorage$class_5143_1, proxy_1, dataFixer_1, commandManager_1, minecraftSessionService_1, gameProfileRepository_1, userCache_1, worldGenerationProgressListenerFactory_1);
    }

    @Inject(method = "loadWorld", at = @At("RETURN"))
    private void loadNBT(String string_1, long long_1, LevelGeneratorOptions levelGeneratorOptions_1, CallbackInfo ci) {
        WorldDataLib.Internals.setGameDir(MinecraftClient.getInstance().runDirectory);
        WorldDataLib.Internals.setWorldDir(new File(worldStorage + "/" + string_1));
        WorldDataLib.getIOCallbacks().forEach(callback -> callback.onWorldLoad(new File(worldStorage + "/" + string_1), MinecraftClient.getInstance().runDirectory));
    }
}
