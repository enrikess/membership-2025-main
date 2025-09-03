package com.promotick.membership.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    private String traceid;
    private boolean success;
    private boolean collection;
    private int count;
    private String data;
    private String error;
}
