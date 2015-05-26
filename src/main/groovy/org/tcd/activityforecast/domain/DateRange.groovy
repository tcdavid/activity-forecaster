package org.tcd.activityforecast.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import java.time.LocalDate

@ToString
@EqualsAndHashCode
class DateRange {

    LocalDate startDate
    LocalDate endDate
    
    List<LocalDate> toList() {
        List<LocalDate> totalDates = new ArrayList<>();
        if (!endDate) {
            totalDates << startDate
        } else {
            LocalDate start = startDate
            while (!start.isAfter(endDate)){
                totalDates.add(start);
                start=start.plusDays(1);
            }
        }
        return totalDates
    }
}
