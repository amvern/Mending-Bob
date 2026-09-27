package github.amvern.mendingbob.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.player.FirstPersonHandsAndItems;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FirstPersonHandsAndItems.class)
public class FirstPersonHandsAndItemsMixin {
    @WrapMethod(method = "shouldInstantlyReplaceVisibleItem")
    private boolean mendingbob$shouldInstantlyReplaceVisibleItem(ItemStack currentlyVisibleItem, ItemStack expectedItem, LocalPlayer player, Operation<Boolean> original) {
        ItemEnchantments enchants = currentlyVisibleItem.getEnchantments();

        boolean hasMending = enchants.keySet().stream()
                .flatMap(holder -> holder.unwrapKey().stream())
                .anyMatch(key -> key.equals(Enchantments.MENDING));

        return hasMending ? false : original.call(currentlyVisibleItem, expectedItem, player);
    }
}