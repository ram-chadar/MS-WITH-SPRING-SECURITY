package com.jbk.product.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.jbk.product.dao.ProductDao;
import com.jbk.product.entity.Product;
import com.jbk.product.model.Product_Supplier;
import com.jbk.product.model.Supplier;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao dao;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public boolean saveProduct(Product product) {

		boolean isAdded = dao.saveProduct(product);

		return isAdded;
	}

	@Override
	public boolean updateProduct(Product product) {

		return dao.updateProduct(product);
	}

	@Override
	public Product getProductById(int productId) {

		return dao.getProductById(productId);
	}

	@Override
	public boolean deleteProductById(int productId) {

		return dao.deleteProductById(productId);
	}

	@Override
	public List<Product> getAllProduct() {

		List<Product> list = dao.getAllProduct();

		return dao.getAllProduct();
	}

	@Override
	public List<Product> getProductByName(String productName) {

		return dao.getProductByName(productName);
	}

	@Override
	public List<Product> getProductsBetweenPrice(double lowPrice, double highPrice) {

		return null;
	}

	@Override
	public List<Product> getMaxPriceProduct() {

		return dao.getMaxPriceProduct();
	}

	@Override
	public int importProduct() {
		String filePath = "E://product.xlsx";
		List<Product> list = new ArrayList<>();
		int count = 0;
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			Workbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rows = sheet.rowIterator();
			Product product = null;

			while (rows.hasNext()) {
				product = new Product();
				Row row = rows.next();

				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();
					int col = cell.getColumnIndex();
					switch (col) {
					case 0:
						product.setProductId((int) cell.getNumericCellValue());
						break;

					case 1:
						product.setProductName(cell.getStringCellValue());
						break;

					case 2:
						product.setProductPrice(cell.getNumericCellValue());
						break;

					case 3:
						product.setQtyPerUnit((int) cell.getNumericCellValue());
						break;
					case 4:
						product.setProductType(cell.getStringCellValue());
						break;

					case 5:
						product.setSupplierId((int) cell.getNumericCellValue());
						break;

					default:
						break;
					}

				}
				list.add(product);

			}
			for (Product prd : list) {
				System.out.println(prd);
			}
			count = dao.uploadSheet(list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public Product_Supplier getProductWithSupplierByProductId(int productId) {
		Product_Supplier product_Supplier = new Product_Supplier();
		Supplier supplier=null;
		try {
			
			Product product = getProductById(productId);
			product_Supplier.setProduct(product);
			 supplier = restTemplate.getForObject(
					"http://localhost:8081/supplier/getSupplierById/" + product.getSupplierId(), Supplier.class);

			product_Supplier.setProduct(product);
			if(supplier!=null) {
				product_Supplier.setSupplier(supplier);
			}
		} 
		catch (ResourceAccessException e) {
			System.out.println("supplier sercive not in running state");
		}
		catch (Exception e) {
			e.printStackTrace();
			product_Supplier.setSupplier(supplier);
		}
		return product_Supplier;
	}

}
