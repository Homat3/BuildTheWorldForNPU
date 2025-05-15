package com.tf.npu.items;

import com.tf.npu.blocks.NpuBlocks;
import com.tf.npu.entities.NpuEntities;
import com.tf.npu.items.dataofnpuitems.DataOfNpuItems;
import com.tf.npu.items.npuitemnewclasses.VehicleItem;
import com.tf.npu.util.FolderDataGetter;
import com.tf.npu.util.Reference;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class NpuItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);
    public static final String dataPath = "itemdata/";


    //注册BlockItem和Item
    static
    {
        for (var tabType : TabType.values())
        {
            tabType.registerItems();
        }
    }

    public enum TabType
    {
        AreaBlock(NpuBlocks.TabType.AreaBlock,
                new FolderDataGetter<>(dataPath + "area_block", DataOfNpuItems.class).getList()),
        SignBlock(NpuBlocks.TabType.SignBlock,
                new FolderDataGetter<>(dataPath + "sign_block", DataOfNpuItems.class).getList()),
        ConstructionBlock(NpuBlocks.TabType.ConstructionBlock,
                new FolderDataGetter<>(dataPath + "construction_block", DataOfNpuItems.class).getList()),
        IndoorBlock(NpuBlocks.TabType.IndoorBlock,
                new FolderDataGetter<>(dataPath + "indoor_block", DataOfNpuItems.class).getList()),
        OutdoorBlock(NpuBlocks.TabType.OutdoorBlock,
                new FolderDataGetter<>(dataPath + "outdoor_block", DataOfNpuItems.class).getList()),
        RoadBlock(NpuBlocks.TabType.RoadBlock,
                new FolderDataGetter<>(dataPath + "road_block", DataOfNpuItems.class).getList()),
        PlaygroundBlock(NpuBlocks.TabType.PlaygroundBlock,
                new FolderDataGetter<>(dataPath + "playground_block", DataOfNpuItems.class).getList()),
        EntityItem(
                new FolderDataGetter<>(dataPath + "entity_item", DataOfNpuItems.class).getList());


        //新方块物品表
        final NpuBlocks.TabType blockItemTabType;
        public final ArrayList<RegistryObject<BlockItem>> blockItemList;
        //新纯物品表
        final List<DataOfNpuItems> itemDataList;
        public final ArrayList<RegistryObject<Item>> itemList;


        TabType(NpuBlocks.TabType blockItemTabType, List<DataOfNpuItems> itemDataList)
        {
            this.blockItemTabType = blockItemTabType;
            this.blockItemList = new ArrayList<>(1);

            this.itemDataList = itemDataList;
            this.itemList = new ArrayList<>(1);
        }
        TabType(List<DataOfNpuItems> itemDataList)
        {
            this.blockItemTabType = null;
            this.blockItemList = new ArrayList<>(1);

            this.itemDataList = itemDataList;
            this.itemList = new ArrayList<>(1);
        }


        public void registerItems()
        {
            if (blockItemTabType != null)
            {
                for (var BLOCK : blockItemTabType.blockList)
                {
                    blockItemList.add(ITEMS.register(blockItemTabType.IDMap.get(BLOCK), () ->
                            new BlockItem(BLOCK.get(), blockItemTabType.createBlockItemProperties(BLOCK))));
                }
            }
            for (DataOfNpuItems data : itemDataList)
            {
                RegistryObject<Item> ITEM;
                if (data.isSpawnEgg)
                    ITEM = ITEMS.register(data.ID, () ->
                            new SpawnEggItem(NpuEntities.MOB_ID_MAP.get(data.creature_ID).get(), data.createItemProperties()));
                else if (data.isVehicle)
                    ITEM = ITEMS.register(data.ID, () ->
                            new VehicleItem(NpuEntities.VEHICLE_ID_MAP.get(data.creature_ID).get(), data.createItemProperties()));
                else
                    ITEM = ITEMS.register(data.ID, () -> new Item(data.createItemProperties()));

                itemList.add(ITEM);
            }
        }
    }
}
