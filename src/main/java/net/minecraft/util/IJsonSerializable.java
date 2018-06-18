package net.minecraft.util;

import com.google.gson.JsonElement;

public interface IJsonSerializable {

    void fromJson(JsonElement jsonelement);

    JsonElement getSerializableElement();
}
