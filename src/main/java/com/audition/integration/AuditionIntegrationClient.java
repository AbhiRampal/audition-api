package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuditionIntegrationClient {


    @Autowired
    private RestTemplate restTemplate;

    //TODO:Abhi: may be move this field to a properties file?

    public static String getPostsURL;

    public static String getCommentsPathVarURL;

    public static String getCommentsQueryParamURL;

    @Value("${endpoint.get.posts}")
    public void setGetPostsURL(String getPostsURL) {
        this.getPostsURL = getPostsURL;
    }

    @Value("${endpoint.get.comments.pathVar}")
    public void setGetCommentsPathVarURL(String getCommentsPathVarURL) {
        this.getCommentsPathVarURL = getCommentsPathVarURL;
    }

    @Value("${endpoint.get.comments.queryParam}")
    public void setGetCommentsQueryParamURL(String getCommentsQueryParamURL) {
        this.getCommentsQueryParamURL = getCommentsQueryParamURL;
    }

    public List<AuditionPost> getPosts(MultiValueMap<String, String> filters) {
        // TODO make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getPostsURL);
        if (filters != null && filters.isEmpty()) {
            for (String key : filters.keySet()) {
                List<String> values = filters.get(key);
                if (values != null) {
                    for (String value : values) {
                        builder.queryParam(key, value);
                    }
                }
            }
        }

        AuditionPost[] posts = restTemplate.getForObject(builder.build().encode().toUri(), AuditionPost[].class);

        return Arrays.asList(posts);
    }

    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getPostsURL)
                .queryParam("id", id);

            return restTemplate.getForObject(builder.build().encode().toUri(), AuditionPost[].class)[0];
        } catch (final RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException(
                    "Cannot find a Post with id " + id + ". Error: " + e.getResponseBodyAsString(),
                    "Resource Not Found", 404);
            } else {
                throw new SystemException(
                    "Error occurred while fetching Post with id " + id + ". Error: " + e.getResponseBodyAsString());
            }
        }
    }

    public List<AuditionPostComment> getCommentsForPost(final String postId) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommentsQueryParamURL)
                .queryParam("postId", postId);

            AuditionPostComment[] comments = restTemplate.getForObject(builder.build().encode().toUri(),
                AuditionPostComment[].class);
            return Arrays.asList(comments);
        } catch (final RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException(
                    "Cannot find a Post with id " + postId + ". Error: " + e.getResponseBodyAsString(),
                    "Resource Not Found", 404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException(
                    "Error occurred while fetching Post with id " + postId + ". Error: " + e.getResponseBodyAsString());
            }
        }
    }


    public List<AuditionPostComment> getCommentsForPostPathVar(final String postId) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommentsPathVarURL)
                .queryParam("postId", postId);

            AuditionPostComment[] comments = restTemplate.getForObject(builder.build().encode().toUri(),
                AuditionPostComment[].class);
            return Arrays.asList(comments);
        } catch (final RestClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException(
                    "Cannot find a Post with id " + postId + ". Error: " + e.getResponseBodyAsString(),
                    "Resource Not Found", 404);
            } else {
                // TODO Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException(
                    "Error occurred while fetching Post with id " + postId + ". Error: " + e.getResponseBodyAsString());
            }
        }
    }
    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
}
