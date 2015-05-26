package org.tcd.activityforecast.service

import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.tcd.activityforecast.domain.*
import org.tcd.activityforecast.rules.RulesManager;

@Service
public class ActivityForecastService {

    @Autowired
    WeatherForecastService forecastService
    
    @Autowired
    RulesManager rulesManager

    List<ActivityForecastSummary> getActivityForecast(Location location, DateRange dateRange) {
        
        return dateRange.toList().collect { date ->
            ZonedDateTime datetime = createZonedDateTime(date)
            def summary = getActivityForecastForDate(location, datetime)
        }
    }

    ActivityForecastSummary getActivityForecastForDate(Location location, ZonedDateTime datetime) {
        Weather weather = forecastService.getForecast(location, datetime)

        def activityForecasts = Activity.values().collect {activity ->
            Rating rating = determineRating(activity, weather)
            new ActivityForecast(activity: activity, rating: rating)
        }

        def summary = new ActivityForecastSummary(time: weather.time,
                                weather: weather, activityForecasts: activityForecasts)
        return summary
    }
    
    Rating determineRating(Activity activity, Weather weather) {
        
        int score = rulesManager.calculateScore(activity, weather)
        
        if (score >= 4) return Rating.GOOD
        if (score >= 2) return Rating.FAIR
        return Rating.POOR
    }
    
    // TODO - use location to get timezone/zone offset
    ZonedDateTime createZonedDateTime(LocalDate date) {
        ZonedDateTime.of(date, LocalTime.of(0,0,0), ZoneOffset.systemDefault())
    }

}
