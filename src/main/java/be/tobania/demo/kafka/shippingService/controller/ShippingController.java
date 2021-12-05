package be.tobania.demo.kafka.shippingService.controller;

import be.tobania.demo.kafka.shippingService.model.Parcel;
import be.tobania.demo.kafka.shippingService.model.ParcelForPatch;
import be.tobania.demo.kafka.shippingService.service.ShippingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/parcel")
public class ShippingController {


    private final ShippingService shippingService;

    @ApiOperation(value = "Add a new shipping", nickname = "addParcel")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "successful operation", response = Parcel.class),
            @ApiResponse(code = 405, message = "Invalid input")})
    @RequestMapping(produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public Parcel createParcel(@Valid @RequestBody @NotNull Parcel parcel) {
        log.info("add new parcel");

        return shippingService.createParcel(parcel);
    }


    @ApiOperation(value = "Finds Parcel by status", nickname = "findParcelByStatus", notes = "Multiple status values can be provided with comma separated strings")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid status value")})
    @GetMapping(
            value = "/findByStatus",
            produces = {"application/json"}
    )
    public List<Parcel> findPaymentsByStatus(@NotNull @ApiParam(value = "Status values that need to be considered for filter") @Valid @RequestParam(value = "status", required = true) List<String> status) {

        return shippingService.getPaymentsByStatus(status);
    }


    @ApiOperation(value = "Find parcel by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Parcel not found")})
    @GetMapping(
            value = "/{parcelId}",
            produces = {"application/json"}
    )
    public Parcel getParcelById(@ApiParam(value = "ID of parcel to return", required = true) @PathVariable("parcelId") Long parcelId) {
        return shippingService.getParcelById(parcelId);
    }

    @ApiOperation(value = "Update the status of an existing parcel", nickname = "updateParcelStatus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Parcel.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Order not found"),
            @ApiResponse(code = 405, message = "Validation exception")})
    @PatchMapping(value = "/{parcelId}",
            produces = {"application/json"},
            consumes = {"application/json"})
    public Parcel updateOrderStatus(@ApiParam(value = "Patch object that needs to be updated", required = true) @Valid @RequestBody ParcelForPatch parcelForPatch, @ApiParam(value = "ID of parcel to return", required = true) @PathVariable("parcelId") Long parcelId) {
        log.info("patch order");
        return shippingService.patchOrder(parcelForPatch, parcelId);
    }



}
