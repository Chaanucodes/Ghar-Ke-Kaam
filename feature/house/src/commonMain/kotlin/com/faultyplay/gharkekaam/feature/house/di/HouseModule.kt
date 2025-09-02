package com.faultyplay.gharkekaam.feature.house.di

import com.faultyplay.gharkekaam.feature.house.ui.create.CreateHouseViewModel
import com.faultyplay.gharkekaam.feature.house.ui.join.JoinHouseViewModel
import com.faultyplay.gharkekaam.feature.house.ui.list.HouseListViewModel
import org.koin.dsl.module

val houseModule = module {
    factory { HouseListViewModel(get(), get()) }
    factory { CreateHouseViewModel(get(), get()) }
    factory { JoinHouseViewModel(get(), get()) }
}
