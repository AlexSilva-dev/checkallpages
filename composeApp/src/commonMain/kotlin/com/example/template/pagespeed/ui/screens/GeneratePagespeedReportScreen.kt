package com.example.template.pagespeed.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.template.app.ui.components.AppButton
import com.example.template.app.ui.components.AppText
import com.example.template.app.ui.components.AppTextField
import com.example.template.app.viewModels.UiState
import com.example.template.pagespeed.ui.viewModels.GeneratePagespeedReportViewModel
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.Close
import compose.icons.evaicons.outline.Settings
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
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

    val dialogState = remember { mutableStateOf(false) }
    val showSnackbar: MutableState<Boolean> = remember { mutableStateOf(false) }
    val errorMessage: MutableState<String> = remember { mutableStateOf("") }

    adaptingToChangeUiState(
        onNavigateToTopics = onNavigateToTopics,
        generatePagespeedReportViewModel = generatePagespeedReportViewModel,
        createQuizzesState = createQuizzesState,
        showSnackbar = showSnackbar,
        errorMessage = errorMessage
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (showSnackbar.value && errorMessage.value.isNotEmpty()) {
            Snackbar(
                shape = MaterialTheme.shapes.medium,
                containerColor = SnackbarDefaults.color.copy(alpha = 0.7f),
                modifier = Modifier
                    .align(
                        alignment = Alignment.BottomStart
                    )
            ) {
                AppText(text = "Erro ao gerar relatório: ${errorMessage.value}")
            }
        }
        TopBarSettings(dialogState, generatePagespeedReportViewModel)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
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
}

@Composable
private fun BoxScope.TopBarSettings(
    dialogState: MutableState<Boolean>,
    generatePagespeedReportViewModel: GeneratePagespeedReportViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .align(Alignment.TopStart)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                dialogState.value = !dialogState.value
            }
        ) {
            Icon(
                imageVector = EvaIcons.Outline.Settings,
                contentDescription = "Configuração"
            )
        }
        if (dialogState.value) {
            Dialog(
                onDismissRequest = { }
            ) {

                Box(
                    modifier = Modifier
                        .size(400.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()

                        ) {

                            AppText(
                                "API Key do Google"
                            )
                            AppTextField(
                                value = generatePagespeedReportViewModel.generatePagespeedReportData.collectAsState().value.apiKeyGoogle,
                                onValueChange = { newValue: String ->
                                    generatePagespeedReportViewModel.onApiKeyGoogleChanged(
                                        apiKeyGoogle = newValue
                                    )
                                }
                            )
                            AppButton(
                                onClick = {
                                    generatePagespeedReportViewModel.onSaveApiKeyGoogle()
                                }
                            ) {
                                AppText(
                                    text = "Salvar"
                                )
                            }
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopEnd)
                    ) {
                        IconButton(
                            onClick = {
                                dialogState.value = !dialogState.value
                            }
                        ) {
                            Icon(
                                imageVector = EvaIcons.Outline.Close,
                                contentDescription = "Fechar"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun adaptingToChangeUiState(
    onNavigateToTopics: () -> Unit,
    generatePagespeedReportViewModel: GeneratePagespeedReportViewModel,
    createQuizzesState: UiState<Unit>,
    showSnackbar: MutableState<Boolean>,
    errorMessage: MutableState<String>,
) {
    LaunchedEffect(createQuizzesState) {
        when (createQuizzesState) {
            is UiState.Success -> {
                generatePagespeedReportViewModel.onReportGenerationActionCompleted()
            }

            is UiState.Error -> {
                errorMessage.value =
                    (createQuizzesState as UiState.Error).message ?: "Erro ao gerar relatório"

                println("Erro ao gerar quizzes: $errorMessage")
                coroutineScope {
                    showSnackbar.value = true
                    delay((errorMessage.value.length * 100).toLong())
                    showSnackbar.value = false
                }
                generatePagespeedReportViewModel.onReportGenerationActionCompleted()
            }

            else -> {
                // Não faz nada nos estados Idle ou Loading
            }
        }
    }
}
