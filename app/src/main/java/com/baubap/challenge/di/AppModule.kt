package com.baubap.challenge.di

import com.baubap.challenge.BuildConfig
import com.baubap.challenge.data.api.ApiService
import com.baubap.challenge.data.repository.AuthRepositoryImpl
import com.baubap.challenge.data.repository.UserRepositoryImpl
import com.baubap.challenge.domain.repository.AuthRepository
import com.baubap.challenge.domain.repository.UserRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class ApiKey

    @Singleton
    @Provides
    @ApiKey
    fun provideApiKey(): String = "reqres-free-v1"

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideGson(): Gson = Gson()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @ApiKey apiKey: String
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithApiKey = originalRequest.newBuilder()
                    .header("x-api-key", apiKey)
                    .build()
                chain.proceed(requestWithApiKey)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideRestCountryApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthRepository(apiService: ApiService, gson: Gson): AuthRepository {
        return AuthRepositoryImpl(apiService, gson)
    }



    @Singleton
    @Provides
    fun providesUserRepository(apiService: ApiService, gson: Gson): UserRepository {
        return UserRepositoryImpl(apiService, gson)
    }
}