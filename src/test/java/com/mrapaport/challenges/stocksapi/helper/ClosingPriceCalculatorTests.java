package com.mrapaport.challenges.stocksapi.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClosingPriceCalculatorTests {

    ClosingPriceCalculator calculator = new ClosingPriceCalculator();

    @Test
    public void increasedValueCalculationTest() {
        String closingPriceDiff = calculator.getClosingPriceDiff("150", "100");
        Assertions.assertEquals("50.0000", closingPriceDiff);
    }

    @Test
    public void decreasedValueCalculationTest() {
        String closingPriceDiff = calculator.getClosingPriceDiff("100", "200");
        Assertions.assertEquals("-50.0000", closingPriceDiff);
    }

}
