package com.mrapaport.challenges.stocksapi.controller;

import com.mrapaport.challenges.stocksapi.dto.StockInfoDto;
import com.mrapaport.challenges.stocksapi.dto.response.StockInfoResponseDto;
import com.mrapaport.challenges.stocksapi.exception.InvalidStockException;
import com.mrapaport.challenges.stocksapi.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    Logger logger = LoggerFactory.getLogger(StockController.class);

    @GetMapping("/{ticker}")
    @Operation(summary = "Get stock info based on it's ticker",
            parameters = @Parameter(in = ParameterIn.HEADER, name = "x-api-key", required = true))
    public StockInfoResponseDto getStockInfo(@PathVariable String ticker) throws InvalidStockException {
        logger.info("Stock request received for ticker: {}", ticker);
        StockInfoDto stockInfo = stockService.getStockInfo(ticker);
        return new StockInfoResponseDto(stockInfo);
    }

}
