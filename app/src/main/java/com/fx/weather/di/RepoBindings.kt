/**
 * Created by
 * @author Jothish on 24/02/2022, 19:04
 * Last modified 24/02/2022, 19:04
 */

package com.fx.weather.di

import com.fx.weather.data.repository.WeatherRepo
import com.fx.weather.data.repository.WeatherRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoBindings {
    @Binds
    abstract fun bindsWeatherRepo(
        weatherRepoImpl: WeatherRepoImpl,
    ): WeatherRepo
}