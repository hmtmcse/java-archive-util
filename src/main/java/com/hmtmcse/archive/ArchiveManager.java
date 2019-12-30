package com.hmtmcse.archive;

import com.hmtmcse.archive.common.ArchiveConstant;
import com.hmtmcse.archive.common.ArchiveException;
import com.hmtmcse.archive.common.ArchiveHolder;
import com.hmtmcse.archive.common.ArchiveProcessor;
import com.hmtmcse.archive.data.ArchiveInput;
import com.hmtmcse.archive.model.ArchiveModel;

public class ArchiveManager {

    private ArchiveInput archiveInput;
    private ArchiveProcessor archiveProcessor;
    private ArchiveHolder archiveHolder;

    public ArchiveManager() {
        this.archiveProcessor = new ArchiveProcessor();
        this.archiveHolder = new ArchiveHolder();
    }

    private void archive(ArchiveModel archiveModel) throws ArchiveException {
        archiveProcessor.archive(archiveModel, archiveInput);
    }

    private void extract(ArchiveModel archiveModel) throws ArchiveException {
        archiveProcessor.extract(archiveModel, archiveInput);
    }

    public ArchiveManager setInputOutput(String source, String tarTo) {
        this.archiveInput = new ArchiveInput(source, tarTo);
        return this;
    }

    public ArchiveManager tar() throws ArchiveException {
        archive(this.archiveHolder.getArchive(ArchiveConstant.TAR));
        return this;
    }

    public ArchiveManager tarExtract() throws ArchiveException {
        extract(this.archiveHolder.getArchive(ArchiveConstant.TAR));
        return this;
    }

    public ArchiveManager zip() throws ArchiveException {
        archive(this.archiveHolder.getArchive(ArchiveConstant.ZIP));
        return this;
    }

    public ArchiveManager zipExtract() throws ArchiveException {
        extract(this.archiveHolder.getArchive(ArchiveConstant.ZIP));
        return this;
    }

}
