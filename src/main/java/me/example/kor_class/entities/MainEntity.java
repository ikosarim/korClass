package me.example.kor_class.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "mainentity")
@Getter
@Setter
public class MainEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "main_generator")
    @SequenceGenerator(name = "main_generator")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DictEntity dictEntity;

    public MainEntity() {
    }

    @Builder
    public MainEntity(String name, double value, DictEntity dictEntity) {
        this.name = name;
        this.value = value;
        this.dictEntity = dictEntity;
    }
}
