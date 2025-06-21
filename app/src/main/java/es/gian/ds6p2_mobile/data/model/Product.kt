package es.gian.ds6p2_mobile.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val stock: Int,
    @SerializedName("imagen_url") val imagenUrl: String?,
    @SerializedName("id_categoria") val categoriaId: Int?
)
