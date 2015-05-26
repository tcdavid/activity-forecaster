package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import java.time.LocalDate

@ToString
@EqualsAndHashCode
class ActivityForecastSummary {
    String date;
    Weather weather;
    List<ActivityForecast> activityForecasts;
}
