package com.hmtmcse.archive.model;

import com.hmtmcse.archive.data.ArchiveInput;
import org.apache.commons.compress.archivers.ArchiveEntry;

public interface ArchiveModel {

    public void init(ArchiveInput archiveInput);
    public void addEntry(ArchiveEntry archiveEntry);
    public void extract(ArchiveEntry archiveEntry);
    public void finish();

}
