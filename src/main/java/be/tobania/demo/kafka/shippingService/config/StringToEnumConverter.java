package be.tobania.demo.kafka.shippingService.config;

import be.tobania.demo.kafka.shippingService.model.enums.StatusEnum;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, StatusEnum> {
    @Override
    public StatusEnum convert(String input) {
        return StatusEnum.valueOf(input.toUpperCase());
    }
}