package com.hmtmcse.archive

import spock.lang.Specification

class ArchiveTest extends Specification{

    def "Create tar Archive"(){
        expect: "Will create tar Archive"
        ArchiveManager archiveManager = new ArchiveManager()
        archiveManager.setInputOutput("C:\\Users\\touhid\\Desktop\\tmp\\vaia\\back.tar.gz", "C:\\Users\\touhid\\Desktop\\tmp\\vaia\\xyz")
        archiveManager.tarExtract()
    }

    def "Create zip Archive"(){
        expect: "Will create zip Archive"
        ArchiveManager archiveManager = new ArchiveManager()
        archiveManager.setInputOutput("C:\\Users\\touhid\\Desktop\\tmp\\vaia\\xyz", "C:\\Users\\touhid\\Desktop\\tmp\\vaia\\xyz.zip")
        archiveManager.zip()
    }

    def "Extract zip Archive"(){
        expect: "Will extract zip Archive"
        ArchiveManager archiveManager = new ArchiveManager()
        archiveManager.setInputOutput("C:\\Users\\touhid\\Desktop\\tmp\\vaia\\xyz.zip", "C:\\Users\\touhid\\Desktop\\tmp\\vaia\\baka")
        archiveManager.zipExtract()
    }

}
