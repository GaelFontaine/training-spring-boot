package com.ecommerce.microcommerce;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicrocommerceApplicationTests {

	@Autowired
	private ProductDao productDao;

	@Test
	public void testFindAll() {
		List<Product> products = productDao.findAll();
		Assert.assertNotNull(products);
	}

	@Test
	public void testFindByID(){

		Product product = productDao.findById(1);
		Assert.assertNotNull(product);
		Assert.assertEquals(product.getId(), 1);
		Assert.assertEquals(product.getNom(), "Ordinateur portable");
	}

	@Test
	public void testFindByPrix(){
		List<Product> products = productDao.findByPrixGreaterThan(500);
		Assert.assertNotNull(products);
	}

	@Test
	public void testFindByNom(){
		List<Product> products = productDao.findByNomLike("Aspirateur Robot");
		Assert.assertNotNull(products);
	}

	@Test
	public void testChercherUnProduitCher(){
		List<Product> products = productDao.chercherUnProduitCher(500);
		Assert.assertNotNull(products);
	}

}
