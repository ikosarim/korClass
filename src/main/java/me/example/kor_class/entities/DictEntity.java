package me.example.kor_class.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class DictEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "dict_generator")
    @SequenceGenerator(name = "dict_generator")
    @Column(name = "id_status", nullable = false)
    private Integer idStatus;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "dictEntity")
    private Set<MainEntity> mainEntitySet;

    public DictEntity(){}

    @Builder
    public DictEntity(String status) {
        this.status = status;
        this.mainEntitySet = new HashSet<>();
    }
}
