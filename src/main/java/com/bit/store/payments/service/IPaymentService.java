package com.bit.store.payments.service;

import com.bit.store.payments.payload.CreateOrderPayload;

import javax.ws.rs.core.Response;

public interface IPaymentService {

    Response createOrder(CreateOrderPayload createOrderPayload);
}
