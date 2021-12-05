package be.tobania.demo.kafka.shippingService.service;

import be.tobania.demo.kafka.shippingService.entities.ParcelEntity;
import be.tobania.demo.kafka.shippingService.model.Parcel;
import be.tobania.demo.kafka.shippingService.model.ParcelForPatch;
import be.tobania.demo.kafka.shippingService.model.enums.ParcelStatus;
import be.tobania.demo.kafka.shippingService.repository.ParcelRepository;
import be.tobania.demo.kafka.shippingService.service.mapper.ParcelApiEntityMapper;
import be.tobania.demo.kafka.shippingService.service.mapper.ParcelEntityApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingService {
    
    private final ParcelRepository parcelRepository;


    @Transactional
    public Parcel createParcel(Parcel parcel) {

        ParcelEntity parcelEntity = ParcelApiEntityMapper.maParcelEntity(parcel);

        ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

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

        final ParcelEntity parcelEntity = parcelRepository.findById(parcelId).orElseThrow(()-> new RuntimeException("No Parcel found for the given id"));

         parcelEntity.setStatus(parcelForPatch.getStatus());

        final ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

        return parcelApi;
    }

    public Parcel getParcelById(final Long parcelId) {

        final ParcelEntity parcelEntity = parcelRepository.findById(parcelId).orElseThrow(()-> new RuntimeException("No Parcel found for the given id"));
        final ParcelEntity addedParcel = parcelRepository.save(parcelEntity);

        final Parcel parcelApi = ParcelEntityApiMapper.maParcel(addedParcel);

        return parcelApi;

    }
}
