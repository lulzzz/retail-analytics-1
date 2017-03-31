package com.flipkart.retail.analytics.persistence.entity;

import com.flipkart.retail.analytics.persistence.utility.LocalDateTimePersistenceConverter;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@MappedSuperclass
@JsonSnakeCase
public class AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id")
  private Long id;

    @Column(name = "created_at")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime updatedAt;

    @PreUpdate
    public void beforeUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void beforeCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }
}
