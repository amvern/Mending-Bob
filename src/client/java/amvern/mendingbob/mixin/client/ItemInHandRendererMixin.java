package amvern.mendingbob.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
	@WrapMethod(method = "shouldInstantlyReplaceVisibleItem")
	private boolean mendingbob$shouldInstantlyReplaceVisibleItem(ItemStack oldItem, ItemStack newItem, Operation<Boolean> original) {
		ItemEnchantments enchants = oldItem.getEnchantments();

		boolean hasMending = enchants.keySet().stream()
				.flatMap(holder -> holder.unwrapKey().stream())
				.anyMatch(key -> key.equals(Enchantments.MENDING));

		return hasMending ? false : original.call(oldItem, newItem);
	}
}