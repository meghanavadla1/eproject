package com.example.demo.controllerTest;

import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.controllers.OrderController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.demo.testutils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestingOrderController {
//TestingOrderController
//orderController
// userRepository
// itemRepository
// cartRepository
// user
// cart
// item
    private OrderController orderController;

    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private User user;
    private Cart cart;
    private Item item;

    @Before
   // used when different test cases share the same logic. The method with the @Before annotation always runs before the execution of each test case
    public void setUp() {
        orderController = new OrderController();
        injectObjects(orderController, "userRepository", userRepository);
        injectObjects(orderController, "orderRepository", orderRepository);
        //from user setid setusername setpassword
        // from cart setid setuser settotal setcart
         // from item set id setname setprice set description
        user = new User();
        user.setId(0b0);
        user.setUsername("usernametest");
        user.setPassword("passwordtest");
// from cart setid setuser settotal setcart
        cart = new Cart();
        cart.setId((long) 0b0);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(14.97));
        user.setCart(cart);
// from item set id setname setprice set description
        item = new Item();
        item.setId((long) 0b0);
        item.setName("itemfortest");
        item.setPrice(new BigDecimal(5.99));
        item.setDescription("Description for test item");
        List<Item> itemsArray;//take a list and add items  into it
        itemsArray = new ArrayList<>();
        itemsArray.add(item);
        itemsArray.add(item);
        itemsArray.add(item);
        cart.setItems(itemsArray);//setitems present in list


         var usernametest = when(userRepository.findByUsername("usernametest")).thenReturn(user);
         var nullusername = when(userRepository.findByUsername("nullusername")).thenReturn(null);

    }

















//PlacingtheOrder_valid
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void PlacingtheOrder_valid() throws Exception {
//check user
        assertNotNull(user);
        assertEquals("usernametest", user.getUsername());
        final AtomicReference<ResponseEntity<UserOrder>> response;
        response = new AtomicReference<>(orderController.submit(user.getUsername()));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

//check if not null
        assertNotNull(response);
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message
        assertEquals(200, response.get().getStatusCodeValue());
        AtomicReference<UserOrder> user_order;
        user_order = new AtomicReference<>(response.get().getBody());
        assertNotNull(user_order);//user_order

        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message
        assertEquals(cart.getTotal(), user_order.get().getTotal());//gettotal
        assertEquals(cart.getUser(), user_order.get().getUser());//getuser
        assertEquals(cart.getItems(), user_order.get().getItems());//getitems

    }
















//getOrdersForUser_UnavailableUser
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void getOrdersForUser_UnavailableUser() throws Exception {
//response
        final AtomicReference<ResponseEntity<List<UserOrder>>> response;
        response = new AtomicReference<>(orderController.getOrdersForUser("nullusername"));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

        assertNotNull(response);
        assertEquals(404, response.get().getStatusCodeValue());
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message
    }




















    //submittheorderUnavailableUser
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void submittheorderUnavailableUser() throws Exception {

//response
        final AtomicReference<ResponseEntity<UserOrder>> response;
        response = new AtomicReference<>(orderController.submit("nullusername"));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

        assertNotNull(response);
        assertEquals(404, response.get().getStatusCodeValue());
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message
    }



}
