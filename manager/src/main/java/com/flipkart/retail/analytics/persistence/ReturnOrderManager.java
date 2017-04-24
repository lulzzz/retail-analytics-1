package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.aggregatedDetails.ROAggregatedDetails;
import com.flipkart.retail.analytics.persistence.entity.ReturnOrder;
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

    public List<ReturnOrder> getReturnOrders(String tableName, List<String> vendorSites, List<String> warehouses) {
        String roQuery = getROQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(roQuery, new ResultSetExtractor<List<ReturnOrder>>() {
            @Override
            public List<ReturnOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ReturnOrder> returnOrderList = new ArrayList<>();
                while (rs.next()) {
                    ReturnOrder returnOrder = new ReturnOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs
                            .getDouble(4));
                    returnOrderList.add(returnOrder);
                }
                return returnOrderList;
            }
        });
    }

    public List<ROAggregatedDetails> getReturnOrderDetails(String tableName, List<String> vendorSites) {
        String query = getRODetailsQuery(tableName, vendorSites);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<ROAggregatedDetails>>() {
            @Override
            public List<ROAggregatedDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ROAggregatedDetails> roAggregatedDetailsList = new ArrayList<>();
                while (rs.next()) {
                    ROAggregatedDetails roAggregatedDetails = new ROAggregatedDetails();
                    roAggregatedDetails.setStatus(rs.getString(1));
                    roAggregatedDetails.setCurrency(rs.getString(2));
                    roAggregatedDetails.setUniqueProducts(rs.getInt(3));
                    roAggregatedDetails.setTotalUnits(rs.getLong(4));
                    roAggregatedDetails.setTotalProcessedUnits(rs.getLong(5));
                    roAggregatedDetails.setTotalAmount(rs.getDouble(6));
                    roAggregatedDetails.setTotalProcessedAmount(rs.getDouble(7));
                    roAggregatedDetailsList.add(roAggregatedDetails);
                }
                return roAggregatedDetailsList;
            }
        });
    }

    private String getROQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(quantity), SUM(total_amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getRODetailsQuery(String tablename, List<String> vendorSites){
        return "SELECT roi_status, currency, COUNT(DISTINCT(`fsn`)), SUM(quantity), SUM(processed_quantity), SUM" +
                "(total_amount), SUM(processed_amount) from " + tablename + " WHERE vs_id IN ('" + Joiner.on("','")
                .join(vendorSites) + "') GROUP BY currency, roi_status;";
    }
}
