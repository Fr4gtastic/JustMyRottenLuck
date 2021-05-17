package fr4gtastic.justmyrottenluck.util;

import betterwithmods.common.registry.bulk.recipes.BulkRecipe;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class RotUtil {

    public static boolean containsRottenItem(ItemStackHandler inv) {
        for (int i = 0; i < inv.getSlots(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (isInputRotten(stack)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInputRotten(ItemStack stack)
    {
        IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
        return cap != null && cap.isRotten();
    }

    public static boolean isOutputFood(BulkRecipe recipe) {
        for (ItemStack itemStack : recipe.getOutputs()) {
            if (itemStack.getItem() instanceof ItemFood) {
                return true;
            }
        }
        return false;
    }
}
