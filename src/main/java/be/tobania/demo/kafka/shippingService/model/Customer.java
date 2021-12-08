package be.tobania.demo.kafka.shippingService.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Getter
@Setter
@Validated
@ApiModel("customer data")
public class Customer   {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

}
