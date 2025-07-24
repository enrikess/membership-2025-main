package com.promotick.lafabril.model.util.bean.massend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.promotick.apiclient.model.response.SNSResponse;
import lombok.Data;


@Data
public class MassendResponse {

    @JsonProperty("RefError")
    private RefError refError;

    @JsonProperty("RefEnvio")
    private RefEnvio refEnvio;
}
