package com.ecommerce.microcommerce.web.exceptions;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(description = "Exception concernant le prix d'un produit (produit gratuit = exception")
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ProduitGratuitException extends RuntimeException {

    /**
     * Exception concernant le prix d'un produit
     * @param exception
     */
    public ProduitGratuitException(String exception){
        super(exception);
    }
}
