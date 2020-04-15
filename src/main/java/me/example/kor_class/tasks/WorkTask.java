package me.example.kor_class.tasks;

import me.example.kor_class.services.FilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkTask {

    private static Logger logger = LoggerFactory.getLogger(WorkTask.class);

    @Autowired
    FilesService filesService;

    @Scheduled(fixedDelay = 300000)
    public void work() {
        logger.warn("Рабочий таск работает");
        List<String> files = filesService.scanPackage();
        filesService.fileProcessing(files);
    }
}
