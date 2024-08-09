package com.shreyanshsinghks.shoppingappuser.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.shreyanshsinghks.shoppingappuser.domain.models.ProductModel
import com.shreyanshsinghks.shoppingappuser.presentation.viewmodel.ShoppingAppViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val productUiState = viewModel.productUiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Categories", style = MaterialTheme.typography.titleMedium)
            Text(text = "See More", style = MaterialTheme.typography.bodyMedium)
        }

        LazyColumn {
            when {
                productUiState.value.isLoading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                }
                productUiState.value.error != null -> {
                    item {
                        Text(
                            text = productUiState.value.error.toString(),
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                !productUiState.value.productList.isNullOrEmpty() -> {
                    items(productUiState.value.productList!!) { product ->
                        ProductItem(product)
                    }
                }
                else -> {
                    item {
                        Text(
                            text = "No products available",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: ProductModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold)
                Text(text = "Price: ${product.price}")
                Text(text = "Category: ${product.category}")
                Text(text = "Date: ${formatDate(product.date)}")
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}