package org.tcd.activityforecast.service

import java.time.ZonedDateTime
import java.time.ZoneId

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

import spock.lang.*

import org.tcd.activityforecast.*
import org.tcd.activityforecast.domain.Location;
import org.springframework.boot.test.*

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
@IntegrationTest
class WeatherForecastServiceIntegrationSpec extends Specification {

    @Autowired
    WeatherForecastService  service
    
    def "getForecast" () {
        
        setup:
            Location location = new Location(latitude: 37.8267, longitude: -122.423)
            ZonedDateTime zonedDateTime = ZonedDateTime.of(2015,05,22,0,0,0,0, ZoneId.systemDefault())
            
        when:
            def result = service.getForecast(location, zonedDateTime)
            
        then:
            result.currently.time == 1432270800
  
    }
    
}
