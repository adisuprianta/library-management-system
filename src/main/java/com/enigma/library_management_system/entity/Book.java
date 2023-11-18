package com.enigma.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_book")
@Builder
public class Book {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToMany
    @JoinTable(
            name ="book_genre",
            joinColumns =@JoinColumn(name = "book_id"),
            inverseJoinColumns =@JoinColumn(name = "genre_id")
    )
    @JsonManagedReference
    private List<Genre> bookGenre;

    @Column(unique = true)
    private String title;

    @Column
    private String author;

    @Column(name = "publishe_date")
    private LocalDate publishedDate;

}
