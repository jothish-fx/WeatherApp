package com.fx.weather.ui.hourlyforecast

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fx.weather.ui.hourlyforecast.HourlyForecastUiState.HasItems
import com.fx.weather.ui.hourlyforecast.HourlyForecastUiState.NoItems
import com.fx.weather.ui.theme.WeatherTheme
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HourlyForecastView(uiState: HourlyForecastUiState) {
    Box(modifier = Modifier.fillMaxSize()) {


        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.Center),
            visible = uiState.isLoading
        ) {
            CircularProgressIndicator()
        }

        if (uiState is HasItems) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {

                item {

                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = "Hourly Weather",
                        style = MaterialTheme.typography.displaySmall
                    )

                    Text(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        text = "Dubai",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                }

                uiState.hourlyForecast.forecasts.forEach { (dayMonth, forecasts) ->
                    stickyHeader {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.background)
                                .padding(top = 16.dp, bottom = 4.dp)
                        ) {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(start = 16.dp),
                                text = dayMonth,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                            ) {
                                Spacer(modifier = Modifier.width(16.dp))

                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Time",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Temp",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = "Feels Like",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall
                                )

                                Text(
                                    modifier = Modifier.weight(2f),
                                    text = "Description",
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }

                        }
                    }

                    items(forecasts) { item ->

                        Divider()

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                modifier = Modifier.weight(1f),
                                text = item.hour,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = "${item.temp}\u2103",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                modifier = Modifier.weight(1f),
                                text = "${item.feelsLike}\u2103",
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.bodySmall
                            )

                            val model =
                                "http://openweathermap.org/img/wn/${item.weather.icon}.png"

                            Timber.d("Image Url = $model")


                            Row(
                                modifier = Modifier
                                    .weight(2f)
                                    .wrapContentHeight()
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(model)
                                        .build(),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                )

                                Text(
                                    text = item.weather.description,
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HourlyForecastPreview() {
    WeatherTheme {
        HourlyForecastView(uiState = NoItems(isLoading = false, listOf()))
    }
}