package com.example.template.pagespeed.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import com.example.template.app.ui.components.AppButton
import com.example.template.app.ui.components.AppText
import com.example.template.app.ui.components.AppTextField
import com.example.template.pagespeed.ui.viewModels.AnalysisStatus
import com.example.template.pagespeed.ui.viewModels.GeneratePagespeedReportViewModel
import com.example.template.pagespeed.ui.viewModels.PageAnalysisState
import compose.icons.EvaIcons
import compose.icons.evaicons.Outline
import compose.icons.evaicons.outline.AlertCircle
import compose.icons.evaicons.outline.ArrowIosDownward
import compose.icons.evaicons.outline.ArrowIosUpward
import compose.icons.evaicons.outline.CheckmarkCircle2
import compose.icons.evaicons.outline.Clock
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
    
    // Agora observamos o estado da SESSÃO via ViewModel
    val generationProgress by generatePagespeedReportViewModel.generationProgress.collectAsState()
    val isGenerating by generatePagespeedReportViewModel.isGenerating.collectAsState()
    val sessionError by generatePagespeedReportViewModel.sessionError.collectAsState()

    val dialogState = remember { mutableStateOf(false) }
    val showSnackbar: MutableState<Boolean> = remember { mutableStateOf(false) }
    
    // Estado local para controlar o modal
    var isOverlayExpanded by remember { mutableStateOf(true) }

    // Reação a erros da sessão
    LaunchedEffect(sessionError) {
        if (sessionError != null) {
            showSnackbar.value = true
            delay(4000)
            showSnackbar.value = false
        }
    }

    // Abre o overlay automaticamente se começar a gerar
    LaunchedEffect(isGenerating) {
        if (isGenerating) {
            isOverlayExpanded = true
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (showSnackbar.value && sessionError != null) {
            Snackbar(
                shape = MaterialTheme.shapes.medium,
                containerColor = SnackbarDefaults.color.copy(alpha = 0.7f),
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
                    .zIndex(10f)
            ) {
                AppText(text = "Erro: ${sessionError}")
            }
        }
        
        // Conteúdo Principal (Fundo)
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                 TopBarSettings(dialogState, generatePagespeedReportViewModel)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    AppText(
                        text = stringResource(Res.string.generate_pagepeed_report_title).trim(),
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize
                    )

                    AppTextField(
                        value = url,
                        onValueChange = { it -> url = it },
                        placeholderTextStyle = TextStyle.Default.copy(color = Color.Gray),
                        placeholderText = stringResource(Res.string.generate_pagespeed_btn_generate_pagespeed_placeholder).trim(),
                        colors = OutlinedTextFieldDefaults.colors(
                            cursorColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    AppButton(
                        onClick = {
                            generatePagespeedReportViewModel.onChangeUrl(url)
                            generatePagespeedReportViewModel.onGenerateReports()
                        },
                        enabled = url.isNotBlank() && !isGenerating,
                        modifier = Modifier.defaultMinSize(minWidth = 24.dp)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(MaterialTheme.typography.labelLarge.fontSize.value.dp)
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
        
        // --- OVERLAY DE PROGRESSO ---
        if (generationProgress.isNotEmpty()) {
            
            // Fundo escurecido (Dimmer) - Só aparece se expandido
             AnimatedVisibility(
                visible = isOverlayExpanded,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.matchParentSize().zIndex(2f)
            ) {
                 Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .clickable { 
                            // Opcional: Clicar fora não faz nada ou minimiza
                        }
                )
            }

            // O Modal em si
            AnimatedVisibility(
                visible = isOverlayExpanded,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter).zIndex(3f)
            ) {
                Card(
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f) // Ocupa 85% da tela
                        .clickable(enabled = false) {} // Impede cliques de passarem
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        // Cabeçalho do Modal
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AppText(
                                text = if (isGenerating) "Gerando Relatórios..." else "Geração Concluída",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Row {
                                IconButton(onClick = { isOverlayExpanded = false }) {
                                    Icon(EvaIcons.Outline.ArrowIosDownward, contentDescription = "Minimizar")
                                }
                                if (!isGenerating) {
                                    IconButton(onClick = { generatePagespeedReportViewModel.clearSession() }) {
                                        Icon(EvaIcons.Outline.Close, contentDescription = "Fechar e Limpar")
                                    }
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        ProgressSection(generationProgress)
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        // Botão de navegação explícita (Opcional, mas útil)
                        AppButton(
                            onClick = onNavigateToTopics,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AppText("Ir para Lista de Relatórios")
                        }
                    }
                }
            }
            
            // --- MINI PLAYER / INDICADOR FLUTUANTE (Quando minimizado) ---
            if (!isOverlayExpanded) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .zIndex(3f)
                        .clickable { isOverlayExpanded = true }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(EvaIcons.Outline.CheckmarkCircle2, contentDescription = null, tint = Color.Green)
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        AppText(
                             text = if (isGenerating) "Gerando..." else "Concluído",
                             style = MaterialTheme.typography.labelLarge
                        )
                        Icon(EvaIcons.Outline.ArrowIosUpward, contentDescription = "Expandir")
                    }
                }
            }
        }
    }
}

@Composable
fun ProgressSection(progressList: List<PageAnalysisState>) {
    val completedCount = progressList.count { it.status == AnalysisStatus.COMPLETED || it.status == AnalysisStatus.ERROR }
    val totalCount = progressList.size
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppText(text = "Processando: $completedCount de $totalCount")
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(progressList) { item ->
                ProgressItem(item)
            }
        }
    }
}

@Composable
fun ProgressItem(item: PageAnalysisState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                text = item.url,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.size(8.dp))
            when (item.status) {
                AnalysisStatus.WAITING -> Icon(EvaIcons.Outline.Clock, contentDescription = "Aguardando", tint = Color.Gray)
                AnalysisStatus.PROCESSING -> CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                AnalysisStatus.COMPLETED -> Icon(EvaIcons.Outline.CheckmarkCircle2, contentDescription = "Concluído", tint = Color.Green)
                AnalysisStatus.ERROR -> Icon(EvaIcons.Outline.AlertCircle, contentDescription = "Erro", tint = Color.Red)
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

