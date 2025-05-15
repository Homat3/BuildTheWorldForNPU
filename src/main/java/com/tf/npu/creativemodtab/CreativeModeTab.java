package com.tf.npu.creativemodtab;

import com.tf.npu.util.Logger;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//用于向原版物品栏添加物品

public class CreativeModeTab
{
    public static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        Logger.LOGGER.info("Adding items to CreativeModeTab >> {}", event.getTabKey().toString());

        Map<ResourceKey<net.minecraft.world.item.CreativeModeTab>, Function<Void, Void>> domap
                = new HashMap<>(14);

        //将物品注册到创造模式物品栏的映射
        domap.put(CreativeModeTabs.BUILDING_BLOCKS, (Void) ->
            {buildingBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.COLORED_BLOCKS, (Void) ->
            {coloredBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.NATURAL_BLOCKS, (Void) ->
            {naturalBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.FUNCTIONAL_BLOCKS, (Void) ->
            {functionalBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.REDSTONE_BLOCKS, (Void) ->
            {redStoneBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.HOTBAR, (Void) ->
            {hotbarBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.SEARCH, (Void) ->
            {searchBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.TOOLS_AND_UTILITIES, (Void) ->
            {toolsAndUtilitiesBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.COMBAT, (Void) ->
            {combatBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.FOOD_AND_DRINKS, (Void) ->
            {foodAndDrinksBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.INGREDIENTS, (Void) ->
            {ingredientsBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.SPAWN_EGGS, (Void) ->
            {snullawnEggsBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.OP_BLOCKS, (Void) ->
            {opBlocksBlocksAdding(event); return null;});
        domap.put(CreativeModeTabs.INVENTORY, (Void) ->
            {inventoryBlocksAdding(event); return null;});

        //执行映射
        Function<Void, Void> fuc = domap.get(event.getTabKey());
        if(fuc != null) fuc.apply(null);
    }

    //在下面添加要往原版创造模式物品栏添加的物品
    private static void buildingBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void coloredBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }
    private static void naturalBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void functionalBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void redStoneBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void hotbarBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void searchBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }
    private static void toolsAndUtilitiesBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void foodAndDrinksBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void ingredientsBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void snullawnEggsBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void combatBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void opBlocksBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }

    private static void inventoryBlocksAdding(BuildCreativeModeTabContentsEvent event)
    {
        //event.accept(NpuItems.EXAMPLE_ITEM);
    }
}
