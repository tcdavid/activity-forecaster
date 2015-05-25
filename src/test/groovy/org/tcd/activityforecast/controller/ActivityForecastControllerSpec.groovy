package org.tcd.activityforecast.controller

    
import org.tcd.activityforecast.domain.DateRange
import org.tcd.activityforecast.domain.Location
import spock.lang.*
import java.time.*

class ActivityForecastControllerSpec extends Specification {

    ActivityForecastController controller = new ActivityForecastController()
    
    def "valid date range 1 week range" () {
        
        setup:
            DateRange range = new DateRange(startDate: LocalDate.of(2015, 5, 23),  endDate: LocalDate.of(2015, 5, 29))
            
        when:
            boolean valid = controller.validateDateRange(range)
            
        then:
            valid == true
  
    }
    
    def "invalid date range 2 week range" () {
        
        setup:
            DateRange range = new DateRange(startDate: LocalDate.of(2015, 5, 23),  endDate: LocalDate.of(2015, 6, 29))
            
        when:
            boolean valid = controller.validateDateRange(range)
            
        then:
            final IllegalArgumentException exception = thrown()
            exception.message == 'max number of days in range is 14'
  
    }
    
    def "invalid date range start after end" () {
        
        setup:
            DateRange range = new DateRange(startDate: LocalDate.of(2015, 5, 30),  endDate: LocalDate.of(2015, 5, 29))
            
        when:
            boolean valid = controller.validateDateRange(range)
            
        then:
            final IllegalArgumentException exception = thrown()
            exception.message == 'startDate after endDate'
  
    }
    
    def "valid location" () {
        
        setup:
            Location location = new Location(latitude: 89.9,  longitude: 179.0)
            
        when:
            boolean valid = controller.validateLocation(location)
            
        then:
            valid == true
  
    }
    
    
    def "invalid location - latitude" () {
        
        setup:
            Location location = new Location(latitude: 90.9,  longitude: 179.0)
            
        when:
            boolean valid = controller.validateLocation(location)
            
        then:
            final IllegalArgumentException exception = thrown()
            exception.message == 'invalid latitude'
  
    }
    
    def "invalid location - longitude" () {
        
        setup:
            Location location = new Location(latitude: 89.9,  longitude: 180.1)
            
        when:
            boolean valid = controller.validateLocation(location)
            
        then:
            final IllegalArgumentException exception = thrown()
            exception.message == 'invalid longitude'
  
    }
}
