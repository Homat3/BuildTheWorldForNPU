package com.tf.npu.creativemodtab;

import com.tf.npu.items.NpuItems;
import com.tf.npu.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


//用于注册新物品栏并向其中添加物品

public class NpuCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);

    //新物品栏ID表
    //public static final String EXAMPLE_BLOCK_TAB_ID = "example_block_tab";
    public static final String AREA_BLOCK_TAB_ID = "area_block_tab";
    public static final String SIGN_BLOCK_TAB_ID = "sign_block_tab";
    public static final String CONSTRUCTION_BLOCK_TAB_ID = "construction_block_tab";
    public static final String INDOOR_BLOCK_TAB_ID = "indoor_block_tab";
    public static final String OUTDOOR_BLOCK_TAB_ID = "outdoor_block_tab";
    public static final String ROAD_BLOCK_TAB_ID = "road_block_tab";
    public static final String PLAYGROUND_BLOCK_TAB_ID = "playground_block_tab";
    public static final String ENTITY_ITEM_TAB_ID = "entity_item_tab";


    //注册新物品栏示例
    /*
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(EXAMPLE_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu" + EXAMPLE_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)                        //设置物品栏位置
            .icon(() -> new ItemStack(NpuItems.TabType.EXAMPLE.itemList.get(0).get()))   //设置物品栏图标为WHAT_YOU_WANT_ITEM的图标
            .displayItems(((itemDisplayParameters, output) ->
                {
                    Logger.LOGGER.info("Adding NPU items to NpuCreativeModeTab >> {}", EXAMPLE_BLOCK_TAB_ID);

                    //加物品
                    for (var BLOCK_ITEM : NpuItems.TabType.ExampleBlock.blockItemList)
                        output.accept(BLOCK_ITEM.get());
                    for (var ITEM : NpuItems.TabType.ExampleBlock.itemList)
                        output.accept(ITEM.get());
                }))
            .build());
     */

    public static final RegistryObject<CreativeModeTab> NPU_AREA_BLOCK_TAB = CREATIVE_MODE_TABS.register(AREA_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + AREA_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.AreaBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                //加物品
                for (var BLOCK_ITEM : NpuItems.TabType.AreaBlock.blockItemList)
                    output.accept(BLOCK_ITEM.get());
                for (var ITEM : NpuItems.TabType.AreaBlock.itemList)
                    output.accept(ITEM.get());
                }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> SIGN_BLOCK_TAB = CREATIVE_MODE_TABS.register(SIGN_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + SIGN_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.SignBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                    {
                        //加物品
                        for (var BLOCK_ITEM : NpuItems.TabType.SignBlock.blockItemList)
                            output.accept(BLOCK_ITEM.get());
                        for (var ITEM : NpuItems.TabType.SignBlock.itemList)
                            output.accept(ITEM.get());
                    }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> NPU_CONSTRUCTION_BLOCK_TAB = CREATIVE_MODE_TABS.register(CONSTRUCTION_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + CONSTRUCTION_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.ConstructionBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                    //加物品
                    for (var BLOCK_ITEM : NpuItems.TabType.ConstructionBlock.blockItemList)
                        output.accept(BLOCK_ITEM.get());
                    for (var ITEM : NpuItems.TabType.ConstructionBlock.itemList)
                        output.accept(ITEM.get());
                }
                )
            .build());

    public static final RegistryObject<CreativeModeTab> INDOOR_BLOCK_TAB = CREATIVE_MODE_TABS.register(INDOOR_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + INDOOR_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.IndoorBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                //加物品
                for (var BLOCK_ITEM : NpuItems.TabType.IndoorBlock.blockItemList)
                    output.accept(BLOCK_ITEM.get());
                for (var ITEM : NpuItems.TabType.IndoorBlock.itemList)
                    output.accept(ITEM.get());
                }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> OUTDOOR_BLOCK_TAB = CREATIVE_MODE_TABS.register(OUTDOOR_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + OUTDOOR_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.OutdoorBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                    {
                        //加物品
                        for (var BLOCK_ITEM : NpuItems.TabType.OutdoorBlock.blockItemList)
                            output.accept(BLOCK_ITEM.get());
                        for (var ITEM : NpuItems.TabType.OutdoorBlock.itemList)
                            output.accept(ITEM.get());
                    }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> NPU_ROAD_BLOCK_TAB = CREATIVE_MODE_TABS.register(ROAD_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + ROAD_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.RoadBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                //加物品
                for (var BLOCK_ITEM : NpuItems.TabType.RoadBlock.blockItemList)
                    output.accept(BLOCK_ITEM.get());
                for (var ITEM : NpuItems.TabType.RoadBlock.itemList)
                    output.accept(ITEM.get());
                }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> NPU_PLAYGROUND_BLOCK_TAB = CREATIVE_MODE_TABS.register(PLAYGROUND_BLOCK_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + PLAYGROUND_BLOCK_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.PlaygroundBlock.blockItemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                //加物品
                for (var BLOCK_ITEM : NpuItems.TabType.PlaygroundBlock.blockItemList)
                    output.accept(BLOCK_ITEM.get());
                for (var ITEM : NpuItems.TabType.PlaygroundBlock.itemList)
                    output.accept(ITEM.get());
                }
            )
            .build());

    public static final RegistryObject<CreativeModeTab> NPU_ENTITY_ITEM_TAB = CREATIVE_MODE_TABS.register(ENTITY_ITEM_TAB_ID, () -> CreativeModeTab.builder()
            .title(Component.translatable("creativemodetab.npu." + ENTITY_ITEM_TAB_ID))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> new ItemStack(NpuItems.TabType.EntityItem.itemList.get(0).get()))
            .displayItems((itemDisplayParameters, output) ->
                {
                    //加物品
                    for (var BLOCK_ITEM : NpuItems.TabType.EntityItem.blockItemList)
                        output.accept(BLOCK_ITEM.get());
                    for (var ITEM : NpuItems.TabType.EntityItem.itemList)
                        output.accept(ITEM.get());
                }
                )
            .build());

}
