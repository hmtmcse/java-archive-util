package com.hmtmcse.archive.archive;

import com.hmtmcse.archive.common.ArchiveCommon;
import com.hmtmcse.archive.common.ArchiveException;
import com.hmtmcse.archive.data.ArchiveInput;
import com.hmtmcse.archive.model.ArchiveModel;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ZipArchive extends ArchiveCommon implements ArchiveModel {

    private ArchiveInput archiveInput;
    private ZipArchiveOutputStream zipArchiveOutputStream;

    private void init(ArchiveInput archiveInput) {
        super.processInput(archiveInput);
        this.archiveInput = archiveInput;
    }

    @Override
    public void initArchive(ArchiveInput archiveInput) throws ArchiveException {
        init(archiveInput);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(archiveInput.destination);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            this.zipArchiveOutputStream = new ZipArchiveOutputStream(bufferedOutputStream);
        } catch (Exception e) {
            throw new ArchiveException(e.getMessage());
        }
    }


    @Override
    public FileVisitResult addFileEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException {
        try {
            String entryName = getEntryName(path);
            ZipArchiveEntry entry = new ZipArchiveEntry(path.toFile(), entryName);
            zipArchiveOutputStream.putArchiveEntry(entry);
            IOUtils.copy(getInputStream(path), zipArchiveOutputStream);
            zipArchiveOutputStream.closeArchiveEntry();
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult addDirectoryEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException {
        String entryName = getEntryName(path);
        try {
            if (!entryName.equals("")) {
                ZipArchiveEntry entry = new ZipArchiveEntry(path.toFile(), entryName);
                zipArchiveOutputStream.putArchiveEntry(entry);
                zipArchiveOutputStream.closeArchiveEntry();
            }
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
        return FileVisitResult.CONTINUE;
    }


    @Override
    public ArchiveInputStream extractInputStream(ArchiveInput archiveInput) throws ArchiveException {
        init(archiveInput);
        try {
            InputStream inputStream = Files.newInputStream(source);
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            return new ZipArchiveInputStream(bufferedInputStream);
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
    }


    @Override
    public void finish() throws ArchiveException {
        try {
            this.zipArchiveOutputStream.close();
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
    }
}