package com.example.jwt.simpleSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@Entity
@AllArgsConstructor
//@NoArgsConstructor
public class AuthLevel {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @NotEmpty
    private String text;
    @NotNull
    private Double value;
//    @NotNull
////    @ManyToOne(fetch = FetchType.EAGER)
//    @ToString.Exclude
//    private User user;

    public AuthLevel() {
        id = new ObjectId().toString();
    }

    public AuthLevel(LocalDate date, LocalTime time, String text, Double value) {
        this.id = new ObjectId().toString();
        this.date = date;
        this.time = time;
        this.text = text;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthLevel authLevel = (AuthLevel) o;
        return Objects.equals(id, authLevel.id) && Objects.equals(date, authLevel.date) && Objects.equals(time, authLevel.time) && Objects.equals(text, authLevel.text) && Objects.equals(value, authLevel.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, time, text, value);
    }
}
