package org.tcd.activityforecast.service

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.tcd.activityforecast.domain.Location

import groovy.json.JsonSlurper
import groovy.transform.Memoized;
import java.sql.Timestamp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@Service
public class WeatherForecastService {

    Logger logger = LoggerFactory.getLogger(WeatherForecastService.class)
    
    @Value('${api.url}')
    String url
    
    @Value('${api.key}')
    String key
    
    // TODO - handle error response behavior
    // TODO - translate to Weather object here
    @Memoized
    def getForecast(Location location, ZonedDateTime datetime) {
        logger.debug("location ${location}")
        logger.debug("dateTime ${datetime}")
        
        String formattedDateTime = formatDateTime(datetime)
        logger.debug("formattedDateTime ${formattedDateTime}")
        RestTemplate restTemplate = new RestTemplate()
        String result = restTemplate.getForObject(url, String.class, key, location.toCSV(), formattedDateTime)
        logger.debug("weather forecast response ${result}")
        def slurper = new JsonSlurper()
        slurper.parseText(result)
    }
    
    String formatDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter f = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        return f.format(zonedDateTime)
    }
    
}
