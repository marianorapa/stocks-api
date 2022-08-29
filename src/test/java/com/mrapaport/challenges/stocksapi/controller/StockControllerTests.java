//package com.mrapaport.challenges.stocksapi.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mrapaport.challenges.stocksapi.dto.StockInfoDto;
//import com.mrapaport.challenges.stocksapi.dto.response.StockInfoResponseDto;
//import com.mrapaport.challenges.stocksapi.exception.InvalidStockException;
//import com.mrapaport.challenges.stocksapi.filter.AuthorizationFilter;
//import com.mrapaport.challenges.stocksapi.service.AuthorizationService;
//import com.mrapaport.challenges.stocksapi.service.StockService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@WebMvcTest(controllers = StockController.class, excludeFilters={
//        @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value=AuthorizationFilter.class)})
//@AutoConfigureMockMvc
//public class StockControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @MockBean
//    private StockService stockService;
//
//    @Test
//    public void whenRequestingStockInfo_givenInvalidTicker_shouldReturnNotFound() throws Exception {
//
//        Mockito.when(stockService.getStockInfo("ABC")).thenThrow(InvalidStockException.class);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/ABC"))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
//
//    @Test
//    public void whenRequestingStockInfo_givenValidTicker_shouldReturnItsInfo() throws Exception {
//
//        StockInfoDto stockInfo = new StockInfoDto("100.00", "120.00", "95.00", "115.00", "-5");
//        Mockito.when(stockService.getStockInfo("ABC")).thenReturn(stockInfo);
//
//        StockInfoResponseDto expectedResponse = new StockInfoResponseDto(stockInfo);
//        mockMvc.perform(MockMvcRequestBuilders.get("/stocks/ABC"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(mapper.writeValueAsString(expectedResponse)));
//    }
//
//}
