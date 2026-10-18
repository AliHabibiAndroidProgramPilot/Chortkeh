package info.alihabibi.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import info.alihabibi.report.screen.ReportDestination
import kotlinx.serialization.Serializable

@Serializable
object ReportGraphRoute

@Serializable
object Report

fun NavGraphBuilder.reportGraph() {

    navigation<ReportGraphRoute>(startDestination = Report) {

        composable<Report> {
            ReportDestination()
        }

    }

}