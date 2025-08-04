package kh.edu.cstad.mbapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="medias")
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false,unique=true,length=100)
    private String name ;

    @Column(nullable=false,length=10)
    private String extension;

    @Column(nullable=false,length=20)
    private String mimeTypeFile;

    @Column(nullable=false)
    private Boolean isDeleted;
}
