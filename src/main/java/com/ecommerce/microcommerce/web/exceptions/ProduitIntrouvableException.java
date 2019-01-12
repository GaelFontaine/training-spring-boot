package com.ecommerce.microcommerce.web.exceptions;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(description = "Exception concernant la présence d'un produit (produit introuvable = exception")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitIntrouvableException extends RuntimeException {

    public ProduitIntrouvableException(String s) {
        super(s);
    }
}
