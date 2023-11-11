package com.silverafederico.apimarvel.di

import com.silverafederico.apimarvel.data.repositories.CharacterRepository
import com.silverafederico.apimarvel.data.repositories.ICharacterRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoriesModule = module {
    singleOf(::CharacterRepository) { bind<ICharacterRepository>() }
}
