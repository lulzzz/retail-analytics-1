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
                    ReturnOrder returnOrder = new ReturnOrder(rs.getString(1), rs.getString(2), rs.getInt(3), rs
                            .getDouble(4));
                    returnOrderList.add(returnOrder);
                }
                return returnOrderList;
            }
        });
    }

    @Override
    public List<ReturnOrder> getreturnOrderDetails(String tableName, List<String> vendorSites) {
        return null;
    }

    private String getROQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(quantity), SUM(amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }

    private String getRODetailsQuery(String tablename, List<String> vendorSites){
        return "SELECT roi_status, currency, SUM(received_quantity), SUM(amount) from " + tablename + " WHERE vs_id IN ('" +
                Joiner.on("','").join(vendorSites) + "') GROUP BY currency, status;";
    }
}
