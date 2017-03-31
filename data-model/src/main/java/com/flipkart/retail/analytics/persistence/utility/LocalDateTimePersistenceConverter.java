package com.flipkart.retail.analytics.persistence.utility;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
                                               AttributeConverter<LocalDateTime, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(LocalDateTime dateTime) {

    if(dateTime==null){
      return null;
    }

    return Timestamp.valueOf(dateTime);
  }

  @Override
  public LocalDateTime convertToEntityAttribute(Timestamp dbTimeStamp) {

    if(dbTimeStamp==null){
      return null;
    }

    return dbTimeStamp.toLocalDateTime();
  }
}