package com.hmtmcse.archive.model;

import com.hmtmcse.archive.common.ArchiveException;
import com.hmtmcse.archive.data.ArchiveInput;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public interface ArchiveModel {

    public void initArchive(ArchiveInput archiveInput) throws ArchiveException;
    public FileVisitResult addDirectoryEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException;
    public FileVisitResult addFileEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException;
    public ArchiveInputStream extractInputStream(ArchiveInput archiveInput) throws ArchiveException;
    public void finish() throws ArchiveException ;

}
