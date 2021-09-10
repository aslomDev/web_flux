package com.web.uz.service;

import com.web.uz.domain.Custumer;
import com.web.uz.domain.DetailForOrder;
import com.web.uz.domain.Order;
import com.web.uz.domain.Product;
import com.web.uz.repository.CustumerRepository;
import com.web.uz.repository.DetailForOrderRepository;
import com.web.uz.repository.OrderRepository;
import com.web.uz.repository.ProductRepository;
import com.web.uz.service.dto.OrderDto;
import org.hibernate.type.LocalDateTimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Service
@Transactional
public class OrderV2Service {

    private final Logger log = LoggerFactory.getLogger(OrderV2Service.class);

    private final ProductRepository productRepository;
    private final CustumerRepository custumerRepository;
    private final OrderRepository orderRepository;
    private final DetailForOrderRepository detailForOrderRepository;


    public OrderV2Service(ProductRepository productRepository, CustumerRepository custumerRepository, OrderRepository orderRepository, DetailForOrderRepository detailForOrderRepository) {
        this.productRepository = productRepository;
        this.custumerRepository = custumerRepository;
        this.orderRepository = orderRepository;
        this.detailForOrderRepository = detailForOrderRepository;
    }


    public Mono<Product> createProduct(Product product){
        return productRepository.save(product);
    }

    public Mono<Custumer> createCustomer(Custumer custumer){
        return custumerRepository.save(custumer);
    }

    public Mono<DetailForOrder> createOrder(OrderDto orderDto){
//
//
//        Order order = new Order();
//
//        DetailForOrder detailForOrder = new DetailForOrder();
//
//        Mono<Custumer> custumer = custumerRepository.findById(orderDto.getCust_id());
//            .flatMap(custumer1 -> {
//                if (custumer1 != null)
//                    order.setCustumer(custumer1);
//                return Mono.error(new Exception("customer Not found"));
//            });

        Mono<Product> product = productRepository.findById(orderDto.getPr_id());
//            .flatMap(product1 -> {
//                if (product1 != null)
//                    detailForOrder.setProduct(product1);
//                    detailForOrder.setOrder(order);
//                    detailForOrder.setQuantity(orderDto.getQuantity());
//                    detailForOrder.setAmount(calc(orderDto.getQuantity(), product1.getPrice()));
//                return Mono.error(new Exception("not found product"));
//            });

//        orderRepository.save(order);
//        detailForOrderRepository.save(detailForOrder);
//        return Mono.just(detailForOrder);
        return Mono.error(new Exception("error by Aslom"));
    }

    private BigDecimal calc(Long quantity, BigDecimal price) {
        return new BigDecimal(BigInteger.valueOf(quantity)).multiply(BigDecimal.valueOf(quantity));
    }
}
