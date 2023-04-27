package com.xzx.shippingapplication.controller;

import com.xzx.shippingapplication.ShippingApplicationTests;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


/**
 * <Description> UserAccountControllerTests
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.controller
 */
@Slf4j
public class UserAccountControllerTests extends ShippingApplicationTests {

    @Autowired
    private UserAccountController userAccountController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userAccountController).build();
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/login")
                .accept(MediaType.APPLICATION_JSON)
                .param("email", "20251079@bjtu.edu.cn")
                .param("password","123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testSendCode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/get-register-code")
                .accept(MediaType.APPLICATION_JSON).param("email", "20251079@bjtu.edu.cn"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testRegister() throws Exception {
        String userPack = "{\"email\":\"20251079@bjtu.edu.cn\",\"password\":\"123\",\"username\":\"hzl\",\"identity\":1,\"code\":\"123456\"}";

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/register").content(userPack).contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }


}
