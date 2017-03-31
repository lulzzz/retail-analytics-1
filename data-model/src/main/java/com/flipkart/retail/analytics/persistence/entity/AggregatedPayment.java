package com.flipkart.retail.analytics.persistence.entity;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "aggregated_payments")
@XmlRootElement
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedQueries({
        @NamedQuery(name="findPaymentsByVendorSiteIds",
                query ="select a.vendorSiteId, ROUND(sum(a.amount),3) from AggregatedPayment a where a.vendorSiteId in (:vendorSiteIds) and (a.month between :startDate and :endDate) group by a.vendorSiteId")
})
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

}
