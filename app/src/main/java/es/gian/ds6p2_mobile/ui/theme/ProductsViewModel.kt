package es.gian.ds6p2_mobile.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.gian.ds6p2_mobile.data.model.Category
import es.gian.ds6p2_mobile.data.model.Product
import es.gian.ds6p2_mobile.data.network.RetrofitClient
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private val _categories = mutableStateOf<List<Category>>(emptyList())
    val categories: State<List<Category>> = _categories

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _selectedCategory = mutableStateOf<Int?>(null)
    val selectedCategory: State<Int?> = _selectedCategory

    init {
        loadCategories()
        loadProducts(null)
    }

    fun loadCategories() = viewModelScope.launch {
        runCatching {
            RetrofitClient.api.getCategories()
        }.onSuccess { resp ->
            if (resp.success) _categories.value = resp.categories
        }
    }

    fun loadProducts(categoryId: Int?) = viewModelScope.launch {
        runCatching {
            RetrofitClient.api.getProducts(categoryId)
        }.onSuccess { resp ->
            if (resp.success) _products.value = resp.products
        }
    }

    fun onCategorySelected(catId: Int?) {
        _selectedCategory.value = catId
        loadProducts(catId)
    }
}