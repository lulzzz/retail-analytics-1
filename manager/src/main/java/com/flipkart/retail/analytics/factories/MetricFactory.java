package com.flipkart.retail.analytics.factories;

import com.flipkart.retail.analytics.annotations.MetricHandler;
import com.flipkart.retail.analytics.repository.MetricRepository;
import com.flipkart.retail.analytics.service.AggregationService;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.dropwizard.lifecycle.Managed;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MetricFactory implements Managed {
    private final String PACKAGE = "com.flipkart.retail.analytics.service.aggregated";

    private final MetricRepository metricRepository;
    private final Injector injector;

    @Override
    public void start() throws Exception {
        getAllHandlers().forEach(this::registerHandler);
    }

    @Override
    public void stop() throws Exception {

    }

    private void registerHandler(Class<AggregationService> klass){
        MetricHandler metricHandlerAnnotation = klass.getAnnotation(MetricHandler.class);
        AggregationService aggregationService = injector.getInstance(klass);
        if(Objects.nonNull(metricHandlerAnnotation)){
            MetricHandler.Type[] metricTypeAnnotations = metricHandlerAnnotation.value();
            for(MetricHandler.Type entityTypeAnnotation : metricTypeAnnotations){
                metricRepository.addHandler(entityTypeAnnotation.metricType(), aggregationService);
            }
        }
    }

    private Set<Class<AggregationService>> getAllHandlers(){
        Reflections reflections = new Reflections(PACKAGE);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MetricHandler.class);
        return annotatedClasses.stream().filter(AggregationService.class::isAssignableFrom)
                .map(klass -> (Class<AggregationService>) klass).collect(Collectors.toSet());
    }
}
