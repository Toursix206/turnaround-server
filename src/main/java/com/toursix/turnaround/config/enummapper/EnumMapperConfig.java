package com.toursix.turnaround.config.enummapper;

import com.toursix.turnaround.common.util.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumMapperConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();

        return enumMapper;
    }
}
