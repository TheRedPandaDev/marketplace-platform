package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.PostPostingRequest;
import com.realthomasmiles.marketplace.dto.mapper.PostingMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.util.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PostingServiceImpl implements PostingService {

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Set<PostingDto> getAllPostings() {
        return StreamSupport.stream(postingRepository.findAll().spliterator(), false)
                .map(posting -> modelMapper.map(posting, PostingDto.class))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public PostingDto postPosting(PostPostingRequest postPostingRequest) {
        Posting posting = new Posting()
                .setArticle("article")
                .setIsActive(true)
                .setCategoryId(postPostingRequest.getCategoryId())
                .setAuthorId(0L)
                .setLocationId(postPostingRequest.getLocationId())
                .setPosted(DateUtils.today())
                .setName(postPostingRequest.getName())
                .setDescription(postPostingRequest.getDescription())
                .setPrice(postPostingRequest.getPrice());

        posting = postingRepository.save(posting);

        return PostingMapper.toPostingDto(posting);
    }

}
