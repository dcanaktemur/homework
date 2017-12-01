package com.dcanaktemur;

import com.dcanaktemur.service.FileService;
import com.dcanaktemur.service.IFileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dogus on 12/1/17.
 */
@RunWith(SpringRunner.class)
public class FileServiceTests {

    @TestConfiguration
    static class FileServiceTestContextConfiguration{
        @Bean
        public IFileService fileService(){
            return new FileService();
        }
    }

    @Autowired
    IFileService fileService;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    public void shouldReturnFileLinesWhenReadFileWithGivenPath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:words.txt");
        File wordsAsFile = resource.getFile();
        List<String> fileLines = fileService.readFile(wordsAsFile.getPath()).collect(Collectors.toList());

        Assert.assertNotNull(fileLines);
    }

}
