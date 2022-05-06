package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CommentDto {
    //region private variables
    private String commentId;
    private String targetId;
    private String comments;
    private Date commentedDate;
    private String commentTime;
    private String commentBy;
    private Integer commentByRoleId;
    //endregion
}
