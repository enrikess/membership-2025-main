package com.promotick.lafabril.model.web;

import com.promotick.lafabril.model.util.BeanBase;
import lombok.Data;

@Data
public class MundialRanking extends BeanBase {


    private static final long serialVersionUID = 5182343245288564764L;
    private String nombres;
    private String nivel;
    private Integer puntosMundial;


}
