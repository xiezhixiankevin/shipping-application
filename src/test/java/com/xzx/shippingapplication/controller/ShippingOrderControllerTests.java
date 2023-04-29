package com.xzx.shippingapplication.controller;

import com.alibaba.fastjson.JSONObject;
import com.xzx.shippingapplication.ShippingApplicationTests;
import com.xzx.shippingapplication.controller.order.ShippingOrderController;
import com.xzx.shippingapplication.pojo.LogisticsRecord;
import com.xzx.shippingapplication.pojo.ShippingOrder;
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

import java.util.Date;

/**
 * <Description> ShippingOrderControllerTests
 *
 * @author 26802
 * @version 1.0
 * @see com.xzx.shippingapplication.controller
 */
@Slf4j
public class ShippingOrderControllerTests extends ShippingApplicationTests {

    @Autowired
    private ShippingOrderController shippingOrderController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(shippingOrderController).build();
    }

    @Test
    public void testCreate() throws Exception {

        ShippingOrder shippingOrder = new ShippingOrder();
        shippingOrder.setEstimateCapacity(20.3);
        shippingOrder.setCargoInfo("cargoinfo");
        shippingOrder.setCargoName("name");
        shippingOrder.setConsumerId(1);
        shippingOrder.setCargoWeight(11.2);
        shippingOrder.setEstimateDistance(11.2);
        shippingOrder.setId(1);
        shippingOrder.setLatestDeliveryTime(new Date());
        shippingOrder.setCreateTimestamp(new Date());
        shippingOrder.setUpdateTimestamp(new Date());
        shippingOrder.setOrderSenderCharge(118.0);
        shippingOrder.setSenderAddress("北京");
        shippingOrder.setSenderName("xzx");
        shippingOrder.setSenderPhoneNumber("123124124");
        shippingOrder.setReceiverAddress("上海");
        shippingOrder.setReceiverName("hzl");
        shippingOrder.setReceiverPhoneNumber("123123123");
//        shippingOrder.setRefrigerated(false);
        shippingOrder.setUrgentLevel(2);

        shippingOrder.setProviderId(1);

        String requestJson = JSONObject.toJSONString(shippingOrder);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/order/create").content(requestJson).contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testAddLogisticsRecordById() throws Exception {

        LogisticsRecord logisticsRecord = new LogisticsRecord();
        logisticsRecord.setOrderId(8);
        logisticsRecord.setState(3);
        logisticsRecord.setContent("测试123");
        String requestJson = JSONObject.toJSONString(logisticsRecord);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post("/order/add-logistics-record-by-id").content(requestJson).contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testListLogisticsRecord() throws Exception {


        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/order/list-logistics-record?orderId="+"8")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

}
