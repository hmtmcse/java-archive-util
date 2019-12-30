package com.hmtmcse.archive.common;

import com.hmtmcse.archive.archive.TarArchive;
import com.hmtmcse.archive.archive.ZipArchive;
import com.hmtmcse.archive.model.ArchiveModel;

import java.util.LinkedHashMap;

public class ArchiveHolder {

    private LinkedHashMap<String, ArchiveModel> archives = new LinkedHashMap<>();

    public ArchiveHolder() {
        archives.put(ArchiveConstant.TAR, new TarArchive());
        archives.put(ArchiveConstant.ZIP, new ZipArchive());
    }

    public ArchiveModel getArchive(String name) {
        return archives.get(name);
    }
}
