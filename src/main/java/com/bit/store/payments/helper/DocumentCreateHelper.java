package com.bit.store.payments.helper;

import com.bit.store.payments.dto.CreateOrderResponse;
import com.bit.store.payments.utils.PaymentConstants;
import org.bson.Document;

import java.util.UUID;

public class DocumentCreateHelper {

    public static Document createOrderDocument(CreateOrderResponse createOrderResponse, String orderId){
        Document document = new Document();
        document.put(PaymentConstants.CreateOrderResponse.PRIMARY_KEY, UUID.randomUUID().toString());
        document.put(PaymentConstants.CreateOrderResponse.STORE_ORDER_ID, orderId);
        document.put(PaymentConstants.CreateOrderResponse.RAZORPAY_ORDER_ID, createOrderResponse.getId());
        document.put(PaymentConstants.CreateOrderResponse.ENTITY, createOrderResponse.getEntity());
        document.put(PaymentConstants.CreateOrderResponse.AMOUNT, createOrderResponse.getAmount());
        document.put(PaymentConstants.CreateOrderResponse.AMOUNT_PAID, createOrderResponse.getAmountPaid());
        document.put(PaymentConstants.CreateOrderResponse.AMOUNT_DUE, createOrderResponse.getAmountDue());
        document.put(PaymentConstants.CreateOrderResponse.CURRENCY, createOrderResponse.getCurrency());
        document.put(PaymentConstants.CreateOrderResponse.RECEIPT, createOrderResponse.getReceipt());
        document.put(PaymentConstants.CreateOrderResponse.OFFER_ID, createOrderResponse.getOfferId());
        document.put(PaymentConstants.CreateOrderResponse.STATUS, createOrderResponse.getStatus());
        document.put(PaymentConstants.CreateOrderResponse.ATTEMPTS, createOrderResponse.getAttempts());
        document.put(PaymentConstants.CreateOrderResponse.NOTES, createOrderResponse.getNotes());
        document.put(PaymentConstants.CreateOrderResponse.CREATED_AT, createOrderResponse.getCreatedAt());
        document.put(PaymentConstants.CreateOrderResponse.RAZORPAY_RAW_RESPONSE, "");
        return document;
    }
}
