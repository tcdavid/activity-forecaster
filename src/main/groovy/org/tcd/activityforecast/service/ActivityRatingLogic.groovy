package org.tcd.activityforecast.service

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service
import org.tcd.activityforecast.domain.*

import org.tcd.activityforecast.rules.RulesManager

@Service
class ActivityRatingLogic {

    @Autowired
    RulesManager rulesManager
    
    Rating determineRating(Activity activity, Weather weather) {
        
        int score = rulesManager.calculateScore(activity, weather)
        
        if (score >= 4) return Rating.GOOD
        if (score >= 2) return Rating.FAIR
        return Rating.POOR
  
    }
}
