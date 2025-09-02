package com.faultyplay.gharkekaam.core.data.di

import com.faultyplay.gharkekaam.core.data.repository.AuthRepository
import com.faultyplay.gharkekaam.core.data.repository.AuthRepositoryImpl
import com.faultyplay.gharkekaam.core.data.repository.HouseRepository
import com.faultyplay.gharkekaam.core.data.repository.HouseRepositoryImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val dataModule = module {
    // Firebase
    single { Firebase.auth }
    single { Firebase.firestore }

    // Repositories
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<HouseRepository> { HouseRepositoryImpl(get()) }
}
