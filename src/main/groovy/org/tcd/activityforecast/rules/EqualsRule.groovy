package org.tcd.activityforecast.rules

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class EqualsRule extends Rule {

    def ruleValue
    
    int appliedScore(def data) {
        def value = data."$field"
        if (value ==  ruleValue ) {
            return score
        }
        return 0
    }
}
