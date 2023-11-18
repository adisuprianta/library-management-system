package com.enigma.library_management_system.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

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
    @Column(unique = true)
    private String title;
    @Column
    private String author;
    @Column
    private String genre;
    @Column(name = "publishe_date")
    private LocalDate publishedDate;

}
