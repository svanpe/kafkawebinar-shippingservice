package be.tobania.demo.kafka.shippingService.entities;

import be.tobania.demo.kafka.shippingService.model.enums.ParcelStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ParcelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ParcelStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private OrderEntity order;


}
