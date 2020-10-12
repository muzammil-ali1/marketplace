package com.crypto.marketplace.functional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.crypto.marketplace.MarketplaceApplication;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = SpringBootDerbyApplication.class)
//@WebAppConfiguration

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = MarketplaceApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:test.properties")
public abstract class AbstractTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    protected <T> List<T> listFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType listType = mapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, clazz);
        return mapper.readValue(json, listType);
    }

}