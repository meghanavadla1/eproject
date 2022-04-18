package com.example.demo.controllerTest;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.demo.testutils.injectObjects;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestingUserController {
    //TestingUserController class
    //userController
    // userRepository
    // cartRepository
    // bCryptPasswordEncoder
    private UserController userController;

    private UserRepository userRepository;

    {
        userRepository = mock(UserRepository.class);
    }

    private CartRepository cartRepository;

    {
        cartRepository = mock(CartRepository.class);
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    {
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    }




//setup
    @Before
    //used when different test cases share the same logic. The method with the @Before annotation always runs before the execution of each test case
    public void setUp() {
        userController = new UserController();
        injectObjects(userController, "userRepository", userRepository);
        injectObjects(userController, "cartRepository", cartRepository);
        injectObjects(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
//from user
// setid
// setusername
// setpassword
        User user;
        user = new User();
        user.setId(0b0);
        user.setUsername("usernametest");
        user.setPassword("passwordtest");
// from cart
// setid
// setuser
// settotal
// setcart
        AtomicReference<Cart> cart;
        cart = new AtomicReference<>(new Cart());
        cart.get().setId((long) 0b0);
        cart.get().setUser(user);
        user.setCart(cart.get());




        OngoingStubbing<User> usernametest;
        usernametest = when(userRepository.findByUsername("usernametest")).thenReturn(user);
      ////////////////////////////////////////////////////////////////////////////////////////////
        OngoingStubbing<Optional<User>> optionalOngoingStubbing;
        optionalOngoingStubbing = when(userRepository.findById((long) 0)).thenReturn(Optional.of(user));

        OngoingStubbing<User> differentuser;
        differentuser = when(userRepository.findByUsername("differentuser")).thenReturn(null);
    }


    //find_user_with_the_help_of_id
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void find_user_with_the_help_of_id() throws Exception {
//finding the user bu using id number which is assigned
        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.findById((long) 0));
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

       // assertEquals
        assertEquals(200, response.get().getStatusCodeValue());
        AtomicReference<User> actualUser;
        actualUser = new AtomicReference<>(response.get().getBody());
        assertNotNull(actualUser);

        assertEquals(0, actualUser.get().getId());
        assertEquals("passwordtest", actualUser.get().getPassword());
        assertEquals("usernametest", actualUser.get().getUsername());
          //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message

    }










    //searchUserbyUsingUsername
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void searchUserbyUsingUsername() throws Exception {

        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.findByUserName("usernametest"));
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.
         // assertEquals
        assertEquals(200, response.get().getStatusCodeValue());
        User actualUser = response.get().getBody();
        assertNotNull(actualUser);
        /* Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given message */
       // assertEquals
        assertEquals("passwordtest", actualUser.getPassword());
        assertEquals(0, actualUser.getId());
        assertEquals("usernametest", actualUser.getUsername());


    }

    //creatingtheuser_givingvalid_credentials
    @Test
    //test is a method contained in a class which is only used for testing

    public void creatingtheuser_givingvalid_credentials() throws Exception {


        OngoingStubbing<String> stringOngoingStubbing;
        stringOngoingStubbing = when(bCryptPasswordEncoder.encode("passwordtest")).thenReturn("testhashedpassword");
        AtomicReference<CreateUserRequest> createUserRequest;
        createUserRequest = new AtomicReference<>(new CreateUserRequest());
        createUserRequest.get().setUsername("usernametest");
        createUserRequest.get().setPassword("passwordtest");
        createUserRequest.get().setConfirmPassword("passwordtest");
          //set username
        // set password
        // setconfirm password
        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.createUser(createUserRequest.get()));
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.
        // assertEquals

        assertEquals(200, response.get().getStatusCodeValue());
        AtomicReference<User> user;//user
        user = new AtomicReference<>(response.get().getBody());
        assertNotNull(user);//check user
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given messag
        assertEquals(0, user.get().getId());//get id
        assertEquals("usernametest", user.get().getUsername());//getusername
        assertEquals("testhashedpassword", user.get().getPassword());//getpassword
    }



    //creatingthe_user_invalid_toconfirmPassword
    @Test
    //test is a method contained in a class which is only used for testing
    //
    // The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void creatingthe_user_invalid_toconfirmPassword() throws Exception {

        OngoingStubbing<String> stringOngoingStubbing;
        stringOngoingStubbing = when(bCryptPasswordEncoder.encode("passwordtest")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        //object of createUserRequest
        createUserRequest.setUsername("usernametest");//setUsername
        createUserRequest.setPassword("passwordtest");//setPassword
        createUserRequest.setConfirmPassword("wrongpassowrd");//setConfirmPassword
        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.createUser(createUserRequest));
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.


        assertEquals(400, response.get().getStatusCodeValue());
//Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given messag
    }


    //create_user_short_password
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void create_user_short_password() throws Exception {

        OngoingStubbing<String> stringOngoingStubbing;
        stringOngoingStubbing = when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("usernametest");//setUsername
        createUserRequest.setPassword("pwd");//setPassword
        createUserRequest.setConfirmPassword("pwd");//setConfirmPassword
        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.createUser(createUserRequest));
        assertNotNull(response);
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.



        assertEquals(400, response.get().getStatusCodeValue());
//Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given messag
    }








    //testUnavailableUser
    @Test
    //test is a method contained in a class which is only used for testing
//The Test annotation tells JUnit that the public void method to which it is attached can be run as a test case
    public void testUnavailableUser() {
        final AtomicReference<ResponseEntity<User>> response;
        response = new AtomicReference<>(userController.findByUserName("differentuser"));
        //assertNotNull() method means "a passed parameter must not be null ": if it is null then the test case fails.

        assertNotNull(response);//response
        //Asserts that two object arrays are equal. If they are not, an AssertionError is thrown with the given messag
        assertEquals(404, response.get().getStatusCodeValue());
    }
}
