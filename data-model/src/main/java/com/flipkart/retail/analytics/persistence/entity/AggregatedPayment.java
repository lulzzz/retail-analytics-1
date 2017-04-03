package com.flipkart.retail.analytics.persistence.entity;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "aggregated_payments")
@XmlRootElement
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSnakeCase
public class AggregatedPayment extends AbstractEntity {

    @Column(name = "vendor_site_id")
    private String vendorSiteId;

    @Column(name = "month")
    private String month;

    @Column(name = "amount")
    private float amount;

    @Column(name = "currency")
    private String currency;

}
