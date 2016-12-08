package ru.roox.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.roox.rest.model.Customer;
import ru.roox.rest.model.PartnerMapping;
import ru.roox.rest.service.CustomerService;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by DGolub on 08.12.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class CustomerControllerTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

    private MockMvc mockMvc;

    private static final int existedCustomerId = 1;
    private static final int notExistedCustomerId = 2;
    private static final int existedMappingId = 5;
    private static final int notExistedMappingId = 6;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        when(customerService.isCustomerExist(existedCustomerId)).thenReturn(true);
        when(customerService.isCustomerExist(notExistedCustomerId)).thenReturn(false);
    }

    @Test
    public void testGetCustomerOk() throws Exception {
        when(customerService.getCustomer(existedCustomerId)).thenReturn(new Customer());
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}", existedCustomerId)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetCustomerNotExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}", notExistedCustomerId)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCustomerMappingsOk() throws Exception {
        when(customerService.getAllCustomerPartnerMappings(existedCustomerId)).thenReturn(Arrays.asList(new PartnerMapping()));
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}/mappings", existedCustomerId)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetAllCustomerMappingsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}/mappings", notExistedCustomerId)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetCustomerMappingByIdOk() throws Exception {
        when(customerService.getCustomerPartnerMappingById(existedCustomerId, existedMappingId)).thenReturn(new PartnerMapping());
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}/mappings/{mapping_id}", existedCustomerId, existedMappingId)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void testGetCustomerMappingByIdCustomerNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}/mappings/{mapping_id}", notExistedCustomerId, existedMappingId)).andExpect(status().isNotFound());
    }

    @Test
    public void testGetCustomerMappingByIdMappingNotFound() throws Exception {
        when(customerService.getCustomerPartnerMappingById(existedCustomerId, notExistedMappingId)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/customerapi/{customer_id}/mappings/{mapping_id}", existedCustomerId, notExistedMappingId)).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCustomerMappingNotValidAvatarUrl() throws Exception {
        PartnerMapping mapping = new PartnerMapping(1, 1, "we", "we", "we", "http://not_valid_url");
        when(customerService.createCustomerPartnerMapping(existedCustomerId, mapping)).thenReturn(mapping);
        mockMvc.perform(MockMvcRequestBuilders.post("/customerapi/{customer_id}/mappings", existedCustomerId).contentType(MediaType.APPLICATION_JSON).content(asJsonString(mapping))).andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateCustomerMappingCustomerNotFound() throws Exception {
        PartnerMapping mapping = new PartnerMapping(1, 1, "we", "we", "we", "http://test.com/test.jpg");
        mockMvc.perform(MockMvcRequestBuilders.post("/customerapi/{customer_id}/mappings", notExistedCustomerId).contentType(MediaType.APPLICATION_JSON).content(asJsonString(mapping))).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomerMappingCustomerNotFound() throws Exception {
        PartnerMapping mapping = new PartnerMapping(1, 1, "we", "we", "we", "http://test.com/test.jpg");
        mockMvc.perform(MockMvcRequestBuilders.put("/customerapi/{customer_id}/mappings/{mapping_id}", notExistedCustomerId, existedMappingId).contentType(MediaType.APPLICATION_JSON).content(asJsonString(mapping))).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomerMappingMappingNotFound() throws Exception {
        PartnerMapping mapping = new PartnerMapping(1, 1, "we", "we", "we", "http://test.com/test.jpg");
        mockMvc.perform(MockMvcRequestBuilders.put("/customerapi/{customer_id}/mappings/{mapping_id}", existedCustomerId, notExistedMappingId).contentType(MediaType.APPLICATION_JSON).content(asJsonString(mapping))).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomerMappingCustomerNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customerapi/{customer_id}/mappings/{mapping_id}", notExistedCustomerId, existedMappingId)).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomerMappingMappingNotFound() throws Exception {
        when(customerService.removeCustomerPartnerMapping(existedCustomerId, notExistedMappingId)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/customerapi/{customer_id}/mappings/{mapping_id}", existedCustomerId, notExistedCustomerId)).andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCustomerMappingMappingOk() throws Exception {
        when(customerService.removeCustomerPartnerMapping(existedCustomerId, existedMappingId)).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/customerapi/{customer_id}/mappings/{mapping_id}", existedCustomerId, existedMappingId)).andExpect(status().isAccepted());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public CustomerService customerService() {
            return mock(CustomerService.class);
        }

        @Bean
        public CustomerController customerController() {
            return new CustomerController();
        }
    }
}
