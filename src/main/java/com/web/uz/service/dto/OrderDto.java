package com.web.uz.service.dto;

public class OrderDto {

    private Long pr_id;
    private Long cust_id;
    private Long quantity;


    public Long getPr_id() {
        return pr_id;
    }

    public void setPr_id(Long pr_id) {
        this.pr_id = pr_id;
    }

    public Long getCust_id() {
        return cust_id;
    }

    public void setCust_id(Long cust_id) {
        this.cust_id = cust_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
