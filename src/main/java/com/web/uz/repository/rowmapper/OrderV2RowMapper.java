package com.web.uz.repository.rowmapper;

import com.web.uz.domain.OrderV2;
import com.web.uz.service.ColumnConverter;
import io.r2dbc.spi.Row;
import java.math.BigDecimal;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link OrderV2}, with proper type conversions.
 */
@Service
public class OrderV2RowMapper implements BiFunction<Row, String, OrderV2> {

    private final ColumnConverter converter;

    public OrderV2RowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link OrderV2} stored in the database.
     */
    @Override
    public OrderV2 apply(Row row, String prefix) {
        OrderV2 entity = new OrderV2();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setQuantity(converter.fromRow(row, prefix + "_quantity", Long.class));
        entity.setAmount(converter.fromRow(row, prefix + "_amount", BigDecimal.class));
        entity.setDetailName(converter.fromRow(row, prefix + "_detail_name", String.class));
        entity.setOrderId(converter.fromRow(row, prefix + "_order_id", Long.class));
        entity.setProductId(converter.fromRow(row, prefix + "_product_id", Long.class));
        return entity;
    }
}
