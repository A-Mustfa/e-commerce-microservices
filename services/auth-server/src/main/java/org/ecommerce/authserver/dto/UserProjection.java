package org.ecommerce.authserver.dto;

public interface UserProjection {

    Long getId();
    String getEmail();
    String getStatus();
    String getRole();

}
