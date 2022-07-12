package jpabook.jpashop.domain.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember("member1", new Address("서울", "경기", "123-123"));

        Book book = createBook("시골 JPA", 10000, 10);


        //when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(10000 * orderCount).isEqualTo(getOrder.getTotalPrice());
        assertThat(book.getStockQuantity()).isEqualTo(8);
    }

    @Test
    public void 상품주문_재고수량_초과() throws Exception{
        //given
        Member member = createMember("member1", new Address("서울", "경기", "123-123"));
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 11;

        //when
        NotEnoughStockException e = assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        //then
        assertThat(e.getMessage()).isEqualTo("need more Stock");

    }


    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember("member1", new Address("서울", "경기", "123-123"));
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(getOrder.getStatus());
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }
//
//    @Test
//    public void 주문취소불가() throws Exception{
//        //given
//        Member member = createMember("member1", new Address("서울", "경기", "123-123"));
//        Book item = createBook("시골 JPA", 10000, 10);
//        Delivery delivery = new Delivery();
//
//        int orderCount = 2;
//        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
//
//        //when
//        Order getOrder = orderRepository.findOne(orderId);
//        System.out.println("getOrder = ");
//
//        //then
//
//    }



    private Book createBook(String name, int price, int stockQuantity) {
        Book book = Book.createBook(name, price, stockQuantity, "test", "111" );
//        Book book = new Book();
//        book.setName(name);
//        book.setPrice(price);
//        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

}
