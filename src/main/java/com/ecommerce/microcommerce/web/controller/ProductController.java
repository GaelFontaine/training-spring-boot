package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Api(description = "API pour les opérations CRUD sur les produits.")

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    /**
     * Récupérer la liste des produits
     */
    @ApiOperation(value = "Récupérer la liste des produits")
    @RequestMapping(value = "/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits() {

        Iterable<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }


    /**
     * Récupérer un produit par son Id
     */
    @ApiOperation(value = "Récupère un produit grâce à son ID à condition que celui-ci soit en stock!")
    @GetMapping(value = "/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id) {

        Product produit = productDao.findById(id);

        if (produit == null)
            throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");

        //On ajoute la vérification pour savoir si un produit n'est pas gratuit
        if (produit.getPrixAchat() == 0){
            throw new ProduitGratuitException("Le produit " + produit.getNom() + " avec l'id " + id + " est gratuit.");
        }

        return produit;
    }

    /**
     * Ajouter un produit
     */
    @ApiOperation(value = "Ajouter un produit")
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product) {

        Product productAdded = productDao.save(product);

        if (productAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Supprimer un produit
     * @param id
     */
    @ApiOperation(value = "Supprimer un produit")
    @DeleteMapping(value = "/Produits/{id}")
    public void supprimerProduit(@PathVariable int id) {

        productDao.delete(id);
    }

    /**
     * Modifier un produit
     * @param product
     */
    @ApiOperation(value = "Modifier un produit")
    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product) {

        productDao.save(product);
    }


    /**
     * Pour les tests
     */
    @ApiOperation(value = "Pour les tests")
    @GetMapping(value = "test/produits/{prix}")
    public List<Product> testeDeRequetes(@PathVariable int prix) {

        return productDao.chercherUnProduitCher(400);
    }

    /**
     * Calcule la marge de chaque produit (différence entre
     * prix d‘achat et prix de vente).
     *
     * @return La liste des produits mise en forme
     */
    @ApiOperation(value = "Calcule la marge de chaque produit (différence entre prix d‘achat et prix de vente).")
    @GetMapping(value = "/AdminProduits")
    public ArrayList<String> calculerMargeProduit() {
        Iterable<Product> products = productDao.findAll();
        // La liste de produits mis en forme
        ArrayList<String> listProducts = new ArrayList<>();
        for (Product product : products) {
            int marge = product.getPrix() - product.getPrixAchat();
            listProducts.add("Product" + product.toString() + marge);
        }
        return listProducts;
    }

    /**
     * Retourne la liste de tous les produits
     * triés par nom croissant
     *
     * @return La liste des produits trier par ordre alphabétique
     */
    @ApiOperation(value = "Retourne la liste de tous les produits triés par nom croissant")
    @GetMapping(value = "/triProduits")
    public List<Product> trierProduitsParOrdreAlphabetique() {
        // Utilise la méthode présente dans l'interface ProductDao
        return productDao.trierProduitsParOrdreAlphabetique();
    }

}
