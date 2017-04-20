package com.flipkart.retail.analytics.config;

import com.flipkart.retail.analytics.factories.EntityFactory;
import com.flipkart.retail.analytics.persistence.*;
import com.flipkart.retail.analytics.persistence.impl.*;
import com.flipkart.retail.analytics.repository.EntityRepository;
import com.flipkart.retail.analytics.repository.MetricRepository;
import com.flipkart.retail.analytics.resources.AggregatedPaymentResource;
import com.flipkart.retail.analytics.resources.InvoiceResource;
import com.flipkart.retail.analytics.resources.PaymentResource;
import com.flipkart.retail.analytics.service.aggregated.PurchaseOrderService;
import com.flipkart.retail.analytics.service.aggregated.ReturnOrderService;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import fk.sp.common.extensions.GraphiteConfig;
import fk.sp.common.extensions.dropwizard.db.HasDataSourceFactory;
import fk.sp.sa.reports.update.ReportDefinitionFile;
import flipkart.retail.server.admin.config.RotationManagementConfig;
import io.dropwizard.client.JerseyClientConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.time.Clock;
import java.util.List;

public class AnalyticsModule extends AbstractModule {
    @Override
    protected void configure() {
        List<String> aggrReports = Lists.newArrayList(
                "reports/purchase_order_aggr.yaml",
                "reports/return_order_aggr.yaml",
                "reports/irn_aggr.yaml",
                "reports/invoice_aggr.yaml",
                "reports/qc_aggr.yaml"
        );

        Multibinder<ReportDefinitionFile> multiBinder = Multibinder.newSetBinder(binder(), ReportDefinitionFile.class);
        aggrReports.stream().forEach(
                reportDefinitionFile -> multiBinder.addBinding().toInstance(new ReportDefinitionFile(reportDefinitionFile))
        );

        bind(PaymentResource.class).in(Singleton.class);
        bind(AggregatedPaymentResource.class).in(Singleton.class);
        bind(PaymentsManager.class).to(PaymentsManagerImpl.class).in(Singleton.class);
        bind(PaymentItemsPersistenceManager.class).to(PaymentItemsPersistenceManagerImpl.class).in(Singleton.class);
        bind(AggregatedPaymentsManager.class).to(AggregatedPaymentsManagerImpl.class).in(Singleton.class);
        bind(InvoiceDao.class).to(InvoiceDaoImpl.class).in(Singleton.class);
        bind(IRNDao.class).to(IRNDaoImpl.class).in(Singleton.class);
        bind(PaymentsDao.class).to(PaymentsDaoImpl.class).in(Singleton.class);
        bind(PurchaseOrderDao.class).to(PurchaseOrderDaoImpl.class).in(Singleton.class);
        bind(QCDao.class).to(QCDaoImpl.class).in(Singleton.class);
        bind(ReturnOrderDao.class).to(ReturnOrderDaoImpl.class).in(Singleton.class);
        bind(EntityRepository.class).in(Singleton.class);
        bind(MetricRepository.class).in(Singleton.class);
        bind(EntityFactory.class).in(Singleton.class);
        bind(InvoiceResource.class).in(Singleton.class);
        bind(PurchaseOrderService.class).in(Singleton.class);
        bind(ReturnOrderService.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    JerseyClientConfiguration providesJerseyClientConfiguration(Provider<AnalyticsConfiguration> provider) {
        return provider.get().getJerseyClient();
    }

    @Provides
    @Singleton
    RotationManagementConfig providesRotationConfig(Provider<AnalyticsConfiguration> provider) {
        return provider.get().getRotationManagementConfig();
    }

    @Provides
    Clock providesClock() {
        return Clock.systemDefaultZone();
    }

    @Provides
    public HasDataSourceFactory providesHasDataSourceFactory(AnalyticsConfiguration analyticsConfiguration) {
        return () -> analyticsConfiguration.getDatabaseConfiguration();
    }

    @Provides
    GraphiteConfig providesGraphiteConfig(final Provider<AnalyticsConfiguration> sellerAnalyticsConfigurationProvider) {
        return sellerAnalyticsConfigurationProvider.get().getGraphiteConfig();
    }

    @Provides
    JdbcTemplate providesJdbcTemplate(DataSource dataSource) throws ClassNotFoundException {
        return new JdbcTemplate(dataSource);
    }

    @Provides
    @Singleton
    ReportsConfiguration providesReportConfiguration(AnalyticsConfiguration analyticsConfiguration){
        return analyticsConfiguration.getReportsConfiguration();
    }
}
