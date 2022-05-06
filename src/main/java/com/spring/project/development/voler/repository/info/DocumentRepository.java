package com.spring.project.development.voler.repository.info;

import com.spring.project.development.voler.entity.info.InfoHelpDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<InfoHelpDoc, String> {
    InfoHelpDoc findByFileId(String fileId);
}
