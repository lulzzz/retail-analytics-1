package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.QCDao;
import com.flipkart.retail.analytics.persistence.entity.QC;
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
public class QCDaoImpl implements QCDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<QC> getQC(String tableName, List<String> vendorSites, List<String> warehouses) {
        String qcQuery = getQCQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(qcQuery, new ResultSetExtractor<List<QC>>() {
            @Override
            public List<QC> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<QC> qcList = new ArrayList<>();
                while (rs.next()) {
                    QC qc = new QC(rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getInt(4), rs.getDouble(5), rs
                            .getString(6));
                    qcList.add(qc);
                }
                return qcList;
            }
        });
    }

    private String getQCQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, SUM(reject_qty), SUM(reject_amount), SUM(excess_qty), SUM(excess_amount), currency from "
                + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites) + "') AND " +
                "warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }
}
