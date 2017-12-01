package com.dcanaktemur.service;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by dogus on 12/1/17.
 */
public interface IFileService {

    Stream<String> readFile(String path) throws IOException;
}
