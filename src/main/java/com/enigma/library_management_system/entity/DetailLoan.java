package com.enigma.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_detail_loan")
@Builder
public class DetailLoan {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    @JsonBackReference
    private Loan loan;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_copy_id")
    @JsonBackReference
    private BookCopies bookCopies;
}
