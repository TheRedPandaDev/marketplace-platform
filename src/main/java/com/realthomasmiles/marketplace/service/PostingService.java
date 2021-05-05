package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;

import java.util.List;

public interface PostingService {

    List<PostingDto> getAllPostings();

    PostingDto postPosting(PostPostingRequest postPostingRequest);

}
