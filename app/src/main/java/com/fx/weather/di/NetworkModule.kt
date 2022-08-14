/**
 * Created by
 * @author Jothish on 24/02/2022, 14:28
 * Last modified 24/02/2022, 14:28
 */

package com.fx.weather.di


import com.fx.weather.BuildConfig
import com.fx.weather.core.network.NetworkHelper
import com.fx.weather.data.network.NoConnectionInterceptor
import com.fx.weather.data.network.api.WeatherService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideNoConnectionInterceptor(networkHelper: NetworkHelper) =
        NoConnectionInterceptor(networkHelper)


    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        noConnectionInterceptor: NoConnectionInterceptor,
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(10, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClient.readTimeout(10, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(10, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.addInterceptor(noConnectionInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .build()

    @Singleton
    @Provides
    fun provideConverterFactory(
        moshi: Moshi,
    ): Converter.Factory = MoshiConverterFactory.create(moshi)


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.API_VERSION)
            .addConverterFactory(converterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun providesLoginService(retrofit: Retrofit): WeatherService =
        retrofit.create((WeatherService::class.java))


}
