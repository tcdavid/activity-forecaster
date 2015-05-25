package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

@ToString
@EqualsAndHashCode
class Weather {
    BigDecimal humidity
    BigDecimal precipProbability
    String precipType
    BigDecimal temperatureMin
    BigDecimal temperatureMax
    BigDecimal cloudCover
    BigDecimal visibility
    BigDecimal windSpeed
    BigDecimal moonPhase
}
