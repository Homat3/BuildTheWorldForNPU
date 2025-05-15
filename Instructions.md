# BuildTheWorldForNPU
Made by Homat3

## 公约
- 注册新东西以及存放新资源文件时，要条理清晰，把代码写到该写的地方，把东西放到该放的位置上
- 日志能加最好都加上
- 保持代码美观

## 重构结构简述
- npu
    - blocks  
        - dataofnpublocks                 新方块的属性数据
        - npublocknewclasses              新方块的模板
        - NpuBlocks.class                 一切新方块将在此自动注册
    - creativemodtab
        - CreativeModeTab.class           用于向原版创造模式物品栏添加新物品
        - NpuCreativeModeTabs.class       一切新创造模式物品栏应该在此注册
    - entities
        - npuentitynewclasses
            - geo                         用到geo组件的新实体
            - normal                      普通的新实体
        - NpuEntities.class               一切新实体应该在此注册
        - NpuEntitySubscriber.class       向事件加入新实体与新渲染方式的链接
    - items
        - dataofnpublocks                 新物品的属性数据
        - NpuItems.class                  一切新物品（包括方块物品）将在此自动注册
    - util                                一些工具，一般不用改它
        - FileDataGetter                  获取文件的json数据
        - FolderDataGetter                获取文件夹的json数据
        - Logger.class                    用于输出日志
        - Reference.class                 用于获得模组基本信息
        - Register.class                  用于提供新东西的注册器
    - Config.class                        别改就是
    - NPU                                 主类（一般不动它）

## 如何操作

### 注册新方块
注意少修改原模组中的方块ID，因为移植后Minecraft会认不得，但是遇到某些过分的ID就改了吧

1. 根据原模组中方块的信息判断是哪种方块 

    NORMAL_STRUCTURE                                普通方块

    HORIZONTAL_DIRECTIONAL_STRUCTURE                具有东南西北四个方向的方块

    HORIZONTAL_MULTIPLE_DIRECTIONAL_STRUCTURE       具有十二个方向的方块

    NORMAL_HALF_SLAB                                普通可堆叠的台阶方块

    HORIZONTAL_DIRECTIONAL_HALF_SLAB                具有东南西北四个方向的可堆叠的台阶方块

    DOOR_AND_WINDOW                                 具有开关两种模型的方块

    （更多类型敬请期待）
2. 根据原模组中方块的信息判断方块材质

    IRON

    ROCK
3. 将属性写入json文件中并存放到blockstates/data/中你希望的创造模式物品栏文件夹下
4. 从原模组资源文件中导入资源，导入前记得搜索一下重构模组这里是不是已经有了，别搞重复了，而且需要对导入的json文件中的路径进行亿点修改

涉及资源lang，blockstates，models/blocks，models/item，textures/block

NpuBlocks.class里声明了常用材料属性和常用体积模型属性的枚举，有需求可以自己加

### 注册新物品

1. 将属性写入json文件中并存放到itemdata/中，你希望的创造模式物品栏文件夹下
2. 从原模组资源文件中导入资源，导入前记得搜索一下重构模组这里是不是已经有了，别搞重复了，而且需要对导入的json文件中的路径进行亿点修改

涉及资源lang，models/item，textures/item

### 注册新实体
注意涉及到的不同的字段命名EXAMPLE,EXAMPLE_ID

1. 先明确你是搞朴素的实体，还是用geo的实体
2. 朴素实体：
   1. 在npuentitynewclasses/normal/中创建包
   2. 向包中添加实体类和实体渲染类
   3. 实体类中需要实现registerAttributes()方法来设置实体属性，而实体渲染类中需要指定贴图和缩放方式
   4. 在NpuEntities进行注册，注意写入ID_MAP中
   5. 加入生物蛋的json数据，以及其他资源文件
3. geo实体
    
    （敬请期待）

涉及资源lang，textures/entity，models/item

### 注册新创造模式物品栏
注意涉及到的不同的字段命名EXAMPLE_TAB,EXAMPLE_TAB_ID

1. 在NpuCreativeModeTab中相应位置注册（声明物品栏字段以及对应物品栏ID字段）
2. 在NpuBlocks.TabType和NpuItems.TabType中加入相应的枚举（如果里面有东西还需在blockstates/data/和itemdata/中创建相应目录）

## 后记
-有新的模板或API的需求或者已有的模板或API有更优化的方案可以告诉我