package com.audition.web;

import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import com.audition.validation.NumericString;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class AuditionController {

    private final AuditionService auditionService;

    @Autowired
    public AuditionController(AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    @GetMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<AuditionPost> getPosts(
        @RequestParam(required = false) MultiValueMap<String, String> filters) {
        return auditionService.getPosts(filters);
    }

    @GetMapping(value = "/posts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPosts(@PathVariable("id") @NumericString @NotNull final String postId) {
        return auditionService.getPostById(postId);
    }

    @GetMapping(value = "/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostWithComments(
        @PathVariable("postId") @NumericString @NotNull final String postId) {
        return auditionService.getPostWithComments(postId);
    }

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody AuditionPost getPostWithCommentsPathVar(
        @RequestParam("postId") @NumericString @NotNull final String postId) {
        return auditionService.getPostWithCommentsPathVar(postId);
    }
}

