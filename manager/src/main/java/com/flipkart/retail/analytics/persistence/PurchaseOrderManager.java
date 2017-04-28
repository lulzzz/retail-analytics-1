package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.POAggregatedDetails;
import com.flipkart.retail.analytics.dto.purchasingTrend.POPurchasingTrend;
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

    public List<PurchasingTrend> getPurchaseOrders(String tableName, List<String> vendorSites, List<String> warehouses) {
        String poQuery = getPoQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(poQuery, new ResultSetExtractor<List<PurchasingTrend>>() {
            @Override
            public List<PurchasingTrend> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchasingTrend> purchaseOrderList = new ArrayList<>();
                while (rs.next()) {
                    POPurchasingTrend purchaseOrder = new POPurchasingTrend(rs.getString(1), rs.getString(2), rs
                            .getLong(3), rs.getLong(4), rs.getDouble(5), rs.getDouble(6));
                    purchaseOrderList.add(purchaseOrder);
                }
                return purchaseOrderList;
            }
        });
    }

    public List<POAggregatedDetails.PODetails> getPurchaseOrderDetails(String poTable, List<String> vendorSites,
                                                                       String fromMonth, String toMonth) {
        String query = getPoDetailsQuery(poTable, vendorSites, fromMonth, toMonth);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<POAggregatedDetails.PODetails>>() {
            @Override
            public List<POAggregatedDetails.PODetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<POAggregatedDetails.PODetails> poAggregatedDetailsList = new ArrayList<>();
                while (rs.next()) {
                    POAggregatedDetails.PODetails poDetails = (new POAggregatedDetails()).new PODetails();
                    poDetails.setStatus(rs.getString(1));
                    poDetails.setCurrency(rs.getString(2));
                    poDetails.setUniqueProducts(rs.getInt(3));
                    poDetails.setReceivedUnits(rs.getLong(4));
                    poDetails.setPendingUnits(rs.getLong(5));
                    poDetails.setCancelledUnits(rs.getLong(6));
                    poDetails.setReceivedAmount(rs.getDouble(7));
                    poDetails.setPendingAmount(rs.getDouble(8));
                    poDetails.setCancelledAmount(rs.getDouble(9));
                    poAggregatedDetailsList.add(poDetails);
                }
                return poAggregatedDetailsList;
            }
        });
    }

    public Long getAggregatedPOCount(String tableName, List<String> vendorSites){
        String query = getPoCountQuery(tableName, vendorSites);
        return jdbcTemplate.query(query, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return rs.getLong(1);
                }
                return null;
            }
        });
    }

    private String getPoQuery(String poTable, List<String> vendorSites, List<String> warehouses){
        return "SELECT month, currency, SUM(received_quantity), SUM(total_quantity), SUM(received_amount), SUM" +
                "(total_amount) from  " + poTable + " WHERE vs_id IN ('" + Joiner.on("','").join(vendorSites) + "') " +
                "AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getPoDetailsQuery(String poTable, List<String> vendorSites, String fromMonth,
                                     String toMonth){
        return "SELECT status, currency, COUNT(DISTINCT(`fsn`)), SUM(received_quantity), SUM(pending_quantity), SUM" +
                "(cancelled_quantity), SUM(received_amount), SUM(pending_amount), SUM(cancelled_amount) from " +
                poTable + " WHERE vs_id IN ('" + Joiner.on("','").join(vendorSites) + "') GROUP BY currency, status;";
    }

    private String getPoCountQuery(String metricTable, List<String> vendorSites){
        return "SELECT SUM(aggregated_pending_po) from " + metricTable + " WHERE vs_id IN ('" + Joiner.on("','").join
                (vendorSites) + "');";
    }
}
