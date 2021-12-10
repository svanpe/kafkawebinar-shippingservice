package be.tobania.demo.kafka.shippingService.service;

import be.tobania.demo.kafka.shippingService.entities.ParcelEntity;
import be.tobania.demo.kafka.shippingService.model.Order;
import be.tobania.demo.kafka.shippingService.model.Parcel;
import be.tobania.demo.kafka.shippingService.model.ParcelForPatch;
import be.tobania.demo.kafka.shippingService.model.enums.OrderStatus;
import be.tobania.demo.kafka.shippingService.model.enums.ParcelStatus;
import be.tobania.demo.kafka.shippingService.repository.ParcelRepository;
import be.tobania.demo.kafka.shippingService.service.mapper.ParcelApiEntityMapper;
import be.tobania.demo.kafka.shippingService.service.mapper.ParcelEntityApiMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {

    private static final String ORDER_TOPIC = "orders";
    private static final String SHIPPING_TOPIC = "shipping";


    private final ParcelRepository parcelRepository;
    public final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Parcel> kafkaTemplate;


    @Transactional
    public Parcel createParcel(Parcel parcel) {

        ParcelEntity parcelEntity = ParcelApiEntityMapper.maParcelEntity(parcel);

        ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

        if(parcelApi.getStatus()!=null){
            publishParcel(parcelApi);
        }

        return parcelApi;
    }

    @Transactional
    public List<Parcel> getPaymentsByStatus(List<String> statuses) {

        List<ParcelEntity> parcelEntities = new ArrayList<>();

        statuses.forEach(status -> parcelEntities.addAll(parcelRepository.findParcelEntitiesByStatus(ParcelStatus.fromValue(status))));

        return parcelEntities.stream().map(ParcelEntityApiMapper::maParcel).collect(Collectors.toList());
    }


    @Transactional
    public Parcel patchOrder(final ParcelForPatch parcelForPatch, final Long parcelId) {

        final ParcelEntity parcelEntity = parcelRepository.findById(parcelId).orElseThrow(() -> new RuntimeException("No Parcel found for the given id"));

        parcelEntity.setStatus(parcelForPatch.getStatus());

        final ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

        log.info("publish updated parcel");

        publishParcel(parcelApi);

        return parcelApi;
    }

    public Parcel getParcelById(final Long parcelId) {

        final ParcelEntity parcelEntity = parcelRepository.findById(parcelId).orElseThrow(() -> new RuntimeException("No Parcel found for the given id"));
        final ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        final Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

        return parcelApi;

    }


    @Async
    public void publishParcel(Parcel parcel){

        log.info("start publishing parcel");

        kafkaTemplate.send(SHIPPING_TOPIC, parcel.getId().toString(), parcel);

        log.info("payment published");

    }


    @KafkaListener(topics = ORDER_TOPIC, groupId = "shipping-service")
    public void consume(Order order) throws IOException {

        //TOD: refactor this part of the code to make more readable

        if (OrderStatus.PAYED == order.getStatus()) {
            log.info(String.format("# Consumed new order with status-> %s", order.getStatus().name()));

            log.info("generate a new parcel");

            Parcel parcel = new Parcel();

            parcel.setOrder(order);

            Parcel newParcel = this.createParcel(parcel);
            String logParcel = objectMapper.writeValueAsString(newParcel);

            log.info(logParcel);

        }
    }
}
