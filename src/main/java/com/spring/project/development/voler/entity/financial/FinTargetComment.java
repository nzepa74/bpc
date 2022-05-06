package com.spring.project.development.voler.entity.financial;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_fin_target_comment")
public class FinTargetComment extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "commentId")
    private String commentId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "comments")
    private String comments;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
