package product.model.service;

import java.sql.Connection;
import java.util.List;

import product.model.dao.ProductDao;
import product.model.exception.AmountException;
import product.model.vo.Product;
import product.model.vo.ProductIo;

import static common.JDBCTemplate.*;

public class ProductService {

	private static final String STATUS_INPUT = "I";
	private static final String STATUS_OUPUT = "O";

	private ProductDao productDao = new ProductDao();

	//application의 필요한 상수는 주로 업무로직 담당인 Service단에 작성한다.
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
		int stock = productDao.checkStock(conn, prodIo.getProductId());

		if (prodIo.getStatus().equals(STATUS_OUPUT) && prodIo.getAmount() > stock) {
			throw new AmountException("재고 이상을 출고할수 없습니다.");
		}

		int result = productDao.inputProdIo(conn, prodIo);

		close(conn);

		if (result > 0)
			commit(conn);
		else
			rollback(conn);

		return result;
	}

}
