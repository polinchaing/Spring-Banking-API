package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column( nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isEnable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns= @JoinColumn(name="user_id" ,referencedColumnName = "id"),
            inverseJoinColumns= @JoinColumn(name = "role_id" ,referencedColumnName = "id")
    )
    private Set<Role> roles;
}
