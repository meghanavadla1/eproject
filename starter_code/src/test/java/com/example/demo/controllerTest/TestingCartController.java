package com.example.demo.controllerTest;

import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.demo.testutils.injectObjects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestingCartController {

    private CartController cartController;//cartController
    private CartRepository cartRepository;//cartRepository

    {
        cartRepository = mock(CartRepository.class);
    }

    private UserRepository userRepository;//userRepository

    {
        userRepository = mock(UserRepository.class);
    }

    private ItemRepository itemRepository;//itemRepository

    {
        itemRepository = mock(ItemRepository.class);
    }
////////////////////////////////////////////////////////////////////////
    //item,user,cart,modifyCartRequest
    private ModifyCartRequest modifyCartRequest;
    private User user;
    private Item item;
    private Cart cart;

    //user ,item,cart
     //setup
    @Before
    //used when different test cases share the same logic. The method with the @Before annotation always runs before the execution of each test case
    public void setUp() {
        cartController = new CartController();
        injectObjects(cartController, "userRepository", userRepository);
        injectObjects(cartController, "cartRepository", cartRepository);
        injectObjects(cartController, "itemRepository", itemRepository);

    }

    @Test
    //test is a method contained in a class which is only used for testing
                        //The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void addtocartvalid() throws Exception {
         //user
        // set userid
        // username
        // password
        // item
        // itemid
        // itemname
        // price
        // description
        // cart
        // set cartid
        User user = new User();
        user.setUsername("usernametest");
        user.setId(0b0);
        user.setPassword("passwordtest");
        Item item;
        item = new Item();
        item.setId((long) 0);
        item.setName("testingItem");
        item.setPrice(new BigDecimal(10));
        item.setDescription("Description of item");

        Cart cart = new Cart();
        cart.setId((long) 0b0);
        cart.setUser(user);
        user.setCart(cart);
        modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("usernametest");
        modifyCartRequest.setItemId(0b0);
        modifyCartRequest.setQuantity(10);

        OngoingStubbing<Optional<Item>> optionalOngoingStubbing;
        optionalOngoingStubbing = when(itemRepository.findById((long) 0)).thenReturn(Optional.of(item));
        OngoingStubbing<User> usernametest;
        usernametest = when(userRepository.findByUsername("usernametest")).thenReturn(user);

        List<Item> items_list = Arrays.asList(new Item[modifyCartRequest.getQuantity()]);
       //list items
        int len = items_list.size();//len variable
        int i = 0;
        while (i < len) {

            items_list.set(i, item);
            i++;
        }

        final AtomicReference<ResponseEntity<Cart>> response = new AtomicReference<>(cartController.addTocart(modifyCartRequest));
        //final AtomicReference<ResponseEntity<Cart>> response;

        assertNotNull(response);//response
        assertEquals(200, response.get().getStatusCodeValue());
        AtomicReference<Cart> actualCart;
        actualCart = new AtomicReference<>(response.get().getBody());
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.


       // '
    //'
        assertNotNull(actualCart);
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message
        assertEquals(new BigDecimal(100), actualCart.get().getTotal());//get total
        assertEquals(items_list, actualCart.get().getItems());//get items
        assertEquals(cart.getUser(), actualCart.get().getUser());//get user
        assertEquals(cart.getId(), actualCart.get().getId());//getid
        
    }
}
