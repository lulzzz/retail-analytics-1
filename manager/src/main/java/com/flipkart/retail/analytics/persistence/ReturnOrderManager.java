package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.aggregatedDetails.ROAggregatedDetails;
import com.flipkart.retail.analytics.dto.purchasingTrend.ROPurchasingTrend;
import com.flipkart.retail.analytics.persistence.entity.ROAggregatedCount;
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
public class ReturnOrderManager {
    private final JdbcTemplate jdbcTemplate;

    public List<PurchasingTrend> getReturnOrders(String tableName, List<String> vendorSites, List<String> warehouses) {
        String roQuery = getROQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(roQuery, new ResultSetExtractor<List<PurchasingTrend>>() {
            @Override
            public List<PurchasingTrend> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchasingTrend> returnOrderList = new ArrayList<>();
                while (rs.next()) {
                    ROPurchasingTrend returnOrder = new ROPurchasingTrend(rs.getString(1), rs.getString(2), rs.getInt(3), rs
                            .getDouble(4));
                    returnOrderList.add(returnOrder);
                }
                return returnOrderList;
            }
        });
    }

    public List<ROAggregatedDetails.RODetails> getReturnOrderDetails(String tableName, List<String>
            vendorSites) {
        String query = getRODetailsQuery(tableName, vendorSites);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<ROAggregatedDetails.RODetails>>() {
            @Override
            public List<ROAggregatedDetails.RODetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ROAggregatedDetails.RODetails> roAggregatedDetailsList = new ArrayList<>();
                while (rs.next()) {
                    ROAggregatedDetails.RODetails roDetails = (new ROAggregatedDetails()).new RODetails();
                    roDetails.setStatus(rs.getString(1));
                    roDetails.setCurrency(rs.getString(2));
                    roDetails.setUniqueProducts(rs.getInt(3));
                    roDetails.setUnits(rs.getLong(4));
                    roDetails.setProcessedUnits(rs.getLong(5));
                    roDetails.setAmount(rs.getDouble(6));
                    roDetails.setProcessedAmount(rs.getDouble(7));
                    roAggregatedDetailsList.add(roDetails);
                }
                return roAggregatedDetailsList;
            }
        });
    }

    public ROAggregatedCount getAggregatedROCount(String tableName, List<String> vendorSites){
        String query = getROCountQuery(tableName, vendorSites);
        return jdbcTemplate.query(query, new ResultSetExtractor<ROAggregatedCount>() {
            @Override
            public ROAggregatedCount extractData(ResultSet rs) throws SQLException, DataAccessException {
                ROAggregatedCount roAggregatedCount = new ROAggregatedCount();
                if(rs.next()){
                    roAggregatedCount = new ROAggregatedCount(rs.getLong(1), rs.getLong(2), rs.getLong(3));
                }
                return roAggregatedCount;
            }
        });
    }

    private String getROQuery(String roTable, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(quantity), SUM(total_amount) from " + roTable + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getRODetailsQuery(String roTable, List<String> vendorSites){
        return "SELECT roi_status, currency, COUNT(DISTINCT(`fsn`)), SUM(quantity), SUM(processed_quantity), SUM" +
                "(total_amount), SUM(processed_amount) from " + roTable + " WHERE vs_id IN ('" + Joiner.on("','")
                .join(vendorSites) + "') GROUP BY currency, roi_status;";
    }

    private String getROCountQuery(String metricTable, List<String> vendorSites){
        return "SELECT SUM(aggregated_pending_ro), SUM(aggregated_approved_ro), SUM(aggregated_rejected_ro) from " +
                metricTable + " WHERE vs_id IN ('" + Joiner.on("','").join(vendorSites) + "')";
    }
}
