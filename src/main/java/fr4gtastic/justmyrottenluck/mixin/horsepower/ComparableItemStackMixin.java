package fr4gtastic.justmyrottenluck.mixin.horsepower;

import fr4gtastic.justmyrottenluck.interfaces.IComparableItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import se.gory_moon.horsepower.recipes.ComparableItemStack;

@Mixin(ComparableItemStack.class)
public abstract class ComparableItemStackMixin implements IComparableItemStack {

    @Shadow
    private ItemStack stack;

    @Override
    public ItemStack getStack() {
        return stack;
    }

    /**
     * @author Fr4gtastic
     * @reason allow TFC foods in HorsePower machines
     */
    @Overwrite(remap = false)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComparableItemStack)) {
            return false;
        }

        IComparableItemStack that = (IComparableItemStack) o;

        return this.hashCode() == that.hashCode() &&
                (that.getStack().getMetadata() == OreDictionary.WILDCARD_VALUE
                        ? stack.getItem() == that.getStack().getItem()
                        : stack.isItemEqual(that.getStack()));
    }
}
