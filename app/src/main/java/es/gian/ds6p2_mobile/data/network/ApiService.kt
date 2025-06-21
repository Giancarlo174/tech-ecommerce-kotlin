package es.gian.ds6p2_mobile.data.network

import es.gian.ds6p2_mobile.data.model.Category
import es.gian.ds6p2_mobile.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

// Modelos para la respuesta completa
data class CategoriesResponse(
    val success: Boolean,
    val categories: List<Category>
)

data class ProductsResponse(
    val success: Boolean,
    val products: List<Product>
)

interface ApiService {
    @GET("api/categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("api/products.php")
    suspend fun getProducts(
        @Query("category") categoryId: Int? = null
    ): ProductsResponse
}