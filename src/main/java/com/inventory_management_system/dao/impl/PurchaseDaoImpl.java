package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.PurchaseDao;
import com.inventory_management_system.model.Purchase;
import com.inventory_management_system.util.DBUtil;

import java.sql.Connection;
import java.util.List;

public class PurchaseDaoImpl implements PurchaseDao {

    private final Connection connection;

    public PurchaseDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return null;
    }

    @Override
    public Purchase getPurchaseById(int id) {
        return null;
    }

    @Override
    public void addPurchase(Purchase purchase) {

    }

    @Override
    public void updatePurchase(Purchase purchase) {

    }

    @Override
    public void deletePurchase(int id) {

    }
}
