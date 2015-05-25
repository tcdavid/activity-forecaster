package org.tcd.activityforecast.controller

import java.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.tcd.activityforecast.domain.ActivityForecast
import org.tcd.activityforecast.domain.ActivityForecastSummary
import org.tcd.activityforecast.domain.DateRange
import org.tcd.activityforecast.domain.Location
import org.tcd.activityforecast.service.ActivityForecastService

@RestController
@RequestMapping("/api/activityforecast")
public class ActivityForecastController {

    @Autowired
    ActivityForecastService service
    
    @RequestMapping(value = "/{location}/{daterange}", method = RequestMethod.GET)
    List<ActivityForecastSummary> getActivityForecast(@PathVariable String location, @PathVariable String daterange) {
        
        // TODO - check parameters for validity
        // DateRange - startDate >= today - 1 week
        // DateRange - endDate - startDate <= 14
        
        // TODO - use location to get Timezone
        return service.getActivityForecast(createLocation(location), createDateRange(daterange))
    }
    
    Location createLocation(String value) {
        List<String> values = value.tokenize(",")
        return new Location(latitude: new BigDecimal(values[0]), longitude: new BigDecimal( values[1]))
    }
    
    DateRange createDateRange(String value) {
        DateRange dateRange
        List<String> values = value.tokenize(",")
        if (values.size() == 2) {
           dateRange = new DateRange(startDate: LocalDate.parse(values[0]), endDate: LocalDate.parse(values[1]))
        } else {
           dateRange = new DateRange(startDate: LocalDate.parse(values[0]), endDate: null)
        }
        return dateRange
    }
}
