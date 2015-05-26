package org.tcd.activityforecast.controller

import java.time.ZonedDateTime
import java.time.ZoneId
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

import spock.lang.*

import org.tcd.activityforecast.*
import org.tcd.activityforecast.domain.ActivityForecast
import org.tcd.activityforecast.domain.ActivityForecastSummary;
import org.tcd.activityforecast.domain.Location;
import org.tcd.activityforecast.domain.Activity;
import org.tcd.activityforecast.domain.Rating;
import org.tcd.activityforecast.service.WeatherForecastService;
import org.springframework.boot.test.*

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
@IntegrationTest
class ActivityForecastControllerIntegrationSpec extends Specification {
    
    @Autowired
    ActivityForecastController  controller
    
    def "getForecast" () {
        
        setup:
            String location = "36,-122.422"
            String daterange = "2015-05-20,2015-05-23"
            
        when:
             List<ActivityForecastSummary> summaries = controller.getActivityForecast(location, daterange)
            
        then:
            summaries.size == 4
            summaries[0].activityForecasts[0] == new ActivityForecast(activity: Activity.RUNNING, rating: Rating.GOOD)
    }
   
}
