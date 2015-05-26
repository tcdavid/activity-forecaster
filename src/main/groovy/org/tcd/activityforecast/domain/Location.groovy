package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
class Location {

    BigDecimal latitude
    BigDecimal longitude
    
    String toCSV() {
        return "${latitude},${longitude}"
    }
}
