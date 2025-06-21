package es.gian.ds6p2_mobile.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*                // Material3 completo
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import es.gian.ds6p2_mobile.data.model.Product
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(vm: ProductsViewModel = viewModel()) {
    val cats by vm.categories
    val prods by vm.products
    val selectedCat by vm.selectedCategory

    Column(modifier = Modifier.fillMaxSize()) {
        // Estado del dropdown
        var expanded by remember { mutableStateOf(false) }
        val selectedLabel = cats.find { it.id == selectedCat }?.nombre ?: "Todas"

        // MENU ANCLADO
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = selectedLabel,
                onValueChange = { },
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),            // <— este es el ancla
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todas") },
                    onClick = {
                        vm.onCategorySelected(null)
                        expanded = false
                    }
                )
                cats.forEach { cat ->
                    DropdownMenuItem(
                        text = { Text(cat.nombre) },
                        onClick = {
                            vm.onCategorySelected(cat.id)
                            expanded = false
                        }
                    )
                }
            }
        }

        // LISTA DE PRODUCTOS
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(prods) { p: Product ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(8.dp)) {
                        AsyncImage(
                            model = p.imagenUrl,
                            contentDescription = p.nombre,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(p.nombre, style = MaterialTheme.typography.titleMedium)
                            Text("$${p.precio}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}