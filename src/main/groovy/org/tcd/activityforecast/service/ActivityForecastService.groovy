package org.tcd.activityforecast.service

import java.time.ZonedDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.tcd.activityforecast.domain.*

@Service
public class ActivityForecastService {

    @Autowired
    ForecastService forecastService
    
    @Autowired
    ActivityRatingLogic ratingLogic
    
    List<ActivityForecastSummary> getActivityForecast(Location location, DateRange dateRange) {
        
        // TODO - do multiple calls for range
        ZonedDateTime datetime = ZonedDateTime.of(dateRange.startDate, LocalTime.of(0,0,0), ZoneOffset.systemDefault())
        def forecast = forecastService.getForecast(location, datetime)
        def summaries = []
        
        for (i in 0..0) {
        
            Weather weather = translateWeatherData(forecast.daily.data[i])
            
            def activityForecasts = []
            
            for (activity in Activity.values()) {
                Rating rating = ratingLogic.determineRating(activity, weather)
                activityForecasts << new ActivityForecast(activity: activity, rating: rating)
            }
            
            def summary = new ActivityForecastSummary(time: 1234L, 
                    weather: weather, activityForecasts: activityForecasts)
            
            summaries << summary
        
        }
        
        return summaries
    }
    
    // TODO - perform the translation in the forecast service
    Weather translateWeatherData(def data) {
        Weather weather = new Weather()
        weather.cloudCover = data.cloudCover
        weather.moonPhase = data.moonPhase
        weather.precipProbability = data.precipProbability
        weather.precipType = data.precipType
        weather.temperatureMax = data.temperatureMax
        weather.temperatureMin = data.temperatureMin
        weather.visibility = data.visibility
        weather.windSpeed = data.windSpeed
        weather.humidity = data.humidity
        return weather
    }
}