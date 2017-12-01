package com.dcanaktemur.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dogus on 12/1/17.
 */
@Service
public class FileService implements IFileService {

    @Override
    public List<String> readFile(InputStream stream) throws IOException {

        List<String>  linesForFile = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        while(reader.ready()){
            String line = reader.readLine();
            linesForFile.add(line);
        }


        return linesForFile;



    }
}
