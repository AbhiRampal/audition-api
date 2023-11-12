package com.audition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class AuditionIntegrationClientTest {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetPosts() {
        mockServer.expect(requestTo(AuditionIntegrationClient.getPostsURL))
            .andRespond(withSuccess("[{\"id\":1,\"title\":\"test title\",\"body\":\"test body\"}]",
                MediaType.APPLICATION_JSON));

        List<AuditionPost> posts = auditionIntegrationClient.getPosts(null);

        assertNotNull(posts);
        assertTrue(posts.size() > 0);
        assertEquals(1, posts.get(0).getId());
        assertEquals("test title", posts.get(0).getTitle());
        assertEquals("test body", posts.get(0).getBody());
    }

    @Test
    public void testGetPostById() {
        mockServer.expect(requestTo(AuditionIntegrationClient.getPostsURL + "?id=1"))
            .andRespond(withSuccess("[{\"id\":1,\"title\":\"test title\",\"body\":\"test body\"}]",
                MediaType.APPLICATION_JSON));

        AuditionPost post = auditionIntegrationClient.getPostById("1");

        assertNotNull(post);
        assertEquals(1, post.getId());
        assertEquals("test title", post.getTitle());
        assertEquals("test body", post.getBody());
    }

    @Test
    public void testGetCommentsForPost() {
        mockServer.expect(requestTo(AuditionIntegrationClient.getCommentsQueryParamURL + "?postId=1"))
            .andRespond(withSuccess(
                "[{\"postId\":1,\"id\":1,\"name\":\"test name\",\"email\":\"test email\",\"body\":\"test body\"}]",
                MediaType.APPLICATION_JSON));

        List<AuditionPostComment> comments = auditionIntegrationClient.getCommentsForPost("1");

        assertNotNull(comments);
        assertTrue(comments.size() > 0);
        assertEquals(1, comments.get(0).getPostId());
        assertEquals(1, comments.get(0).getId());
        assertEquals("test name", comments.get(0).getName());
        assertEquals("test email", comments.get(0).getEmail());
        assertEquals("test body", comments.get(0).getBody());
    }

    @Test
    public void testGetCommentsForPost_PathVar() {
        mockServer.expect(requestTo(AuditionIntegrationClient.getCommentsQueryParamURL + "?postId=1"))
            .andRespond(withSuccess(
                "[{\"postId\":1,\"id\":1,\"name\":\"test name\",\"email\":\"test email\",\"body\":\"test body\"}]",
                MediaType.APPLICATION_JSON));

        List<AuditionPostComment> comments = auditionIntegrationClient.getCommentsForPost("1");

        assertNotNull(comments);
        assertTrue(comments.size() > 0);
        assertEquals(1, comments.get(0).getPostId());
        assertEquals(1, comments.get(0).getId());
        assertEquals("test name", comments.get(0).getName());
        assertEquals("test email", comments.get(0).getEmail());
        assertEquals("test body", comments.get(0).getBody());
    }

    @Test
    public void testGetCommentsForPost_QueryParam() {
        mockServer.expect(requestTo(AuditionIntegrationClient.getCommentsQueryParamURL + "?postId=1"))
            .andRespond(withSuccess(
                "[{\"postId\":1,\"id\":1,\"name\":\"test name\",\"email\":\"test email\",\"body\":\"test body\"}]",
                MediaType.APPLICATION_JSON));

        List<AuditionPostComment> comments = auditionIntegrationClient.getCommentsForPost("1");

        assertNotNull(comments);
        assertTrue(comments.size() > 0);
        assertEquals(1, comments.get(0).getPostId());
        assertEquals(1, comments.get(0).getId());
        assertEquals("test name", comments.get(0).getName());
        assertEquals("test email", comments.get(0).getEmail());
        assertEquals("test body", comments.get(0).getBody());
    }
}

