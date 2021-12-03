package be.tobania.demo.kafka.shippingService.model;

import be.tobania.demo.kafka.shippingService.model.enums.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Validated
@Getter
@Setter
@NoArgsConstructor
public class OrderForPatch {

    private StatusEnum status;

}
