package com.tf.npu.creativemodtab;

import com.mojang.logging.LogUtils;
import com.tf.npu.creativemodtab.dataofnpucreativemodetabs.DataOfNpuCreativeModeTabs;
import com.tf.npu.items.NpuItems;
import com.tf.npu.util.FolderDataGetter;
import com.tf.npu.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;


//用于注册新物品栏并向其中添加物品

public class NpuCreativeModeTabs {
    public static final org.slf4j.Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Reference.MODID);
    public static final String dataPath = Reference.PATH.get(Reference.PathType.CREATIVEMODETAB);
    //新创造模式物品栏属性表
    public static final List<DataOfNpuCreativeModeTabs> dataList = new FolderDataGetter<>(dataPath, DataOfNpuCreativeModeTabs.class).getList();

    static {
        for (var data : dataList) {
            RegistryObject<CreativeModeTab> CREATIVE_MODE_TAB = switch (data.getIconType()){
                case "Item":
                    List<RegistryObject<Item>> itemList = NpuItems.getTabType(data.ENUM_NAME).itemList;
                    yield  CREATIVE_MODE_TABS.register(data.ID, () -> CreativeModeTab.builder()
                                    .title(Component.translatable(dataPath + "." + Reference.MODID + "." + data.ID))
                                    .withTabsBefore(CreativeModeTabs.COMBAT)
                                    .displayItems((itemDisplayParameters, output) ->
                                            {
                                                //加物品
                                                for (var BLOCK_ITEM : NpuItems.getTabType(data.ENUM_NAME).blockItemList)
                                                    output.accept(BLOCK_ITEM.get());
                                                for (var ITEM : NpuItems.getTabType(data.ENUM_NAME).itemList)
                                                    output.accept(ITEM.get());
                                            }
                                    )
                                    .icon(() -> new ItemStack(itemList.get(data.getIconIndex()).get()))
                                    .build());
                case "BlockItem":
                    List<RegistryObject<BlockItem>> blockItemList = NpuItems.getTabType(data.ENUM_NAME).blockItemList;
                    yield  CREATIVE_MODE_TABS.register(data.ID, () -> CreativeModeTab.builder()
                                    .title(Component.translatable(dataPath + "." + Reference.MODID + "." + data.ID))
                                    .withTabsBefore(CreativeModeTabs.COMBAT)
                                    .displayItems((itemDisplayParameters, output) ->
                                            {
                                                //加物品
                                                for (var BLOCK_ITEM : NpuItems.getTabType(data.ENUM_NAME).blockItemList)
                                                    output.accept(BLOCK_ITEM.get());
                                                for (var ITEM : NpuItems.getTabType(data.ENUM_NAME).itemList)
                                                    output.accept(ITEM.get());
                                            }
                                    )
                                    .icon(() -> new ItemStack(blockItemList.get(data.getIconIndex()).get()))
                                    .build());
                default:
                    yield null;
            };
            if (CREATIVE_MODE_TAB != null) {
                LOGGER.info("Registered creative mode tab: {}", CREATIVE_MODE_TAB.getId());
            }
        }
    }
}
