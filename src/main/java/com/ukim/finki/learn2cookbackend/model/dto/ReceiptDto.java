package com.ukim.finki.learn2cookbackend.model.dto;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceiptDto {
    Receipt receipt;
    boolean canMake;
}
