package org.tcd.activityforecast.rules

import org.tcd.activityforecast.domain.Activity

abstract class Rule {
    Activity activity
    int score
    String field
    
    abstract int appliedScore(def data)
}
