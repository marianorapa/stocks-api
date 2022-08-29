package com.mrapaport.challenges.stocksapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfoDto {

    @JsonProperty(value = "last_open_price")
    private String lastOpenPrice;

    @JsonProperty(value = "last_higher_price")
    private String lastHigherPrice;

    @JsonProperty(value = "last_lower_price")
    private String lastLowerPrice;

    @JsonProperty(value = "last_closing_price")
    private String lastClosingPrice;

    @JsonProperty(value = "closing_price_variation")
    private String closingPriceVariation;

}
