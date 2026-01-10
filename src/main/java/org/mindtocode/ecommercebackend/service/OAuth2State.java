package org.mindtocode.ecommercebackend.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2State {

    @Id
    private String state;
    private String provider;
}
