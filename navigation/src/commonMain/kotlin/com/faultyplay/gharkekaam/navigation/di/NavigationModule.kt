package com.faultyplay.gharkekaam.navigation.di

import com.faultyplay.gharkekaam.navigation.Navigator
import org.koin.dsl.module

val navigationModule = module {
    single { Navigator() }
}
