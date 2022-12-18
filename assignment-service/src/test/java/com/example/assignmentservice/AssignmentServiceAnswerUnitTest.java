package com.example.assignmentservice;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.junit.jupiter.MockitoExtension;

import com.example.assignmentservice.model.CourseAnswer;
import com.example.assignmentservice.repository.AnswerRepository;
import com.example.assignmentservice.service.AnswerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceAnswerUnitTest {
    
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    void shouldGetCourseAnswer(){
        List<CourseAnswer> listAllAnswer = new ArrayList<>();
        CourseAnswer test1 = new CourseAnswer(75.0f, 1, 1);
        CourseAnswer test2 = new CourseAnswer(85.0f, 1, 1);
        listAllAnswer.add(test1);
        listAllAnswer.add(test2);

        when(answerRepository.findCourseAnswer(1, 1)).thenReturn(Optional.of(listAllAnswer));

        assertArrayEquals(listAllAnswer.toArray(), answerService.getCourseAnswer(1, 1).toArray());
    }

    @Test
    void shouldNotGetCourseAnswer(){
        when(answerRepository.findCourseAnswer(1, 1)).thenReturn(Optional.empty());

        assertNull(answerService.getCourseAnswer(1, 1));
    }

    @Test
    void shouldGetAverage(){
        List<CourseAnswer> listAllAnswer = new ArrayList<>();
        CourseAnswer test1 = new CourseAnswer(75.0f, 1, 1);
        CourseAnswer test2 = new CourseAnswer(85.0f, 1, 1);
        listAllAnswer.add(test1);
        listAllAnswer.add(test2);

        Float averageFloat = 80.0f;

        assertEquals(averageFloat, answerService.getAverageCourseAnswer(listAllAnswer));
    }

    @Test
    void shouldReturnProfiecient(){
        Float averageFloat = 80.0f;

        assertEquals("Proficient", answerService.checkGoodOrBad(averageFloat));

        Float averageFloat2 = 100.0f;
        assertEquals("Proficient", answerService.checkGoodOrBad(averageFloat2));
    }

    @Test
    void shouldReturnBeginning(){
        Float averageFloat = 79.8f;

        assertEquals("Beginning", answerService.checkGoodOrBad(averageFloat));

        Float averageFloat2 = 65.0f;
        assertEquals("Beginning", answerService.checkGoodOrBad(averageFloat2));
    }

    @Test
    void shouldReturnOrienting(){
        Float averageFloat = 0.0f;

        assertEquals("Orienting", answerService.checkGoodOrBad(averageFloat));

        Float averageFloat2 = 60.0f;
        assertEquals("Orienting", answerService.checkGoodOrBad(averageFloat2));
    }

}
