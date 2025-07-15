package kh.edu.cstad.mbapi.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="account_types")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,unique=true,length=100)
    private String accountType; //eg PAYROLL, SAVING, CREDIT, DEBIT, JUNIOR,

    @Column(nullable=false)
    private Boolean isDeleted;

    @OneToMany(mappedBy="accountType")
    @JoinColumn(nullable=false)
    private List<Account> account;
}
