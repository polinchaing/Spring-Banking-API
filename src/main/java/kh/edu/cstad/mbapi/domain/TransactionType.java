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
@Table(name="transaction_types")
public class TransactionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,unique=true)
    private String transactionType; //eg PAYMENT , TRANSFER , ONLINE_PAYMENT

    @OneToMany(mappedBy = "transactionType")
    private List<Transaction> transaction;
}
