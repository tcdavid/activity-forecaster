package org.tcd.activityforecast.service

import java.time.ZoneId;
import java.time.ZonedDateTime;

import spock.lang.*
class WeatherForecastServiceSpec extends Specification {
    
    def "format Date" () {
        
        setup:
            WeatherForecastService service = new WeatherForecastService()
            ZonedDateTime zonedDateTime = ZonedDateTime.of(2015,12,25,0,0,0,0, ZoneId.systemDefault())
            
        when:
            String formattedDate = service.formatDateTime(zonedDateTime)
            
        then:
            formattedDate == "2015-12-25T00:00:00-06:00"
  
    }
}
