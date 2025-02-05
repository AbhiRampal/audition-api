package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionPost;
import com.audition.model.AuditionPostComment;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuditionIntegrationClient {


    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(AuditionIntegrationClient.class);

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
        if (filters != null && !filters.isEmpty()) {
            for (String key : filters.keySet()) {
                List<String> values = filters.get(key);
                if (values != null) {
                    for (String value : values) {
                        builder.queryParam(key, value);
                    }
                }
            }
        }

        try {
            ResponseEntity<List<AuditionPost>> responseEntity = restTemplate.exchange(builder.build().encode().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AuditionPost>>() {
                });

            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // log the error details
            LOG.error("Error occurred while fetching Posts. Error: {}", e.getResponseBodyAsString(), e);

            throw new SystemException(
                "Error occurred while fetching Posts. Error: " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            // log the error details
            LOG.error("Error occurred while fetching Posts. Error: {}", e.getMessage(), e);

            throw new SystemException(
                "Error occurred while fetching Posts. Error: " + e.getMessage());
        }
    }


    public AuditionPost getPostById(final String id) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getPostsURL)
                .queryParam("id", id);

            ResponseEntity<AuditionPost[]> responseEntity = restTemplate.getForEntity(builder.build().encode().toUri(),
                AuditionPost[].class);
            AuditionPost[] posts = responseEntity.getBody();

            if (posts != null && posts.length > 0) {
                return posts[0];
            } else {
                throw new SystemException(
                    "Cannot find a Post with id " + id,
                    "Resource Not Found", 404);
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", id, e.getResponseBodyAsString(), e);

            throw new SystemException(
                "Error occurred while fetching Post with id " + id + ". Error: " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", id, e.getMessage(), e);

            throw new SystemException(
                "Error occurred while fetching Post with id " + id + ". Error: " + e.getMessage());
        }
    }


    public List<AuditionPostComment> getCommentsForPost(final String postId) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommentsQueryParamURL)
                .queryParam("postId", postId);

            ResponseEntity<List<AuditionPostComment>> responseEntity = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AuditionPostComment>>() {
                });

            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", postId,
                e.getResponseBodyAsString(), e);

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException(
                    "Cannot find a Post with id " + postId + ". Error: " + e.getResponseBodyAsString(),
                    "Resource Not Found", 404);
            } else {
                throw new SystemException(
                    "Error occurred while fetching Post with id " + postId + ". Error: " + e.getResponseBodyAsString());
            }
        } catch (RestClientException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", postId, e.getMessage(), e);

            throw new SystemException(
                "Error occurred while fetching Post with id " + postId + ". Error: " + e.getMessage());
        }
    }


    public List<AuditionPostComment> getCommentsForPostPathVar(final String postId) {
        // TODO get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getCommentsPathVarURL)
                .queryParam("postId", postId);

            ResponseEntity<List<AuditionPostComment>> responseEntity = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<AuditionPostComment>>() {
                });

            return responseEntity.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", postId, e.getResponseBodyAsString(),
                e);

            throw new SystemException(
                "Error occurred while fetching Post with id " + postId + ". Error: " + e.getResponseBodyAsString());
        } catch (RestClientException e) {
            // log the error details
            LOG.error("Error occurred while fetching Post with id {}. Error: {}", postId, e.getMessage(), e);

            throw new SystemException(
                "Error occurred while fetching Post with id " + postId + ". Error: " + e.getMessage());
        }
    }

    // TODO Write a method GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.

    // TODO write a method. GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    // The comments are a separate list that needs to be returned to the API consumers. Hint: this is not part of the AuditionPost pojo.
}
