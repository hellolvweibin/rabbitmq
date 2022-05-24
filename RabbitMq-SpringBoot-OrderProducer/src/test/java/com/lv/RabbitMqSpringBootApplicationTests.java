package com.lv;

import com.lv.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitMqSpringBootApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {

        orderService.makeOrderFanout("1","1",12);
    }

    @Test
    void testDirectOrder() {

        orderService.makeOrderDirect("1","1",12);
    }

    @Test
    void testTopicOrder() {

        orderService.makeOrderTopic("1","1",12);
    }


}
