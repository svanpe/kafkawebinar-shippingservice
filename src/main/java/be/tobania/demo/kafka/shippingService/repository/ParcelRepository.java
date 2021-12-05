package be.tobania.demo.kafka.shippingService.repository;

import be.tobania.demo.kafka.shippingService.entities.ParcelEntity;
import be.tobania.demo.kafka.shippingService.model.enums.ParcelStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParcelRepository extends CrudRepository<ParcelEntity, Long> {

    public List<ParcelEntity> findParcelEntitiesByStatus(ParcelStatus status);

}
