package fr4gtastic.justmyrottenluck.mixin.horsepower;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import se.gory_moon.horsepower.util.Utils;

@Mixin(Utils.class)
public abstract class UtilsMixin {

    /**
     * @author Fr4gtastic
     * @reason allow TFC foods in HorsePower machines
     */
    @Overwrite(remap = false)
    public static int getItemStackHashCode(ItemStack stack) {
        if (stack.isEmpty()) return 0;

        NBTTagCompound tag = stack.writeToNBT(new NBTTagCompound());
        tag.removeTag("Count");
        tag.removeTag("Damage");
        tag.removeTag("ForgeCaps");
        return tag.hashCode();
    }
}
