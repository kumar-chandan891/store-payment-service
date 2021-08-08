package com.bit.store.payments.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("amount")
    private int amount;

    @JsonProperty("amount_paid")
    private int amountPaid;

    @JsonProperty("amount_due")
    private int amountDue;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("receipt")
    private String receipt;

    @JsonProperty("offer_id")
    private String offerId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("attempts")
    private int attempts;

    @JsonProperty("notes")
    private List<String> notes;

    @JsonProperty("created_at")
    private Date createdAt;

}
