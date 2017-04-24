package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.aggregatedDetails.POAggregatedDetails;
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
public class PurchaseOrderManager {
    private final JdbcTemplate jdbcTemplate;

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

    public List<POAggregatedDetails> getPurchaseOrderDetails(String tableName, List<String> vendorSites, String
            fromMonth, String toMonth) {
        String query = getPoDetailsQuery(tableName, vendorSites, fromMonth, toMonth);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<POAggregatedDetails>>() {
            @Override
            public List<POAggregatedDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<POAggregatedDetails> poAggregatedDetailsList = new ArrayList<>();
                while (rs.next()) {
                    POAggregatedDetails poAggregatedDetails = new POAggregatedDetails();
                    poAggregatedDetails.setStatus(rs.getString(1));
                    poAggregatedDetails.setCurrency(rs.getString(2));
                    poAggregatedDetails.setUniqueProducts(rs.getInt(3));
                    poAggregatedDetails.setTotalReceivedUnits(rs.getLong(4));
                    poAggregatedDetails.setTotalPendingUnits(rs.getLong(5));
                    poAggregatedDetails.setTotalCancelledUnits(rs.getLong(6));
                    poAggregatedDetails.setTotalReceivedAmount(rs.getDouble(7));
                    poAggregatedDetails.setTotalPendingAmount(rs.getDouble(8));
                    poAggregatedDetails.setTotalCancelledAmount(rs.getDouble(9));
                    poAggregatedDetailsList.add(poAggregatedDetails);
                }
                return poAggregatedDetailsList;
            }
        });
    }

    private String getPoQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "SELECT month, currency, SUM(received_quantity), SUM(received_amount) from " + tableName + " WHERE vs_id IN ('" +
                Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getPoDetailsQuery(String tableName, List<String> vendorSites, String fromMonth, String toMonth){
        return "SELECT status, currency, COUNT(DISTINCT(`fsn`)), SUM(received_quantity), SUM(pending_quantity), SUM" +
                "(cancelled_quantity), SUM(received_amount), SUM(pending_amount), SUM(cancelled_amount) from " +
                tableName + " WHERE vs_id IN ('" + Joiner.on("','").join(vendorSites) + "') GROUP BY currency, status;";
    }
}
