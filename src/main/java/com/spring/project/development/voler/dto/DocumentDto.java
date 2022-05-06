package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class DocumentDto {
    //region variables
    private String fileId;
    private String url;
    private String fileName;
    private String fileExtension;
    private List<MultipartFile> files;
    private List<FileAttachmentDto> fileAttachmentDTOs;

    //endregion
}
