package com.hmtmcse.archive.data;

public class ArchiveInput {


    public String source;
    public String archiveTo;

    public ArchiveInput() {}

    public ArchiveInput(String source, String archiveTo) {
        this.source = source;
        this.archiveTo = archiveTo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getArchiveTo() {
        return archiveTo;
    }

    public void setArchiveTo(String archiveTo) {
        this.archiveTo = archiveTo;
    }
}
