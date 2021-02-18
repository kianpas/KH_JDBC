package product.view;

import java.util.List;
import java.util.Scanner;

import product.controller.ProductController;
import product.model.vo.Product;
import product.model.vo.ProductIo;

public class productMenu {

	private Scanner sc = new Scanner(System.in);
	private ProductController productController = new ProductController();

	public void mainMenu() {

		String menu = "***** 상품재고관리프로그램 *****\n" + "1. 전체상품조회\n" + "2. 상품아이디검색\n" + "3. 상품명검색\n" + "4. 상품추가\n"
				+ "5. 상품정보변경\n" + "6. 상품삭제\n" + "7. 상품입/출고 메뉴\n" + "9. 프로그램종료\n" + "선택 >>> ";

		do {
			Product product = null;
			List<Product> list = null;
			int result = 0;

			System.out.print(menu);
			switch (sc.next()) {
			case "1":
				list = productController.selectAll();
				displayList(list);
				break;
			case "2":
				product = inputProductId();
				list = productController.selectOne(product);
				displayList(list);
				break;
			case "3":
				product = inputProductName();
				list = productController.selectOne(product);
				displayList(list);
				break;
			case "4":
				product = inputProduct();
				result = productController.insertProduct(product);
				
				break;
			case "5":
				updateProductMenu();
				break;
			case "6":
				product = inputProductId();
				result = productController.deleteProduct(product);
				break;
			case "7":
				productIOMenu();
				break;
			case "9":
				return;
				
			}

		} while (true);

	}
	
	private Product inputProduct() {
		Product product = new Product();
		System.out.print("상품아이디 입력 : ");
		product.setProductId(sc.next());

		System.out.print("상품이름 입력 : ");
		product.setProductName(sc.next());

		System.out.print("상품가격 입력 : ");
		product.setPrice(sc.nextInt());

		sc.nextLine();

		System.out.print("상품설명 입력 : ");
		product.setDescription(sc.nextLine());

		System.out.print("상품재고 입력 : ");
		product.setStock(sc.nextInt());
		return product;
	}

	private Product inputProductName() {
		Product product = new Product();
		System.out.print("상품명검색 : ");
		product.setProductName(sc.next());
		return product;
	}

	private Product inputProductId() {
		Product product = new Product();
		System.out.print("상품아이디검색 : ");
		product.setProductId(sc.next());
		
		return product;
	}

	private void displayList(List<Product> list) {
		int i = 0;
		for(i = 0; i < list.size(); i++) {
			System.out.println((i+1) + " : " + list.get(i) + "]");
		}
	}
	
	
	private void displayIoList(List<ProductIo> list) {
		int i = 0;
		for(i = 0; i < list.size(); i++) {
			System.out.println((i+1) + " : " + list.get(i) + "]");
		}
	}
	
	private void productIOMenu() {

		String menu = "***** 상품입출고메뉴 *****\n" + "1. 전체입출고내역조회\n" + "2. 상품입고\n" + "3. 상품출고\n" + "9. 메인메뉴로 돌아가기\n"
				+ "선택 >>> ";

		do {

			ProductIo prodIo = null;
			List<ProductIo> list = null;
			int result = 0;

			System.out.print(menu);
			switch (sc.next()) {
			case "1":
				list = productController.selectAllDel();
				displayIoList(list);
				break;
			case "2":
				prodIo = inputProdIo();
				result = productController.inputProdIo(prodIo);
				break;
			case "3":
				prodIo = inputProdOut();
				result = productController.inputProdIo(prodIo);
				break;
			case "9":
				return;
			default:
				System.out.println("잘못");
				break;
			}

		} while (true);

	}

	private ProductIo inputProdOut() {
		System.out.println("상품출고");
		ProductIo prodIo = new ProductIo();
		
		System.out.print("상품 아이디 입력");
		prodIo.setProductId(sc.next());
		
		System.out.print("상품 수량 입력");
		prodIo.setAmount(sc.nextInt());
		
		System.out.println("입출고여부(I/O)");
		prodIo.setStatus(String.valueOf(sc.next().toUpperCase().charAt(0)));
			
		return prodIo;
	}

	private ProductIo inputProdIo() {
		
		System.out.println("상품입고");
		ProductIo prodIo = new ProductIo();
		
		System.out.print("상품 아이디 입력");
		prodIo.setProductId(sc.next());
		
		System.out.print("상품 수량 입력");
		prodIo.setAmount(sc.nextInt());
		
		System.out.println("입출고여부(I/O)");
		prodIo.setStatus(String.valueOf(sc.next().toUpperCase().charAt(0)));
		
		return prodIo;
	}

	private void updateProductMenu() {

		String menu = "***** 상품정보변경메뉴 *****\n" + "1. 상품명변경\n" + "2. 가격변경\n" + "3. 설명변경\n" + "9. 메인메뉴로 돌아가기\n"
				+ "선택 >>> ";

		do {
			Product product = null;
			List<Product> list = null;
			int result = 0;
			
			System.out.print(menu);
			switch (sc.next()) {
			case "1":
				product = inputUpProductName();
				result = productController.updateProduct(product);
				break;
			case "2":
				product = inputProductPrice();
				result = productController.updateProduct(product);
				break;
			case "3":
				product = inputProductDesc();
				result = productController.updateProduct(product);
				break;

			case "9":
				return;
			default:
				System.out.println("잘못");
				break;
			}

		} while (true);

	}

	private Product inputProductDesc() {
		Product product = new Product();
		System.out.print("상품아이디검색 : ");
		product.setProductId(sc.next());
		
		sc.nextLine();
		
		System.out.print("상품 설명 입력 : ");
		product.setDescription(sc.nextLine());
		return product;
	}

	private Product inputProductPrice() {
		Product product = new Product();
		System.out.print("상품아이디검색 : ");
		product.setProductId(sc.next());
		
		System.out.print("상품 가격 입력 : ");
		product.setPrice(sc.nextInt());
		return product;
	}
	
	private Product inputUpProductName() {
		Product product = new Product();
		System.out.print("상품아이디검색 : ");
		product.setProductId(sc.next());
		
		System.out.print("상품명 입력 : ");
		product.setProductName(sc.next());
		return product;
	}

}
