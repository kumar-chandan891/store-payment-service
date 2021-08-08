package com.bit.store.payments.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(name = "create_order_payload", description = "request schema for payment order creation")
public class CreateOrderPayload {

    @JsonProperty("order_id")
    @Schema(required = true, description = "order_id for which payment order has to be created.")
    @NotBlank(message = "order_id is a mandatory field")
    private String orderId;

    @JsonProperty("amount")
    @Schema(required = true, description = "amount for which payment order has to be created.")
    @NotBlank(message = "amount is required for payment to be processed")
    private Integer amount;

    @JsonProperty("currency")
    @Schema(description = "currency in which customer has to be charged.", defaultValue = "INR")
    private String currency;

    @JsonProperty("transaction_code")
    @Schema(description = "transaction_code")
    private String transactionCode;

    @JsonProperty("tenant")
    @Schema(description = "tenant", defaultValue = "CITY_STORE")
    private String tenant;

    @JsonProperty("receipt_id")
    @Schema(required = true, description = "receipt_id for invoice generation.")
    @NotBlank(message = "receipt_is is must for invoice generation")
    private String receiptId;

    @JsonProperty("create_timestamp")
    @Schema(description = "timestamp for payment order creation")
    private Date createTimestamp;

}
