package com.promotick.membership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MisionRegistradaResponse {
    private int code;
    private String traceid;
    private String message;
    private List<Error> errors;
}
