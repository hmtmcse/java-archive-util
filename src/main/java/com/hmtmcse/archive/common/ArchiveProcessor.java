package com.hmtmcse.archive.common;

import com.hmtmcse.archive.data.ArchiveInput;
import com.hmtmcse.archive.model.ArchiveModel;
import com.hmtmcse.fileutil.fd.FileDirectory;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class ArchiveProcessor {

    private FileDirectory fileDirectory;
    private ScanAndAddToArchive scanAndAddToArchive;

    public ArchiveProcessor() {
        this.fileDirectory = new FileDirectory();
        this.scanAndAddToArchive = new ScanAndAddToArchive();
    }

    private void validateInput(ArchiveModel archiveModel, ArchiveInput archiveInput) throws ArchiveException {
        if (archiveInput.source == null || archiveInput.source.equals("") || archiveInput.destination == null || archiveInput.destination.equals("")) {
            throw new ArchiveException(ArchiveMessage.SOURCE_ARCHIVE_TO_NULL);
        } else if (fileDirectory.isExist(archiveInput.destination)) {
            throw new ArchiveException(ArchiveMessage.ARCHIVE_TO_EXIST);
        } else if (!fileDirectory.isExist(archiveInput.source)) {
            throw new ArchiveException(ArchiveMessage.NULL_SOURCE);
        } else if (archiveModel == null) {
            throw new ArchiveException(ArchiveMessage.NULL_PROCESSOR);
        }
    }


    public void archive(ArchiveModel archiveModel, ArchiveInput archiveInput) throws ArchiveException {
        validateInput(archiveModel, archiveInput);
        archiveModel.initArchive(archiveInput);
        scanAndAddToArchive.init(archiveModel);
        try {
            Files.walkFileTree(Paths.get(archiveInput.source), scanAndAddToArchive);
        } catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
        archiveModel.finish();
    }


    public void extract(ArchiveModel archiveModel, ArchiveInput archiveInput) throws ArchiveException {
        validateInput(archiveModel, archiveInput);
        extractData(archiveModel.extractInputStream(archiveInput), archiveInput);
    }


    private void extractData(ArchiveInputStream archiveInputStream, ArchiveInput archiveInput) throws ArchiveException {
        if (archiveInputStream == null) {
            throw new ArchiveException(ArchiveMessage.NULL_STREAM);
        }
        try{
            ArchiveEntry entry = null;
            while ((entry = archiveInputStream.getNextEntry()) != null) {
                if (!archiveInputStream.canReadEntryData(entry)) {
                    continue;
                }
                String name = entry.getName();
                File f = new File(archiveInput.destination, name);
                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new ArchiveException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new ArchiveException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath())) {
                        IOUtils.copy(archiveInputStream, o);
                    }
                }
            }
        }catch (IOException e) {
            throw new ArchiveException(e.getMessage());
        }
    }

}
