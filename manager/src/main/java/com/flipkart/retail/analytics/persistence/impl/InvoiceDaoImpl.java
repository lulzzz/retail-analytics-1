package com.flipkart.retail.analytics.persistence.impl;

import com.flipkart.retail.analytics.persistence.InvoiceDao;
import com.flipkart.retail.analytics.persistence.entity.Invoice;
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
public class InvoiceDaoImpl implements InvoiceDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Invoice> getInvoice(String tableName, List<String> vendorSites, List<String> warehouses) {
        String invoiceQuery = getInvoiceQuery(tableName, vendorSites, warehouses);
        return jdbcTemplate.query(invoiceQuery, new ResultSetExtractor<List<Invoice>>() {
            @Override
            public List<Invoice> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Invoice> invoiceList = new ArrayList<>();
                while (rs.next()) {
                    Invoice invoice = new Invoice(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
                    invoiceList.add(invoice);
                }
                return invoiceList;
            }
        });
    }

    private String getInvoiceQuery(String tableName, List<String> vendorSites, List<String> warehouses){
        return "select month, currency, SUM(total_quantity), SUM(total_invoice_amount) from " + tableName + " where vs_id IN ('" + Joiner.on("','").join(vendorSites)
                + "') AND fk_warehouse IN ('"+ Joiner.on("','").join(warehouses) + "')  GROUP BY month, currency";
    }
}
