package com.hmtmcse.archive.common;

import com.hmtmcse.archive.data.ArchiveInput;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ArchiveCommon {

    private ArchiveInput archiveInput;
    public Path source;
    public Path destination;

    public ArchiveCommon() {}

    public ArchiveCommon(ArchiveInput archiveInput) {
        this.processInput(archiveInput);
    }

    public void processInput(ArchiveInput archiveInput) {
        this.archiveInput = archiveInput;
        this.source = Paths.get(archiveInput.source);
        this.destination = Paths.get(archiveInput.destination);
    }

    public String getEntryName(Path path) {
        return source.relativize(path).toString();
    }

    public InputStream getInputStream(Path path) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        return new BufferedInputStream(fileInputStream);
    }

}
