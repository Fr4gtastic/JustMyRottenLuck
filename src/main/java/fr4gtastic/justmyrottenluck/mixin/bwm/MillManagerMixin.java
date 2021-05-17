package fr4gtastic.justmyrottenluck.mixin.bwm;

import betterwithmods.common.BWRegistry;
import betterwithmods.common.BWSounds;
import betterwithmods.common.blocks.mechanical.tile.TileEntityMill;
import betterwithmods.common.registry.bulk.manager.CraftingManagerBulk;
import betterwithmods.common.registry.bulk.manager.MillManager;
import betterwithmods.common.registry.bulk.recipes.MillRecipe;
import betterwithmods.util.InvUtils;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemPowder;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static fr4gtastic.justmyrottenluck.util.RotUtil.containsRottenItem;
import static fr4gtastic.justmyrottenluck.util.RotUtil.isOutputFood;

@Mixin(MillManager.class)
public abstract class MillManagerMixin extends CraftingManagerBulk<MillRecipe> {

    /**
     * @author Fr4gtastic
     * @reason disallow crafting with rotten food in BWM Mill
     */
    @Overwrite(remap = false)
    public boolean craftRecipe(World world, TileEntity tile, ItemStackHandler inv) {
        if (tile instanceof TileEntityMill) {
            TileEntityMill mill = (TileEntityMill)tile;
            MillRecipe recipe = this.findRecipe(this.recipes, tile, inv).orElse(null);
            if (mill.getBlockWorld().rand.nextInt(20) == 0) {
                mill.getBlockWorld().playSound(null, mill.getBlockPos(), BWSounds.STONEGRIND, SoundCategory.BLOCKS,
                        0.5F + mill.getBlockWorld().rand.nextFloat() * 0.1F,
                        0.5F + mill.getBlockWorld().rand.nextFloat() * 0.1F);
            }

            if (recipe != null) {

                if (mill.grindMax != recipe.getTicks()) {
                    mill.grindMax = recipe.getTicks();
                }

                if (mill.getBlockWorld().rand.nextInt(40) < 2) {
                    mill.getBlockWorld().playSound(null, mill.getBlockPos(), recipe.getSound(), SoundCategory.BLOCKS,
                            0.75F, mill.getWorld().rand.nextFloat() * 0.4F + 0.8F);
                }

                if (this.canCraft(recipe, tile, inv)) {
                    if (containsRottenItem(inv) && isOutputFood(recipe)) {
                        BWRegistry.MILLSTONE.craftItem(recipe, world, tile, inv);
                        InvUtils.ejectStackWithOffset(world, mill.getBlockPos(), new ItemStack(ItemPowder.get(Powder.FERTILIZER)));
                    } else {
                        mill.ejectRecipe(BWRegistry.MILLSTONE.craftItem(recipe, world, tile, inv));
                    }

                    mill.grindCounter = 0;
                    return true;
                }

                mill.grindCounter = Math.min(mill.grindMax, mill.grindCounter + mill.getIncrement());
                mill.markDirty();
            } else {
                mill.grindCounter = 0;
                mill.grindMax = -1;
            }
        }

        return false;
    }


}
