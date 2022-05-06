package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmOrgMgtEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.OrgMgtSubTargetDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.orgMgt.OrgMgtTargetComment;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.*;
import com.spring.project.development.voler.repository.orgMgt.OrgMgtTargetCommentRepository;
import com.spring.project.development.voler.repository.orgMgt.formulation.bcpm.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created By zepaG on 3/21/2022.
 */
@Service
public class TfBcpmOrgMgtEditTargetService {
    private final TfBcpmOrgMgtTargetActivityRepository tfBcpmOrgMgtTargetActivityRepository;
    private final TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository;
    private final TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository;
    private final TfBcpmOrgMgtSubTarget_aRepository tfBcpmOrgMgtSubTarget_aRepository;
    private final TfBcpmOrgMgtTargetWriteupRepository tfBcpmOrgMgtTargetWriteupRepository;
    private final TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository;
    private final TfBcpmOrgMgtSubTargetDocRepository tfBcpmOrgMgtSubTargetDocRepository;
    private final TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository;
    private final OrgMgtTargetCommentRepository orgMgtTargetCommentRepository;
    private final TfBcpmOrgMgtEditTargetDao tfBcpmOrgMgtEditTargetDao;
    private final TfBcpmOrgMgtSubTargetReviewerRemarkRepository tfBcpmOrgMgtSubTargetReviewerRemarkRepository;
    private final TfBcpmOrgMgtTargetStageRepository tfBcpmOrgMgtTargetStageRepository;
    private final CommonService commonService;

