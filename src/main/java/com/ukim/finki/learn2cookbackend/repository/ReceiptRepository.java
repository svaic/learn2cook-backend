package com.ukim.finki.learn2cookbackend.repository;

import com.ukim.finki.learn2cookbackend.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
