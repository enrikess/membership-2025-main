package com.promotick.lafabril.web.service;

import com.promotick.lafabril.model.web.TransaccionToken;

public interface TransaccionTokenWebService {
    Integer guardarTransaccionToken(TransaccionToken transaccionToken);

    Boolean existeTransaccionToken(String token);
}
