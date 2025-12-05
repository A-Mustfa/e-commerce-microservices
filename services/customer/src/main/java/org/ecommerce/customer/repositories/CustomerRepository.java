package org.ecommerce.customer.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ecommerce.customer.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {



}