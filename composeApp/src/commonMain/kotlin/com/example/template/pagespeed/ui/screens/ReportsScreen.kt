package com.example.template.pagespeed.ui.screens

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarAdapter
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.template.app.ui.components.AppText
import com.example.template.pagespeed.domain.entities.ReportPathsPageSpeed
import com.example.template.pagespeed.ui.viewModels.ReportsViewModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.ArrowDown
import compose.icons.evaicons.outline.ArrowRight
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
fun PageSpeedReportScreen(
    reportsViewModel: ReportsViewModel = koinInject<ReportsViewModel>()
) {
    reportsViewModel.onUpdateDomainPaths()
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AppText(
                    text = "Relatórios",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
            }

            if (reportsViewModel.reportsViewModelData.value.domainPaths.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxSize()
                ) {
                    AppText(
                        text = "Nada aqui.",
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        reportsViewModel.reportsViewModelData.value.domainPaths
                    ) { reportPathsPageSpeed: ReportPathsPageSpeed ->

                        var openSitemaps by remember {
                            mutableStateOf(
                                false
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .heightIn(max = 500.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth()
                            ) {
                                Card(
                                    onClick = {
                                        reportsViewModel.onGetSitmapPaths(
                                            reportPathsPageSpeed.path
                                        )
                                        openSitemaps = !openSitemaps
                                    },
                                    modifier = Modifier
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .wrapContentHeight()
                                            .fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = if (!openSitemaps) EvaIcons.Outline.ArrowRight else EvaIcons.Outline.ArrowDown,
                                            contentDescription = "Folder"
                                        )
                                        AppText(reportPathsPageSpeed.name)
                                        AppText(
                                            text = reportPathsPageSpeed.atCreate,
                                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                                        )
                                    }
                                }

                                SitemapList(openSitemaps, reportsViewModel, reportPathsPageSpeed)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SitemapList(
    openSitemaps: Boolean,
    reportsViewModel: ReportsViewModel,
    reportPathsPageSpeed: ReportPathsPageSpeed
) {
    Column(
        modifier = Modifier
            .padding(start = 30.dp)
    ) {
        if (openSitemaps) {
            when (
                reportsViewModel.reportsViewModelData.value.sitemapPaths.getValue(
                    reportPathsPageSpeed.path
                )
            ) {

                emptyList<ReportPathsPageSpeed>() -> {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        AppText("Vazio")
                    }
                }

                is List<ReportPathsPageSpeed> -> {
                    LazyColumn(
                        modifier = Modifier
                            .wrapContentHeight()
                    ) {
                        items(
                            reportsViewModel.reportsViewModelData.value.sitemapPaths.getValue(
                                reportPathsPageSpeed.path
                            )
                        ) { reportPathsPageSpeed: ReportPathsPageSpeed ->

                            var openReport by remember {
                                mutableStateOf(
                                    false
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Card(
                                        onClick = {
                                            reportsViewModel.onGetReportPaths(
                                                reportPathsPageSpeed.path
                                            )
                                            openReport = !openReport
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.Start,
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .padding(
                                                    8.dp
                                                )
                                                .fillMaxSize()
                                        ) {
                                            Icon(
                                                imageVector = if (!openReport) EvaIcons.Outline.ArrowRight else EvaIcons.Outline.ArrowDown,
                                                contentDescription = "Folder"
                                            )
                                            AppText(
                                                reportPathsPageSpeed.name
                                            )
                                            AppText(
                                                text = reportPathsPageSpeed.atCreate,
                                                fontSize = MaterialTheme.typography.labelSmall.fontSize
                                            )
                                        }
                                    }

                                    Reports(openReport, reportsViewModel, reportPathsPageSpeed)
                                }
                            }
                        }
                    }


                }

                else -> CircularProgressIndicator()

            }
        }
    }
}

@Composable
private fun Reports(
    reportExtend: Boolean,
    reportsViewModel: ReportsViewModel,
    reportPathsPageSpeed: ReportPathsPageSpeed
) {
    Column(
        modifier = Modifier
            .padding(start = 30.dp)
    ) {
        if (reportExtend) {
            when (
                reportsViewModel.reportsViewModelData.value.reportPaths.getValue(
                    reportPathsPageSpeed.path
                )
            ) {

                emptyList<ReportPathsPageSpeed>() -> {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        AppText("Vazio")
                    }
                }

                is List<ReportPathsPageSpeed> -> {
                    println(

                        reportsViewModel.reportsViewModelData.value.reportPaths.getValue(
                            reportPathsPageSpeed.path
                        )
                    )
                    val state = rememberLazyListState()
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp)
                    ) {
                        LazyColumn(
                            state = state,
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(end = 12.dp)
                        ) {
                            items(
                                reportsViewModel.reportsViewModelData.value.reportPaths.getValue(
                                    reportPathsPageSpeed.path
                                )
                            ) { reportPathsPageSpeed: ReportPathsPageSpeed ->

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Card(
                                            onClick = {
                                                reportsViewModel.onOpenFile(
                                                    reportPathsPageSpeed.path
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Row(
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                modifier = Modifier
                                                    .padding(
                                                        8.dp
                                                    )
                                                    .fillMaxSize()
                                            ) {

                                                AppText(
                                                    reportPathsPageSpeed.name
                                                )
                                                AppText(
                                                    text = reportPathsPageSpeed.atCreate,
                                                    fontSize = MaterialTheme.typography.labelSmall.fontSize
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        VerticalScrollbar(
                            adapter = ScrollbarAdapter(
                                scrollState = state
                            ),
                            style = LocalScrollbarStyle.current.copy(
                                unhoverColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                hoverColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxHeight()
                        )
                    }

                }

                else -> CircularProgressIndicator()

            }
        }
    }
}


@Preview
@Composable
fun PageSpeedReportScreenPreview() {
    // TODO: Create a ReportsViewModel instance for previewing
    // PageSpeedReportScreen(reportsViewModel = ...)
}

