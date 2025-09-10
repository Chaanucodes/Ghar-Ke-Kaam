package com.faultyplay.gharkekaam.core.data.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val appScopeModule = module {
    single {
        // A SupervisorJob ensures that if one coroutine fails, it doesn't cancel the whole scope.
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
}