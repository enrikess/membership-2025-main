package com.promotick.lafabril.model.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FiltroReporteMundialTrivia implements Serializable {

    private static final long serialVersionUID = -8634958740727239060L;
    private Integer start;
    private Integer length;
    private Integer nroTrivia;


}
