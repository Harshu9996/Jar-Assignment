package com.myjar.jarassignment.data.model

import com.google.gson.annotations.SerializedName

data class ComputerItem(
    val id: String,
    val name: String,
    val data: ItemData? = null
){
    fun doesSatisfyQuery(query:String):Boolean{
        return name.contains(query)
    }
}

data class ItemData(
    val color: String? = null,
    val capacity: String? = null,
    @SerializedName("price") val price: Double? = null,
    @SerializedName("capacity GB") val capacityGB: Int? = null,
    @SerializedName("Screen size") val screenSize: Double? = null,
    @SerializedName("Description") val description: String? = null,
    @SerializedName("Generation") val generation: String? = null,
    @SerializedName("Strap Colour") val strapColour: String? = null,
    @SerializedName("Case Size") val caseSize: String? = null,
    @SerializedName("CPU model") val cpuModel: String? = null,
    @SerializedName("Hard disk size") val hardDiskSize: String? = null
)
