package me.example.kor_class.repos;

import me.example.kor_class.entities.DictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictRepository extends JpaRepository<DictEntity, Integer> {

    DictEntity findDictEntityByStatusEquals(String status);
}
