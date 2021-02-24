package com.huchuchu.book.web.dto;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HelloResponseDtoTest {

    @Autowired
    Assertions assertions;

    @Test
    public void 롬복_기능_테스트(){

        String name = "test";
        int amount = 1000;

        HelloResponseDto dto = new HelloResponseDto(name, amount);


//        name = "test01";

        assertions.assertThat(dto.getName()).isEqualTo(name);
        assertions.assertThat(dto.getAmount()).isEqualTo(amount);




    }
}
