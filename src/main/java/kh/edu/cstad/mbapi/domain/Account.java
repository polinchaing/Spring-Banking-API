package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name= "cust_id",referencedColumnName="id")
    private Customer customer;

    @ManyToOne
    private AccountType accountType;
}
