package com.realthomasmiles.marketplace.service;

import com.realthomasmiles.marketplace.controller.v1.request.MakeSaleRequest;
import com.realthomasmiles.marketplace.dto.mapper.SaleMapper;
import com.realthomasmiles.marketplace.dto.model.marketplace.PostingDto;
import com.realthomasmiles.marketplace.dto.model.marketplace.SaleDto;
import com.realthomasmiles.marketplace.dto.model.user.UserDto;
import com.realthomasmiles.marketplace.exception.EntityType;
import com.realthomasmiles.marketplace.exception.ExceptionType;
import com.realthomasmiles.marketplace.exception.MarketPlaceException;
import com.realthomasmiles.marketplace.model.marketplace.Offer;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import com.realthomasmiles.marketplace.model.marketplace.Sale;
import com.realthomasmiles.marketplace.model.user.User;
import com.realthomasmiles.marketplace.repository.marketplace.OfferRepository;
import com.realthomasmiles.marketplace.repository.marketplace.PostingRepository;
import com.realthomasmiles.marketplace.repository.marketplace.SaleRepository;
import com.realthomasmiles.marketplace.repository.user.UserRepository;
import com.realthomasmiles.marketplace.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Override
    public SaleDto getSaleByPosting(PostingDto postingDto) {
        Optional<Posting> posting = postingRepository.findById(postingDto.getId());
        if (posting.isPresent()) {
            if (!posting.get().getIsActive()) {
                Optional<Sale> sale = Optional.ofNullable(saleRepository.findByPostingId(posting.get().getId()));
                if (sale.isPresent()) {
                    return SaleMapper.toSaleDto(sale.get());
                }

                throw exception(EntityType.POSTING, ExceptionType.ENTITY_EXCEPTION,
                        "Inactive posting with code " + postingDto.getId() +
                                " does not have an associated sale entry");
            }

            throw exception(EntityType.POSTING, ExceptionType.ENTITY_EXCEPTION,
                    "Posting with code " + postingDto.getId() + " is still active");
        }

        throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND, postingDto.getId().toString());
    }

    @Override
    public List<SaleDto> getSalesByBuyer(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            return saleRepository.findByBuyerId(user.get().getId()).stream()
                    .map(SaleMapper::toSaleDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public List<SaleDto> getSalesBySeller(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            return saleRepository.findBySellerId(user.get().getId()).stream()
                    .map(SaleMapper::toSaleDto)
                    .collect(Collectors.toList());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public SaleDto makeSale(MakeSaleRequest makeSaleRequest, UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            Optional<Posting> posting = postingRepository.findById(makeSaleRequest.getPostingId());
            if (posting.isPresent()) {
                if (posting.get().getAuthor().getId().equals(user.get().getId())) {
                    if (posting.get().getIsActive()) {
                        Optional<Offer> offer = offerRepository.findById(makeSaleRequest.getOfferId());
                        if (offer.isPresent()) {
                            Sale sale = new Sale()
                                    .setSeller(posting.get().getAuthor())
                                    .setPosting(posting.get())
                                    .setBuyer(offer.get().getAuthor())
                                    .setOffer(offer.get())
                                    .setMade(DateUtils.today());

                            sale = saleRepository.save(sale);

                            posting.get().setIsActive(false);

                            postingRepository.save(posting.get());

                            return SaleMapper.toSaleDto(sale);
                        }

                        throw exception(EntityType.OFFER, ExceptionType.ENTITY_NOT_FOUND,
                                makeSaleRequest.getOfferId().toString());
                    }

                    throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND,
                            posting.get().getId().toString());
                }

                throw exception(EntityType.POSTING, ExceptionType.UNAUTHORIZED_ACCESS_TO_ENTITY,
                        posting.get().getId().toString());
            }

            throw exception(EntityType.POSTING, ExceptionType.ENTITY_NOT_FOUND,
                    makeSaleRequest.getPostingId().toString());
        }

        throw exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return MarketPlaceException.throwException(entityType, exceptionType, args);
    }

}
