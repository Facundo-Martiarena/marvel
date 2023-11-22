package com.silverafederico.apimarvel.di

import com.silverafederico.apimarvel.data.ApiService
import com.silverafederico.apimarvel.data.repositories.CharacterRepository
import com.silverafederico.apimarvel.data.repositories.ComicsRepository
import com.silverafederico.apimarvel.data.repositories.CharacterNameRepository
import com.silverafederico.apimarvel.data.repositories.ICharacterNameRepository
import com.silverafederico.apimarvel.data.repositories.ICharacterRepository
import com.silverafederico.apimarvel.data.repositories.IComicsRepository
import com.silverafederico.apimarvel.ui.home.HomeViewModel
import com.silverafederico.apimarvel.ui.character.CharacterViewModel
import com.silverafederico.apimarvel.ui.comic.ComicViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoriesModule = module {
    singleOf(::CharacterRepository) { bind<ICharacterRepository>() }
    singleOf(::ComicsRepository) { bind<IComicsRepository>() }
    singleOf(::CharacterNameRepository) { bind<ICharacterNameRepository>()}
}

val viewModelsModule = module {
    singleOf(::HomeViewModel)
    singleOf(::CharacterViewModel)
    singleOf(::ComicViewModel)
}
val networkModule = module {
    singleOf(::OkHttpClient){
        provideOkHttpClient()
    }
    single<Retrofit>{
        provideRetrofit(get())
    }
    single<ApiService> {
        provideApi(get())
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
    return Retrofit.Builder().baseUrl("https://gateway.marvel.com:443/v1/public/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient():OkHttpClient{
    return OkHttpClient().newBuilder().build()
}
fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)