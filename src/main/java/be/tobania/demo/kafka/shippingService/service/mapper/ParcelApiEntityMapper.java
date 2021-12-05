package be.tobania.demo.kafka.shippingService.service.mapper;

import be.tobania.demo.kafka.shippingService.entities.*;
import be.tobania.demo.kafka.shippingService.model.Order;
import be.tobania.demo.kafka.shippingService.model.Parcel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Transactional
public class ParcelApiEntityMapper {


    public static OrderEntity mapOrder(Order order){

        CustomerEntity customer = new CustomerEntity();
                customer.setEmail(order.getCustomer().getEmail());
                customer.setFirstName(order.getCustomer().getFirstName());
                customer.setLastName(order.getCustomer().getLastName());
                customer.setPassword(order.getCustomer().getPassword());

        List<OrderItemEntity> orderItemList = order.getOrderItems().stream().map(item->{
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setQuantity(item.getQuantity());

            ProductEntity productEntity = new ProductEntity();
            productEntity.setName(item.getProduct().getName());
            productEntity.setDescription(item.getProduct().getDescription());

            orderItem.setProductEntity(productEntity);
            return orderItem;
        }).collect(Collectors.toList());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCreationDate(order.getCreationDate());
        orderEntity.setCustomerEntity(customer);
        orderEntity.setStatus(order.getStatus().getValue());
        orderEntity.setOrderItems(orderItemList);

        return orderEntity;

    }

    public static ParcelEntity maParcelEntity(Parcel parcel){

        ParcelEntity parcelEntity = new ParcelEntity();

        parcelEntity.setOrder(mapOrder(parcel.getOrder()));

        parcelEntity.setStatus(parcel.getStatus());

        return parcelEntity;

    }



}
