package com.example.classservice;

import static org.junit.Assert.assertTrue;

import org.hamcrest.CoreMatchers;
import org.joda.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.classservice.model.Course;
import com.example.classservice.repository.CourseRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ClassServiceCourseIntegrationTest extends AbstractContainerBaseTest{
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void retrieveAllCourse() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Course test1 = new Course(1, "Data Mining", now.toDate(), now.toDate(), "http://bakpao.com", "Data Mining Course", null, null, null);
        Course test2 = new Course(2, "Data Preprocessing", now.toDate(), now.toDate(), "http://bakpao.com", "Data Preprocessing Course", null, null, null);
        courseRepository.save(test1);
        courseRepository.save(test2);

        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.get("/course").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void retrieveOneCourse() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Course test1 = new Course(1, "Data Mining", now.toDate(), now.toDate(), "http://bakpao.com", "Data Mining Course", null, null, null);
        courseRepository.save(test1);
        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.get("/course/1").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.course_name").value("Data Mining"));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.course_link").value("http://bakpao.com"));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.details").value("Data Mining Course"));

        assertTrue(courseRepository.findAll().size() == 1);
    }

    @Test
    public void deleteCourse() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        Course test1 = new Course(1, "Data Mining", now.toDate(), now.toDate(), "http://bakpao.com", "Data Mining Course", null, null, null);
        Course test2 = new Course(2, "Data Preprocessing", now.toDate(), now.toDate(), "http://bakpao.com", "Data Preprocessing Course", null, null, null);
        courseRepository.save(test1);
        courseRepository.save(test2);

        RequestBuilder requestBuilderGET = MockMvcRequestBuilders.delete("/course/2").accept(MediaType.APPLICATION_JSON);
        ResultActions response = mockMvc.perform(requestBuilderGET);
        response.andExpect(MockMvcResultMatchers.status().isOk());
        
        assertTrue(courseRepository.findAll().size() == 1);
    }

}
