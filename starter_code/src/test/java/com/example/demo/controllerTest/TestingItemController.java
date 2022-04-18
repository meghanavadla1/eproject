package com.example.demo.controllerTest;

import com.example.demo.model.persistence.User;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.demo.testutils.injectObjects;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestingItemController  {
//TestingItemController


    //item
    // user
    // itemcontroller
    // itemrepository
    private Item item;
    private User user;
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    //used when different test cases share the same logic. The method with the @Before annotation always runs before the execution of each test case
    public void setUp() {
        itemController = new ItemController();
        injectObjects(itemController, "itemRepository", itemRepository);


        //item
        // setname
        // setprice
        // set description
        item = new Item();
        item.setName("ItemTest");
        item.setPrice(new BigDecimal(4.99));
        item.setDescription("Description of a test item");
        Optional<Item> optionalItem;
        optionalItem = Optional.of(item);
           //items
        //list of items type
        List<Item> item_list;
        item_list = new ArrayList<>();
        int i=0;
        while (i < 3) {
            item.setId((long) i);
            item_list.add(item);
            i++;
        }

        OngoingStubbing<List<Item>> listOngoingStubbing;
        listOngoingStubbing = when(itemRepository.findAll()).thenReturn(item_list);
        //find all

        OngoingStubbing<Optional<Item>> optionalOngoingStubbing;
        optionalOngoingStubbing = when(itemRepository.findById((long) 0)).thenReturn(optionalItem);
        //findbyid

        OngoingStubbing<List<Item>> itemTest;
        itemTest = when(itemRepository.findByName("ItemTest")).thenReturn(item_list);
        //findbyname

        OngoingStubbing<List<Item>> nullItemTest;
        nullItemTest = when(itemRepository.findByName("nullItemTest")).thenReturn(null);
        //findbyname

        OngoingStubbing<List<Item>> differentItemTest;
        differentItemTest = when(itemRepository.findByName("differentItemTest")).thenReturn(item_list);
        //find by name
    }








    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void gettheitemsthatare_valid() throws Exception {


        //response
        final AtomicReference<ResponseEntity<List<Item>>> response;
        response = new AtomicReference<>(itemController.getItems());//get item
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.


        assertEquals(200, response.get().getStatusCodeValue());
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message

        AtomicReference<List<Item>> testItems;//testitems
        testItems = new AtomicReference<>(response.get().getBody());
        assertNotNull(testItems);
        int i=0;
        while (i < 3) {
            assertTrue(testItems.get().contains(item));
            i++;
        }

    }

    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void gettingtheitemwiththehelpofID_valid() throws Exception {

        final AtomicReference<ResponseEntity<Item>> response = new AtomicReference<>(itemController.getItemById((long) 0));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.


        assertNotNull(response);
        assertEquals(200, response.get().getStatusCodeValue());


        AtomicReference<Item> testItems;
        testItems = new AtomicReference<>(response.get().getBody());
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

        // assertNotNull(testItems);
        //        assertEquals(item.getId(), testItems.get().getId());

        assertNotNull(testItems);
        assertEquals(item.getId(), testItems.get().getId());
        assertEquals(item.getName(), testItems.get().getName());
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message

    }

    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void testing_theitem_bytheName_null() throws Exception {
        final ResponseEntity<List<Item>> response = itemController.getItemsByName("nullItemTest");
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

        assertNotNull(response);//response
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void gettheitemswiththehelpof_the_name_invalid() throws Exception {

        final AtomicReference<ResponseEntity<List<Item>>> response;
        response = new AtomicReference<>(itemController.getItemsByName("differentItemTest"));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.


        assertNotNull(response);//response
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message




        assertEquals(200, response.get().getStatusCode().value());
        AtomicReference<List<Item>> itemsByNameBody;
        itemsByNameBody = new AtomicReference<>(response.get().getBody());
        assertNotNull(itemsByNameBody);
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message



        assertEquals(true, itemsByNameBody.get().contains(item));
    }



}