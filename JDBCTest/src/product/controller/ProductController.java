package product.controller;

import java.sql.Connection;
import java.util.List;

import product.model.exception.AmountException;
import product.model.exception.ProductException;
import product.model.service.ProductService;
import product.model.vo.Product;
import product.model.vo.ProductIo;

import static common.JDBCTemplate.*;

public class ProductController {

	private ProductService productService = new ProductService();
	
	public List<Product> selectAll() {
				
		List<Product> list = null;
		try {
			list =	productService.selectAll();
		}catch(ProductException e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	public List<Product> selectOne(Product product) {
				
		List<Product> list = null;
		try {
			list =	productService.selectOne(product);
		}catch(ProductException e) {
			System.out.println(e.getMessage());
		}
		return list;
		
	}

	public int insertProduct(Product product) {
		int result = 0;
		try {
			result = productService.insertProduct(product);
		} catch (ProductException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public int deleteProduct(Product product) {
		int result = 0;
		try {
			result = productService.deleteProduct(product);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
		}

		return result;
	}

	public int updateProduct(Product product) {
		int result = 0;
		try {
			result = productService.updateProduct(product);

		} catch (ProductException e) {
			System.out.println(e.getMessage());
		}

		return result;

	}

	public List<ProductIo> selectAllDel() {
		List<ProductIo> list = null;
		try {
			list = productService.selectAllDel();
		}catch(ProductException e) {
			System.out.println(e.getMessage());
		}
		return list;
		
	}

	public int inputProdIo(ProductIo prodIo) {
		int result = 0;
		try {
			result = productService.inputProdIo(prodIo);

		} catch(AmountException e1) {
			System.out.println(e1.getMessage());
			
		} catch (ProductException e) {
			System.out.println(e.getMessage());
		}

		return result;
		
	}

}
