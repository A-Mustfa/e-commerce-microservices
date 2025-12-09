package org.ecommerce.payment.mapper;

import org.ecommerce.payment.dto.PaymentRequest;
import org.ecommerce.payment.dto.PaymentResponse;
import org.ecommerce.payment.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel ="spring")
public interface PaymentMapStruct {

    @Mapping(source = "id",target = "paymentId")
    PaymentResponse toDtoResponse(Payment payment);
    Payment toEntity(PaymentRequest paymentRequest);
}
