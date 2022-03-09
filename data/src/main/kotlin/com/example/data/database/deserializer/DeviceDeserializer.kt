package com.example.data.database.deserializer

import android.util.Log
import com.example.data.network.response.DeviceResp
import com.example.data.network.response.DeviceTypeResp
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

internal class DeviceDeserializer(private val gson: Gson) : JsonDeserializer<DeviceResp> {

    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): DeviceResp? {
        val jsonObject = json.asJsonObject
        return when (gson.fromJson(jsonObject.get(DEVICE_TYPE_FIELD_NAME), DeviceTypeResp::class.java)) {
            DeviceTypeResp.LIGHT -> gson.fromJson(jsonObject, DeviceResp.LightResp::class.java)
            DeviceTypeResp.HEATER -> gson.fromJson(jsonObject, DeviceResp.HeaterResp::class.java)
            DeviceTypeResp.ROLLER_SHUTTER -> gson.fromJson(jsonObject, DeviceResp.RollerShutterResp::class.java)
            else -> {
                Log.e("RRR", "null")
                null
            }
        }
    }

    companion object {
        private const val DEVICE_TYPE_FIELD_NAME = "productType"
    }
}
