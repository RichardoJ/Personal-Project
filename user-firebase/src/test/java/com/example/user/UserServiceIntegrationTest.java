package com.example.user;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.user.model.Student;
import com.example.user.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceIntegrationTest extends AbstractContainerBaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void retrieveDetailsForStudent() throws Exception {
        Student mockStudent = new Student(1, "Michael3", "Pinarto@gmail.com", "mantabjiwa",
                "jl. cendrawasih 165b, Makassar", 6, null);
        Student mockStudent2 = new Student(2, "Michael4", "Pinarto@gmail.com", "mantabjiwa2",
                "jl. cendrawasih 165b, Makassar", 8, null);

        studentRepository.save(mockStudent);
        studentRepository.save(mockStudent2);
        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.get("/student").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(2)));
    }
}
