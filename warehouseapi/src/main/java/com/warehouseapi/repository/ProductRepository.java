package com.warehouseapi.repository;

import com.warehouseapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    public List<Product> findByVendor(String Vendor);

    boolean existsBySku(String sku);
}
