package com.mrapaport.challenges.stocksapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrapaport.challenges.stocksapi.dto.StockInfoDto;
import com.mrapaport.challenges.stocksapi.exception.InvalidStockException;
import com.mrapaport.challenges.stocksapi.helper.ClosingPriceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class StockServiceTests {

    @Autowired
    private StockService stockService;

    @MockBean(name = "Stocks")
    private RestTemplate restTemplate;

    @Autowired
    private ClosingPriceCalculator calculator;

    @Value("${stocks.external.api.key}")
    private String stocksApiKey;

    @Test
    public void whenRequestingStockInfo_givenNotExistentTicker_shouldThrowInvalidStockException() throws JsonProcessingException {
        String ticker = "ABCDE";
        String uri = String.format("query?function=TIME_SERIES_DAILY&symbol=%s&outputsize=compact&apikey=%s", ticker, stocksApiKey);
        JsonNode response = new ObjectMapper().readTree("{\"Error Message\": \"Invalid API call. Please retry or visit the documentation (https://www.alphavantage.co/documentation/) for TIME_SERIES_DAILY.\"}");
        Mockito.when(restTemplate.getForObject(uri, Object.class)).thenReturn(response);
        Assertions.assertThrows(InvalidStockException.class, () -> stockService.getStockInfo(ticker));
    }

    @Test
    public void whenRequestingStockInfo_givenExistentTicker_shouldReturnItsInfo() throws JsonProcessingException, InvalidStockException {
        String ticker = "ABC";
        String uri = String.format("query?function=TIME_SERIES_DAILY&symbol=%s&outputsize=compact&apikey=%s", ticker, stocksApiKey);
        JsonNode response = new ObjectMapper().readTree("{\n" +
                "    \"Meta Data\": {\n" +
                "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                "        \"2. Symbol\": \"ABC\",\n" +
                "        \"3. Last Refreshed\": \"2022-08-26\",\n" +
                "        \"4. Output Size\": \"Compact\",\n" +
                "        \"5. Time Zone\": \"US/Eastern\"\n" +
                "    },\n" +
                "    \"Time Series (Daily)\": {\n" +
                "        \"2022-08-26\": {\n" +
                "            \"1. open\": \"150.4600\",\n" +
                "            \"2. high\": \"150.9699\",\n" +
                "            \"3. low\": \"146.0300\",\n" +
                "            \"4. close\": \"146.1900\",\n" +
                "            \"5. volume\": \"766717\"\n" +
                "        },\n" +
                "        \"2022-08-25\": {\n" +
                "            \"1. open\": \"148.2700\",\n" +
                "            \"2. high\": \"150.5200\",\n" +
                "            \"3. low\": \"147.8000\",\n" +
                "            \"4. close\": \"150.4200\",\n" +
                "            \"5. volume\": \"747074\"\n" +
                "        },\n" +
                "        \"2022-08-24\": {\n" +
                "            \"1. open\": \"148.3800\",\n" +
                "            \"2. high\": \"148.7400\",\n" +
                "            \"3. low\": \"147.1300\",\n" +
                "            \"4. close\": \"148.2300\",\n" +
                "            \"5. volume\": \"455243\"\n" +
                "        },\n" +
                "        \"2022-08-23\": {\n" +
                "            \"1. open\": \"148.5500\",\n" +
                "            \"2. high\": \"149.2400\",\n" +
                "            \"3. low\": \"147.6600\",\n" +
                "            \"4. close\": \"148.0200\",\n" +
                "            \"5. volume\": \"552813\"\n" +
                "        }" +
                "   }" +
                "}");
        Mockito.when(restTemplate.getForObject(uri, Object.class)).thenReturn(response);
        StockInfoDto stockInfo = stockService.getStockInfo(ticker);
        Assertions.assertEquals("150.4600", stockInfo.getLastOpenPrice());
        Assertions.assertEquals("150.9699", stockInfo.getLastHigherPrice());
        Assertions.assertEquals("146.0300", stockInfo.getLastLowerPrice());
        Assertions.assertEquals("146.1900", stockInfo.getLastClosingPrice());
        Assertions.assertEquals(calculator.getClosingPriceDiff("146.1900", "150.4200"), stockInfo.getClosingPriceVariation());
    }

}
