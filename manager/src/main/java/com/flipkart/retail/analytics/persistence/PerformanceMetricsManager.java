package com.flipkart.retail.analytics.persistence;

import com.flipkart.retail.analytics.persistence.entity.PerformanceMetrics;
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
public class PerformanceMetricsManager {
    private final JdbcTemplate jdbcTemplate;

    public List<PerformanceMetrics> getPerformanceMetrics(String tableName, List<String> vendorSites, List<String>
            warehouse) {
        String query = getMetricsQuery(tableName, vendorSites, warehouse);
        return jdbcTemplate.query(query, new ResultSetExtractor<List<PerformanceMetrics>>() {
            @Override
            public List<PerformanceMetrics> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<PerformanceMetrics> performanceMetricsList = new ArrayList<>();
                while (rs.next()){
                    performanceMetricsList.add(new PerformanceMetrics(rs.getString(1), rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs
                            .getString(9)));
                }
                return performanceMetricsList;
            }
        });
    }

    private String getMetricsQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "SELECT month,\n" +
                "\tFORMAT(SUM(fill_rate_received_qty) / SUM(fill_rate_total_qty), 2) AS fill_rate,\n" +
                " \tFORMAT(SUM(lead_time_for_qty) / SUM(lead_time_received_qty), 2) AS lead_time,\n" +
                " \tFORMAT(SUM(qc_reject_reject_qty) / SUM(qc_reject_received_qty) * 100, 2) AS qc_reject_with_excess,\n" +
                " \tFORMAT((SUM(qc_reject_reject_qty) - SUM(qc_reject_excess_qty)) / SUM(qc_reject_received_qty) * " +
                "100, 2) AS qc_reject_without_excess,\n" +
                "\tFORMAT(SUM(ro_approval_ro_tat) / SUM(ro_approval_total_ro), 2) AS ro_Approval_tat,\n" +
                "\tFORMAT(SUM(ro_approval_ro_approved_eager) / SUM(ro_approval_total_ro) * 100, 2) AS ro_approved_eager,\n" +
                "\tFORMAT(SUM(ro_reject_rejected_ro) / SUM(ro_reject_total_ro), 2) AS ro_reject,\n" +
                "\tSUM(unactioned_ro_total) AS ro_unactioned\n" +
                "FROM performance_metrics_2\n" +
                "WHERE vs_id IN ('" + Joiner.on("','").join(vendorSites) + "')\n" +
                "AND warehouse IN ('" + Joiner.on("','").join(warehouses) + "')" +
                "GROUP BY month;";
    }
}
