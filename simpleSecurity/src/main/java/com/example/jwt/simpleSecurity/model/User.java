package com.example.jwt.simpleSecurity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@Entity
@AllArgsConstructor
//@NoArgsConstructor
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Document(collection = "user")
public class User {

    public enum Role {USER, ADMIN, USER_MANAGER}

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotEmpty
    @Email
    @Indexed(unique = true)
    private String email;
    @JsonIgnore
    @ToString.Exclude
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;
    private Double minGleePerDay;
    @JsonIgnore
//    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private List<AuthLevel> glee;

    public User() {
        id = new ObjectId().toString();
    }

    public User(String email, String password, Role role, Double minGleePerDay, List<AuthLevel> glee) {
        this.id = new ObjectId().toString();
        this.email = email;
        this.password = password;
        this.role = role;
        this.minGleePerDay = minGleePerDay;
        this.glee = glee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(minGleePerDay, user.minGleePerDay) && Objects.equals(glee, user.glee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role, minGleePerDay, glee);
    }
}
