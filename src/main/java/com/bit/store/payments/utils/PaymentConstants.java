package com.bit.store.payments.utils;

public class PaymentConstants {

    public static final String APPLICATION_NAMESPACE = "store-payment-service";
    public static final String RAZORPAY_NAMESPACE = "razorpay";

    public static final String DATABASE_NAME = "store";

    public static final String COLLECTION_CREATE_ORDER_RESPONSE = "create_order_response";

    public interface CreateOrderResponse{
        String PRIMARY_KEY = "_id";
        String STORE_ORDER_ID = "store_order_id";
        String RAZORPAY_ORDER_ID = "razorpay_order_id";
        String ENTITY = "entity";
        String AMOUNT = "amount";
        String AMOUNT_PAID = "amount_paid";
        String AMOUNT_DUE = "amount_due";
        String CURRENCY = "currency";
        String RECEIPT = "receipt";
        String OFFER_ID = "offer_id";
        String STATUS = "status";
        String ATTEMPTS = "attempts";
        String NOTES = "notes";
        String CREATED_AT = "created_at";
        String RAZORPAY_RAW_RESPONSE = "razorpay_raw_response";
    }
}
