package org.tcd.activityforecast.controller

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.tcd.activityforecast.domain.ActivityForecast
import org.tcd.activityforecast.domain.ActivityForecastSummary
import org.tcd.activityforecast.service.ActivityForecastService

@RestController
@RequestMapping("/api/activityforecast")
public class ActivityForecastController {

    @Autowired
    ActivityForecastService service
    
    @RequestMapping(value = "/{location}", method = RequestMethod.GET)
    List<ActivityForecastSummary> getActivityForecast(@PathVariable String location) {
        
        return service.getActivityForecast(location)
    }
}
