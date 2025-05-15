# BuildTheWorldForNPU
Made by Infinomat

## Convention
- When registering new things and storing new resource files, be organized, write the code where it should be, and put things in the right place.
- If possible, add logs.
- Keep the code clean.

## Refactoring Structure Overview
- npu
    - blocks  
        - dataofnpublocks                 Attribute data for new blocks
        - npublocknewclasses              Templates for new blocks
        - NpuBlocks.class                 All new blocks will be automatically registered here
    - creativemodtab
        - dataofnpucreativemodetabs       Attribute data for new creative mode tabs
        - CreativeModeTab.class           Used to add new items to the vanilla creative mode tabs
        - NpuCreativeModeTabs.class       All new creative mode tabs should be registered here
    - entities
        - npuentitynewclasses             Templates for new entities
        - NpuEntities.class               All new entities should be registered here
        - NpuEntitySubscriber.class       Links new entities and new rendering methods to events
    - items
        - dataofnpublocks                 Attribute data for new items
        - npuitemnewclasses               Templates for new items
        - NpuItems.class                  All new items (including block items) will be automatically registered here
    - util
        - FileDataGetter                  Get JSON data from files
        - FolderDataGetter                Get JSON data from folders
        - Logger.class                    Used to output logs
        - Reference.class                 Used to obtain mod basic information
        - Register.class                  Used to provide new thing registrars
    - Config.class                        Do not modify the mod properties
    - NPU                                 Main class (generally do not move it)

## How to Operate

### Register New Blocks
It is recommended to modify the block IDs of the original mod as little as possible, because Minecraft may not recognize them after transplantation, but if you encounter some excessive IDs, you can change them.

1. According to the information of the block in the original mod, determine what type of block it is.

    NORMAL_STRUCTURE                                Normal block

    HORIZONTAL_DIRECTIONAL_STRUCTURE                Block with four directions (east, south, west, north)

    HORIZONTAL_MULTIPLE_DIRECTIONAL_STRUCTURE       Block with twelve directions

    NORMAL_HALF_SLAB                                Normal stackable slab block

    HORIZONTAL_DIRECTIONAL_HALF_SLAB                Stackable slab block with four directions (east, south, west, north)

    DOOR_AND_WINDOW                                 Block with open and close two models

    (More types are coming soon)
2. According to the information of the block in the original mod, determine the material of the block.

    IRON

    ROCK

    (More types are coming soon)
3. Write the properties into a json file and save it to the folder you want in [resources/data/npu/block](src/main/resources/data/npu/block) under the creative mode tab folder.
4. Write the block state to [resources/assets/npu/blockstates](src/main/resources/assets/npu/blockstates).
5. Write the corresponding item state to [resources/assets/npu/items](src/main/resources/assets/npu/items).
6. Import the model file from the original mod block model file to this mod [resources/assets/npu/models/block](src/main/resources/assets/npu/models/block), and classify it as appropriate.
7. Import the corresponding texture from the original mod texture file to this mod [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block), remember to search in [Texture Correspondence Table.txt](Texture Correspondence Table.txt) before importing to see if the refactored mod already has it, don't duplicate, and need to modify the path in the imported json file.
8. According to the translation file of the original mod, add the translation to the translation file [resources/assets/npu/lang](src/main/resources/assets/npu/lang).
9. Finally, remember to update [Texture Correspondence Table.txt](Texture Correspondence Table.txt) and [ID Change Table.txt](ID Change Table.txt) (if you have changed the id, this is very important).

NpuBlocks.class declares common material properties and common volume model properties enumeration, you can add your own if needed.

### Register New Items

1. Write the properties into a json file and save it to the folder you want in [resources/data/npu/item](src/main/resources/data/npu/item) under the creative mode tab folder.
2. Write the corresponding item state to [resources/assets/npu/items](src/main/resources/assets/npu/items).
3. Import the model file from the original mod item model file to this mod [resources/assets/npu/models/item](src/main/resources/assets/npu/models/item), and classify it as appropriate.
4. Import the corresponding texture from the original mod texture file to this mod [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block), remember to search in [Texture Correspondence Table.txt](Texture Correspondence Table.txt) before importing to see if the refactored mod already has it, don't duplicate, and need to modify the path in the imported json file.
5. According to the translation file of the original mod, add the translation to the translation file [resources/assets/npu/lang](src/main/resources/assets/npu/lang).
6. Finally, remember to update [Texture Correspondence Table.txt](Texture Correspondence Table.txt) and [ID Change Table.txt](ID Change Table.txt) (if you have changed the id).

### Register New Entities
Note the different field naming EXAMPLE,EXAMPLE_ID

Since the registration of entities varies greatly, currently it is not supported to create entities directly from resource files through json files.

The details of creating from code are more complicated, but the basic operation process is as follows:
1. You can choose to inherit the existing entity of the original version, or inherit the entity template I have already created (NpuVehicle is coming soon), or create an entity from a more basic class yourself (this will be troublesome).
2. Implement the features you want.
3. Create an entity rendering class.
4. Create an entity model class (if you inherit the existing entity of the original version, it is not required).
5. Register the entity rendering class in NpuEntitySubscriber.class (if your base class is not Mob or its subclass, it is not required).
6. Register the entity in NpuEntities.class.
7. Add translation to the translation file [resources/assets/npu/lang](src/main/resources/assets/npu/lang).

### Register New Creative Mode Tabs

1. Write the properties into a json file and save it to [resources/data/npu/creativemodetabs](src/main/resources/data/npu/creativemodetab).
2. You can choose to create a directory with the same name as your id in [resources/data/npu/block](src/main/resources/data/npu/block) (block database) and [resources/data/npu/item](src/main/resources/data/npu/item) (item database), the blocks or items placed in it will automatically be added to this item tab.
3. Add translation to the translation file [resources/assets/npu/lang](src/main/resources/assets/npu/lang).

## Postscript
- If there are new template or API requirements, or if the existing templates or APIs have more optimized solutions, please let me know.

[简体中文](Instructions.zh_cn.md)

[Back](README.md)