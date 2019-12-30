package com.hmtmcse.archive.common;

import com.hmtmcse.archive.model.ArchiveModel;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class ScanAndAddToArchive extends SimpleFileVisitor<Path> {

    private ArchiveModel archiveModel;

    public ScanAndAddToArchive() {}

    public ScanAndAddToArchive(ArchiveModel archiveModel) {
        this.init(archiveModel);
    }

    public void init(ArchiveModel archiveModel) {
        this.archiveModel = archiveModel;
    }


    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        try {
            return this.archiveModel.addDirectoryEntry(dir, attrs);
        } catch (ArchiveException e) {
            throw new IOException(e.getMessage());
        }
    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        try {
            return this.archiveModel.addFileEntry(file, attrs);
        } catch (ArchiveException e) {
            throw new IOException(e.getMessage());
        }
    }

}
