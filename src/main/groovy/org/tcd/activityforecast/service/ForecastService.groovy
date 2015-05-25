package org.tcd.activityforecast.service

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import groovy.json.JsonSlurper
import groovy.transform.Memoized;


@Service
public class ForecastService {

    Logger logger = LoggerFactory.getLogger(ForecastService.class)
    
    @Value('${api.url}')
    String url
    
    @Value('${api.key}')
    String key
    
    // TODO - error response behavior
    @Memoized
    def getForecast(String location) {
        RestTemplate restTemplate = new RestTemplate()
        
        String result = restTemplate.getForObject(url, String.class, key, location)
        logger.debug(result)
        def slurper = new JsonSlurper()
        slurper.parseText(result)
        
    }
    
}
