package be.tobania.demo.kafka.shippingService.model;

import be.tobania.demo.kafka.shippingService.model.enums.StatusEnum;
import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Validated
@ApiModel("Contain order data")
@ToString
public class Order {

    private Long id;

    private LocalDate creationDate;

    private Customer customer;

    private StatusEnum status;

    private List<OrderItem> orderItems;
}
