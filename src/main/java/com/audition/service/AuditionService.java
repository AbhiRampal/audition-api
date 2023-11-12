package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class AuditionService {

    @Autowired
    private AuditionIntegrationClient auditionIntegrationClient;


    public List<AuditionPost> getPosts(MultiValueMap<String, String> filters) {
        return auditionIntegrationClient.getPosts(filters);
    }

    public AuditionPost getPostById(final String postId) {
        return auditionIntegrationClient.getPostById(postId);
    }

    public AuditionPost getPostWithComments(final String postId) {
        AuditionPost post = auditionIntegrationClient.getPostById(postId);
        List<AuditionPostComment> comment = auditionIntegrationClient.getCommentsForPost(postId);
        post.setComments(comment);
        return post;
    }

    public AuditionPost getPostWithCommentsPathVar(final String postId) {
        AuditionPost post = auditionIntegrationClient.getPostById(postId);
        List<AuditionPostComment> comment = auditionIntegrationClient.getCommentsForPostPathVar(postId);
        post.setComments(comment);
        return post;
    }

}
