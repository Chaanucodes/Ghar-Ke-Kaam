package com.faultyplay.gharkekaam.feature.auth.di

import com.faultyplay.gharkekaam.feature.auth.ui.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    factory { AuthViewModel(get()) }
}
