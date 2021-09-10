package com.web.uz.repository;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import com.web.uz.domain.DetailForOrder;
import com.web.uz.repository.rowmapper.DetailForOrderRowMapper;
import com.web.uz.repository.rowmapper.OrderRowMapper;
import com.web.uz.repository.rowmapper.ProductRowMapper;
import com.web.uz.service.EntityManager;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive custom repository implementation for the DetailForOrder entity.
 */
@SuppressWarnings("unused")
class DetailForOrderRepositoryInternalImpl implements DetailForOrderRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final OrderRowMapper orderMapper;
    private final ProductRowMapper productMapper;
    private final DetailForOrderRowMapper detailfororderMapper;

    private static final Table entityTable = Table.aliased("detail_for_order", EntityManager.ENTITY_ALIAS);
    private static final Table orderTable = Table.aliased("jhi_order", "e_order");
    private static final Table productTable = Table.aliased("product", "product");

    public DetailForOrderRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        OrderRowMapper orderMapper,
        ProductRowMapper productMapper,
        DetailForOrderRowMapper detailfororderMapper
    ) {
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.detailfororderMapper = detailfororderMapper;
    }

    @Override
    public Flux<DetailForOrder> findAllBy(Pageable pageable) {
        return findAllBy(pageable, null);
    }

    @Override
    public Flux<DetailForOrder> findAllBy(Pageable pageable, Criteria criteria) {
        return createQuery(pageable, criteria).all();
    }

    RowsFetchSpec<DetailForOrder> createQuery(Pageable pageable, Criteria criteria) {
        List<Expression> columns = DetailForOrderSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(OrderSqlHelper.getColumns(orderTable, "order"));
        columns.addAll(ProductSqlHelper.getColumns(productTable, "product"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(orderTable)
            .on(Column.create("order_id", entityTable))
            .equals(Column.create("id", orderTable))
            .leftOuterJoin(productTable)
            .on(Column.create("product_id", entityTable))
            .equals(Column.create("id", productTable));

        String select = entityManager.createSelect(selectFrom, DetailForOrder.class, pageable, criteria);
        String alias = entityTable.getReferenceName().getReference();
        String selectWhere = Optional
            .ofNullable(criteria)
            .map(
                crit ->
                    new StringBuilder(select)
                        .append(" ")
                        .append("WHERE")
                        .append(" ")
                        .append(alias)
                        .append(".")
                        .append(crit.toString())
                        .toString()
            )
            .orElse(select); // TODO remove once https://github.com/spring-projects/spring-data-jdbc/issues/907 will be fixed
        return db.sql(selectWhere).map(this::process);
    }

    @Override
    public Flux<DetailForOrder> findAll() {
        return findAllBy(null, null);
    }

    @Override
    public Mono<DetailForOrder> findById(Long id) {
        return createQuery(null, where("id").is(id)).one();
    }

    private DetailForOrder process(Row row, RowMetadata metadata) {
        DetailForOrder entity = detailfororderMapper.apply(row, "e");
        entity.setOrder(orderMapper.apply(row, "order"));
        entity.setProduct(productMapper.apply(row, "product"));
        return entity;
    }

    @Override
    public <S extends DetailForOrder> Mono<S> insert(S entity) {
        return entityManager.insert(entity);
    }

    @Override
    public <S extends DetailForOrder> Mono<S> save(S entity) {
        if (entity.getId() == null) {
            return insert(entity);
        } else {
            return update(entity)
                .map(
                    numberOfUpdates -> {
                        if (numberOfUpdates.intValue() <= 0) {
                            throw new IllegalStateException("Unable to update DetailForOrder with id = " + entity.getId());
                        }
                        return entity;
                    }
                );
        }
    }

    @Override
    public Mono<Integer> update(DetailForOrder entity) {
        //fixme is this the proper way?
        return r2dbcEntityTemplate.update(entity).thenReturn(1);
    }
}

class DetailForOrderSqlHelper {

    static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("quantity", table, columnPrefix + "_quantity"));
        columns.add(Column.aliased("amount", table, columnPrefix + "_amount"));

        columns.add(Column.aliased("order_id", table, columnPrefix + "_order_id"));
        columns.add(Column.aliased("product_id", table, columnPrefix + "_product_id"));
        return columns;
    }
}
