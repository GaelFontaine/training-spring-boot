package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.controller.ProductController;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicrocommerceApplicationTests {

	@MockBean
	private ProductDao productDao;

	@Autowired
	private ProductController controller;

    MockMvc mockMvc;

	private List<Product> listeProduct;
	private Product product1;
	private Product product2;
	private Product freeProduct;

	@Before
	public void init(){
		this.mockMvc = standaloneSetup(this.controller).build();

		this.product1 = new Product(1, "Product1", 100, 10);
		this.product2 = new Product(2, "Product2", 200, 50);
		this.freeProduct = new Product(3, "freeProduct", 0, 0);
		this.listeProduct = new ArrayList<>();
		listeProduct.add(product1);
		listeProduct.add(product2);
		listeProduct.add(freeProduct);
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void testFindAll() throws Exception {
        when(productDao.findAll()).thenReturn(listeProduct);
        mockMvc.perform(get("/Produits")
                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(product1.getId())))
                .andExpect(jsonPath("$[0].nom", is(product1.getNom())))
                .andExpect(jsonPath("$[0].prix", is(product1.getPrix())))
                .andExpect(jsonPath("$[0].prixAchat", is(product1.getPrixAchat())));
	}

	@Test
	public void testFindByID() throws Exception {
		when(productDao.findById(1)).thenReturn(listeProduct.get(0));
		mockMvc.perform(get("/Produits/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindByPrix() throws Exception {
		when(productDao.findByPrixGreaterThan(100)).thenReturn(listeProduct);
		mockMvc.perform(get("/test/produits/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testFindByNom() throws Exception {
		when(productDao.findByNomLike("product1")).thenReturn(listeProduct);
		mockMvc.perform(get("/test/produits/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testProduitGratuitException() throws Exception {
		when(productDao.findById(3)).thenReturn(listeProduct.get(2));
		mockMvc.perform(get("/Produits/3")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	public void testProduitNotFoundException() throws Exception {
		mockMvc.perform(get("/Produits/100")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testTriProduct() throws Exception {
		when(productDao.findAll()).thenReturn(listeProduct);
		mockMvc.perform(get("/triProduits/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testMargeProduct() throws Exception {
		when(productDao.findAll()).thenReturn(listeProduct);
		mockMvc.perform(get("/AdminProduits/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void testAjouterProduct() throws Exception {
		Product productToAdd = new Product(15, "new", 1, 1);
		given(productDao.save(Mockito.any(Product.class)))
				.willReturn(productToAdd);

		Gson gson = new Gson();
		String jsonProduct = gson.toJson(productToAdd);

		mockMvc.perform(
				post("/Produits")
						.contentType(MediaType.APPLICATION_JSON)
						.content(jsonProduct))
				.andExpect(status().isCreated());
	}

	@Test
	public void testSupprimerProduct() throws Exception {
		mockMvc.perform(
				delete("/Produits/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateProduct() throws Exception {
		Product productToUpdate = new Product(1, "update", 1, 1);
		given(productDao.save(Mockito.any(Product.class)))
				.willReturn(productToUpdate);

		Gson gson = new Gson();
		String newProductAsJson = gson.toJson(productToUpdate);

		mockMvc.perform(
				put("/Produits")
						.contentType(MediaType.APPLICATION_JSON)
						.content(newProductAsJson))
				.andExpect(status().isOk());
	}

}
