package com.flipkart.retail.analytics;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.flipkart.retail.analytics.config.AnalyticsConfiguration;
import com.flipkart.retail.analytics.config.AnalyticsModule;
import com.google.common.collect.Sets;
import com.google.inject.Stage;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.palominolabs.metrics.guice.MetricsInstrumentationModule;
import fk.sp.common.extensions.dropwizard.elb.config.ElbHealthcheckModule;
import fk.sp.common.extensions.dropwizard.jersey.JerseyClientModule;
import fk.sp.common.extensions.dropwizard.jersey.LoggingFilter;
import fk.sp.common.extensions.guice.jpa.spring.JpaWithSpringModule;
import fk.sp.common.extensions.swagger.SwaggerBundle;
import flipkart.retail.server.admin.bundle.RotationManagementBundle;
import flipkart.retail.server.admin.config.RotationManagementConfig;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Properties;

public class AnalyticsManagementApplication extends Application<AnalyticsConfiguration> {

    private GuiceBundle<AnalyticsConfiguration> guiceBundle;

    public static void main(String[] args) throws Exception {
        AnalyticsManagementApplication
                analyticsManagementApplication =
                new AnalyticsManagementApplication();
        analyticsManagementApplication.run(args);
    }

    @Override
    public void initialize(Bootstrap<AnalyticsConfiguration> bootstrap) {
        super.initialize(bootstrap);

        this.guiceBundle = GuiceBundle.<AnalyticsConfiguration>newBuilder()
                .setConfigClass(AnalyticsConfiguration.class)
                .addModule(new AnalyticsModule())
                .addModule(new JerseyClientModule())
                .addModule(new ElbHealthcheckModule())
                .addModule(new MetricsInstrumentationModule(bootstrap.getMetricRegistry()))
                .addModule(new JpaWithSpringModule(
                        Sets.newHashSet(
                                "com.flipkart.retail.analytics"
                        ), new Properties()))
                .enableAutoConfig(
                        "fk.sp.common.extensions.guice.jpa",
                        "fk.sp.sa.common.bundles",
                        "fk.sp.common.extensions.exception",
                        "fk.sp.common.extensions.dropwizard.hystrix",
                        "fk.sp.common.extensions.dropwizard.logging",
                        "fk.sp.common.extensions.dropwizard.jpa",
                        "fk.sp.common.extensions.filter",
                        "com.flipkart.retail.analytics"
                )
                .build(Stage.DEVELOPMENT);
        bootstrap.addBundle(guiceBundle);

        //For oor and bir task and healthcheck integration
        bootstrap.addBundle(new RotationManagementBundle<AnalyticsConfiguration>() {
            @Override
            public RotationManagementConfig getRotationManagementConfig(
                    AnalyticsConfiguration AnalyticsConfiguration) {
                return AnalyticsConfiguration.getRotationManagementConfig();
            }
        });

        bootstrap.addBundle(new SwaggerBundle());

        //For dbMigrations support
        bootstrap.addBundle(new MigrationsBundle<AnalyticsConfiguration>() {
            public DataSourceFactory getDataSourceFactory(
                    AnalyticsConfiguration configuration) {
                return configuration.getDatabaseConfiguration();
            }
        });
    }

    @Override
    public void run(AnalyticsConfiguration configuration, Environment environment) throws Exception {

        //For swagger UI
        AssetsBundle assetsBundle = new AssetsBundle("/apidocs", "/apidocs", "index.html", "/apidocs");
        assetsBundle.run(environment);

        environment.getObjectMapper().registerModules(new JavaTimeModule());
        environment.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        environment.getObjectMapper().setPropertyNamingStrategy(
                PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

        environment.jersey().register(
                new LoggingFilter(java.util.logging.Logger.getLogger("InboundRequestResponse"), true));

    }
}
