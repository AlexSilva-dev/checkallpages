package com.example.template.pagespeed.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.template.app.ui.components.AppButton
import com.example.template.app.ui.components.AppText
import com.example.template.app.ui.components.AppTextField
import com.example.template.app.viewModels.UiState
import com.example.template.pagespeed.ui.viewModels.GeneratePagespeedReportViewModel
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import template.composeapp.generated.resources.Res
import template.composeapp.generated.resources.generate_pagepeed_report_title
import template.composeapp.generated.resources.generate_pagespeed_btn_generate_pagespeed_placeholder
import template.composeapp.generated.resources.generate_pagespeed_report_btn_generation

@Composable
@Preview
fun GeneratePagespeedReportScreen(
    onNavigateToTopics: () -> Unit,
    generatePagespeedReportViewModel: GeneratePagespeedReportViewModel = koinInject<GeneratePagespeedReportViewModel>()
) {
    var url: String by remember { mutableStateOf("") }
    val createQuizzesState by generatePagespeedReportViewModel.reportGeneration.collectAsState()

    adaptingToChangeUiState(
        onNavigateToTopics = onNavigateToTopics,
        generatePagespeedReportViewModel = generatePagespeedReportViewModel,
        createQuizzesState = createQuizzesState
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText(
            text = stringResource(Res.string.generate_pagepeed_report_title).trim(),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )

        AppTextField(
            value = url,
            onValueChange = { it ->
                url = it
            },
            placeholderTextStyle = TextStyle.Default.copy(color = Color.Gray),
            placeholderText = stringResource(Res.string.generate_pagespeed_btn_generate_pagespeed_placeholder).trim(),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        AppButton(
            onClick = {
                generatePagespeedReportViewModel.onChangeUrl(
                    url
                )
                generatePagespeedReportViewModel.onGenerateReports()
            },

            enabled = url.isNotBlank()
                    && createQuizzesState !is UiState.Loading,

            modifier = Modifier
                .defaultMinSize(minWidth = 24.dp)
        ) {
            if (createQuizzesState is UiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(MaterialTheme.typography.labelLarge.fontSize.value.dp)
                )
            } else {
                AppText(
                    text = stringResource(Res.string.generate_pagespeed_report_btn_generation).trim(),
                    fontSize = MaterialTheme.typography.labelLarge.fontSize
                )
            }
        }
    }
}

@Composable
private fun adaptingToChangeUiState(
    onNavigateToTopics: () -> Unit,
    generatePagespeedReportViewModel: GeneratePagespeedReportViewModel,
    createQuizzesState: UiState<Unit>
) {
    LaunchedEffect(createQuizzesState) {
        when (createQuizzesState) {
            is UiState.Success -> {
                generatePagespeedReportViewModel.onReportGenerationActionCompleted()
            }

            is UiState.Error -> {
                val errorMessage =
                    (createQuizzesState as UiState.Error).message

                println("Erro ao gerar quizzes: $errorMessage")
                generatePagespeedReportViewModel.onReportGenerationActionCompleted()
            }

            else -> {
                // Não faz nada nos estados Idle ou Loading
            }
        }
    }
}
