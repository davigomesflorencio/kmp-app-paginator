package com.davi.dev.firstappkmp.presentation.viewModels

import com.davi.dev.firstappkmp.domain.ProductDto

data class ProductState(
    val products: List<ProductDto> = emptyList(),
    val isLoadingMore: Boolean = false,
    val error: String? = null
)