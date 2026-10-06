/*
 * Locate Plus
 * Copyright (C) 2026 forest_mask
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package dev.locateplus.compat;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * The handful of calls that differ between game versions.
 *
 * 1.20.5 replaced item NBT with components, which changed how a custom name is read, how the
 * contents of a shulker box are reached, and how enchantments and status effects are keyed. Every
 * one of those is wrapped here so the rest of the mod can be written once. Version-specific types
 * are named in full rather than imported, because an import of a class that does not exist yet
 * fails to compile even when the code using it is switched off.
 */
public final class Compat {

    private Compat() {
    }

    /** Whether the stack carries a name a player gave it. */
    public static boolean hasCustomName(ItemStack stack) {
        //? if >=1.20.5 {
        /*return stack.contains(net.minecraft.component.DataComponentTypes.CUSTOM_NAME);
        *///?} else
        return stack.hasCustomName();
    }

    /**
     * Contents of a container held as an item, a shulker box being the obvious case.
     *
     * @return the non-empty stacks inside, or an empty list when this is not a container
     */
    public static List<ItemStack> nestedItems(ItemStack container) {
        //? if >=1.20.5 {
        /*net.minecraft.component.type.ContainerComponent held =
                container.get(net.minecraft.component.DataComponentTypes.CONTAINER);
        if (held == null) {
            return List.of();
        }
        return held.streamNonEmpty().toList();
        *///?} else {
        if (!(container.getItem() instanceof net.minecraft.item.BlockItem)) {
            return List.of();
        }
        NbtCompound nbt = container.getNbt();
        if (nbt == null
                || !nbt.contains("BlockEntityTag", net.minecraft.nbt.NbtElement.COMPOUND_TYPE)) {
            return List.of();
        }
        NbtCompound blockTag = nbt.getCompound("BlockEntityTag");
        if (!blockTag.contains("Items", net.minecraft.nbt.NbtElement.LIST_TYPE)) {
            return List.of();
        }
        net.minecraft.nbt.NbtList items =
                blockTag.getList("Items", net.minecraft.nbt.NbtElement.COMPOUND_TYPE);
        List<ItemStack> out = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ItemStack inner;
            try {
                inner = ItemStack.fromNbt(items.getCompound(i));
            } catch (Throwable t) {
                continue;
            }
            if (inner != null && !inner.isEmpty()) {
                out.add(inner);
            }
        }
        return out;
        //?}
    }

    /** Saved data of a block entity, used to read machine progress. */
    public static NbtCompound blockEntityNbt(BlockEntity be, World world) {
        //? if >=1.20.5 {
        /*return be.createNbt(world.getRegistryManager());
        *///?} else
        return be.createNbt();
    }

    /** Whether the stack has any enchantment on it. */
    public static boolean hasEnchantments(ItemStack stack) {
        //? if >=1.20.5 {
        /*return net.minecraft.enchantment.EnchantmentHelper.hasEnchantments(stack);
        *///?} else
        return stack.hasEnchantments();
    }

    /**
     * Enchantments on a stack as {@code "sharpness 5"} lines.
     *
     * Returned already formatted because the two versions disagree about what an enchantment even
     * is: a plain object before 1.20.5, a registry entry after.
     */
    public static List<String> enchantmentLines(ItemStack stack) {
        List<String> out = new ArrayList<>();
        //? if >=1.20.5 {
        /*net.minecraft.component.type.ItemEnchantmentsComponent found =
                net.minecraft.enchantment.EnchantmentHelper.getEnchantments(stack);
        for (net.minecraft.registry.entry.RegistryEntry<net.minecraft.enchantment.Enchantment> entry
                : found.getEnchantments()) {
            String name = entry.getKey()
                    .map(key -> dev.locateplus.report.Msg.words(key.getValue().getPath()))
                    .orElseGet(() -> entry.value().toString());
            out.add(name + " " + found.getLevel(entry));
        }
        *///?} else {
        net.minecraft.enchantment.EnchantmentHelper.get(stack).forEach((enchantment, level) -> {
            Identifier id = net.minecraft.registry.Registries.ENCHANTMENT.getId(enchantment);
            out.add((id == null ? enchantment.toString()
                    : dev.locateplus.report.Msg.words(id.getPath())) + " " + level);
        });
        //?}
        return out;
    }

    /** Registry id of a status effect, or null when it has none. */
    public static Identifier statusEffectId(StatusEffectInstance instance) {
        //? if >=1.20.5 {
        /*return instance.getEffectType().getKey().map(key -> key.getValue()).orElse(null);
        *///?} else
        return net.minecraft.registry.Registries.STATUS_EFFECT.getId(instance.getEffectType());
    }

    /** Whether a status effect is a good one, which decides the colour it is printed in. */
    public static boolean statusEffectBeneficial(StatusEffectInstance instance) {
        //? if >=1.20.5 {
        /*return instance.getEffectType().value().isBeneficial();
        *///?} else
        return instance.getEffectType().isBeneficial();
    }
}
