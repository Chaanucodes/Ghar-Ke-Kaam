package com.faultyplay.gharkekaam.di

import com.faultyplay.gharkekaam.core.data.di.dataModule
import com.faultyplay.gharkekaam.feature.auth.di.authModule
import com.faultyplay.gharkekaam.feature.house.di.houseModule
import com.faultyplay.gharkekaam.navigation.di.navigationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            authModule,
            dataModule,
            navigationModule,
            houseModule
        )
    }
}
