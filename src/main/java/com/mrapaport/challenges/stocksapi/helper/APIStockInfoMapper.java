package com.mrapaport.challenges.stocksapi.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrapaport.challenges.stocksapi.dto.response.external.APIStockInfoDto;
import com.mrapaport.challenges.stocksapi.exception.NoTimeSeriesDataPresentException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class APIStockInfoMapper {

    public APIStockInfoDto from(JsonNode node) {
        APIStockInfoDto response = new APIStockInfoDto();
        response.setOpeningPrice(node.get("1. open").asText());
        response.setHighestPrice(node.get("2. high").asText());
        response.setLowestPrice(node.get("3. low").asText());
        response.setClosingPrice(node.get("4. close").asText());
        return response;
    }

    public JsonNode getDailyData(Object apiResponse) throws NoTimeSeriesDataPresentException {
        JsonNode jsonNode = new ObjectMapper().valueToTree(apiResponse);
        String dataKey = "Time Series (Daily)";
        if (jsonNode.has(dataKey)) {
            return jsonNode.get(dataKey);
        }

        throw new NoTimeSeriesDataPresentException();
    }
}
