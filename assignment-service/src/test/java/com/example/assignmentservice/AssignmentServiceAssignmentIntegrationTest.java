package com.example.assignmentservice;


import static org.junit.Assert.assertTrue;

import org.hamcrest.CoreMatchers;
import org.joda.time.LocalDateTime;
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

import com.example.assignmentservice.model.Assignment;
import com.example.assignmentservice.repository.AssignmentRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentServiceAssignmentIntegrationTest extends AbstractContainerBaseTest{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Test
    public void retrieveDetailsForAssignment() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Assignment test1 = new Assignment(1,1, "Data Mining", "Assignment for Data Mining",now.toDate());

        assignmentRepository.save(test1);

        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.get("/assignment/1").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.course_id_assignment").value(1));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.assignment_name").value("Data Mining"));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Assignment for Data Mining"));
    }

    @Test
    public void retrieveAllAssignment() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Assignment test1 = new Assignment(1,1, "Data Mining", "Assignment for Data Mining",now.toDate());
        Assignment test2 = new Assignment(2,1, "Data Preprocessing", "Assignment for Data Preprocessing",now.toDate());
        
        assignmentRepository.save(test1);
        assignmentRepository.save(test2);

        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.get("/assignment").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void deleteAssignment() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Assignment test1 = new Assignment(1,1, "Data Mining", "Assignment for Data Mining",now.toDate());
        Assignment test2 = new Assignment(2,1, "Data Preprocessing", "Assignment for Data Preprocessing",now.toDate());
        
        assignmentRepository.save(test1);
        assignmentRepository.save(test2);

        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.delete("/assignment/2").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        
        assertTrue(assignmentRepository.findAll().size() == 1);
    }
}
