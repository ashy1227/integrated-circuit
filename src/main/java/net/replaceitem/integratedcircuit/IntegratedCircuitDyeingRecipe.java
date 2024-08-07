package net.replaceitem.integratedcircuit;

import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class IntegratedCircuitDyeingRecipe extends SpecialCraftingRecipe {
    public IntegratedCircuitDyeingRecipe(CraftingRecipeCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingRecipeInput inventory, World world) {
        int circuitIndex = -1;
        int dyeIndex = -1;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if(stack.isEmpty()) continue;

            if(stack.getItem() instanceof DyeItem) {
                if(dyeIndex != -1) return false;
                dyeIndex = i;
            } else if(stack.getItem() instanceof IntegratedCircuitItem && stack.isIn(IntegratedCircuit.Tags.DYEABLE_INTEGRATED_CIRCUITS_ITEM_TAG)) {
                if(circuitIndex != -1) return false;
                circuitIndex = i;
            } else {
                return false;
            }
        }

        return circuitIndex != -1 && dyeIndex != -1;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput inventory, RegistryWrapper.WrapperLookup wrapperLookup) {
        int circuitIndex = -1;
        int dyeIndex = -1;

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);

            if(stack.isEmpty())
                continue;

            if(stack.getItem() instanceof DyeItem) {
                if(dyeIndex != -1) return ItemStack.EMPTY;
                dyeIndex = i;
            } else if(stack.getItem() instanceof IntegratedCircuitItem && stack.isIn(IntegratedCircuit.Tags.DYEABLE_INTEGRATED_CIRCUITS_ITEM_TAG)) {
                if(circuitIndex != -1) return ItemStack.EMPTY;
                circuitIndex = i;
            } else {
                return ItemStack.EMPTY;
            }
        }

        if(circuitIndex != -1 && dyeIndex != -1) {
            ItemStack circuit = inventory.getStackInSlot(circuitIndex);
            ItemStack dye = inventory.getStackInSlot(dyeIndex);

            IntegratedCircuitBlock block = IntegratedCircuitBlock.fromColor(((DyeItem) dye.getItem()).getColor());
            return circuit.copyComponentsToNewStack(block, 1);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return IntegratedCircuit.CIRCUIT_DYEING_RECIPE;
    }
}