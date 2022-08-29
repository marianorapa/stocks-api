package com.mrapaport.challenges.stocksapi.dto.response.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIStockInfoDto {

    private String openingPrice;

    private String highestPrice;

    private String lowestPrice;

    private String closingPrice;


}
