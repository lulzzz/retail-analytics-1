package com.flipkart.retail.analytics.factories;

import com.flipkart.retail.analytics.annotations.EntityHandler;
import com.flipkart.retail.analytics.repository.EntityRepository;
import com.flipkart.retail.analytics.service.AggregationService;
import com.google.inject.Injector;
import io.dropwizard.lifecycle.Managed;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;

import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class EntityHandlerFactory implements Managed{
    private final String PACKAGE = "com.flipkart.retail.analytics.service.aggregated";

    private final EntityRepository entityRepository;
    private final Injector injector;

    @Override
    public void start() throws Exception {
        getAllHandlers().forEach(this::registerHandler);
    }

    @Override
    public void stop() throws Exception {

    }

    private void registerHandler(Class<AggregationService> klass){
        EntityHandler entityHandlerAnnotation = klass.getAnnotation(EntityHandler.class);
        AggregationService aggregationService = injector.getInstance(klass);
        if(Objects.nonNull(entityHandlerAnnotation)){
            EntityHandler.Type[] entityTypeAnnotations = entityHandlerAnnotation.value();
            for(EntityHandler.Type entityTypeAnnotation : entityTypeAnnotations){
                entityRepository.addHandler(entityTypeAnnotation.entityType(), aggregationService);
            }
        }
    }

    private Set<Class<AggregationService>> getAllHandlers(){
        Reflections reflections = new Reflections(PACKAGE);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(EntityHandler.class);
        return annotatedClasses.stream().filter(AggregationService.class::isAssignableFrom)
                .map(klass -> (Class<AggregationService>) klass).collect(Collectors.toSet());
    }
}
