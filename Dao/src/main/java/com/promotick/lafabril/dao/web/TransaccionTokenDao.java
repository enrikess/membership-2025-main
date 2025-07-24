package com.promotick.lafabril.dao.web;

import com.promotick.lafabril.model.web.TransaccionToken;

public interface TransaccionTokenDao {

    Integer guardarTransaccionToken(TransaccionToken transaccionToken);

    Boolean existeTransaccionToken(String token);

}
