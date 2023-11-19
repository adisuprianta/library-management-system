package com.enigma.library_management_system.entity;

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
@Table(name = "t_loan")
@Builder
public class Loan {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(name = "checkout_date")
    private LocalDate checkoutDate;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "checkin_date")
    private LocalDate checkinDate;

    @OneToMany(mappedBy = "loan",cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<DetailLoan> detailLoans;
}
