package me.example.kor_class.services;

import me.example.kor_class.entities.DictEntity;
import me.example.kor_class.entities.MainEntity;

import java.util.List;

public interface DataService {

    DictEntity findDictRowByStatus(String status);

    boolean checkMainRowNotFoundByNameAndValue(String name, double value);

    void insertDataToDict(List<DictEntity> dictEntityList);

    void insertDataToMain(List<MainEntity> mainEntityList);
}
