package com.bit.store.payments.resources;

import com.bit.store.payments.exception.ErrorResponse;
import com.bit.store.payments.payload.CreateOrderPayload;
import com.bit.store.payments.service.PaymentService;
import com.bit.store.payments.utils.PaymentConstants;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("payment-api")
@Slf4j
public class PaymentResource {

    private final PaymentService paymentService;

    public PaymentResource(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @POST
    @Metered
    @Path("create-order")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a payment order", description = "create an order for payment to be processed in front-end")
    @APIResponse(responseCode = "201", description = "create a payment order", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = CreateOrderPayload.class)))
    @APIResponse(responseCode = "400", description = "bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)))
    @APIResponse(responseCode = "500", description = "internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = ErrorResponse.class)))
    public Response createPayment(CreateOrderPayload createOrderPayload){
        try{
            log.info("{}", createOrderPayload);
            createOrderPayload.setCreateTimestamp(new Date());
            return paymentService.createOrder(createOrderPayload);
        }catch (Exception exception){
            log.error(exception.toString());
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .namespace(PaymentConstants.APPLICATION_NAMESPACE)
                    .name(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message("")
                    .exception(exception.toString())
                    .build();
            return Response
                    .status(500)
                    .entity(errorResponse)
                    .build();
        }
    }

    @POST
    @Path("update-order")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Metered
    public Response update(CreateOrderPayload createOrderPayload){
        log.info(createOrderPayload.toString());
        if(createOrderPayload.getOrderId() == null){
            return Response.status(500)
                    .entity("order_id is null")
                    .build();
        }
        createOrderPayload.setCreateTimestamp(new Date());
        return paymentService.updateOrder(createOrderPayload);
    }


}
