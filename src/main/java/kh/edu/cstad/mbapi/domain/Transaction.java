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
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition="TEXT")
    private String remark;

    @Column(nullable=false)
    private Boolean isDeleted;

    @ManyToOne
    private TransactionType transactionType;

    @ManyToOne(optional=false)
    @JoinColumn(name = "sender", nullable = false)
    private Account sender;

    @ManyToOne(optional=false)
    @JoinColumn(name = "receiver", nullable = false)
    private Account receiver;

}
