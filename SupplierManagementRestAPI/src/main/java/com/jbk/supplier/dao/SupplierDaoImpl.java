package com.jbk.supplier.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jbk.supplier.entity.Supplier;

@Repository
public class SupplierDaoImpl implements SupplierDao {

	@Autowired
	private SessionFactory sf;

	@Override
	public boolean saveSupplier(Supplier supplier) {
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction(); // save , update, delete
		boolean isAdded = false;
		try {
		Supplier sp=	session.get(Supplier.class, supplier.getSupplierId());
		if(sp==null) {
			session.save(supplier);
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
	public Supplier getSupplier(int supplierId) {
		Session session = sf.openSession();

		Supplier supplier = null;
		try {
			supplier = session.get(Supplier.class, supplierId);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}

		}

		return supplier;
	}

}
