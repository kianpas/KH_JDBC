package product.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import product.model.exception.AmountException;
import product.model.exception.ProductException;
import product.model.vo.Product;
import product.model.vo.ProductIo;

import static common.JDBCTemplate.*;

public class ProductDao {

	private Properties prop = new Properties();
	
	public ProductDao() {
		
		try {
			prop.load(new FileReader("resources/data-query.properties"));
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<Product> selectAll(Connection conn)  {

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");

		List<Product> list = null;

		try {
			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			list = new ArrayList<Product>();

			while (rset.next()) {

				String productId = rset.getString("product_id");
				String productName = rset.getString("product_name");
				int price = rset.getInt("price");
				String description = rset.getString("description");
				int stock = rset.getInt("stock");

				Product product = new Product(productId, productName, price, description, stock);
				list.add(product);

			}

		} catch (SQLException e) {
			
			throw new ProductException("재고 전체 조회", e);
			
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public List<Product> selectOne(Connection conn, Product product) {

		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rset = null;
		List<Product> list = null;

		try {
		
			if (product.getProductId() != null) {
				sql = prop.getProperty("selectId");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + product.getProductId() + "%");
				
			} else if (product.getProductName() != null) {
				
				sql = prop.getProperty("selectName");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + product.getProductName() + "%");
			}
			
			rset = pstmt.executeQuery();

			list = new ArrayList<>();

			while (rset.next()) {

				String productId = rset.getString("product_id");
				String productName = rset.getString("product_name");
				int price = rset.getInt("price");
				String description = rset.getString("description");
				int stock = rset.getInt("stock");

				Product p = new Product(productId, productName, price, description, stock);
				list.add(p);

			}

		} catch (SQLException e) {

			throw new ProductException("재고 하나 조회", e);
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int insertProduct(Connection conn, Product product) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertProduct");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getProductId());
			pstmt.setString(2, product.getProductName());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getDescription());
			pstmt.setInt(5, product.getStock());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			throw new ProductException("재고 입력", e);
		} finally {
			
			close(pstmt);
		}
		
		
		return result;
	}

	public int deleteProduct(Connection conn, Product product) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteProduct");
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getProductId());
					
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			throw new ProductException("재고 삭제", e);
		} finally {
			
			close(pstmt);
		}
		
		
		return result;
	}

	public int updateProduct(Connection conn, Product product) {

		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;

		try {
			if (product.getProductName() != null) {
				sql = prop.getProperty("updateName");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, product.getProductName());
			} else if (product.getPrice() != 0) {
				sql = prop.getProperty("updatePrice");
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, product.getPrice());
			} else if (product.getDescription() != null) {
				sql = prop.getProperty("updateDescription");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, product.getDescription());
			}

			pstmt.setString(2, product.getProductId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			throw new ProductException("재고 정보 변경", e);
		} finally {

			close(pstmt);
		}

		return result;
	}

	public List<ProductIo> selectAllDel(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAllDel");

		List<ProductIo> list = null;

		try {
			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			list = new ArrayList<ProductIo>();

			while (rset.next()) {

				int ioNo = rset.getInt("io_no");
				String productId = rset.getString("product_id");
				Date ioDate = rset.getDate("iodate");
				int amount = rset.getInt("amount");
				String status = rset.getString("status");

				ProductIo prodIo = new ProductIo(ioNo, productId, ioDate, amount, status);
				list.add(prodIo);

			}

		} catch (SQLException e) {

			throw new ProductException("입출력 내역 조회", e);
		} finally {

			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int inputProdIo(Connection conn, ProductIo prodIo) {
		
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
		
		try {

			if (prodIo.getStatus().equals("I")) {

				sql = prop.getProperty("inputProdIo");
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, prodIo.getProductId());
				pstmt.setInt(2, prodIo.getAmount());
				pstmt.setString(3, prodIo.getStatus());

			} else if (prodIo.getStatus().equals("O")) {

				sql = prop.getProperty("outputProdIo");
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, prodIo.getProductId());
				pstmt.setInt(2, prodIo.getAmount());
				pstmt.setString(3, prodIo.getStatus());

			}

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			
			throw new ProductException("재고 입출고", e);
			
		} finally {
			close(pstmt);
		}
			
		return result;
	}

	public int checkStock(Connection conn, String productId) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("checkStock");
							
		int result = 0;
		ResultSet rset = null;

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, productId);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				result = rset.getInt("stock");
			}

		} catch (SQLException e) {

			throw new AmountException("재고 수량의 오류", e);


		} finally {
			close(rset);
			close(pstmt);
			close(conn);
		}

		return result;
	}
}
