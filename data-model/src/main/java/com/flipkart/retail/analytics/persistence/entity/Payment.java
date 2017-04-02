package com.flipkart.retail.analytics.persistence.entity;

import com.flipkart.retail.analytics.persistence.utility.Currencies;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name="findLastPaymentByVsIds",
                query = "select p from Payment p where p.vendorSiteId in (:vendorSiteIds) order by p.amount desc")
})
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSnakeCase
public class Payment extends AbstractEntity {

    @Column(name = "paid_date")
    private String paidDate;


    @Column(name = "ref_number")
    private String refNumber;

    @Column(name = "payment_number")
    private String paymentNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private String status;

    @Column(name = "vendor_bank_name")
    private String vendorBankName;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "vendor_site_id")
    private String vendorSiteId;

    @Column(name = "vendor_bank_account_number")
    private String vendorBankAccountNumber;

    @Column(name = "currency")
    private String currency;

}
