package org.tcd.activityforecast.rules

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.tcd.activityforecast.domain.Activity
import org.tcd.activityforecast.domain.Weather
import javax.annotation.PostConstruct

@Component
class RulesManager {

    Logger logger = LoggerFactory.getLogger(RulesManager.class)
    
    List<Rule> rules = []
    
    public RulesManager() { }
    
    @PostConstruct
    init() {
        this.getClass().getResource('/rules.txt').eachLine { line ->
            if (!line.empty) {
                rules << parseRule(line)
            }
        }
        
        logger.debug("Number of rules ${rules.size}")
    }
    
    int calculateScore(Activity activity, Weather data) {
        int score = 0
        List<Rule> applicableRules = getRulesForActivity(activity);
        if (applicableRules) {
            score = applicableRules.sum { it.appliedScore(data) }
        }
        
        logger.debug("score " + score)
        return score
    }
    
    Rule parseRule(String text) {
        
        List<String> tokens = text.tokenize()
        
        if (tokens[2] == "EQUALS") {
            return new EqualsRule(
                activity: Activity.valueOf(tokens[0]),
                field: tokens[1],
                ruleValue: tokens[3],
                score: new Integer(tokens[6]))
        } else {
            return new BetweenRule(
                activity: Activity.valueOf(tokens[0]),
                field: tokens[1],
                lowerRange: new BigDecimal(tokens[3]),
                upperRange: new BigDecimal(tokens[5]),
                score: new Integer(tokens[8]))
        }
    }
    
    List<Rule> getRulesForActivity(Activity activity) {
        return rules.findAll { it.activity == activity }
    }
    
}
