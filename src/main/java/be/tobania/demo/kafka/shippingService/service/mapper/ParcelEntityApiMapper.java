package be.tobania.demo.kafka.shippingService.service.mapper;


import be.tobania.demo.kafka.shippingService.entities.OrderEntity;
import be.tobania.demo.kafka.shippingService.entities.ParcelEntity;
import be.tobania.demo.kafka.shippingService.model.*;
import be.tobania.demo.kafka.shippingService.model.enums.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ParcelEntityApiMapper {

    public static Order mapOrder(OrderEntity order){

       Customer customer = new Customer();
        customer.setId(order.getCustomerEntity().getId());
        customer.setEmail(order.getCustomerEntity().getEmail());
        customer.setFirstName(order.getCustomerEntity().getFirstName());
        customer.setLastName(order.getCustomerEntity().getLastName());
        customer.setPassword(order.getCustomerEntity().getPassword());

       List<OrderItem> orderItemList = order.getOrderItems().stream().map(item->{
            OrderItem orderItem = new OrderItem();

            orderItem.setId(item.getId());
            orderItem.setQuantity(item.getQuantity());

           Product product = new Product();

           product.setId(item.getProductEntity().getId());
           product.setDescription(item.getProductEntity().getName());
           product.setName(item.getProductEntity().getDescription());

           orderItem.setProduct(product);
            return orderItem;
        }).collect(Collectors.toList());


        Order orderApi = new Order();
        orderApi.setId(order.getId());
        orderApi.setCreationDate(order.getCreationDate());
        orderApi.setCustomer(customer);
        orderApi.setStatus(OrderStatus.fromValue(order.getStatus()));
        orderApi.setOrderItems(orderItemList);

        return orderApi;

    }

    public static Parcel maParcel(ParcelEntity parcel){

        Parcel parcelAPI = new Parcel();

        parcelAPI.setOrder(mapOrder(parcel.getOrder()));

        parcelAPI.setStatus(parcel.getStatus());

        return parcelAPI;

    }


}
