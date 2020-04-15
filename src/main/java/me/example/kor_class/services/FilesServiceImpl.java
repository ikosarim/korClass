package me.example.kor_class.services;


import me.example.kor_class.entities.DictEntity;
import me.example.kor_class.entities.MainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

@Service
@PropertySource("app.properties")
public class FilesServiceImpl implements FilesService {

    @Autowired
    DataService dataService;

    private Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    @Override
    public List<String> scanPackage() {
        File directory = new File(requireNonNull(env.getProperty("source.dir")));
        String[] files = directory.list((dir, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0) {
            return new ArrayList<>();
        }
        return asList(files);
    }

    @Override
    public void fileProcessing(List<String> fileList) {
        Pattern pattern = Pattern.compile(",");
        File duplicatedFile = new File(env.getProperty("duplicated.path"));
        try {
            duplicatedFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileList.forEach(file -> {
            List<MainEntity> mainEntityList = new ArrayList<>();
            List<DictEntity> dictEntityList = new ArrayList<>();
            String fileToRead = env.getProperty("source.dir") + "/" + file;
            try {
                try (BufferedReader in = new BufferedReader(new FileReader(fileToRead))) {
                    in.lines().skip(1).forEach(line -> {
                        String[] x = pattern.split(line);
                        MainEntity mainEntity = MainEntity.builder()
                                .name(x[1])
                                .value(parseDouble(x[2]))
                                .build();
                        if (dataService.checkMainRowNotFoundByNameAndValue(mainEntity.getName(), mainEntity.getValue())
                                && !collectionContainsRow(mainEntityList, mainEntity.getName(), mainEntity.getValue())) {
                            DictEntity dictEntity = dictEntityList.stream()
                                    .filter(de -> de.getStatus().equals(x[3]))
                                    .findFirst()
                                    .orElse(null);
                            if (dictEntity == null) {
                                dictEntity = dataService.findDictRowByStatus(x[3]);
                            }
                            if (dictEntity == null) {
                                dictEntity = DictEntity.builder().status(x[3]).build();
                                dictEntityList.add(dictEntity);
                            }
                            dictEntity.getMainEntitySet().add(mainEntity);
                            mainEntity.setDictEntity(dictEntity);
                            mainEntityList.add(mainEntity);
                        } else {
                            try (FileWriter fileWriter = new FileWriter(duplicatedFile.getAbsoluteFile())) {
                                fileWriter.write(line);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            new File(fileToRead).renameTo(new File(env.getProperty("processed.dir"), file));
            dataService.insertDataToDict(dictEntityList);
            dataService.insertDataToMain(mainEntityList);
        });
    }

    private boolean collectionContainsRow(List<MainEntity> mainEntityList, String name, double value) {
        for (MainEntity entity : mainEntityList) {
            if (entity.getName().equals(name)
                    && entity.getValue() == value) return true;
        }
        return false;
    }
}
