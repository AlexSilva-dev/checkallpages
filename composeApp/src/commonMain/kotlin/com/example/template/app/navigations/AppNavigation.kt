package com.example.template.app.navigations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.template.app.ui.layouts.MainLayout
import com.example.template.pagespeed.ui.screens.GeneratePagespeedReportScreen
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalUuidApi::class)
@Composable
fun AppNavegation(
    navController: NavHostController = rememberNavController()
) {
    val navBlackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute: String? = navBlackStackEntry?.destination?.route

    val onNavigate: (route: String) -> Unit = { route ->
        println(currentRoute)
        navController.navigate(route)
    }

    NavHost(
        navController = navController,
        startDestination = Screen.GeneratePagespeedReport.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {

        composable(route = Screen.GeneratePagespeedReport.route) {
            MainLayout(
                onNavigate = onNavigate,
                currentRoute = currentRoute
            ) {
                GeneratePagespeedReportScreen(
                    onNavigateToTopics = {
                        navController.navigate(
                            route = Screen.PagespeedReports.route
                        )
                    }
                )
            }

            composable(route = Screen.PagespeedReports.route) {
                MainLayout(
                    onNavigate = onNavigate,
                    currentRoute = currentRoute
                ) {
                    Column {  }
                }
            }
        }
    }
}