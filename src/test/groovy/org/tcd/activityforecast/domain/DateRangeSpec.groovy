package org.tcd.activityforecast.domain

import spock.lang.*
import java.time.*

class DateRangeSpec extends Specification {
    
    def "toList 1 week range" () {
        
        setup:
            DateRange range = new DateRange(startDate: LocalDate.of(2015, 5, 23),  endDate: LocalDate.of(2015, 5, 29))
            
        when:
            List<LocalDate> dates = range.toList()
            
        then:
            dates.size() == 7 
  
    }
    
    def "toList single date" () {
        
       setup:
            DateRange range = new DateRange(startDate: LocalDate.of(2015, 5, 23),  endDate: null)
            
        when:
            List<LocalDate> dates = range.toList()
            
        then:
            dates.size() == 1
  
    }
}
