package com.tf.npu.items;

import com.mojang.logging.LogUtils;
import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.creativemodtab.dataofnpucreativemodetabs.DataOfNpuCreativeModeTabs;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.items.dataofnpuitems.DataOfNpuItems;
import com.tf.npu.items.npuitemnewclasses.VehicleItem;
import com.tf.npu.util.FolderDataGetter;
import com.tf.npu.util.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NpuItems {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Reference.MODID);
    public static final String dataPath = Reference.PATH.get(Reference.PathType.ITEM);
    public static final List<DataOfNpuCreativeModeTabs> dataList = new FolderDataGetter<>(Reference.PATH.get(Reference.PathType.CREATIVEMODETAB), DataOfNpuCreativeModeTabs.class).getList();

    //创造模式物品栏表
    public static final Map<String, TabType> tabTypeMap = new HashMap<>(0);

    static {
        // 物品注册
        for (DataOfNpuCreativeModeTabs tabData : dataList) {
            tabTypeMap.put(tabData.ENUM_NAME, new TabType(tabData, new FolderDataGetter<>(dataPath + '/' + tabData.ID.substring(0, tabData.ID.length() - 4), DataOfNpuItems.class).getList()));
        }
        for (TabType tabType : tabTypeMap.values()) {
            tabType.registerItems();
        }
    }

    public static TabType getTabType(String ENUM_NAME) {
        return tabTypeMap.get(ENUM_NAME);
    }

    public static class TabType {
        public final ArrayList<DeferredItem<BlockItem>> blockItemList;
        public final DataOfNpuCreativeModeTabs data;
        public final ArrayList<DeferredItem<Item>> itemList;
        //新方块物品表
        final NpuBlocks.TabType blockItemTabType;
        //新纯物品表
        final List<DataOfNpuItems> itemDataList;


        public TabType(DataOfNpuCreativeModeTabs tabData, List<DataOfNpuItems> itemDataList) {
            data = tabData;
            this.blockItemTabType = NpuBlocks.getTabType(tabData.ENUM_NAME);
            this.blockItemList = new ArrayList<>(0);

            this.itemDataList = itemDataList;
            this.itemList = new ArrayList<>(0);
        }


        public void registerItems() {
            if (blockItemTabType != null) {
                for (var BLOCK : blockItemTabType.blockList) {
                    DeferredItem<BlockItem> BLOCK_ITEM = ITEMS.register(blockItemTabType.IDMap.get(BLOCK), () ->
                            new BlockItem(BLOCK.get(), blockItemTabType.createBlockItemProperties(BLOCK)));
                    blockItemList.add(BLOCK_ITEM);
                    LOGGER.info("Registered blockitem: {}", BLOCK_ITEM.getId());
                }
            }
            for (DataOfNpuItems data : itemDataList) {
                DeferredItem<Item> ITEM;
                if (data.isSpawnEgg)
                    ITEM = ITEMS.register(data.ID, () ->
                            new SpawnEggItem(NpuEntities.MOB_ID_MAP.get(data.creature_ID).get(), data.createItemProperties()));
                else if (data.isVehicle)
                    ITEM = ITEMS.register(data.ID, () ->
                            new VehicleItem(NpuEntities.VEHICLE_ID_MAP.get(data.creature_ID).get(), data.createItemProperties()));
                else
                    ITEM = ITEMS.register(data.ID, () -> new Item(data.createItemProperties()));

                itemList.add(ITEM);
                LOGGER.info("Registered item: {}", ITEM.getId());
            }
        }
    }
}
