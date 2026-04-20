package com.example.sensunshop2.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Handyman
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Store
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Info
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Surface
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.sensunshop2.ui.theme.SensunDarkGray
import com.example.sensunshop2.ui.theme.SensunLightGray1
import com.example.sensunshop2.ui.theme.SensunLightGray2
import com.example.sensunshop2.ui.theme.SensunOrange
import com.example.sensunshop2.ui.theme.SensunPurpleDark
import com.example.sensunshop2.ui.theme.SensunShop2Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    
    var selectedTab by remember { mutableStateOf(0) }
    var showContactDialog by remember { mutableStateOf(false) }
    var showMoreMenuDialog by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("Todos") }

    BackHandler {
        if (selectedCategory != "Todos") {
            selectedCategory = "Todos"
        } else if (backPressedOnce) {
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show()
            coroutineScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        }
    }

    Scaffold(
        bottomBar = { 
            HomeBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { 
                    selectedTab = it 
                    if (it == 0) selectedCategory = "Todos" 
                },
                onContactClick = { showContactDialog = true },
                onMoreClick = { showMoreMenuDialog = true }
            ) 
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = Color.White
    ) { innerPadding ->
        if (showContactDialog) {
            ContactDialog(onDismiss = { showContactDialog = false })
        }
        if (showMoreMenuDialog) {
            MoreMenuDialog(onDismiss = { showMoreMenuDialog = false })
        }

        if (selectedCategory == "Todos") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                HomeTopBar(modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(24.dp))
                HomeSearchBar(modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(24.dp))
                HeroBanner(modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(32.dp))
                CategoryFilters(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                FeaturedBusinessesSection()
                Spacer(modifier = Modifier.height(32.dp))
            }
        } else {
            CategoryGridScreen(
                categoryName = selectedCategory,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = "file:///android_asset/images/iconosensunshop.webp",
                contentDescription = "Logo Sensun Shop",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Sensun Shop",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = SensunDarkGray
            )
        }
        Icon(
            imageVector = Icons.Rounded.NotificationsNone,
            contentDescription = "Notifications",
            tint = SensunDarkGray,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Composable
fun HomeSearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SensunLightGray1)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search",
            tint = SensunDarkGray.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "¿Qué estás buscando?",
            color = SensunDarkGray.copy(alpha = 0.5f),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun HeroBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1509391366360-1e97f5240ab7?q=80&w=800&auto=format&fit=crop",
            contentDescription = "Solar panels banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            SensunPurpleDark,
                            SensunPurpleDark.copy(alpha = 0.8f),
                            Color.Transparent
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "De nosotros,\npara nosotros.",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = SensunOrange),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = "Ver negocios",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CategoryFilters(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { CategoryItem(icon = Icons.Rounded.GridView, label = "Todos", isSelected = selectedCategory == "Todos", onClick = { onCategorySelected("Todos") }) }
        item { CategoryItem(icon = Icons.Rounded.Storefront, label = "Emprendedores", isSelected = selectedCategory == "Emprendedores", onClick = { onCategorySelected("Emprendedores") }) }
        item { CategoryItem(icon = Icons.Rounded.Store, label = "Negocios locales", isSelected = selectedCategory == "Negocios locales", onClick = { onCategorySelected("Negocios locales") }) }
        item { CategoryItem(icon = Icons.Rounded.Home, label = "Comida", isSelected = selectedCategory == "Comida", onClick = { onCategorySelected("Comida") }) }
        item { CategoryItem(icon = Icons.Rounded.Handyman, label = "Oficios", isSelected = selectedCategory == "Oficios", onClick = { onCategorySelected("Oficios") }) }
        item { CategoryItem(icon = Icons.Rounded.Person, label = "Profesionales", isSelected = selectedCategory == "Profesionales", onClick = { onCategorySelected("Profesionales") }) }
    }
}

@Composable
fun CategoryItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector, 
    label: String, 
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(if (isSelected) SensunPurpleDark else SensunLightGray1),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) Color.White else SensunDarkGray,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = SensunDarkGray,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun FeaturedBusinessesSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Negocios Destacados",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = SensunDarkGray
            )
            Text(
                text = "Ver todos",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = SensunOrange,
                modifier = Modifier.clickable { }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(featuredBusinesses) { business ->
                BusinessCard(business)
            }
        }
    }
}

data class Business(val name: String, val rating: String, val reviews: String, val imageUrl: String)

val featuredBusinesses = listOf(
    Business("SolarTech SV", "4.8", "(32)", "https://images.unsplash.com/photo-1592833159155-c62df1b65634?q=80&w=400&auto=format&fit=crop"),
    Business("Energía Plus SV", "4.9", "(18)", "https://images.unsplash.com/photo-1613665813446-82a78c468a1d?q=80&w=400&auto=format&fit=crop"),
    Business("Green & Sun", "4.7", "(12)", "https://images.unsplash.com/photo-1588508065123-287b28e0139b?q=80&w=400&auto=format&fit=crop")
)

@Composable
fun BusinessCard(business: Business) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(210.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = business.imageUrl,
                contentDescription = business.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = business.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = SensunDarkGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "Rating",
                        tint = SensunOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = business.rating,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = SensunDarkGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = business.reviews,
                        style = MaterialTheme.typography.bodyMedium,
                        color = SensunDarkGray.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onContactClick: () -> Unit,
    onMoreClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = Modifier.height(80.dp)
    ) {
        val navColors = NavigationBarItemDefaults.colors(
            selectedIconColor = SensunOrange,
            selectedTextColor = SensunOrange,
            indicatorColor = SensunOrange.copy(alpha = 0.1f),
            unselectedIconColor = SensunDarkGray.copy(alpha = 0.6f),
            unselectedTextColor = SensunDarkGray.copy(alpha = 0.6f)
        )

        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Rounded.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            colors = navColors
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Rounded.Search, contentDescription = "Explorar") },
            label = { Text("Explorar") },
            colors = navColors
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = { onTabSelected(2) },
            icon = { Icon(Icons.Rounded.FavoriteBorder, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            colors = navColors
        )
        NavigationBarItem(
            selected = false,
            onClick = onContactClick,
            icon = { Icon(Icons.Rounded.ChatBubbleOutline, contentDescription = "Contacto") },
            label = { Text("Contacto") },
            colors = navColors
        )
        NavigationBarItem(
            selected = false,
            onClick = onMoreClick,
            icon = { Icon(Icons.Rounded.Menu, contentDescription = "Más") },
            label = { Text("Más") },
            colors = navColors
        )
    }
}

@Composable
fun ContactDialog(onDismiss: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.ChatBubbleOutline,
                    contentDescription = "Contacto",
                    tint = SensunOrange,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "¡Únete a Sensun Shop!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SensunDarkGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "¿Quieres estar en la plataforma de manera gratuita? Comunícate con nosotros.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = SensunDarkGray.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/50372359113"))
                        context.startActivity(intent)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SensunOrange),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text(
                        text = "Escríbenos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun MoreMenuDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Más opciones",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = SensunDarkGray,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                MoreMenuItem(
                    icon = Icons.Rounded.Settings,
                    label = "Ajustes",
                    onClick = { onDismiss() }
                )
                MoreMenuItem(
                    icon = Icons.Rounded.Person,
                    label = "Perfil",
                    onClick = { onDismiss() }
                )
                MoreMenuItem(
                    icon = Icons.Rounded.Info,
                    label = "Sobre nosotros",
                    onClick = { onDismiss() }
                )
            }
        }
    }
}

@Composable
fun MoreMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = SensunDarkGray.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = SensunDarkGray,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    SensunShop2Theme {
        HomeScreen()
    }
}
