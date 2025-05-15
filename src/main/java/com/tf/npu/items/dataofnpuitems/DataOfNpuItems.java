package com.tf.npu.items.dataofnpuitems;

import com.tf.npu.util.Reference;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class DataOfNpuItems
{
  public boolean isSpawnEgg;
  public boolean isVehicle;
  public String ID;
  public String creature_ID;

  public Item.Properties createItemProperties()
  {

    Item.Properties properties = new Item.Properties();

    ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(Reference.MODID, ID);
    return properties.setId(ResourceKey.create(ResourceKey.createRegistryKey(LOCATION), LOCATION));
  }
}
