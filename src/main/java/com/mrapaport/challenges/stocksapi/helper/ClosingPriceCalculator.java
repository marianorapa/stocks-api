package com.mrapaport.challenges.stocksapi.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class ClosingPriceCalculator {

    public String getClosingPriceDiff(String closestDay, String furthestDay) throws NumberFormatException {
        BigDecimal closestDayValue = new BigDecimal(closestDay).setScale(6, RoundingMode.CEILING);
        BigDecimal furthestDayValue = new BigDecimal(furthestDay).setScale(6, RoundingMode.CEILING);
        BigDecimal difference = closestDayValue.subtract(furthestDayValue).setScale(6, RoundingMode.CEILING);
        BigDecimal i = difference.divide(furthestDayValue, RoundingMode.CEILING).multiply(BigDecimal.valueOf(100));
        return i.setScale(4, RoundingMode.CEILING).toString();
    }

}