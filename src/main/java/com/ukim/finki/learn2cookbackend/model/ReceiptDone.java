package com.ukim.finki.learn2cookbackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ReceiptDone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    Receipt receipt;

    String imageUrl;

    LocalDateTime date;
}
