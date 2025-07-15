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
@Table(name ="customer_segments")
public class CustomerSegment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,unique=true,length=100)
    private String segment;

    private String Description;

    private BigDecimal overLimit;

    @Column(nullable=false)
    private Boolean isDeleted;

    @OneToMany(mappedBy="customerSegment")
    List<Customer> customer;
}
