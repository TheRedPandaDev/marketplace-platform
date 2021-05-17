package com.realthomasmiles.marketplace.model.user;

import com.realthomasmiles.marketplace.model.marketplace.Offer;
import com.realthomasmiles.marketplace.model.marketplace.Posting;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users", indexes = @Index(
        name = "idx_user_email",
        columnList = "email",
        unique = true
))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Offer> offers;
    @OneToMany(mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<Posting> postings;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH})
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Collection<Role> roles;

    public String getFullName() {
        return firstName != null ? firstName.concat(" ").concat(lastName) : "";
    }

}
