package com.davi.dev.firstappkmp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davi.dev.firstappkmp.domain.Paginator
import com.davi.dev.firstappkmp.domain.services.ProductsApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductsViewModel(private val api: ProductsApi) : ViewModel() {

    private val _state = MutableStateFlow(ProductState())
    val state = _state.asStateFlow()

    private val pageSize = 10
    private val paginator = Paginator(
        initialKey = 0,
        onLoadUpdated = {
            _state.update { it.copy(isLoadingMore = it.isLoadingMore) }
        },
        onRequest = { currentPage ->
            api.getProducts(page = currentPage, pageSize = pageSize)
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { throwable ->
            _state.update { it.copy(error = throwable?.message) }
        },
        onSuccess = { products, nextPage ->
            _state.update {
                it.copy(
                    products = it.products + products.products,
                    error = null
                )
            }
        },
        endReached = { currentPage, response ->
            (currentPage * pageSize) >= response.total
        }
    )


    init {
        loadNextItems()
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }

    }
}