package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.IRNDao;
import com.flipkart.retail.analytics.persistence.entity.IRN;
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
public class IRNDaoImpl implements IRNDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<IRN> getIRNs(String tableName, List<String> vendorSites, List<String> warehouses) {
        String irnQuery = getIrnQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(irnQuery, new ResultSetExtractor<List<IRN>>() {
            @Override
            public List<IRN> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<IRN> irnList = new ArrayList<>();
                while (rs.next()) {
                    IRN purchaseOrder = new IRN(rs.getString(1), rs.getInt(2), rs.getDouble(3));
                    irnList.add(purchaseOrder);
                }
                return irnList;
            }
        });
    }

    private String getIrnQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, SUM(total_qty), SUM(total_invoice_amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month";
    }
}
