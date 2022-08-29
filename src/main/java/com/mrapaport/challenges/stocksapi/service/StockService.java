package com.mrapaport.challenges.stocksapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mrapaport.challenges.stocksapi.dto.StockInfoDto;
import com.mrapaport.challenges.stocksapi.dto.response.external.APIStockInfoDto;
import com.mrapaport.challenges.stocksapi.exception.InvalidStockException;
import com.mrapaport.challenges.stocksapi.exception.NoTimeSeriesDataPresentException;
import com.mrapaport.challenges.stocksapi.helper.APIStockInfoMapper;
import com.mrapaport.challenges.stocksapi.helper.ClosingPriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;

@Service
public class StockService {

    @Autowired
    @Qualifier("Stocks")
    private RestTemplate restTemplate;

    @Value("${stocks.external.api.key}")
    private String stocksApiKey;

    @Autowired
    APIStockInfoMapper mapper;

    @Autowired
    ClosingPriceCalculator calculator;

    public StockInfoDto getStockInfo(String ticker) throws InvalidStockException {
        String uri = buildUri(ticker);
        Object apiResponse = restTemplate.getForObject(uri, Object.class);
        try {
            JsonNode node = mapper.getDailyData(apiResponse);

            Iterator<JsonNode> elements = node.elements();

            APIStockInfoDto lastDay = mapper.from(elements.next());
            APIStockInfoDto dayBefore = mapper.from(elements.next());

            return new StockInfoDto(lastDay.getOpeningPrice(), lastDay.getHighestPrice(),
                    lastDay.getLowestPrice(), lastDay.getClosingPrice(), calculator.getClosingPriceDiff(lastDay.getClosingPrice(), dayBefore.getClosingPrice()));
        }
        catch (NoTimeSeriesDataPresentException e) {
            throw new InvalidStockException(ticker);
        }
    }

    private String buildUri(String ticker) {
        return String.format("query?function=TIME_SERIES_DAILY&symbol=%s&outputsize=compact&apikey=%s", ticker, stocksApiKey);
    }
}