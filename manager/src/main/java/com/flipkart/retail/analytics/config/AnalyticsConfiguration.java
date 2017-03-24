package com.flipkart.retail.analytics.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import fk.sp.common.extensions.dropwizard.db.HasDataSourceFactory;
import fk.sp.common.extensions.swagger.HasSwaggerConfiguration;
import fk.sp.common.extensions.swagger.SwaggerConfiguration;
import flipkart.retail.server.admin.config.RotationManagementConfig;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

@Data
public class AnalyticsConfiguration extends Configuration implements HasDataSourceFactory,
        HasSwaggerConfiguration {

    private DataSourceFactory dataSource;

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
