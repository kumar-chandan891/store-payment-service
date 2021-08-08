package com.bit.store.payments.helper;

import com.bit.store.payments.dto.CreateOrderResponse;
import com.razorpay.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ExternalResponseMapper {

    public static CreateOrderResponse createOrderResponseMapper(Order order){
        String receipt = "";
        String offerId = "";
        try{
            Object receiptObject = order.get("receipt");
            Object offerObject = order.get("offer_id");
            receipt = receiptObject == null ? "" : receiptObject.toString();
            offerId = offerObject == null ? "" : offerObject.toString();
        }catch (Exception exception){
            log.error(exception.toString());
        }

        return CreateOrderResponse.builder()
                .id(order.get("id"))
                .entity(order.get("entity"))
                .amount(order.get("amount"))
                .amountPaid(order.get("amount_paid"))
                .amountDue(order.get("amount_due"))
                .currency(order.get("currency"))
                .receipt(receipt)
                .offerId(offerId)
                .status(order.get("status"))
                .attempts(order.get("attempts"))
                .notes(null)
                .createdAt(order.get("created_at"))
                .build();
    }
}
