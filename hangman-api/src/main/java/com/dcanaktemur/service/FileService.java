package com.dcanaktemur.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by dogus on 12/1/17.
 */
@Service
public class FileService implements IFileService {

    @Override
    public Stream<String> readFile(String path) throws IOException {

        Stream<String> linesForFile = Files.lines(Paths.get(path));

        return linesForFile;
    }
}
