package me.example.kor_class.services;

import me.example.kor_class.entities.DictEntity;
import me.example.kor_class.entities.MainEntity;
import me.example.kor_class.repos.DictRepository;
import me.example.kor_class.repos.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    MainRepository mainRepository;
    @Autowired
    DictRepository dictRepository;

    @Override
    public DictEntity findDictRowByStatus(String status) {
        return dictRepository.findDictEntityByStatusEquals(status);
    }

    @Override
    public boolean checkMainRowNotFoundByNameAndValue(String name, double value) {
        return mainRepository.findAll()
                .stream()
                .map(main -> Pair.of(main.getName(), main.getValue()))
                .noneMatch(mainPair -> name.equals(mainPair.getFirst()) && value == mainPair.getSecond());
    }

    @Override
    @Transactional
    public void insertDataToDict(List<DictEntity> dictEntityList) {
        dictRepository.saveAll(dictEntityList);
    }

    @Override
    @Transactional
    public void insertDataToMain(List<MainEntity> mainEntityList) {
        mainRepository.saveAll(mainEntityList);
    }
}
