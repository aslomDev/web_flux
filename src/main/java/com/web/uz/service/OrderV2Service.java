package com.web.uz.service;

import com.web.uz.domain.Custumer;
import com.web.uz.domain.Order;
import com.web.uz.domain.OrderV2;
import com.web.uz.domain.Product;
import com.web.uz.repository.CustumerRepository;
import com.web.uz.repository.OrderRepository;
import com.web.uz.repository.OrderV2Repository;
import com.web.uz.repository.ProductRepository;
import com.web.uz.service.dto.OrderDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
public class OrderV2Service {

    private final Logger log = LoggerFactory.getLogger(OrderV2Service.class);

    private final ProductRepository productRepository;
    private final CustumerRepository custumerRepository;
    private final OrderRepository orderRepository;
    private final OrderV2Repository orderV2Repository;


    public OrderV2Service(ProductRepository productRepository, CustumerRepository custumerRepository, OrderRepository orderRepository, OrderV2Repository orderV2Repository) {
        this.productRepository = productRepository;
        this.custumerRepository = custumerRepository;
        this.orderRepository = orderRepository;
        this.orderV2Repository = orderV2Repository;
    }


    public Mono<?> createProduct(OrderDto orderDto){
        Product product = new Product();
        product.setDescription("test2");
        product.setName("testName2");
        product.setPrice(BigDecimal.valueOf(15001));
        return Mono.just(productRepository.save(product));
    }

    public Mono<Custumer> createCustomer(Custumer custumer){
        return custumerRepository.save(custumer);
    }

//    @Transactional(readOnly = true)
    public Mono<?> createOrder(OrderDto orderDto) {

        return productRepository.findById(orderDto.getPr_id())
            .switchIfEmpty(Mono.error(new Exception("error not found product")))
            .flatMap(product -> custumerRepository.findById(orderDto.getCust_id())
                .flatMap(custumer -> {
                    Order order = new Order();
                    OrderV2 detailForOrder = new OrderV2();
                    order.setCustumerId(custumer.getId());
                    order.setCustumer(custumer);
                    order.setDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    return orderRepository.save(order)
                        .flatMap(order1 -> {
                            detailForOrder.setOrderId(order.getId());
                            detailForOrder.setOrder(order);
                            detailForOrder.setDetailName("detail name");
                            detailForOrder.setQuantity(orderDto.getQuantity());
                            detailForOrder.setAmount(calc(orderDto.getQuantity(), product.getPrice()));
                            detailForOrder.setProductId(product.getId());
                            detailForOrder.setProduct(product);
                            return orderV2Repository.save(detailForOrder);
                        });
                })
            );
    }


    public Mono<?> saveTest(){


        OrderV2 orderV2 = new OrderV2();
        orderV2.setDetailName("blabla TYS EDOCS Test");
        orderV2Repository.save(orderV2);

        return Mono.just("ok");

    }
    private BigDecimal calc(Long quantity, BigDecimal price) {
        return new BigDecimal(BigInteger.valueOf(quantity)).multiply(price);
    }
}
