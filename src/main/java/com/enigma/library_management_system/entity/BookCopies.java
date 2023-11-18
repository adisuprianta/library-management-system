package com.enigma.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_book_copies")
public class BookCopies {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private Book book;
    @Column(name = "availability_status")
    private Boolean availabilityStatus;
}
