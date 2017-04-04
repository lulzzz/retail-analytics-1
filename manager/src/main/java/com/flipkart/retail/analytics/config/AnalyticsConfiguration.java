package com.flipkart.retail.analytics.config;

import fk.sp.common.extensions.GraphiteConfig;
import fk.sp.common.extensions.dropwizard.db.HasDataSourceFactory;
import fk.sp.common.extensions.swagger.HasSwaggerConfiguration;
import fk.sp.common.extensions.swagger.SwaggerConfiguration;
import fk.sp.sa.common.bundles.HasGraphiteConfig;
import flipkart.retail.server.admin.config.RotationManagementConfig;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class AnalyticsConfiguration extends Configuration implements HasDataSourceFactory,
        HasSwaggerConfiguration, HasGraphiteConfig {
    @Valid
    private DataSourceFactory dataSource;

    @Valid
    private GraphiteConfig graphiteConfig = null;

    @Valid
    @NotNull
    private RotationManagementConfig rotationManagementConfig;
    @Valid
    @NotNull
    private SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();

    @Override
    public DataSourceFactory getDatabaseConfiguration() {
        return dataSource;
    }

    @NotNull
    @Valid
    JerseyClientConfiguration jerseyClient;

}
