package product.model.service;

import java.sql.Connection;
import java.util.List;

import product.model.dao.ProductDao;
import product.model.vo.Product;
import product.model.vo.ProductIo;

import static common.JDBCTemplate.*;

public class ProductService {

	private ProductDao productDao = new ProductDao();
	
	public List<Product> selectAll() {
		
		Connection conn = getConnection();	
		
		List<Product> list = productDao.selectAll(conn);
		
		close(conn);
		
		return list;
	}

	public List<Product> selectOne(Product product) {
		
		Connection conn = getConnection();
		
		List<Product> list = productDao.selectOne(conn, product);
		
		close(conn);
		
		return list;
	}

	public int insertProduct(Product product) {
		
		Connection conn = getConnection();
		
		int result = productDao.insertProduct(conn, product);
		
		close(conn);
		
		return result;
	}

	public int deleteProduct(Product product) {
		
		Connection conn = getConnection();
		
		int result = productDao.deleteProduct(conn, product);
		
		close(conn);
		
		return result;
	}

	public int updateProduct(Product product) {
		
		Connection conn = getConnection();
		
		int result = productDao.updateProduct(conn, product);
		
		close(conn);
				
		return result;
	}

	public List<ProductIo> selectAllDel() {
		Connection conn = getConnection();	
		
		List<ProductIo> list = productDao.selectAllDel(conn);
		
		close(conn);
		
		return list;
	}

	public int inputProdIo(ProductIo prodIo) {
		Connection conn = getConnection();	
		
		int result = productDao.inputProdIo(conn, prodIo);
		
		close(conn);
		return result;
	}

}
