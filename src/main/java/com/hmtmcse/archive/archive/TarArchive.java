package com.hmtmcse.archive.archive;

import com.hmtmcse.archive.common.ArchiveCommon;
import com.hmtmcse.archive.common.ArchiveException;
import com.hmtmcse.archive.data.ArchiveInput;
import com.hmtmcse.archive.model.ArchiveModel;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class TarArchive extends ArchiveCommon implements ArchiveModel {

    private ArchiveInput archiveInput;
    private TarArchiveOutputStream tarArchiveOutputStream;

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
            GzipCompressorOutputStream gzipCompressorOutputStream = new GzipCompressorOutputStream(bufferedOutputStream);
            this.tarArchiveOutputStream = new TarArchiveOutputStream(gzipCompressorOutputStream);
            this.tarArchiveOutputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
        } catch (Exception e) {
            throw new ArchiveException(e.getMessage());
        }
    }


    @Override
    public FileVisitResult addFileEntry(Path path, BasicFileAttributes basicFileAttributes) throws ArchiveException {
        try {
            String entryName = getEntryName(path);
            TarArchiveEntry entry = new TarArchiveEntry(path.toFile(), entryName);
            tarArchiveOutputStream.putArchiveEntry(entry);
            IOUtils.copy(getInputStream(path), tarArchiveOutputStream);
            tarArchiveOutputStream.closeArchiveEntry();
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
                TarArchiveEntry entry = new TarArchiveEntry(path.toFile(), entryName);
                tarArchiveOutputStream.putArchiveEntry(entry);
                tarArchiveOutputStream.closeArchiveEntry();
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
            try {
                InputStream gzipCompressorInputStream = new GzipCompressorInputStream(bufferedInputStream);
                return new TarArchiveInputStream(gzipCompressorInputStream);
            } catch (Exception e) {
                return new TarArchiveInputStream(bufferedInputStream);
            }
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
    }


    @Override
    public void finish() throws ArchiveException {
        try {
            this.tarArchiveOutputStream.close();
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
    }
}
