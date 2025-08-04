package com.example.template.app.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Folder
import compose.icons.evaicons.outline.Plus
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import template.composeapp.generated.resources.Res
import template.composeapp.generated.resources.commonGenerationFlashcardsScreenLabel
import template.composeapp.generated.resources.pagepeed_reports_title

@Suppress("TRANSIENT_IS_REDUNDANT")
@Serializable
sealed class Screen {

    @Transient
    val title: String
        @Composable
        get() = stringResource(this.titleRes).trim()

    @Transient
    abstract val titleRes: StringResource

    @Transient
    abstract val icon: ImageVector
    abstract val route: String

    data object GeneratePagespeedReport : Screen() {
        override val titleRes = Res.string.commonGenerationFlashcardsScreenLabel
        override val icon: ImageVector
            get() = EvaIcons.Outline.Plus
        override val route = "/generate-pagespeed-report"
    }

    data object PagespeedReports : Screen() {
        override val titleRes = Res.string.pagepeed_reports_title
        override val icon: ImageVector get() = EvaIcons.Outline.Folder
        override val route = "/pagespeed-reports"
    }
}