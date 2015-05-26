package org.tcd.activityforecast.rules

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component
import org.tcd.activityforecast.domain.Activity
import org.tcd.activityforecast.domain.Weather
import javax.annotation.PostConstruct;

@Component
class RulesManager {

    Logger logger = LoggerFactory.getLogger(RulesManager.class)
    
    List<Rule> rules = []
    
    public RulesManager() { }
    
    @PostConstruct
    init() {
        this.rules = new ArrayList<Rule>()
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
            EqualsRule rule = new EqualsRule()
            rule.activity = Activity.valueOf(tokens[0])
            rule.field = tokens[1]
            rule.ruleValue = tokens[3]
            rule.score = new Integer(tokens[6])
            return rule
        } else {
            BetweenRule rule = new BetweenRule()
            rule.activity = Activity.valueOf(tokens[0])
            rule.field = tokens[1]
            rule.lowerRange = new BigDecimal(tokens[3])
            rule.upperRange = new BigDecimal(tokens[5])
            rule.score = new Integer(tokens[8])
            return rule
        }
    }
    
    List<Rule> getRulesForActivity(Activity activity) {
        return rules.findAll { it.activity == activity }
    }
    
}
