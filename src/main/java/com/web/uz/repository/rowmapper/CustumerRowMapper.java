package com.web.uz.repository.rowmapper;

import com.web.uz.domain.Custumer;
import com.web.uz.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Custumer}, with proper type conversions.
 */
@Service
public class CustumerRowMapper implements BiFunction<Row, String, Custumer> {

    private final ColumnConverter converter;

    public CustumerRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Custumer} stored in the database.
     */
    @Override
    public Custumer apply(Row row, String prefix) {
        Custumer entity = new Custumer();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setAddress(converter.fromRow(row, prefix + "_address", String.class));
        entity.setPhoneNumber(converter.fromRow(row, prefix + "_phone_number", String.class));
        return entity;
    }
}
