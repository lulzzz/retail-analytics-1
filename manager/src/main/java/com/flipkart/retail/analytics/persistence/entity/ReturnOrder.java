package com.flipkart.retail.analytics.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Entity
@XmlRootElement

@Getter
@Setter
@AllArgsConstructor
public class ReturnOrder {
    @Id
    private Long id;

    @Column(name = "vs_id")
    private String vsId;

    private String fsn;

    @Column(name = "fk_warehouse")
    private String fkWarehouse;

    private String currency;

    private String month;

    @Column(name = "roi_status")
    private String roiStatus;

    private Integer quantity;

    private Double amount;

    @Column(name = "lost_quantity")
    private Integer lostQuantity;

    @Column(name = "processed_quantity")
    private Integer processedQuantity;

    @Column(name = "updated_at")
    private Date updatedAt;

    public ReturnOrder(String month, String currency, Integer quantity, Double amount){
        this.month = month;
        this.currency = currency;
        this.quantity = quantity;
        this.amount = amount;
    }
}
