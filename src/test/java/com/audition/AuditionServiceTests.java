package com.audition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import com.audition.service.AuditionService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuditionServiceTests {

    @Mock
    private AuditionIntegrationClient auditionIntegrationClient;

    @InjectMocks
    private AuditionService auditionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        List<AuditionPost> posts = Arrays.asList(new AuditionPost(), new AuditionPost());
        AuditionPost post = new AuditionPost();

        List<AuditionPostComment> postsWithComments = Arrays.asList(new AuditionPostComment(),
            new AuditionPostComment());

        when(auditionIntegrationClient.getPosts(null)).thenReturn(posts);
        when(auditionIntegrationClient.getPostById("1")).thenReturn(post);
        when(auditionIntegrationClient.getCommentsForPost("1")).thenReturn(postsWithComments);
        when(auditionIntegrationClient.getCommentsForPostPathVar("1")).thenReturn(postsWithComments);
    }

    @Test
    public void testGetPosts() {
        List<AuditionPost> posts = auditionService.getPosts(null);

        assertNotNull(posts);
        assertEquals(2, posts.size());
    }

    @Test
    public void testGetPostById() {
        AuditionPost post = auditionService.getPostById("1");

        assertNotNull(post);
        assertNull(post.getComments());
    }

    @Test
    public void testGetPostWithComments() {
        AuditionPost post = auditionService.getPostWithComments("1");

        assertNotNull(post);
        assertEquals(2, post.getComments().size());
    }

    @Test
    public void testGetPostWithCommentsPathVar() {
        AuditionPost post = auditionService.getPostWithCommentsPathVar("1");

        assertNotNull(post);
        assertEquals(2, post.getComments().size());
    }
}
