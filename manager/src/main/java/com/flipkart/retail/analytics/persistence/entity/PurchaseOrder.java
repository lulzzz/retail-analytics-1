package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement

@Getter
@Setter
@AllArgsConstructor
public class PurchaseOrder {
    @Id
    private Long id;

    @Column(name = "vs_id")
    private String vsId;

    private String fsn;

    @Column(name = "fk_warehouse")
    private String fkWarehouse;

    private String currency;

    private String month;

    private String status;

    @Column(name = "received_quantity")
    private Integer receivedQuantity;

    private Double amount;

    public PurchaseOrder(String month, String currency, Integer receivedQuantity, Double amount){
        this.month = month;
        this.currency = currency;
        this.receivedQuantity = receivedQuantity;
        this.amount = amount;
    }
}
