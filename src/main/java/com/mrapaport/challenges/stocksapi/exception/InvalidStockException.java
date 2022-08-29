package com.mrapaport.challenges.stocksapi.exception;

import lombok.Getter;

@Getter
public class InvalidStockException extends Exception{

    private String ticker;

    public InvalidStockException(String ticker) {
        this.ticker = ticker;
    }

}
