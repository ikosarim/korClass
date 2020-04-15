package me.example.kor_class.services;

import java.util.List;

public interface FilesService {

    List<String> scanPackage();

    void fileProcessing(List<String> fileList);
}
