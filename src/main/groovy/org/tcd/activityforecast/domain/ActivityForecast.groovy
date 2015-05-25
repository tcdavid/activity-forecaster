package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class ActivityForecast {

    Activity activity
    Rating rating
}
