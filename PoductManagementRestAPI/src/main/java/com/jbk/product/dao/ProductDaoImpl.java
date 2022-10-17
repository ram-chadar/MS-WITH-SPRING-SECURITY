package com.jbk.product.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.product.entity.Product;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public boolean saveProduct(Product product) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction(); // save , update, delete
		boolean isAdded = false;
		try {
		Product prd=	session.get(Product.class, product.getProductId());
		if(prd==null) {
			session.save(product);
			transaction.commit();
			isAdded = true;
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return isAdded;
	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction(); // save , update, delete
		boolean isUpdated = false;
		try {
			Product prd = session.get(Product.class, product.getProductId());
			if (prd != null) {
				session.evict(prd);
				session.update(product);
				transaction.commit();
				isUpdated = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return isUpdated;
	}

	@Override
	public Product getProductById(int productId) {
		Session session = sf.openSession();

		Product product = null;
		try {
			product = session.get(Product.class, productId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return product;
	}

	@Override
	public boolean deleteProductById(int productId) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction(); // save , update, delete
		boolean isDeleted = false;
		Product product = null;
		try {
			product = session.get(Product.class, productId);
			if (product != null) {
				session.delete(product);
				transaction.commit();
				isDeleted = true;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return isDeleted;
	}

	@Override
	public List<Product> getAllProduct() { // using criteria
		Session session = sf.openSession();
		// create criteria

		Criteria criteria = session.createCriteria(Product.class);
		List<Product> list = criteria.list(); // select * from product

		return list;
	}

	@Override
	public List<Product> getProductByName(String productName) {

		Session session = sf.openSession();

		List<Product> list = null;

		try {
			// product = session.get(Product.class, productName);
			Criteria criteria = session.createCriteria(Product.class);

			criteria.add(Restrictions.eq("productName", productName));
			list=criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public List<Product> getProductsBetweenPrice(double lowPrice, double highPrice) {

		return null;
	}

	@Override
	public List<Product> getMaxPriceProduct() {
		Session session = sf.openSession();

		List<Product> list = null;

		try {
			// product = session.get(Product.class, productName);
			Criteria criteria1 = session.createCriteria(Product.class);
			Criteria criteria2 = session.createCriteria(Product.class);

			criteria1.setProjection(Projections.max("productPrice"));

			Double max = (Double) criteria1.list().get(0);

			criteria2.add(Restrictions.eq("productPrice", max));

			list = criteria2.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return list;
	}

	@Override
	public int uploadSheet(List<Product> list) {
		Session session =null;
		int count = 0;
		try {
			
			for (Product prd : list) {
				 session = sf.openSession();
				Transaction transaction = session.beginTransaction(); // save , update, delete
				session.save(prd);
				transaction.commit();
				count=count+1;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return count;
	}

}
