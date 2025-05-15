# BuildTheWorldForNPU
Made by Infinomat

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
        - dataofnpucreativemodetabs       新创造模式物品栏的属性数据
        - CreativeModeTab.class           用于向原版创造模式物品栏添加新物品
        - NpuCreativeModeTabs.class       一切新创造模式物品栏应该在此注册
    - entities
        - npuentitynewclasses             新实体的模板
        - NpuEntities.class               一切新实体应该在此注册
        - NpuEntitySubscriber.class       向事件加入新实体与新渲染方式的链接
    - items
        - dataofnpublocks                 新物品的属性数据
        - npuitemnewclasses               新物品的模板
        - NpuItems.class                  一切新物品（包括方块物品）将在此自动注册
    - util
        - FileDataGetter                  获取文件的json数据
        - FolderDataGetter                获取文件夹的json数据
        - Logger.class                    用于输出日志
        - Reference.class                 用于获得模组基本信息
        - Register.class                  用于提供新东西的注册器
    - Config.class                        模组属性别改就是
    - NPU                                 主类（一般不动它）

## 如何操作

### 注册新方块
建议少修改原模组中的方块ID，因为移植后Minecraft会认不得，但是遇到某些过分的ID就改了吧

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

（更多类型敬请期待）
3. 将属性写入json文件中并存放到 [resources/data/npu/block](src/main/resources/data/npu/block) 中你希望的创造模式物品栏文件夹下
4. 将方块状态写到 [resources/assets/npu/blockstates](src/main/resources/assets/npu/blockstates) 中
5. 将对应物品状态写到 [resources/assets/npu/items](src/main/resources/assets/npu/items) 中
6. 从原模组方块模型文件中导入模型文件到本模组 [resources/assets/npu/models/block](src/main/resources/assets/npu/models/block) 看情况做好分类
7. 从原模组贴图文件中导入对应贴图到本模组 [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block) 中，导入前记得在 [贴图对应表.txt](贴图对应表.txt) 搜索一下重构模组这里是不是已经有了，别搞重复了，而且需要对导入的json文件中的路径进行对应的修改
8. 根据原模组翻译文件向翻译文件 [resources/assets/npu/lang](src/main/resources/assets/npu/lang) 中添加翻译
9. 最后一定要记得更新 [贴图对应表.txt](贴图对应表.txt) 和 [id变动表.txt](id变动表.txt)（如果你动了id的话，这很重要）

NpuBlocks.class里声明了常用材料属性和常用体积模型属性的枚举，有需求可以自己加

### 注册新物品

1. 将属性写入json文件中并存放到 [resources/data/npu/item](src/main/resources/data/npu/item) 中你希望的创造模式物品栏文件夹下
2. 将对应物品状态写到 [resources/assets/npu/items](src/main/resources/assets/npu/items) 中
3. 从原模组物品模型文件中导入模型文件到本模组 [resources/assets/npu/models/item](src/main/resources/assets/npu/models/item) 看情况做好分类
4. 从原模组贴图文件中导入对应贴图到本模组 [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block) 中，导入前记得在 [贴图对应表.txt](贴图对应表.txt) 搜索一下重构模组这里是不是已经有了，别搞重复了，而且需要对导入的json文件中的路径进行对应的修改
5. 根据原模组翻译文件向翻译文件 [resources/assets/npu/lang](src/main/resources/assets/npu/lang) 中添加翻译
6. 最后一定要记得更新 [贴图对应表.txt](贴图对应表.txt) 和 [id变动表.txt](id变动表.txt)（如果你动了id的话）

### 注册新实体
注意涉及到的不同的字段命名EXAMPLE,EXAMPLE_ID

由于实体的注册变数较大，所以目前还不支持直接从资源文件中通过json文件创建。

从代码中创建细节比较多，但基本操作流程如下
1. 你可以选择继承原版已有的实体，或者继承我已经创建好的实体模板（NpuVehicle 敬请期待），或是从更基础的类自己创建一个实体（这会很麻烦）
2. 实现你想要的功能
3. 创建一个实体渲染类
4. 创建一个实体模型类（如果是继承原版已有实体则不需要）
5. 将实体渲染类注册到NpuEntitySubscriber.class中（如果你的基类不是Mob或其子类的话则不需要）
6. 将实体注册到NpuEntities.class中
7. 向翻译文件 [resources/assets/npu/lang](src/main/resources/assets/npu/lang) 中添加翻译

### 注册新创造模式物品栏

1. 将属性写入json文件中并存放到 [resources/data/npu/creativemodetabs](src/main/resources/data/npu/creativemodetab) 中
2. 你可以选择在 [resources/data/npu/block](src/main/resources/data/npu/block)（方块的数据库）跟[resources/data/npu/item](src/main/resources/data/npu/item)（物品的数据库）中创建与你的id名相一致的目录，放入其中的方块或物品将自动加入这个物品栏中
3. 向翻译文件 [resources/assets/npu/lang](src/main/resources/assets/npu/lang) 中添加翻译

## 后记
-有新的模板或API的需求或者已有的模板或API有更优化的方案可以告诉我

[English](Instructions.en_us.md)

[返回](README.md)