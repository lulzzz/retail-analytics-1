package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.ReturnOrderDao;
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
public class ReturnOrderDaoImpl implements ReturnOrderDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<ReturnOrder> getReturnOrders(String tableName, List<String> vendorSites, List<String> warehouses) {
        String roQuery = getROQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(roQuery, new ResultSetExtractor<List<ReturnOrder>>() {
            @Override
            public List<ReturnOrder> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<ReturnOrder> returnOrderList = new ArrayList<>();
                while (rs.next()) {
//                    ReturnOrder returnOrder = new ReturnOrder(rs.getLong(1), rs.getString(2), rs.getString(3),
//                            rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs
//                            .getDouble(9), rs.getInt(10), rs.getInt(11), rs.getDate(12));
                    ReturnOrder returnOrder = new ReturnOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs
                            .getDouble(4));
                    returnOrderList.add(returnOrder);
                }
                return returnOrderList;
            }
        });
    }

    private String getROQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(quantity), SUM(amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }
}
