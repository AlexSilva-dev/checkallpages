package com.example.template.app.di

import com.example.template.pagespeed.api.controllers.QuizController
import com.example.template.pagespeed.domain.useCases.CreateQuizzesByTopicUseCase
import com.example.template.ai.infrastructure.services.AIProviderFactory
import com.example.template.ai.infrastructure.services.AiServicePort
import com.example.template.ai.infrastructure.services.strategies.AIStrategy
import com.example.template.pagespeed.api.controllers.TopicController
import com.example.template.pagespeed.domain.ports.PagespeedRepository
import com.example.template.pagespeed.domain.ports.TopicRepository
import com.example.template.pagespeed.domain.useCases.GetQuizUseCaseImpl
import com.example.template.pagespeed.infrastructure.dataSources.remote.strategies.GeminiQuizStrategy
import com.example.template.pagespeed.domain.useCases.GetAllTopics
import com.example.template.pagespeed.domain.useCases.GetQuizzesByTopicUseCaseImpl
import com.example.template.pagespeed.domain.useCases.SaveTopicUseCase
import com.example.template.pagespeed.domain.useCases.interfaces.GetQuizUseCase
import com.example.template.pagespeed.infrastructure.repositories.QuizRepositoryImpl
import com.example.template.pagespeed.infrastructure.repositories.TopicRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val serverModule: Module = module {
    single<QuizController> {
        QuizController(
            createQuizzesByTopicUseCase = get(),
            getQuizUseCase = get(),
            getQuizzesByTopicUseCase = get(),
        )
    }

    single {
        CreateQuizzesByTopicUseCase(repository = get())
    }

    single<PagespeedRepository> {
        QuizRepositoryImpl(aiService = get())
    }

    single<AIStrategy> { GeminiQuizStrategy() }
    // single { OpenAIPoemStrategy() } // Exemplo de outra estratégia futura

    single<AiServicePort> {
        AIProviderFactory(
            strategies = getAll<AIStrategy>().toSet()
        )
    }

    single {
        TopicController(
            saveTopicUseCase = get(),
            getAllTopics = get()
        )
    }

    single {
        SaveTopicUseCase(repository = get())
    }

    single {
        SaveTopicUseCase(
            repository = get()
        )
    }

    single {
        GetAllTopics(get())
    }

    single<TopicRepository> {
        TopicRepositoryImpl()
    }

    single<GetQuizUseCase> {
        GetQuizUseCaseImpl(
            pagespeedRepository = get()
        )
    }

    single<GetQuizzesByTopicUseCaseImpl> {
        GetQuizzesByTopicUseCaseImpl(
            pagespeedRepository = get()
        )
    }
}
