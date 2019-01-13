package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {

    Product produit1 = new Product(1, "produit1", 10, 5);
    Product produit2 = new Product(2, "produit2", 100, 50);
    Product produit3 = new Product(3, "produit3", 1000, 500);
    Product simpleProduct = new Product();

    @Test
    public void testGetIdProduct(){
        Assert.assertEquals(produit1.getId(), 1);
        Assert.assertEquals(produit2.getId(), 2);
        Assert.assertEquals(produit3.getId(), 3);
    }

    @Test
    public void testSetIdProduct(){
        simpleProduct.setId(123);
        Assert.assertEquals(simpleProduct.getId(), 123);
    }

    @Test
    public void testGetNomProduct(){
        Assert.assertEquals(produit1.getNom(), "produit1");
        Assert.assertEquals(produit2.getNom(), "produit2");
        Assert.assertEquals(produit3.getNom(), "produit3");
    }

    @Test
    public void testSetNomProduct(){
        simpleProduct.setNom("simpleProduct");
        Assert.assertEquals(simpleProduct.getNom(), "simpleProduct");
    }

    @Test
    public void testGetPrixProduct(){
        Assert.assertEquals(produit1.getPrix(), 10);
        Assert.assertEquals(produit2.getPrix(), 100);
        Assert.assertEquals(produit3.getPrix(), 1000);
    }

    @Test
    public void testSetPrixProduct(){
        simpleProduct.setPrix(10000);
        Assert.assertEquals(simpleProduct.getPrix(), 10000);
    }

    @Test
    public void testGetPrixAchatProduct(){
        Assert.assertEquals(produit1.getPrixAchat(), 5);
        Assert.assertEquals(produit2.getPrixAchat(), 50);
        Assert.assertEquals(produit3.getPrixAchat(), 500);
    }

    @Test
    public void testSetPrixAchatProduct(){
        simpleProduct.setPrixAchat(5000);
        Assert.assertEquals(simpleProduct.getPrixAchat(), 5000);
    }

    @Test
    public void testToStringProduct(){
        Assert.assertEquals(simpleProduct.toString(), "Product{id=0, nom='null', prix=0}");
    }
}
