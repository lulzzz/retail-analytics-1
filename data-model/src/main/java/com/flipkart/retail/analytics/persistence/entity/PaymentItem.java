package com.flipkart.retail.analytics.persistence.entity;

import com.flipkart.retail.analytics.persistence.utility.LocalDateTimePersistenceConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_items")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PaymentItem.findAll", query = "SELECT p FROM PaymentItem p"),
        @NamedQuery(name = "PaymentItem.findById", query = "SELECT p FROM PaymentItem p WHERE p.id = :id"),
        @NamedQuery(name = "PaymentItem.findByItemRefNumber", query = "SELECT p FROM PaymentItem "
                + " p WHERE p.itemRefNumber = "
                + ":itemRefNumber AND p.payments.status = 'ISSUED'"),

})
@Getter
@Setter
@NoArgsConstructor
public class PaymentItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_ref_number")
    private String itemRefNumber;

    @Column(name="item_net_amount")
    private Float itemNetAmount;

    @Column(name="invoice_id")
    private String invoiceId;

    @Column(name="txn_number")
    private String txnNumber;

    @Column(name="invoice_amount")
    private Float invoiceAmount;

    @Column(name="invoice_date")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime invoiceDate;

    @Column(name="invoice_description")
    private String invoiceDescription;

    @Column(name="prepayment_amount")
    private Float prepaymentAmount;

    @Column(name="payment_type")
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payments;
}

