# BuildTheWorldForNPU
Made by Infinomat

## Conventions
- Register new things and store new resource files in a clear and organized manner, placing code where it belongs and things where they should be.
- If possible, add logs to everything.
- Keep the code clean and well-organized.

## Refactoring Structure Overview
- npu
    - blocks  
        - dataofnpublocks                 Properties of new blocks
        - npublocknewclasses              Templates for new blocks
        - NpuBlocks.class                 All new blocks will be automatically registered here
    - creativemodtab
        - dataofnpucreativemodetabs       Properties of new creative mode tabs
        - CreativeModeTab.class           Used to add new items to the original creative mode tabs
        - NpuCreativeModeTabs.class       All new creative mode tabs should be registered here
    - entities
        - npuentitynewclasses             Templates for new entities
        - NpuEntities.class               All new entities should be registered here
        - NpuEntitySubscriber.class       Links new entities and their rendering methods to events
    - items
        - dataofnpublocks                 Properties of new items
        - npuitemnewclasses               Templates for new items
        - NpuItems.class                  All new items (including block items) will be automatically registered here
    - util
        - FileDataGetter                  Get JSON data from files
        - FolderDataGetter                Get JSON data from folders
        - Logger.class                    Used to output logs
        - Reference.class                 Used to obtain basic information about the mod
        - Register.class                  Provides registrars for new things
    - Config.class                        Do not modify the mod properties
    - NPU                                 Main class (generally not modified)

## How to Operate

### Registering New Blocks
Note that you should avoid modifying block IDs in the original mod as much as possible, as Minecraft may not recognize them after transplantation. However, if you encounter overly problematic IDs, you can change them.

1. Determine the type of block based on the information in the original mod:

    NORMAL_STRUCTURE                                Ordinary block

    HORIZONTAL_DIRECTIONAL_STRUCTURE                Block with four directions (east, south, west, north)

    HORIZONTAL_MULTIPLE_DIRECTIONAL_STRUCTURE       Block with twelve directions

    NORMAL_HALF_SLAB                                Ordinary stackable slab block

    HORIZONTAL_DIRECTIONAL_HALF_SLAB                Stackable slab block with four directions (east, south, west, north)

    DOOR_AND_WINDOW                                 Block with open and closed models

    (More types to come)
2. Determine the material of the block based on the information in the original mod:

    IRON

    ROCK

   (More types to come)
3. Write the properties into a JSON file and save it in the desired creative mode tab folder under [resources/data/npu/block](src/main/resources/data/npu/block).
4. Write the block state to [resources/assets/npu/blockstates](src/main/resources/assets/npu/blockstates).
5. Write the corresponding item state to [resources/assets/npu/items](src/main/resources/assets/npu/items).
6. Import model files from the original mod's block model files into this mod's [resources/assets/npu/models/block](src/main/resources/assets/npu/models/block), and categorize them as needed.
7. Import the corresponding textures from the original mod's texture files into this mod's [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block), search [Texture Correspondence Table.txt](Texture Correspondence Table.txt) first to see if the refactored mod already has it, avoid duplication, and modify the paths in the imported JSON files accordingly.
8. Add translation files based on the original mod's translation files to [resources/assets/npu/lang](src/main/resources/assets/npu/lang).
9. Remember to update [Texture Correspondence Table.txt](Texture Correspondence Table.txt) and [ID Change Table.txt](ID Change Table.txt) (if you have modified the IDs).

NpuBlocks.class declares common material properties and common volume model properties enums. If needed, you can add more.

### Registering New Items

1. Write the properties into a JSON file and save it in the desired creative mode tab folder under [resources/data/npu/item](src/main/resources/data/npu/item).
2. Write the corresponding item state to [resources/assets/npu/items](src/main/resources/assets/npu/items).
3. Import model files from the original mod's item model files into this mod's [resources/assets/npu/models/item](src/main/resources/assets/npu/models/item), and categorize them as needed.
4. Import the corresponding textures from the original mod's texture files into this mod's [resources/assets/npu/textures/block](src/main/resources/assets/npu/textures/block), search [Texture Correspondence Table.txt](Texture Correspondence Table.txt) first to see if the refactored mod already has it, avoid duplication, and modify the paths in the imported JSON files accordingly.
5. Remember to update [Texture Correspondence Table.txt](Texture Correspondence Table.txt) and [ID Change Table.txt](ID Change Table.txt) (if you have modified the IDs).

### Registering New Entities
Note the different field naming conventions for EXAMPLE, EXAMPLE_ID.

1. First, clarify whether you are working on a plain entity or a geo entity.
2. Plain entity:
   1. Create a package in npuentitynewclasses/normal/.
   2. Add the entity class and entity renderer class to the package.
   3. The entity class needs to implement the registerAttributes() method to set entity attributes, while the entity renderer class needs to specify the texture and scaling method.
   4. Register in NpuEntities, note to write into ID_MAP.
   5. Add JSON data for spawn eggs and other resource files.
3. Geo entity
    
    (To be expected)

Involves resource files lang, textures/entity, models/item.

### Registering New Creative Mode Tabs
Note the different field naming conventions for EXAMPLE_TAB, EXAMPLE_TAB_ID.

1. Register in NpuCreativeModeTab at the appropriate location (declare the tab field and corresponding tab ID field).
2. Add the corresponding enum in NpuBlocks.TabType and NpuItems.TabType (if there is content, you also need to create the corresponding directory in blockstates/data/ and itemdata/).

## Postscript
- If there are new template or API requirements, or if there are more optimized solutions for existing templates or APIs, please let me know.

[简体中文](Instructions.zh_cn.md)

[Go Back](README.en_us.md)