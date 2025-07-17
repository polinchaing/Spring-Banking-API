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

    @Column(nullable = false)
    private String accName;

    @Column(nullable = false)
    private String accNo;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal overLimit;

    @Column(nullable = false,length = 10)
    private String accCurrency;

    @Column(nullable = false)
    private Boolean isHide;

    @Column(nullable=false)
    private Boolean isDeleted;

    @ManyToOne(optional = false)
    @JoinColumn(name= "cust_id",referencedColumnName="id")
    private Customer customer;

    @ManyToOne(optional=false)
    @JoinColumn(name = "acc_type", nullable = false)
    private AccountType accountType;

    @OneToMany(mappedBy = "sender")
    private List<Transaction> transactions;
}
