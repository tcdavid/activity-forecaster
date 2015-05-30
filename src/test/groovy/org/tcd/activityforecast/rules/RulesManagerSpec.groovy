package org.tcd.activityforecast.rules

import spock.lang.*
import org.tcd.activityforecast.domain.*

class RulesManagerSpec extends Specification {

    RulesManager rulesManager = new RulesManager()
    
    def "parse between rule" () {
        
        setup:        
            
        when:
            Rule rule = rulesManager.parseRule("SWIMMING precipType EQUALS rain THEN SCORE -4")
            
        then:
            rule == new EqualsRule(activity : Activity.SWIMMING, field: "precipType", ruleValue: "rain", score: -4 )   
  
    }
    
    def "parse equals rule" () {
        
        setup:
            
        when:
            Rule rule = rulesManager.parseRule("SWIMMING temperatureMax BETWEEN -40 AND 50 THEN SCORE -4")
            
        then:
            rule == new BetweenRule(activity : Activity.SWIMMING, field: "temperatureMax", lowerRange: -40, upperRange: 50, score: -4 )
  
    }
    
    def "get rules for Activity" () {
        
        setup:
   
            def swimmingRule = new BetweenRule(activity : Activity.SWIMMING, field: "temperatureMax", lowerRange: -40, upperRange: 50, score: -4 )
            def runningRule = new BetweenRule(activity : Activity.RUNNING, field: "temperatureMax", lowerRange: -40, upperRange: 50, score: -2 )
            rulesManager.rules << swimmingRule
            rulesManager.rules << runningRule
            
        when:
            List<Rule> rules = rulesManager.getRulesForActivity(Activity.SWIMMING)
        
        then:
            rules == [swimmingRule]

    }
    
    def "calculate score" () {
        
        setup:
   
            def swimmingRule1 = new BetweenRule(activity : Activity.SWIMMING, field: "temperatureMax", lowerRange: -40, upperRange: 50, score: -4 )
            def swimmingRule2 = new BetweenRule(activity : Activity.SWIMMING, field: "temperatureMax", lowerRange: 50, upperRange: 60, score: -3 )
            def runningRule1 = new BetweenRule(activity : Activity.RUNNING, field: "temperatureMax", lowerRange: -40, upperRange: 50, score: -2 )
            rulesManager.rules << swimmingRule1
            rulesManager.rules << swimmingRule2
            rulesManager.rules << runningRule1
            
            def weather = new Weather(temperatureMax : 55)
            
        when:
            int score = rulesManager.calculateScore(Activity.SWIMMING, weather)
        
        then:
            score == -3

    }
}
