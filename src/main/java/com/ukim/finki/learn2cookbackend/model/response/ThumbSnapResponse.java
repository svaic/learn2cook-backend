package com.ukim.finki.learn2cookbackend.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThumbSnapResponse {
    ThumbSnapData data;
    Boolean success;
    Integer status;
}
