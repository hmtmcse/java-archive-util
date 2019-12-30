package com.hmtmcse.archive.archive;

import com.hmtmcse.archive.common.ArchiveException;
import com.hmtmcse.archive.data.ArchiveInput;
import com.hmtmcse.archive.model.ArchiveModel;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ZipArchive implements ArchiveModel {

    private ArchiveInput archiveInput;


    @Override
    public void initArchive(ArchiveInput archiveInput) throws ArchiveException {

    }

    @Override
    public FileVisitResult addDirectoryEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException {
        return null;
    }

    @Override
    public FileVisitResult addFileEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException {
        return null;
    }

    @Override
    public ArchiveInputStream extractInputStream(ArchiveInput archiveInput) throws ArchiveException {
        return null;
    }


    @Override
    public void finish() throws ArchiveException {

    }
}
