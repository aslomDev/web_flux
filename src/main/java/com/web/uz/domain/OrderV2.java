package com.web.uz.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A OrderV2.
 */
@Table("orderv2")
public class OrderV2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    //test
    @Column("quantity")
    private Long quantity;

    @Column("amount")
    private BigDecimal amount;

    @Column("detail_name")
    private String detailName;

    @JsonIgnoreProperties(value = { "custumer" }, allowSetters = true)
    @Transient
    private Order order;

    @Column("order_id")
    private Long orderId;

    @Transient
    private Product product;

    @Column("product_id")
    private Long productId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderV2 id(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public OrderV2 quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public OrderV2 amount(BigDecimal amount) {
        this.amount = amount != null ? amount.stripTrailingZeros() : null;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount != null ? amount.stripTrailingZeros() : null;
    }

    public String getDetailName() {
        return this.detailName;
    }

    public OrderV2 detailName(String detailName) {
        this.detailName = detailName;
        return this;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public Order getOrder() {
        return this.order;
    }

    public OrderV2 order(Order order) {
        this.setOrder(order);
        this.orderId = order != null ? order.getId() : null;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
        this.orderId = order != null ? order.getId() : null;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long order) {
        this.orderId = order;
    }

    public Product getProduct() {
        return this.product;
    }

    public OrderV2 product(Product product) {
        this.setProduct(product);
        this.productId = product != null ? product.getId() : null;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.productId = product != null ? product.getId() : null;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long product) {
        this.productId = product;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderV2)) {
            return false;
        }
        return id != null && id.equals(((OrderV2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderV2{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", amount=" + getAmount() +
            ", detailName='" + getDetailName() + "'" +
            "}";
    }
}
