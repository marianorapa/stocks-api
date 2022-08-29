package com.mrapaport.challenges.stocksapi.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrapaport.challenges.stocksapi.dto.response.external.APIStockInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class APIStockInfoMapperTests {

    APIStockInfoMapper mapper = new APIStockInfoMapper();

    @Test
    public void whenMappingStockInfo_givenValidJsonNode_shouldReturnStockInfoWithValuesMapped() throws JsonProcessingException {
        String jsonString = "{\"1. open\":\"150.4600\",\"2. high\":\"150.9699\",\"3. low\":\"146.0300\",\"4. close\":\"146.1900\",\"5. volume\":\"766717\"}\n";
        JsonNode node = new ObjectMapper().readTree(jsonString);
        APIStockInfoDto mappedDto = mapper.from(node);
        Assertions.assertEquals("150.4600", mappedDto.getOpeningPrice());
        Assertions.assertEquals("150.9699", mappedDto.getHighestPrice());
        Assertions.assertEquals("146.0300", mappedDto.getLowestPrice());
        Assertions.assertEquals("146.1900", mappedDto.getClosingPrice());
    }

}
