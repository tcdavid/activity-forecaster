package org.tcd.activityforecast.service

import java.time.ZonedDateTime
import java.time.ZoneId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.*
import org.tcd.activityforecast.*
import org.springframework.boot.test.*

@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = Application.class)
@WebAppConfiguration
@IntegrationTest
class WeatherForecastServiceIntegrationSpec extends Specification {

    @Autowired
    WeatherForecastService  service
    
    def "format Date" () {
        
        setup:
            
            ZonedDateTime zonedDateTime = ZonedDateTime.of(2015,12,25,0,0,0,0, ZoneId.systemDefault())
            
        when:
            String formattedDate = service.formatDateTime(zonedDateTime)
            
        then:
            formattedDate == "2015-12-25T00:00:00-06:00"
  
    }
}
