package com.tf.npu.blocks;

import com.tf.npu.blocks.dataofnpublocks.DataOfNpuBlocks;
import com.tf.npu.blocks.dataofnpublocks.ShapeData;
import com.tf.npu.blocks.npublocknewclasses.*;
import com.tf.npu.creativemodtab.dataofnpucreativemodetabs.DataOfNpuCreativeModeTabs;
import com.tf.npu.util.FileDataGetter;
import com.tf.npu.util.FolderDataGetter;
import com.tf.npu.util.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

public class NpuBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Reference.MODID);
    public static final String dataPath = Reference.PATH.get(Reference.PathType.BLOCK);
    public static final List<DataOfNpuCreativeModeTabs> dataList = new FolderDataGetter<>(Reference.PATH.get(Reference.PathType.CREATIVEMODETAB), DataOfNpuCreativeModeTabs.class).getList();

    //创造模式物品栏表
    public static final Map<String, TabType> tabTypeMap = new HashMap<>(0);

    static {
        // 方块注册
        for (DataOfNpuCreativeModeTabs tabData : dataList) {
            tabTypeMap.put(tabData.ENUM_NAME, new TabType(new FolderDataGetter<>(dataPath + '/' + tabData.ID.substring(0, tabData.ID.length() - 4), DataOfNpuBlocks.class).getList()));
        }
        for (TabType tabType : tabTypeMap.values())
            tabType.registerBlocks();
    }

    //一个构造方法
    public static BlockBehaviour.Properties createBlockPropertiesOfMaterial(EnumMaterial material) {
        return BlockBehaviour.Properties.of()
                .strength(material.getStrength())
                .sound(material.getSound())
                .lightLevel(material.getLightLevel())
                .friction(material.getFriction());
    }

    public static TabType getTabType(String ENUM_NAME) {
        return tabTypeMap.get(ENUM_NAME);
    }

    public enum StructureType {
        NORMAL_STRUCTURE,
        HORIZONTAL_DIRECTIONAL_STRUCTURE,
        HORIZONTAL_MULTIPLE_DIRECTIONAL_STRUCTURE,
        NORMAL_HALF_SLAB,
        HORIZONTAL_DIRECTIONAL_HALF_SLAB,
        DOOR_AND_WINDOW
    }

    public enum EnumMaterial {
        //EXAMPLE("example", 硬度, 音效包, (BlockState state) ->{根据不同的blockstate返回不同的亮度值}, 阻力系数，即站在上面的移速),
        IRON("iron", 5.0F, SoundType.METAL, (BlockState state) -> 0, 0.6F),
        ROCK("rock", 2.5F, SoundType.STONE, (BlockState state) -> 0, 0.6F);


        private final String name;
        private final float strength;
        private final SoundType sound;
        private final ToIntFunction<BlockState> lightLevel;
        private final float friction;

        EnumMaterial(String name, float strength, SoundType sound, ToIntFunction<BlockState> lightLevel, float friction) {
            this.name = name;
            this.strength = strength;
            this.sound = sound;
            this.lightLevel = lightLevel;
            this.friction = friction;
        }

        public String getName() {
            return this.name;
        }

        public float getStrength() {
            return strength;
        }

        public SoundType getSound() {
            return sound;
        }

        public ToIntFunction<BlockState> getLightLevel() {
            return lightLevel;
        }

        public float getFriction() {
            return friction;
        }
    }

    public enum EmunShape {
        FULL_SHPAE(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
        NULL_SHPAE(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D),
        HALF_SHPAE_BOTTOM(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
        HALF_SHPAE_TOP(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

        final VoxelShape shape;

        EmunShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            shape = Shapes.box(minX, minY, minZ, maxX, maxY, maxZ);
        }

        public VoxelShape getShape() {
            return shape;
        }
    }

    public enum LoadMethod {
        METICULOUS, ROUGH
    }

    //一些常用属性
    public static class TabType {
        //新方块表
        public final ArrayList<RegistryObject<Block>> blockList;
        //新方块ID映射表
        public final Map<RegistryObject<Block>, String> IDMap;
        //新方块属性表
        final List<DataOfNpuBlocks> dataList;

        public TabType(List<DataOfNpuBlocks> dataList) {
            this.dataList = dataList;
            this.blockList = new ArrayList<>(0);
            this.IDMap = new HashMap<>(0);
        }

        public Item.Properties createBlockItemProperties(RegistryObject<Block> BLOCK) {
            Item.Properties properties = new Item.Properties();
            if (BLOCK.getId() != null) {
                return properties.setId(ResourceKey.create(ResourceKey.createRegistryKey(BLOCK.getId()), BLOCK.getId()));
            }
            return properties;
        }

        public void registerBlocks() {
            for (DataOfNpuBlocks data : dataList) {
                RegistryObject<Block> BLOCK;

                BLOCK = switch (StructureType.valueOf(data.StructureType)) {
                    case NORMAL_STRUCTURE -> {
                        ShapeData shapeData =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath, ShapeData.class).getData();
                        yield BLOCKS.register(data.ID, () ->
                                new NormalStructure(data.createBlockProperties(), LoadMethod.valueOf(data.loadMethod)).setSHAPE(shapeData));
                    }
                    case HORIZONTAL_DIRECTIONAL_STRUCTURE -> {
                        ShapeData shapeData =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath, ShapeData.class).getData();
                        yield BLOCKS.register(data.ID, () ->
                                new HorizontalDirectionalStructure(data.createBlockProperties(), LoadMethod.valueOf(data.loadMethod)).setSHAPE(shapeData));
                    }
                    case HORIZONTAL_MULTIPLE_DIRECTIONAL_STRUCTURE -> {
                        ShapeData shapeData0 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath0, ShapeData.class).getData();
                        ShapeData shapeData15 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath15, ShapeData.class).getData();
                        ShapeData shapeData30 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath30, ShapeData.class).getData();
                        ShapeData shapeData45 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath45, ShapeData.class).getData();
                        ShapeData shapeData60 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath60, ShapeData.class).getData();
                        ShapeData shapeData75 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.modelPath75, ShapeData.class).getData();
                        yield BLOCKS.register(data.ID, () ->
                                new HorizontalMultipleDirectionalStructure(data.createBlockProperties(), LoadMethod.valueOf(data.loadMethod))
                                        .setSHAPE(shapeData0, shapeData15, shapeData30, shapeData45, shapeData60, shapeData75));
                    }

                    case NORMAL_HALF_SLAB -> BLOCKS.register(data.ID, () ->
                            new NormalHalfSlab(data.createBlockProperties()).setCanBeDouble(data.double_enable));
                    case HORIZONTAL_DIRECTIONAL_HALF_SLAB -> BLOCKS.register(data.ID, () ->
                            new HorizontalDirectionalHalfSlab(data.createBlockProperties()).setCanBeDouble(data.double_enable));
                    case DOOR_AND_WINDOW -> {
                        ShapeData shapeData1 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.open_modelPath, ShapeData.class).getData();
                        ShapeData shapeData2 =
                                new FileDataGetter<>("../src/main/resources/assets/npu/" + data.close_modelPath, ShapeData.class).getData();
                        yield BLOCKS.register(data.ID, () ->
                                new DoorAndWindow(data.createBlockProperties(), LoadMethod.valueOf(data.loadMethod)).setSHAPE(shapeData1, shapeData2));
                    }
                };

                blockList.add(BLOCK);
                IDMap.put(BLOCK, data.ID);
            }
        }
    }
}
