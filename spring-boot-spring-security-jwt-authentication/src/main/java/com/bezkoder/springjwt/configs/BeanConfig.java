package com.bezkoder.springjwt.configs;
/**
 * Copyright © 2023 Mavenir Systems
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Aditya Patil
 * @Date 08-03-2023
 */

@Component
@Slf4j
public class BeanConfig {

    @Bean
    public List<Integer> userList(){
        return new ArrayList<>();
    }

}
