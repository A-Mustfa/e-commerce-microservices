package org.ecommerce.notificationservice.email;

import lombok.Getter;

public enum EmailTemplate {

    PAYMENT_CONFIRMATION("payment-template.html","payment successfully processed"),
    ORDER_CONFIRMATION("order-template.html","order confirmation");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

}
