package com.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Long> {

	@Override
	public Long convertToDatabaseColumn(Duration attribute) {
		// TODO Auto-generated method stub
		if(attribute==null) return null;
		return attribute.toNanos();
	}

	@Override
	public Duration convertToEntityAttribute(Long dbData) {
		// TODO Auto-generated method stub
		if (dbData==null) return null;
		return Duration.of(dbData, ChronoUnit.NANOS);
	}

}
