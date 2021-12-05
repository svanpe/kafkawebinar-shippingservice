package be.tobania.demo.kafka.shippingService.model;

import be.tobania.demo.kafka.shippingService.model.enums.OrderStatus;
import be.tobania.demo.kafka.shippingService.model.enums.ParcelStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@NoArgsConstructor
public class ParcelForPatch {

    private ParcelStatus status;

}
