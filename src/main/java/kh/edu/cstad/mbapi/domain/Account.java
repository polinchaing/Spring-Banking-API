package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountNo;

    private BigDecimal balance;

    private BigDecimal overLimit;

    @Column(nullable=false)
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name= "cust_id",referencedColumnName="id")
    private Customer customer;

    @ManyToOne(optional=false)
    private AccountType accountType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactions;
}
