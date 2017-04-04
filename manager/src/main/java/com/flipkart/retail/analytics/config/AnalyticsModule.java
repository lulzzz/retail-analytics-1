package com.flipkart.retail.analytics.config;

import com.flipkart.retail.analytics.persistence.AggregatedPaymentsManager;
import com.flipkart.retail.analytics.persistence.PaymentsManager;
import com.flipkart.retail.analytics.persistence.impl.AggregatedPaymentsManagerImpl;
import com.flipkart.retail.analytics.persistence.impl.PaymentsManagerImpl;
import com.flipkart.retail.analytics.resources.AggregatedPaymentResource;
import com.flipkart.retail.analytics.resources.PaymentResource;
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

import java.util.ArrayList;
import java.util.List;

public class AnalyticsModule extends AbstractModule {

  List<String> darwinFiles =  new ArrayList();



  @Provides
  @Singleton
  JerseyClientConfiguration providesJerseyClientConfiguration(
      Provider<AnalyticsConfiguration> provider) {
    return provider.get().getJerseyClient();
  }


  @Provides
  @Singleton
  RotationManagementConfig providesRotationConfig(
      Provider<AnalyticsConfiguration> provider) {
    return provider.get().getRotationManagementConfig();
  }



  @Provides
  GraphiteConfig providesGraphiteConfig(
          final Provider<AnalyticsConfiguration> sellerAnalyticsConfigurationProvider) {
    return sellerAnalyticsConfigurationProvider.get().getGraphiteConfig();
  }



  @Override
  protected void configure() {

    List<String> darwinFiles = new ArrayList<String>();
    darwinFiles.add("reports/seller-tiering.yaml");

    Multibinder<ReportDefinitionFile> multiBinder = Multibinder.newSetBinder(
            binder(),
            ReportDefinitionFile.class
    );
    darwinFiles.stream().forEach(
            reportDefinitionFile -> multiBinder
                    .addBinding()
                    .toInstance(new ReportDefinitionFile(reportDefinitionFile)));

    bind(HasDataSourceFactory.class).to(AnalyticsConfiguration.class);

    //resource binding
    bind(PaymentResource.class).in(Singleton.class);
    bind(AggregatedPaymentResource.class).in(Singleton.class);

    //interface and implementation binding
    bind(PaymentsManager.class).to(PaymentsManagerImpl.class).in(Singleton.class);
    bind(AggregatedPaymentsManager.class).to(AggregatedPaymentsManagerImpl.class).in(Singleton.class);



  }
}
