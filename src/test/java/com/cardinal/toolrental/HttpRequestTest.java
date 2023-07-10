package com.cardinal.toolrental;

import com.cardinal.toolrental.dto.RentalRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldReturnSomeTools() throws Exception {
        this.mockMvc.perform(get("/tools")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void test1() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("JAKR", 5, 101, LocalDate.of(2015, 9, 3));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(req)))
                        .andDo(print())
                        .andExpect(status().isBadRequest())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isNotNull();
        assertThat(content).contains("Please give a discount between 0-100");
    }

    @Test
    void test2() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dueDate").value("2020-07-05"))
                .andExpect(jsonPath("$.chargeDays").value(2))
                .andExpect(jsonPath("$.preDiscountCharge").value(3.98))
                .andExpect(jsonPath("$.discountAmount").value(0.4))
                .andExpect(jsonPath("$.finalAmount").value(3.58))
                .andReturn();
    }

    @Test
    void test3() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dueDate").value("2015-07-07"))
                .andExpect(jsonPath("$.chargeDays").value(3))
                .andExpect(jsonPath("$.preDiscountCharge").value(4.47))
                .andExpect(jsonPath("$.discountAmount").value(1.12))
                .andExpect(jsonPath("$.finalAmount").value(3.35))
                .andReturn();
    }

    @Test
    void test4() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dueDate").value("2015-09-09"))
                .andExpect(jsonPath("$.chargeDays").value(3))
                .andExpect(jsonPath("$.preDiscountCharge").value(8.97))
                .andExpect(jsonPath("$.discountAmount").value(0))
                .andExpect(jsonPath("$.finalAmount").value(8.97))
                .andReturn();
    }

    @Test
    void test5() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dueDate").value("2015-07-11"))
                .andExpect(jsonPath("$.chargeDays").value(6))
                .andExpect(jsonPath("$.preDiscountCharge").value(17.94))
                .andExpect(jsonPath("$.discountAmount").value(0))
                .andExpect(jsonPath("$.finalAmount").value(17.94))
                .andReturn();
    }

    @Test
    void test6() throws Exception {
        RentalRequestDTO req = new RentalRequestDTO("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        mapper.registerModule(new JavaTimeModule());

        MvcResult result = mockMvc.perform(post("/rental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(req)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dueDate").value("2020-07-06"))
                .andExpect(jsonPath("$.chargeDays").value(1))
                .andExpect(jsonPath("$.preDiscountCharge").value(2.99))
                .andExpect(jsonPath("$.discountAmount").value(1.5))
                .andExpect(jsonPath("$.finalAmount").value(1.49))
                .andReturn();
    }
}