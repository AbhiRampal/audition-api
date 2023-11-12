package com.audition.web;

import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import com.audition.validation.NumericString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AuditionController {

    @Autowired
    AuditionService auditionService;

    // TODO Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(@RequestParam MultiValueMap<String, String> filters) {

        // TODO Add logic that filters response data based on the query param

        return auditionService.getPosts(filters);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id") @NumericString final String postId) {
        final AuditionPost auditionPosts = auditionService.getPostById(postId);

        // TODO Add input validation

        return auditionPosts;
    }

    @RequestMapping(value = "/posts/{postId}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostWithComments(@NumericString @PathVariable("postId") String postId) {
        final AuditionPost auditionPosts = auditionService.getPostWithComments(postId);
        // TODO Add input validation

        return auditionPosts;
    }

    // TODO Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/

    @RequestMapping(value = "/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostWithCommentsPathVar(@NumericString @RequestParam("postId") String postId) {
        final AuditionPost auditionPosts = auditionService.getPostWithCommentsPathVar(postId);
        // TODO Add input validation

        return auditionPosts;
    }

}
