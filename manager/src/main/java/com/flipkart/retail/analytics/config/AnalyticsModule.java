package com.flipkart.retail.analytics.config;

import com.flipkart.retail.analytics.resources.AggregatorResource;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import fk.sp.common.extensions.dropwizard.db.HasDataSourceFactory;
import flipkart.retail.server.admin.config.RotationManagementConfig;
import io.dropwizard.client.JerseyClientConfiguration;

public class AnalyticsModule extends AbstractModule {

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



  @Override
  protected void configure() {


    bind(HasDataSourceFactory.class).to(AnalyticsConfiguration.class);
    bind(AggregatorResource.class).in(Singleton.class);

    //resource binding
    bind(AggregatorResource.class).in(Singleton.class);

    //interface and implementation binding
    //    bind(VendorMaster.class).to(VendorMasterImpl.class);
    //    bind(TermPersistenceManager.class).to(TermPersistenceManagerImpl.class).in(Singleton.class);



  }
}
