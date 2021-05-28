package fr4gtastic.justmyrottenluck.mixin.horsepower;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import se.gory_moon.horsepower.recipes.HPRecipeBase;
import se.gory_moon.horsepower.tileentity.TileEntityHPBase;
import se.gory_moon.horsepower.tileentity.TileEntityHandGrindstone;

@Mixin(TileEntityHandGrindstone.class)
public abstract class TileEntityHandGrindstoneMixin extends TileEntityHPBase implements ITickable {

    public TileEntityHandGrindstoneMixin() {
        super(3);
    }

    @Shadow
    public abstract void processSecondaries(World world, ItemStack secondary, ItemStack secondaryOutput, HPRecipeBase recipe, TileEntityHPBase teBase);

    /**
     * @author Fr4gtastic
     * @reason disallow crafting with rotten food in HorsePower Hand Grindstone
     */
    @Overwrite(remap = false)
    private void millItem() {
        if (!world.isRemote && canWork()) {
            HPRecipeBase recipe = getRecipe();
            ItemStack result = recipe.getOutput();
            ItemStack secondary = recipe.getSecondary();

            ItemStack input = getStackInSlot(0);
            ItemStack output = getStackInSlot(1);
            ItemStack secondaryOutput = getStackInSlot(2);

            if (output.isEmpty()) {
                ItemStack newOutput = result.copy();
                if (input.hasCapability(CapabilityFood.CAPABILITY, null)) {
                    CapabilityFood.updateFoodFromPrevious(input, newOutput);
                }
                setInventorySlotContents(1, newOutput);
            } else if (output.isItemEqual(result)) {
                output.grow(result.getCount());
            }
            processSecondaries(getWorld(), secondary, secondaryOutput, recipe, this);

            input.shrink(1);
        }
    }
}
