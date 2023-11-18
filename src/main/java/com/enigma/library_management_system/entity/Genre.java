package com.enigma.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_genre")
@Builder
public class Genre {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "bookGenre")
    @JsonBackReference
    private List<Book> Books;
}
