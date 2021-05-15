package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.MakeOfferRequest;
import com.realthomasmiles.marketplace.dto.mapper.OfferMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.OfferDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Offer;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.repository.marketplace.OfferRepository;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
import com.realthomasmiles.marketplace.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getNumberOfOffers() {
        return offerRepository.count();
    }

    @Override
    public List<OfferDto> getOffersByPosting(PostingDto postingDto) {
        Optional<Posting> posting = postingRepository.findById(postingDto.getId());
        if (posting.isPresent()) {
            return offerRepository.findByPostingId(posting.get().getId()).stream()
                    .map(OfferMapper::toOfferDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND, postingDto.getId().toString());
    }

    @Override
    public List<OfferDto> getOffersByUser(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            return offerRepository.findByAuthorId(user.get().getId()).stream()
                    .map(OfferMapper::toOfferDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public List<OfferDto> getOffersByPostingAndUser(PostingDto postingDto, UserDto userDto) {
        Optional<Posting> posting = postingRepository.findById(postingDto.getId());
        if (posting.isPresent()) {
            Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
            if (user.isPresent()) {
                return offerRepository.findByAuthorId(user.get().getId()).stream()
                        .map(OfferMapper::toOfferDto)
                        .collect(Collectors.toList());
            }

            throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
        }

        throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND, postingDto.getId().toString());
    }

    @Override
    public OfferDto makeOffer(MakeOfferRequest makeOfferRequest, UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            Optional<Posting> posting = postingRepository.findById(makeOfferRequest.getPostingId());
            if (posting.isPresent()) {
                if (posting.get().getIsActive()) {
                    if (!posting.get().getAuthor().getId().equals(user.get().getId())) {
                        Offer offer = new Offer()
                                .setAuthor(user.get())
                                .setPosting(posting.get())
                                .setAmount(makeOfferRequest.getAmount())
                                .setOffered(DateUtils.today());

                        offer = offerRepository.save(offer);

                        return OfferMapper.toOfferDto(offer);
                    }

                    throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND,
                            posting.get().getId().toString());
                }

                throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND,
                        posting.get().getId().toString());
            }

            throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND,
                    makeOfferRequest.getPostingId().toString());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }

}
