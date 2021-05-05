package com.realthomasmiles.marketplace.model.marketplace;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "postings")
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String article;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "location_id")
    private Long locationId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date posted;
    private String name;
    private String description;
    private Long price;

}
