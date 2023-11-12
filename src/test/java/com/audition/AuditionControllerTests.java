package com.audition;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.audition.common.logging.AuditionLogger;
import com.audition.configuration.LoggingInterceptor;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import com.audition.web.AuditionController;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuditionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuditionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditionService auditionService;

    @MockBean
    private LoggingInterceptor loggingInterceptor;

    @MockBean
    private AuditionLogger auditionLogger;

    @BeforeEach
    public void setUp() {
        List<AuditionPost> posts = Arrays.asList(new AuditionPost(), new AuditionPost());
        AuditionPost post = new AuditionPost();

        when(auditionService.getPosts(null)).thenReturn(posts);
        when(auditionService.getPostById("1")).thenReturn(post);
        when(auditionService.getPostWithComments("1")).thenReturn(post);
        when(auditionService.getPostWithCommentsPathVar("1")).thenReturn(post);
    }

    @Test
    public void testGetPosts() throws Exception {
        mockMvc.perform(get("/posts"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetPostsIncorrectEndpoint() throws Exception {
        mockMvc.perform(get("/posts2342"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPostById() throws Exception {
        mockMvc.perform(get("/posts/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetPostWithComments() throws Exception {
        mockMvc.perform(get("/posts/1/comments"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetPostWithCommentsPathVar() throws Exception {
        mockMvc.perform(get("/comments?postId=1"))
            .andExpect(status().isOk());
    }
}
