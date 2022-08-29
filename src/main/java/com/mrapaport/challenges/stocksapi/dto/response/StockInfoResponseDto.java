package com.mrapaport.challenges.stocksapi.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrapaport.challenges.stocksapi.dto.StockInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockInfoResponseDto {

    @JsonProperty(value = "last_open_price")
    private String lastOpenPrice;

    @JsonProperty(value = "last_higher_price")
    private String lastHigherPrice;

    @JsonProperty(value = "last_lower_price")
    private String lastLowerPrice;

    @JsonProperty(value = "last_closing_price")
    private String lastClosingPrice;

    @JsonProperty(value = "closing_price_variation_percent")
    private String closingPriceVariationPercentage;

    public StockInfoResponseDto(StockInfoDto stockInfo) {
        this.lastOpenPrice = stockInfo.getLastOpenPrice();
        this.lastHigherPrice = stockInfo.getLastHigherPrice();
        this.lastLowerPrice = stockInfo.getLastLowerPrice();
        this.lastClosingPrice = stockInfo.getLastClosingPrice();
        this.closingPriceVariationPercentage = stockInfo.getClosingPriceVariation();
    }
}
