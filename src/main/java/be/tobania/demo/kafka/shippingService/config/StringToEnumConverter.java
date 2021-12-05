package be.tobania.demo.kafka.shippingService.config;

import be.tobania.demo.kafka.shippingService.model.enums.OrderStatus;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, OrderStatus> {
    @Override
    public OrderStatus convert(String input) {
        return OrderStatus.valueOf(input.toUpperCase());
    }
}