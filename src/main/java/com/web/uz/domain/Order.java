package com.web.uz.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Order.
 */
@Table("jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column("date")
    private LocalDate date;

    @Transient
    private Custumer custumer;

    @Column("custumer_id")
    private Long custumerId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order id(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Order date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Custumer getCustumer() {
        return this.custumer;
    }

    public Order custumer(Custumer custumer) {
        this.setCustumer(custumer);
        this.custumerId = custumer != null ? custumer.getId() : null;
        return this;
    }

    public void setCustumer(Custumer custumer) {
        this.custumer = custumer;
        this.custumerId = custumer != null ? custumer.getId() : null;
    }

    public Long getCustumerId() {
        return this.custumerId;
    }

    public void setCustumerId(Long custumer) {
        this.custumerId = custumer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
