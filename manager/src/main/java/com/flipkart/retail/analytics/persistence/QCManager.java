package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.dto.PurchasingTrend;
import com.flipkart.retail.analytics.dto.purchasingTrend.QCPurchasingTrend;
import com.google.common.base.Joiner;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class QCManager {
    private final JdbcTemplate jdbcTemplate;

    public List<PurchasingTrend> getQC(String tableName, List<String> vendorSites, List<String> warehouses) {
        String qcQuery = getQCQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(qcQuery, new ResultSetExtractor<List<PurchasingTrend>>() {
            @Override
            public List<PurchasingTrend> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PurchasingTrend> qcPurchasingrendList = new ArrayList<>();
                while (rs.next()) {
                    QCPurchasingTrend qcPurchasingTrend = new QCPurchasingTrend(rs.getString(1), rs.getString(2), rs.getLong(2), rs
                            .getDouble(3), rs.getLong(4), rs.getDouble(5));
                    qcPurchasingrendList.add(qcPurchasingTrend);
                }
                return qcPurchasingrendList;
            }
        });
    }

    private String getQCQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, SUM(reject_qty), SUM(reject_amount), SUM(excess_qty), SUM(excess_amount), currency from "
                + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites) + "') AND " +
                "warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }
}
