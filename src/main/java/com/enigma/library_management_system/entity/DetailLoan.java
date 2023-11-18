package com.enigma.library_management_system.entity;

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
@Table(name = "t_detail_loan")
public class DetailLoan {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;
    @ManyToOne
    @JoinColumn(name = "book_copy_id")
    private BookCopies bookCopies;
}
