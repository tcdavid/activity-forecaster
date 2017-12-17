package org.tcd.activityforecast.service

import groovy.json.JsonSlurper
import groovy.transform.Memoized
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.tcd.activityforecast.domain.Location
import org.tcd.activityforecast.domain.Weather

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Service
public class WeatherForecastService {

    Logger logger = LoggerFactory.getLogger(WeatherForecastService.class)

    @Value('${api.url}')
    String url

    @Value('${api.key}')
    String key

    // TODO - handle error response behavior
    @Memoized
    Weather getForecast(Location location, ZonedDateTime datetime) {
        logger.debug("location ${location}")
        logger.debug("dateTime ${datetime}")

        String formattedDateTime = formatDateTime(datetime)
        logger.debug("formattedDateTime ${formattedDateTime}")
        RestTemplate restTemplate = new RestTemplate()
        String result = restTemplate.getForObject(url, String.class, key, location.toCSV(), formattedDateTime)
        logger.debug("weather forecast response ${result}")
        def slurper = new JsonSlurper()
        def forecast = slurper.parseText(result)
        def data = forecast?.daily?.data
        if (!data) {
            throw new IllegalArgumentException("No data available for location ${location} on ${datetime} ")
        }
        return translateWeatherData(data[0])
    }


    Weather translateWeatherData(def data) {

        new Weather(
                cloudCover: data.cloudCover,
                moonPhase: data.moonPhase,
                precipProbability: data.precipProbability,
                precipType: data.precipType,
                temperatureMax: data.temperatureMax,
                temperatureMin: data.temperatureMin,
                visibility: data.visibility,
                windSpeed: data.windSpeed,
                humidity: data.humidity,
                time: data.time
        )
    }

    String formatDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime)
    }

}
