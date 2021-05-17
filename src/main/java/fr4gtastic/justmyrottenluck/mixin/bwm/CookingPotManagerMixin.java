package fr4gtastic.justmyrottenluck.mixin.bwm;

import betterwithmods.common.blocks.mechanical.tile.TileEntityCookingPot;
import betterwithmods.common.registry.bulk.manager.CookingPotManager;
import betterwithmods.common.registry.bulk.manager.CraftingManagerBulk;
import betterwithmods.common.registry.bulk.recipes.CookingPotRecipe;
import betterwithmods.util.InvUtils;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemPowder;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static fr4gtastic.justmyrottenluck.util.RotUtil.containsRottenItem;
import static fr4gtastic.justmyrottenluck.util.RotUtil.isOutputFood;

@Mixin(CookingPotManager.class)
public abstract class CookingPotManagerMixin extends CraftingManagerBulk<CookingPotRecipe> {

    /**
     * @author Fr4gtastic
     * @reason disallow crafting with rotten food in BWM cooking pot
     */
    @Overwrite(remap = false)
    public boolean craftRecipe(World world, TileEntity tile, ItemStackHandler inv) {
        if (tile instanceof TileEntityCookingPot) {
            TileEntityCookingPot pot = (TileEntityCookingPot)tile;
            CookingPotRecipe r = this.findRecipe(tile, inv);
            if (this.canCraft(r, tile, inv)) {
                if (pot.cookProgress >= pot.getMax()) {
                    if (containsRottenItem(inv) && isOutputFood(r)) {
                        this.craftItem(r, world, tile, inv);
                        InvUtils.insert(inv, new ItemStack(ItemPowder.get(Powder.FERTILIZER)), false);
                    } else {
                        InvUtils.insert(world, pot.getBlockPos().up(), inv, this.craftItem(r, world, tile, inv), false);
                    }
                    pot.cookProgress = 0;
                    return true;
                }

                ++pot.cookProgress;
            } else {
                pot.cookProgress = 0;
            }
        }

        return false;
    }

}
