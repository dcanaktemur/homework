package com.dcanaktemur.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dogus on 12/1/17.
 */
public interface IFileService {

    List<String> readFile(InputStream path) throws IOException;
}
