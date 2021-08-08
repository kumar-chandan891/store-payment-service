package com.bit.store.payments.dao;

import com.bit.store.payments.dto.CreateOrderResponse;
import com.bit.store.payments.helper.DocumentCreateHelper;
import com.mongodb.client.MongoClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import static com.bit.store.payments.utils.PaymentConstants.*;

@ApplicationScoped
@Slf4j
public class PaymentDAO {

    @Inject
    MongoClient mongoClient;

    public void saveCreateOrderResponse(CreateOrderResponse createOrderResponse, String orderId){
        try{
            Document document = DocumentCreateHelper.createOrderDocument(createOrderResponse, orderId);
            mongoClient.getDatabase(DATABASE_NAME)
                    .getCollection(COLLECTION_CREATE_ORDER_RESPONSE)
                    .insertOne(document);
        }catch (Exception exception){
            log.error(exception.toString());
        }
    }


}
