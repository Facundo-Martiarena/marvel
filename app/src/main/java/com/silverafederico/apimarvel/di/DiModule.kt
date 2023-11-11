package com.silverafederico.apimarvel.di

import com.silverafederico.apimarvel.data.ApiService
import com.silverafederico.apimarvel.data.repositories.CharacterRepository
import com.silverafederico.apimarvel.data.repositories.ICharacterRepository
import com.silverafederico.apimarvel.ui.HomeViewModel
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoriesModule = module {
    singleOf(::CharacterRepository) { bind<ICharacterRepository>() }
}

val viewModelsModule = module {
    singleOf(::HomeViewModel)
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