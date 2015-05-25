package org.tcd.activityforecast.controller

import java.time.LocalDate

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.HttpStatus
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
        
        // Check parameters for validity
        // Location - required, valid latitude and longitude
        // DateRange - startDate - required
        // DateRange - startDate >= today - 1 week
        // DateRange - endDate - startDate <= 14
        
        DateRange dateRange = createDateRange(daterange)
        validateDateRange(dateRange)
        Location position = createLocation(location)
        validateLocation(position)
        return service.getActivityForecast(position, dateRange)
    }
    
    Location createLocation(String value) {
        List<String> values = value.tokenize(",")

        new Location(latitude: new BigDecimal(values[0]), longitude: new BigDecimal( values[1]))
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
    
    boolean validateDateRange(DateRange dateRange) {
        if (!dateRange.startDate) {
            throw new IllegalArgumentException("startDate is required")
        }
        if (dateRange.toList().size() > 14) {
            throw new IllegalArgumentException("max number of days in range is 14")
        }
        if (dateRange.startDate && dateRange.endDate) {
            if (dateRange.startDate.isAfter(dateRange.endDate)) {
                throw new IllegalArgumentException("startDate after endDate")
            }
        }
        if (dateRange.startDate.isBefore(LocalDate.now().minusDays(7)) ) {
            throw new IllegalArgumentException("startDate must be not be earlier than 7 days prior to today")
        }
        return true
    }
    
    boolean validateLocation(Location location) {
        if (location.latitude < -90 || location.latitude > 90) {
            throw new IllegalArgumentException("invalid latitude")
        } 
        if (location.longitude < -180 || location.longitude > 180) {
            throw new IllegalArgumentException("invalid longitude")
        }
        return true
    }
}
