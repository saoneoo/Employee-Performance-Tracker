package sawan.dev.com.EmployeePerformanceTracker;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.YearMonth;

@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return (attribute != null ? attribute.toString() : null); // "2025-08"
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return (dbData != null ? YearMonth.parse(dbData) : null);
    }

}