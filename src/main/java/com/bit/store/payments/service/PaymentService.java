package com.bit.store.payments.service;

import com.bit.store.payments.dao.PaymentDAO;
import com.bit.store.payments.dto.CreateOrderResponse;
import com.bit.store.payments.exception.ErrorResponse;
import com.bit.store.payments.helper.ExternalResponseMapper;
import com.bit.store.payments.payload.CreateOrderPayload;
import com.bit.store.payments.utils.PaymentConstants;
import com.razorpay.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Slf4j
public class PaymentService implements IPaymentService {

    private static final String CLIENT_KEY = System.getenv("RAZOR_PAY_CLIENT_KEY");
    private static final String CLIENT_SECRET = System.getenv("RAZOR_PAY_CLIENT_SECRET");
    public final OkHttpClient client = new OkHttpClient.Builder()
                                            .addInterceptor(new HttpLoggingInterceptor())
                                            .build();

    @Inject
    PaymentDAO paymentDAO;


    @Override
    public Response createOrder(CreateOrderPayload createOrderPayload) {
        ErrorResponse errorResponse;
        try{
            RazorpayClient razorpayClient = new RazorpayClient(CLIENT_KEY, CLIENT_SECRET);
            JSONObject createOrderRequest = new JSONObject();
            createOrderRequest.put("amount", createOrderPayload.getAmount());
            createOrderRequest.put("currency", createOrderPayload.getCurrency());
            createOrderRequest.put("receipt", createOrderPayload.getReceiptId());
            log.info("createOrder = {}", createOrderRequest);
            Order order = razorpayClient.Orders.create(createOrderRequest);
            CreateOrderResponse createOrderResponse = ExternalResponseMapper.createOrderResponseMapper(order);
            paymentDAO.saveCreateOrderResponse(createOrderResponse, createOrderPayload.getOrderId());
            return Response.ok()
                    .entity(createOrderResponse)
                    .build();
        }catch (RazorpayException razorpayException){
            log.error(razorpayException.toString());
            errorResponse = ErrorResponse.builder()
                    .namespace(PaymentConstants.RAZORPAY_NAMESPACE)
                    .name(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message("")
                    .exception(razorpayException.toString())
                    .build();
        }catch (Exception exception){
            log.error(exception.toString());
            errorResponse = ErrorResponse.builder()
                    .namespace(PaymentConstants.APPLICATION_NAMESPACE)
                    .name(Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message("")
                    .exception(exception.toString())
                    .build();
        }
        return Response.status(500)
                .entity(errorResponse)
                .build();
    }

    public Response updateOrder(CreateOrderPayload createOrderPayload){
        JSONObject errorPayload = new JSONObject();
        String responseBody;
        JSONObject responseJson;
        Order order = null;
        try{
            JSONObject wrapObject = new JSONObject();
            JSONObject updateRequest = new JSONObject();
            updateRequest.put("amount", createOrderPayload.getAmount());
            updateRequest.put("currency", createOrderPayload.getCurrency());
            updateRequest.put("receipt", createOrderPayload.getTransactionCode());
            RequestBody requestBody = RequestBody.create(MediaType.get("application/json; charset=utf-8"), wrapObject.toString());
            Request request = new Request.Builder()
                    .header("Authorization", "Basic cnpwX3Rlc3RfWUNMcXpPWFFMRkhaaUg6VkE3TjdZbGdQNmRQdVJ1M1pabzFuaElk")
                    .url("https://api.razorpay.com/v1/orders/"+createOrderPayload.getOrderId())
                    .patch(requestBody)
                    .build();
            try (okhttp3.Response response = client.newCall(request).execute()) {
                responseBody = response.body().string();
                responseJson = new JSONObject(responseBody);
                order = new Order(responseJson);
            }catch (Exception exception){
                log.error(exception.toString());
            }
            CreateOrderResponse createOrderResponse = ExternalResponseMapper.createOrderResponseMapper(order);
            paymentDAO.saveCreateOrderResponse(createOrderResponse, createOrderPayload.getOrderId());
            return Response.ok()
                    .entity(createOrderResponse)
                    .build();
        }catch (Exception exception){
            errorPayload.put("namespace", "store-payment-service");
            errorPayload.put("error", exception.toString());
            log.error(exception.toString());
        }
        return Response.status(500)
                .entity(errorPayload)
                .build();
    }
}
