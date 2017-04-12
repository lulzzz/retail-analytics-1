package com.flipkart.retail.analytics.utils;

import fk.sp.sa.reports.tableselector.TableNameSelector;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RetailAnalyticsUtils {
    private final TableNameSelector tableNameSelector;

    public String getTableName(String reportName){
        return tableNameSelector.getActiveTableName(reportName);
    }
}
