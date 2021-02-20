package com.huchuchu.book.web.dto;

import org.junit.jupiter.api.Test;


public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트(){

        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);


    }
}
