package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.PurchaseOrderDao;
import com.flipkart.retail.analytics.persistence.entity.PurchaseOrder;
import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<PurchaseOrder> getPurchaseOrders(String tableName, List<String> vendorSites, List<String> warehouses) {
        String poQuery = getPoQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(poQuery, new ResultSetExtractor<List<PurchaseOrder>>() {
            @Override
            public List<PurchaseOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
                while (rs.next()) {
                    PurchaseOrder purchaseOrder = new PurchaseOrder(rs.getString(1), rs
                            .getString(2), rs.getInt(3), rs.getDouble(4));
                    purchaseOrderList.add(purchaseOrder);
                }
                return purchaseOrderList;
            }
        });
    }

    @Override
    public List<PurchaseOrder> getLeadTime(String tableName, List<String> vendorSites, List<String> warehouses) {
        String query = getLeadTimeQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<PurchaseOrder>>() {
            @Override
            public List<PurchaseOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
                while (rs.next()) {
                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setMonth(rs.getString(1));
                    purchaseOrder.setAmount(rs.getDouble(2));
                    purchaseOrderList.add(purchaseOrder);
                }
                return purchaseOrderList;
            }
        });
    }

    @Override
    public List<PurchaseOrder> getFillRate(String tableName, List<String> vendorSites, List<String> warehouses) {
        return null;
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrderDetails(String tableName, List<String> vendorSites) {
        String query = getPoDetailsQuery(tableName, vendorSites);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<PurchaseOrder>>() {
            @Override
            public List<PurchaseOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
                while (rs.next()) {
                    PurchaseOrder purchaseOrder = new PurchaseOrder();
                    purchaseOrder.setStatus(rs.getString(1));
                    purchaseOrder.setCurrency(rs.getString(2));
                    purchaseOrder.setQuantity(rs.getInt(3));
                    purchaseOrder.setAmount(rs.getDouble(4));
                    purchaseOrderList.add(purchaseOrder);
                }
                return purchaseOrderList;
            }
        });
    }

    private String getPoQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "SELECT month, currency, SUM(received_quantity), SUM(received_amount) from " + tableName + " WHERE vs_id IN ('" +
                Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getPoDetailsQuery(String tableName, List<String> vendorSites){
        return "SELECT status, currency, SUM(received_quantity), SUM(received_amount) from " + tableName + " WHERE vs_id IN ('" +
                Joiner.on("','").join(vendorSites) + "') GROUP BY currency, status;";
    }

    private String getLeadTimeQuery(String tableName, List<String> vendorSites, List<String> warehouses) {
        return "SELECT month, 1 from " + tableName + " WHERE vs_id IN ('" +
                Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month";
    }
}
