package org.ecommerce.payment.repository;

import org.ecommerce.payment.entities.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query("select p from Payment p")
    Optional<List<Payment>> findAllPayments();
}
