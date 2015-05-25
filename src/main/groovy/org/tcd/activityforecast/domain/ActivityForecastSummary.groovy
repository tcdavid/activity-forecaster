package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

@ToString
@EqualsAndHashCode
class ActivityForecastSummary {
    Long time;
    Weather weather;
    List<ActivityForecast> activityForecasts;
}