    public TfBcpmOrgMgtEditTargetService(TfBcpmOrgMgtTargetActivityRepository tfBcpmOrgMgtTargetActivityRepository, TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository, TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository, TfBcpmOrgMgtSubTarget_aRepository tfBcpmOrgMgtSubTarget_aRepository, TfBcpmOrgMgtTargetWriteupRepository tfBcpmOrgMgtTargetWriteupRepository, TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository, TfBcpmOrgMgtSubTargetDocRepository tfBcpmOrgMgtSubTargetDocRepository, TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository, OrgMgtTargetCommentRepository orgMgtTargetCommentRepository, TfBcpmOrgMgtEditTargetDao tfBcpmOrgMgtEditTargetDao, TfBcpmOrgMgtSubTargetReviewerRemarkRepository tfBcpmOrgMgtSubTargetReviewerRemarkRepository, TfBcpmOrgMgtTargetStageRepository tfBcpmOrgMgtTargetStageRepository, CommonService commonService) {
        this.tfBcpmOrgMgtTargetActivityRepository = tfBcpmOrgMgtTargetActivityRepository;
        this.tfBcpmOrgMgtTargetActivity_aRepository = tfBcpmOrgMgtTargetActivity_aRepository;
        this.tfBcpmOrgMgtSubTargetRepository = tfBcpmOrgMgtSubTargetRepository;
        this.tfBcpmOrgMgtSubTarget_aRepository = tfBcpmOrgMgtSubTarget_aRepository;
        this.tfBcpmOrgMgtTargetWriteupRepository = tfBcpmOrgMgtTargetWriteupRepository;
        this.tfBcpmOrgMgtTargetWriteup_aRepository = tfBcpmOrgMgtTargetWriteup_aRepository;
        this.tfBcpmOrgMgtSubTargetDocRepository = tfBcpmOrgMgtSubTargetDocRepository;
        this.tfBcpmOrgMgtTargetStatusRepository = tfBcpmOrgMgtTargetStatusRepository;
        this.orgMgtTargetCommentRepository = orgMgtTargetCommentRepository;
        this.tfBcpmOrgMgtEditTargetDao = tfBcpmOrgMgtEditTargetDao;
        this.tfBcpmOrgMgtSubTargetReviewerRemarkRepository = tfBcpmOrgMgtSubTargetReviewerRemarkRepository;
        this.tfBcpmOrgMgtTargetStageRepository = tfBcpmOrgMgtTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        OrgMgtTargetDto orgMgtTargetDto = tfBcpmOrgMgtEditTargetDao.getTarget(targetAuditId);
        if (orgMgtTargetDto != null) {
            responseMessage.setDTO(orgMgtTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtSubTargetDto> orgMgtSubTargetDtos = tfBcpmOrgMgtEditTargetDao.getSubTarget(targetAuditId);
        if (orgMgtSubTargetDtos != null) {
            responseMessage.setDTO(orgMgtSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfBcpmOrgMgtEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmOrgMgtTargetActivity_a tfBcpmOrgMgtTargetActivity_a = tfBcpmOrgMgtTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
        TfBcpmOrgMgtTargetWriteup_a tfBcpmOrgMgtTargetWriteup_a = tfBcpmOrgMgtTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmOrgMgtTargetActivity_a != null) {
            responseMessage.setDTO(tfBcpmOrgMgtTargetActivity_a);
            responseMessage.setWriteupDto(tfBcpmOrgMgtTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmOrgMgtSubTarget_a> tfBcpmOrgMgtSubTarget_as = tfBcpmOrgMgtSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmOrgMgtSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfBcpmOrgMgtSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfBcpmOrgMgtEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmOrgMgtSubTargetDoc> tfBcpmOrgMgtSubTargetDocs = tfBcpmOrgMgtSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfBcpmOrgMgtSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfBcpmOrgMgtSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmOrgMgtSubTargetDoc tfBcpmOrgMgtSubTargetDocDb = tfBcpmOrgMgtSubTargetDocRepository.findByFileId(fileId);
        if (tfBcpmOrgMgtSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTargetDb = tfBcpmOrgMgtSubTargetRepository.findBySubTargetId(tfBcpmOrgMgtSubTargetDocDb.getSubTargetId());
        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivityDb = tfBcpmOrgMgtTargetActivityRepository.findByTargetId(tfBcpmOrgMgtSubTargetDb.getTargetId());

        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(tfBcpmOrgMgtSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfBcpmOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmOrgMgtSubTargetDoc document = new TfBcpmOrgMgtSubTargetDoc();
        document.setFileId(fileId);
        tfBcpmOrgMgtSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfBcpmOrgMgtSubTargetDoc document = tfBcpmOrgMgtSubTargetDocRepository.findByFileId(fileId);
        String uploadFilePath = document.getFileUrl();
        String fileName = document.getFileName();

        responseMessage = FileUploadToExternalLocation.viewFile(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(document);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;

    }

    public ResponseMessage downloadFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfBcpmOrgMgtSubTargetDoc document = tfBcpmOrgMgtSubTargetDocRepository.findByFileId(fileId);
        String uploadFilePath = document.getFileUrl();
        String fileName = document.getFileName();

        responseMessage = FileUploadToExternalLocation.fileDownloader(fileName, uploadFilePath, response);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage.setDTO(document);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage getRemark(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemark
                = tfBcpmOrgMgtSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfBcpmOrgMgtSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfBcpmOrgMgtSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfBcpmOrgMgtSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCreatedDate(new Date());
        tfBcpmOrgMgtSubTargetReviewerRemarkRepository.save(tfBcpmOrgMgtSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfBcpmOrgMgtSubTargetReviewerRemark tfBcpmOrgMgtSubTargetReviewerRemarkDb
                = tfBcpmOrgMgtSubTargetReviewerRemarkRepository.findByRemarkId(tfBcpmOrgMgtSubTargetReviewerRemark.getRemarkId());
        tfBcpmOrgMgtSubTargetReviewerRemark.setSubTargetId(tfBcpmOrgMgtSubTargetReviewerRemarkDb.getSubTargetId());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCreatedBy(tfBcpmOrgMgtSubTargetReviewerRemarkDb.getCreatedBy());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCreatedDate(tfBcpmOrgMgtSubTargetReviewerRemarkDb.getCreatedDate());
        tfBcpmOrgMgtSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmOrgMgtSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfBcpmOrgMgtSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfBcpmOrgMgtSubTargetReviewerRemarkRepository.save(tfBcpmOrgMgtSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addComment(CurrentUser currentUser, OrgMgtTargetComment orgMgtTargetComment) {
        ResponseMessage responseMessage = new ResponseMessage();
        orgMgtTargetComment.setCommentId(UuidGenerator.generateUuid());
        orgMgtTargetComment.setCreatedBy(currentUser.getUserId());
        orgMgtTargetComment.setCreatedDate(new Date());
        orgMgtTargetCommentRepository.save(orgMgtTargetComment);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage closeTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        Integer myRoleId = currentUser.getRoleId();
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmOrgMgtTargetStatus tfBcpmOrgMgtTargetStatus = new TfBcpmOrgMgtTargetStatus();
        tfBcpmOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStatus.setTargetId(targetId);
        tfBcpmOrgMgtTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfBcpmOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStatus.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStatusRepository.save(tfBcpmOrgMgtTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Closed successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage reopenTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        Integer myRoleId = currentUser.getRoleId();
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmOrgMgtTargetStatus tfBcpmOrgMgtTargetStatus = new TfBcpmOrgMgtTargetStatus();
        tfBcpmOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStatus.setTargetId(targetId);
        tfBcpmOrgMgtTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfBcpmOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStatus.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStatusRepository.save(tfBcpmOrgMgtTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivityDb = tfBcpmOrgMgtTargetActivityRepository.findByTargetId(targetId);
        if (tfBcpmOrgMgtTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(targetId);
        orgMgtTargetDto.setCompanyId(tfBcpmOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivity = new TfBcpmOrgMgtTargetActivity();
        tfBcpmOrgMgtTargetActivity.setTargetId(targetId);
        tfBcpmOrgMgtTargetActivityRepository.delete(tfBcpmOrgMgtTargetActivity);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {

        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTargetDb = tfBcpmOrgMgtSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfBcpmOrgMgtSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivityDb = tfBcpmOrgMgtTargetActivityRepository.findByTargetId(tfBcpmOrgMgtSubTargetDb.getTargetId());

        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(tfBcpmOrgMgtSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfBcpmOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmOrgMgtTargetActivity_a tfBcpmOrgMgtTargetActivity_a = new ModelMapper().map(tfBcpmOrgMgtTargetActivityDb, TfBcpmOrgMgtTargetActivity_a.class);
        tfBcpmOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetActivity_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmOrgMgtTargetActivity_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetActivity_a.setUpdatedDate(new Date());
        tfBcpmOrgMgtTargetActivity_aRepository.save(tfBcpmOrgMgtTargetActivity_a);

        TfBcpmOrgMgtTargetWriteup tfBcpmOrgMgtTargetWriteupDb = tfBcpmOrgMgtTargetWriteupRepository.findByTargetId(tfBcpmOrgMgtSubTargetDb.getTargetId());
        TfBcpmOrgMgtTargetWriteup_a tfBcpmOrgMgtTargetWriteup_a = new ModelMapper().map(tfBcpmOrgMgtTargetWriteupDb, TfBcpmOrgMgtTargetWriteup_a.class);
        tfBcpmOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetWriteup_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
        tfBcpmOrgMgtTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmOrgMgtTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetWriteup_a.setUpdatedDate(new Date());
        tfBcpmOrgMgtTargetWriteup_aRepository.save(tfBcpmOrgMgtTargetWriteup_a);

        TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTargetDelete = new TfBcpmOrgMgtSubTarget();
        tfBcpmOrgMgtSubTargetDelete.setSubTargetId(subTargetId);
        tfBcpmOrgMgtSubTargetRepository.delete(tfBcpmOrgMgtSubTargetDelete);

        List<TfBcpmOrgMgtSubTarget> tfBcpmOrgMgtSubTargets = tfBcpmOrgMgtSubTargetRepository.findByTargetId(tfBcpmOrgMgtTargetActivityDb.getTargetId());
        for (TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTarget : tfBcpmOrgMgtSubTargets) {
            TfBcpmOrgMgtSubTarget_a tfBcpmOrgMgtSubTarget_a = new ModelMapper().map(tfBcpmOrgMgtSubTarget, TfBcpmOrgMgtSubTarget_a.class);
            tfBcpmOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtSubTarget_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
            tfBcpmOrgMgtSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfBcpmOrgMgtSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfBcpmOrgMgtSubTarget_a.setUpdatedDate(new Date());
            tfBcpmOrgMgtSubTarget_aRepository.save(tfBcpmOrgMgtSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivityDb = tfBcpmOrgMgtTargetActivityRepository.findByTargetId(orgMgtTargetDto.getTargetId());
        if (tfBcpmOrgMgtTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        orgMgtTargetDto.setCompanyId(tfBcpmOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmOrgMgtTargetActivity tfBcpmOrgMgtTargetActivity = new ModelMapper().map(orgMgtTargetDto, TfBcpmOrgMgtTargetActivity.class);

        tfBcpmOrgMgtTargetActivity.setCompanyId(tfBcpmOrgMgtTargetActivityDb.getCompanyId());
        tfBcpmOrgMgtTargetActivity.setYear(tfBcpmOrgMgtTargetActivityDb.getYear());
        BigInteger versionNo = getTargetVersionNo(orgMgtTargetDto.getTargetId());
        tfBcpmOrgMgtTargetActivity.setVersionNo(versionNo);
        tfBcpmOrgMgtTargetActivity.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmOrgMgtTargetActivity.setCreatedBy(tfBcpmOrgMgtTargetActivityDb.getCreatedBy());
        tfBcpmOrgMgtTargetActivity.setCreatedDate(tfBcpmOrgMgtTargetActivityDb.getCreatedDate());
        tfBcpmOrgMgtTargetActivity.setUpdatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetActivity.setUpdatedDate(new Date());
        tfBcpmOrgMgtTargetActivityRepository.save(tfBcpmOrgMgtTargetActivity);

        TfBcpmOrgMgtTargetActivity_a tfBcpmOrgMgtTargetActivity_a = new ModelMapper().map(tfBcpmOrgMgtTargetActivity, TfBcpmOrgMgtTargetActivity_a.class);
        tfBcpmOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetActivity_aRepository.save(tfBcpmOrgMgtTargetActivity_a);

        TfBcpmOrgMgtTargetWriteup tfBcpmOrgMgtTargetWriteupDb = tfBcpmOrgMgtTargetWriteupRepository.findByTargetId(orgMgtTargetDto.getTargetId());
        TfBcpmOrgMgtTargetWriteup tfBcpmOrgMgtTargetWriteup = new ModelMapper().map(orgMgtTargetDto, TfBcpmOrgMgtTargetWriteup.class);
        tfBcpmOrgMgtTargetWriteup.setWriteupId(tfBcpmOrgMgtTargetWriteupDb.getWriteupId());
        tfBcpmOrgMgtTargetWriteup.setTargetId(tfBcpmOrgMgtTargetActivity.getTargetId());
        tfBcpmOrgMgtTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmOrgMgtTargetWriteup.setCreatedBy(tfBcpmOrgMgtTargetWriteupDb.getCreatedBy());
        tfBcpmOrgMgtTargetWriteup.setCreatedDate(tfBcpmOrgMgtTargetWriteupDb.getCreatedDate());
        tfBcpmOrgMgtTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetWriteup.setUpdatedDate(new Date());

        tfBcpmOrgMgtTargetWriteupRepository.save(tfBcpmOrgMgtTargetWriteup);
        TfBcpmOrgMgtTargetWriteup_a tfBcpmOrgMgtTargetWriteup_a = new ModelMapper().map(tfBcpmOrgMgtTargetWriteup, TfBcpmOrgMgtTargetWriteup_a.class);
        tfBcpmOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetWriteup_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
        tfBcpmOrgMgtTargetWriteup_aRepository.save(tfBcpmOrgMgtTargetWriteup_a);

        //delete all sub targets by targerId first and save below
        for (OrgMgtSubTargetDto subTargetDto : orgMgtTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTargetDb = tfBcpmOrgMgtSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTarget = new ModelMapper().map(subTargetDto, TfBcpmOrgMgtSubTarget.class);
            if (tfBcpmOrgMgtSubTarget.getSubTargetId() == null) {
                tfBcpmOrgMgtSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfBcpmOrgMgtSubTarget.setCreatedBy(currentUser.getUserId());
                tfBcpmOrgMgtSubTarget.setCreatedDate(new Date());
                tfBcpmOrgMgtSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfBcpmOrgMgtSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfBcpmOrgMgtSubTarget.setCreatedBy(tfBcpmOrgMgtSubTargetDb.getCreatedBy());
                tfBcpmOrgMgtSubTarget.setCreatedDate(tfBcpmOrgMgtSubTargetDb.getCreatedDate());
                tfBcpmOrgMgtSubTarget.setUpdatedBy(currentUser.getUserId());
                tfBcpmOrgMgtSubTarget.setCreatedDate(new Date());
            }
            tfBcpmOrgMgtSubTarget.setTargetId(tfBcpmOrgMgtTargetActivity.getTargetId());

            tfBcpmOrgMgtSubTargetRepository.save(tfBcpmOrgMgtSubTarget);
            TfBcpmOrgMgtSubTarget_a tfBcpmOrgMgtSubTarget_a = new ModelMapper().map(tfBcpmOrgMgtSubTarget, TfBcpmOrgMgtSubTarget_a.class);
            tfBcpmOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtSubTarget_a.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
            tfBcpmOrgMgtSubTarget_aRepository.save(tfBcpmOrgMgtSubTarget_a);

            //save attachment
            List<MultipartFile> attachedFiles = subTargetDto.getAttachedFiles();
            if (attachedFiles != null) {
                for (MultipartFile multipartFile : attachedFiles) {
                    String filename = multipartFile.getOriginalFilename();
                    Long fileSize = multipartFile.getSize();
                    String fileExtension = Objects.requireNonNull(filename).substring(filename.lastIndexOf(".") + 1, filename.length()).toUpperCase();

                    FileUploadDTO fileUploadDTO = FileUploadToExternalLocation.fileUploadPathRetriever(request);
                    String fileUrl = fileUploadDTO.getUploadFilePath().concat(filename);
                    if (!filename.equals("")) {
                        responseMessage = FileUploadToExternalLocation.fileUploader(multipartFile, filename, "attachFile.properties", request);
                        TfBcpmOrgMgtSubTargetDoc tfBcpmOrgMgtSubTargetDoc = new TfBcpmOrgMgtSubTargetDoc();
                        tfBcpmOrgMgtSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmOrgMgtSubTargetDoc.setSubTargetId(tfBcpmOrgMgtSubTarget.getSubTargetId());
                        tfBcpmOrgMgtSubTargetDoc.setFileName(filename);
                        tfBcpmOrgMgtSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmOrgMgtSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmOrgMgtSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmOrgMgtSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmOrgMgtSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmOrgMgtSubTargetDocRepository.save(tfBcpmOrgMgtSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfBcpmOrgMgtTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfBcpmOrgMgtEditTargetDao.getTargetVersionNo(targetId);
        versionNo = versionNo == null ? BigInteger.ZERO : versionNo;
        return versionNo.add(BigInteger.ONE);
    }

    private ResponseMessage validateBeforeEdit(CurrentUser currentUser, OrgMgtTargetDto orgMgtTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = orgMgtTargetDto.getYear();
        String companyId = orgMgtTargetDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        Integer creatorId = SystemRoles.Creator.getValue();
        Integer reviewerId = SystemRoles.Reviewer.getValue();
        String myCompanyId = currentUser.getCompanyId();
        String targetId = orgMgtTargetDto.getTargetId();
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfBcpmOrgMgtTargetStatus tfBcpmOrgMgtTargetStatus = tfBcpmOrgMgtTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
        //only creator, reviewer and admin should able to add
        if (!myRoleId.equals(SystemRoles.Creator.getValue()) && !myRoleId.equals(SystemRoles.Reviewer.getValue()) && !myRoleId.equals(SystemRoles.Admin.getValue())) {
            responseMessage.setText("You are not permitted to perform this action.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        //if myRoleId is creator, my companyId must equal to selected companyId, latest stage status must be null or R=Reverted
        if (myRoleId.equals(creatorId)) {
            if (!companyId.equals(myCompanyId)) {
                responseMessage.setText("Failed to save due to miss match of company.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage != null) {
                if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfBcpmOrgMgtTargetStatus.getStatusFlag() == 'X') {
                responseMessage.setText("You are not permitted to edit/delete because this target is closed. Please request reviewer to make changes.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                return responseMessage;
            }
        }
        //if myRoleId is Reviewer, companyId must exist in company mapping for reviewer id, latest stage status must be S=Submitted
        else if (myRoleId.equals(reviewerId)) {
            //check if it is reviewer belongs to selected company or not
            String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
            if (mappedCompanyId == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because selected company is not mapped to you.");
                return responseMessage;
            }
            // latest stage status must be S=Reverted
            if (tfBcpmOrgMgtTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
