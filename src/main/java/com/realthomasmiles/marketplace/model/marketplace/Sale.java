package com.realthomasmiles.marketplace.model.marketplace;

import com.realthomasmiles.marketplace.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id", unique = true)
    private Posting posting;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @Temporal(TemporalType.TIMESTAMP)
    private Date made;

}
