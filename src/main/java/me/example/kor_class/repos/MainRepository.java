package me.example.kor_class.repos;

import me.example.kor_class.entities.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainRepository extends JpaRepository<MainEntity, Integer> {
}
