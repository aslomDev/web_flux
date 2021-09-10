package com.web.uz.repository.rowmapper;

import com.web.uz.domain.DetailForOrder;
import com.web.uz.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link DetailForOrder}, with proper type conversions.
 */
@Service
public class DetailForOrderRowMapper implements BiFunction<Row, String, DetailForOrder> {

    private final ColumnConverter converter;

    public DetailForOrderRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link DetailForOrder} stored in the database.
     */
    @Override
    public DetailForOrder apply(Row row, String prefix) {
        DetailForOrder entity = new DetailForOrder();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setQuantity(converter.fromRow(row, prefix + "_quantity", Long.class));
        entity.setAmount(converter.fromRow(row, prefix + "_amount", BigDecimal.class));
        entity.setOrderId(converter.fromRow(row, prefix + "_order_id", Long.class));
        entity.setProductId(converter.fromRow(row, prefix + "_product_id", Long.class));
        return entity;
    }
}
