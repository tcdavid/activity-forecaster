package org.tcd.activityforecast.rules

import groovy.transform.EqualsAndHashCode;
import groovy.transform.ToString;

@ToString
@EqualsAndHashCode
class BetweenRule extends Rule {

    BigDecimal lowerRange
    BigDecimal upperRange
    
    int appliedScore(def data) {
        def value = data."$field"
        if (value >= lowerRange && value < upperRange) {
            return score
        }
        return 0
    }
}
