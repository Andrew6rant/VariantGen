package io.github.andrew6rant.variantgen.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingTableMixin {
    @Inject(at = @At("HEAD"), method = "canUse", cancellable = true)
    public void canUse(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(true);
    }
}
