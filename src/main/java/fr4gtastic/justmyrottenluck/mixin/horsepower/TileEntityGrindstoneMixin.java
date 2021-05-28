package fr4gtastic.justmyrottenluck.mixin.horsepower;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import se.gory_moon.horsepower.blocks.BlockGrindstone;
import se.gory_moon.horsepower.recipes.HPRecipeBase;
import se.gory_moon.horsepower.tileentity.TileEntityGrindstone;
import se.gory_moon.horsepower.tileentity.TileEntityHPHorseBase;
import se.gory_moon.horsepower.tileentity.TileEntityHandGrindstone;

@Mixin(TileEntityGrindstone.class)
public abstract class TileEntityGrindstoneMixin extends TileEntityHPHorseBase {

    public TileEntityGrindstoneMixin() {
        super(3);
    }

    @Shadow
    public abstract ItemStack getStackInSlot(int index);

    @Shadow
    public abstract HPRecipeBase getRecipe();

    /**
     * @author Fr4gtastic
     * @reason disallow crafting with rotten food in HorsePower Grindstone
     */
    @Overwrite(remap = false)
    private void millItem() {
        if (canWork()) {
            HPRecipeBase recipe = getRecipe();
            ItemStack result = recipe.getOutput();
            ItemStack secondary = recipe.getSecondary();

            ItemStack input = getStackInSlot(0);
            ItemStack output = getStackInSlot(1);
            ItemStack secondaryOutput = getStackInSlot(2);

            if (output.isEmpty()) {
                ItemStack newOutput = result.copy();
                if (input.hasCapability(CapabilityFood.CAPABILITY, null))
                {
                    CapabilityFood.updateFoodFromPrevious(input, newOutput);
                }
                setInventorySlotContents(1, newOutput);
            } else if (output.isItemEqual(result)) {
                output.grow(result.getCount());
            }
            TileEntityHandGrindstone.processSecondaries(getWorld(), secondary, secondaryOutput, recipe, this);

            input.shrink(1);
            BlockGrindstone.setState(true, world, pos);
        }
    }
}
