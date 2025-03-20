package com.example.shopping.presentation.navigation

import AllCategoriesScreenUi
import GetAllFav
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorDirection
import com.example.bottombar.model.IndicatorStyle
import com.example.shopping.R
import com.example.shopping.presentation.screens.CartScreenUi
import com.example.shopping.presentation.screens.CheckOutScreenUi
import com.example.shopping.presentation.screens.EachCategorieProductScreenUi
import com.example.shopping.presentation.screens.EachProductDetailsScreenUi
import com.example.shopping.presentation.screens.HomeScreenUi
import com.example.shopping.presentation.screens.LoginScreenUi
import com.example.shopping.presentation.screens.PayScreen
import com.example.shopping.presentation.screens.ProfileScreenUi
import com.example.shopping.presentation.screens.SingUpScreenUi
import com.example.shopping.presentation.screens.GetAllProducts
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val shouldShowBottomBar = remember { mutableStateOf(false) }

    // Determine initial navigation state
    val startScreen = remember(firebaseAuth.currentUser) {
        if (firebaseAuth.currentUser == null) {
            SubNavigation.LoginSingUpScreen
        } else {
            SubNavigation.MainHomeScreen
        }
    }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar.value = when (currentDestination) {
            Routes.LoginScreen::class.qualifiedName, 
            Routes.SingUpScreen::class.qualifiedName -> false
            else -> true
        }
    }

    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home, unseletedIcon = Icons.Outlined.Home),
        BottomNavItem("WishList", Icons.Default.Favorite, unseletedIcon = Icons.Outlined.Favorite),
        BottomNavItem("Cart", Icons.Default.ShoppingCart, unseletedIcon = Icons.Outlined.ShoppingCart),
        BottomNavItem("Profile", Icons.Default.Person, unseletedIcon = Icons.Outlined.Person),
    )

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding()
                        ),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    AnimatedBottomBar(
                        selectedItem = selectedItem,
                        itemSize = bottomNavItems.size,
                        containerColor = Color.Transparent,
                        indicatorColor = colorResource(id = R.color.orange),
                        contentColor = MaterialTheme.colorScheme.primary,
                        indicatorDirection = IndicatorDirection.BOTTOM,
                        indicatorStyle = IndicatorStyle.FILLED,
                    ) {
                        bottomNavItems.forEachIndexed { index, navigationItem ->
                            BottomBarItem(
                                modifier = Modifier.align(alignment = Alignment.Top),
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    when (index) {
                                        0 -> navController.navigate(Routes.HomeScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        1 -> navController.navigate(Routes.WishListScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        2 -> navController.navigate(Routes.CartScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                        3 -> navController.navigate(Routes.ProfileScreen) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                },
                                imageVector = navigationItem.icon,
                                label = navigationItem.name,
                                containerColor = Color.Transparent,
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (shouldShowBottomBar.value) 60.dp else 0.dp)
        ) {
            NavHost(
                navController = navController,
                startDestination = startScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                navigation<SubNavigation.LoginSingUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUi(navController = navController)
                    }

                    composable<Routes.SingUpScreen> {
                        SingUpScreenUi(navController = navController)
                    }
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUi(navController = navController)
                    }

                    composable<Routes.ProfileScreen> {
                        ProfileScreenUi(firebaseAuth = firebaseAuth, navController = navController)
                    }

                    composable<Routes.WishListScreen> {
                        GetAllFav(navController = navController)
                    }

                    composable<Routes.CartScreen> {
                        CartScreenUi(navController = navController)
                    }

                    composable<Routes.PayScreen> {
                        PayScreen()
                    }

                    composable<Routes.SeeAllProductsScreen> {
                        GetAllProducts(navController = navController)
                    }

                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUi(navController = navController)
                    }

                    composable<Routes.EachProductDetailsScreen> { route ->
                        EachProductDetailsScreenUi(
                            productId = route.productID,
                            navController = navController
                        )
                    }

                    composable<Routes.EachCategoryItemsScreen> { route ->
                        EachCategorieProductScreenUi(
                            categoryName = route.categoryName,
                            navController = navController
                        )
                    }

                    composable<Routes.CheckoutScreen> { route ->
                        CheckOutScreenUi(
                            productId = route.productID,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

data class BottomNavItem(val name: String, val icon: ImageVector, val unseletedIcon: ImageVector)
