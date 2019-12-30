package com.hmtmcse.archive

import com.hmtmcse.archive.common.ArchiveProcessor
import spock.lang.Specification

class ArchiveTest extends Specification{

    def "Create tar Archive"(){
        expect: "Will create tar Archive"
        ArchiveManager archiveManager = new ArchiveManager()
        archiveManager.setInputOutput("C:\\Users\\touhid\\Desktop\\tmp\\vaia\\back.tar.gz", "C:\\Users\\touhid\\Desktop\\tmp\\vaia\\xyz")
        archiveManager.tarExtract()
    }

}
