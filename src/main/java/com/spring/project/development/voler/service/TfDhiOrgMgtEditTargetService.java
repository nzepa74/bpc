package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiOrgMgtEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.OrgMgtSubTargetDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.orgMgt.OrgMgtTargetComment;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.*;
import com.spring.project.development.voler.repository.orgMgt.OrgMgtTargetCommentRepository;
import com.spring.project.development.voler.repository.orgMgt.formulation.dhi.*;
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
public class TfDhiOrgMgtEditTargetService {
    private final TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository;
    private final TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository;
    private final TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository;
    private final TfDhiOrgMgtSubTarget_aRepository tfDhiOrgMgtSubTarget_aRepository;
    private final TfDhiOrgMgtTargetWriteupRepository tfDhiOrgMgtTargetWriteupRepository;
    private final TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository;
    private final TfDhiOrgMgtSubTargetDocRepository tfDhiOrgMgtSubTargetDocRepository;
    private final TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository;
    private final OrgMgtTargetCommentRepository orgMgtTargetCommentRepository;
    private final TfDhiOrgMgtEditTargetDao tfDhiOrgMgtEditTargetDao;
    private final TfDhiOrgMgtSubTargetReviewerRemarkRepository tfDhiOrgMgtSubTargetReviewerRemarkRepository;
    private final TfDhiOrgMgtTargetStageRepository tfDhiOrgMgtTargetStageRepository;
    private final CommonService commonService;

    public TfDhiOrgMgtEditTargetService(TfDhiOrgMgtTargetActivityRepository tfDhiOrgMgtTargetActivityRepository, TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository, TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository, TfDhiOrgMgtSubTarget_aRepository tfDhiOrgMgtSubTarget_aRepository, TfDhiOrgMgtTargetWriteupRepository tfDhiOrgMgtTargetWriteupRepository, TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository, TfDhiOrgMgtSubTargetDocRepository tfDhiOrgMgtSubTargetDocRepository, TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository, OrgMgtTargetCommentRepository orgMgtTargetCommentRepository, TfDhiOrgMgtEditTargetDao tfDhiOrgMgtEditTargetDao, TfDhiOrgMgtSubTargetReviewerRemarkRepository tfDhiOrgMgtSubTargetReviewerRemarkRepository, TfDhiOrgMgtTargetStageRepository tfDhiOrgMgtTargetStageRepository, CommonService commonService) {
        this.tfDhiOrgMgtTargetActivityRepository = tfDhiOrgMgtTargetActivityRepository;
        this.tfDhiOrgMgtTargetActivity_aRepository = tfDhiOrgMgtTargetActivity_aRepository;
        this.tfDhiOrgMgtSubTargetRepository = tfDhiOrgMgtSubTargetRepository;
        this.tfDhiOrgMgtSubTarget_aRepository = tfDhiOrgMgtSubTarget_aRepository;
        this.tfDhiOrgMgtTargetWriteupRepository = tfDhiOrgMgtTargetWriteupRepository;
        this.tfDhiOrgMgtTargetWriteup_aRepository = tfDhiOrgMgtTargetWriteup_aRepository;
        this.tfDhiOrgMgtSubTargetDocRepository = tfDhiOrgMgtSubTargetDocRepository;
        this.tfDhiOrgMgtTargetStatusRepository = tfDhiOrgMgtTargetStatusRepository;
        this.orgMgtTargetCommentRepository = orgMgtTargetCommentRepository;
        this.tfDhiOrgMgtEditTargetDao = tfDhiOrgMgtEditTargetDao;
        this.tfDhiOrgMgtSubTargetReviewerRemarkRepository = tfDhiOrgMgtSubTargetReviewerRemarkRepository;
        this.tfDhiOrgMgtTargetStageRepository = tfDhiOrgMgtTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        OrgMgtTargetDto orgMgtTargetDto = tfDhiOrgMgtEditTargetDao.getTarget(targetAuditId);
        if (orgMgtTargetDto != null) {
            responseMessage.setDTO(orgMgtTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtSubTargetDto> orgMgtSubTargetDtos = tfDhiOrgMgtEditTargetDao.getSubTarget(targetAuditId);
        if (orgMgtSubTargetDtos != null) {
            responseMessage.setDTO(orgMgtSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfDhiOrgMgtEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiOrgMgtTargetActivity_a tfDhiOrgMgtTargetActivity_a = tfDhiOrgMgtTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
        TfDhiOrgMgtTargetWriteup_a tfDhiOrgMgtTargetWriteup_a = tfDhiOrgMgtTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiOrgMgtTargetActivity_a != null) {
            responseMessage.setDTO(tfDhiOrgMgtTargetActivity_a);
            responseMessage.setWriteupDto(tfDhiOrgMgtTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiOrgMgtSubTarget_a> tfDhiOrgMgtSubTarget_as = tfDhiOrgMgtSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiOrgMgtSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfDhiOrgMgtSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfDhiOrgMgtEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiOrgMgtSubTargetDoc> tfDhiOrgMgtSubTargetDocs = tfDhiOrgMgtSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfDhiOrgMgtSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfDhiOrgMgtSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiOrgMgtSubTargetDoc tfDhiOrgMgtSubTargetDocDb = tfDhiOrgMgtSubTargetDocRepository.findByFileId(fileId);
        if (tfDhiOrgMgtSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTargetDb = tfDhiOrgMgtSubTargetRepository.findBySubTargetId(tfDhiOrgMgtSubTargetDocDb.getSubTargetId());
        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivityDb = tfDhiOrgMgtTargetActivityRepository.findByTargetId(tfDhiOrgMgtSubTargetDb.getTargetId());

        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(tfDhiOrgMgtSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfDhiOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiOrgMgtSubTargetDoc document = new TfDhiOrgMgtSubTargetDoc();
        document.setFileId(fileId);
        tfDhiOrgMgtSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfDhiOrgMgtSubTargetDoc document = tfDhiOrgMgtSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiOrgMgtSubTargetDoc document = tfDhiOrgMgtSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemark
                = tfDhiOrgMgtSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfDhiOrgMgtSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfDhiOrgMgtSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfDhiOrgMgtSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfDhiOrgMgtSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiOrgMgtSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtSubTargetReviewerRemark.setCreatedDate(new Date());
        tfDhiOrgMgtSubTargetReviewerRemarkRepository.save(tfDhiOrgMgtSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemarkDb
                = tfDhiOrgMgtSubTargetReviewerRemarkRepository.findByRemarkId(tfDhiOrgMgtSubTargetReviewerRemark.getRemarkId());
        tfDhiOrgMgtSubTargetReviewerRemark.setSubTargetId(tfDhiOrgMgtSubTargetReviewerRemarkDb.getSubTargetId());
        tfDhiOrgMgtSubTargetReviewerRemark.setCreatedBy(tfDhiOrgMgtSubTargetReviewerRemarkDb.getCreatedBy());
        tfDhiOrgMgtSubTargetReviewerRemark.setCreatedDate(tfDhiOrgMgtSubTargetReviewerRemarkDb.getCreatedDate());
        tfDhiOrgMgtSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiOrgMgtSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfDhiOrgMgtSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfDhiOrgMgtSubTargetReviewerRemarkRepository.save(tfDhiOrgMgtSubTargetReviewerRemark);
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
        TfDhiOrgMgtTargetStatus tfDhiOrgMgtTargetStatus = new TfDhiOrgMgtTargetStatus();
        tfDhiOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStatus.setTargetId(targetId);
        tfDhiOrgMgtTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfDhiOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStatus.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStatusRepository.save(tfDhiOrgMgtTargetStatus);
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
        TfDhiOrgMgtTargetStatus tfDhiOrgMgtTargetStatus = new TfDhiOrgMgtTargetStatus();
        tfDhiOrgMgtTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStatus.setTargetId(targetId);
        tfDhiOrgMgtTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfDhiOrgMgtTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStatus.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStatusRepository.save(tfDhiOrgMgtTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivityDb = tfDhiOrgMgtTargetActivityRepository.findByTargetId(targetId);
        if (tfDhiOrgMgtTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(targetId);
        orgMgtTargetDto.setCompanyId(tfDhiOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivity = new TfDhiOrgMgtTargetActivity();
        tfDhiOrgMgtTargetActivity.setTargetId(targetId);
        tfDhiOrgMgtTargetActivityRepository.delete(tfDhiOrgMgtTargetActivity);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {

        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTargetDb = tfDhiOrgMgtSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfDhiOrgMgtSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivityDb = tfDhiOrgMgtTargetActivityRepository.findByTargetId(tfDhiOrgMgtSubTargetDb.getTargetId());

        OrgMgtTargetDto orgMgtTargetDto = new OrgMgtTargetDto();
        orgMgtTargetDto.setTargetId(tfDhiOrgMgtSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfDhiOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiOrgMgtTargetActivity_a tfDhiOrgMgtTargetActivity_a = new ModelMapper().map(tfDhiOrgMgtTargetActivityDb, TfDhiOrgMgtTargetActivity_a.class);
        tfDhiOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetActivity_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiOrgMgtTargetActivity_a.setUpdatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetActivity_a.setUpdatedDate(new Date());
        tfDhiOrgMgtTargetActivity_aRepository.save(tfDhiOrgMgtTargetActivity_a);

        TfDhiOrgMgtTargetWriteup tfDhiOrgMgtTargetWriteupDb = tfDhiOrgMgtTargetWriteupRepository.findByTargetId(tfDhiOrgMgtSubTargetDb.getTargetId());
        TfDhiOrgMgtTargetWriteup_a tfDhiOrgMgtTargetWriteup_a = new ModelMapper().map(tfDhiOrgMgtTargetWriteupDb, TfDhiOrgMgtTargetWriteup_a.class);
        tfDhiOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetWriteup_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
        tfDhiOrgMgtTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiOrgMgtTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetWriteup_a.setUpdatedDate(new Date());
        tfDhiOrgMgtTargetWriteup_aRepository.save(tfDhiOrgMgtTargetWriteup_a);

        TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTargetDelete = new TfDhiOrgMgtSubTarget();
        tfDhiOrgMgtSubTargetDelete.setSubTargetId(subTargetId);
        tfDhiOrgMgtSubTargetRepository.delete(tfDhiOrgMgtSubTargetDelete);

        List<TfDhiOrgMgtSubTarget> tfDhiOrgMgtSubTargets = tfDhiOrgMgtSubTargetRepository.findByTargetId(tfDhiOrgMgtTargetActivityDb.getTargetId());
        for (TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTarget : tfDhiOrgMgtSubTargets) {
            TfDhiOrgMgtSubTarget_a tfDhiOrgMgtSubTarget_a = new ModelMapper().map(tfDhiOrgMgtSubTarget, TfDhiOrgMgtSubTarget_a.class);
            tfDhiOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiOrgMgtSubTarget_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
            tfDhiOrgMgtSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfDhiOrgMgtSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfDhiOrgMgtSubTarget_a.setUpdatedDate(new Date());
            tfDhiOrgMgtSubTarget_aRepository.save(tfDhiOrgMgtSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivityDb = tfDhiOrgMgtTargetActivityRepository.findByTargetId(orgMgtTargetDto.getTargetId());
        if (tfDhiOrgMgtTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        orgMgtTargetDto.setCompanyId(tfDhiOrgMgtTargetActivityDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiOrgMgtTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiOrgMgtTargetActivity tfDhiOrgMgtTargetActivity = new ModelMapper().map(orgMgtTargetDto, TfDhiOrgMgtTargetActivity.class);

        tfDhiOrgMgtTargetActivity.setCompanyId(tfDhiOrgMgtTargetActivityDb.getCompanyId());
        tfDhiOrgMgtTargetActivity.setYear(tfDhiOrgMgtTargetActivityDb.getYear());
        BigInteger versionNo = getTargetVersionNo(orgMgtTargetDto.getTargetId());
        tfDhiOrgMgtTargetActivity.setVersionNo(versionNo);
        tfDhiOrgMgtTargetActivity.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiOrgMgtTargetActivity.setCreatedBy(tfDhiOrgMgtTargetActivityDb.getCreatedBy());
        tfDhiOrgMgtTargetActivity.setCreatedDate(tfDhiOrgMgtTargetActivityDb.getCreatedDate());
        tfDhiOrgMgtTargetActivity.setUpdatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetActivity.setUpdatedDate(new Date());
        tfDhiOrgMgtTargetActivityRepository.save(tfDhiOrgMgtTargetActivity);

        TfDhiOrgMgtTargetActivity_a tfDhiOrgMgtTargetActivity_a = new ModelMapper().map(tfDhiOrgMgtTargetActivity, TfDhiOrgMgtTargetActivity_a.class);
        tfDhiOrgMgtTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetActivity_aRepository.save(tfDhiOrgMgtTargetActivity_a);

        TfDhiOrgMgtTargetWriteup tfDhiOrgMgtTargetWriteupDb = tfDhiOrgMgtTargetWriteupRepository.findByTargetId(orgMgtTargetDto.getTargetId());
        TfDhiOrgMgtTargetWriteup tfDhiOrgMgtTargetWriteup = new ModelMapper().map(orgMgtTargetDto, TfDhiOrgMgtTargetWriteup.class);
        tfDhiOrgMgtTargetWriteup.setWriteupId(tfDhiOrgMgtTargetWriteupDb.getWriteupId());
        tfDhiOrgMgtTargetWriteup.setTargetId(tfDhiOrgMgtTargetActivity.getTargetId());
        tfDhiOrgMgtTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiOrgMgtTargetWriteup.setCreatedBy(tfDhiOrgMgtTargetWriteupDb.getCreatedBy());
        tfDhiOrgMgtTargetWriteup.setCreatedDate(tfDhiOrgMgtTargetWriteupDb.getCreatedDate());
        tfDhiOrgMgtTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetWriteup.setUpdatedDate(new Date());

        tfDhiOrgMgtTargetWriteupRepository.save(tfDhiOrgMgtTargetWriteup);
        TfDhiOrgMgtTargetWriteup_a tfDhiOrgMgtTargetWriteup_a = new ModelMapper().map(tfDhiOrgMgtTargetWriteup, TfDhiOrgMgtTargetWriteup_a.class);
        tfDhiOrgMgtTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetWriteup_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
        tfDhiOrgMgtTargetWriteup_aRepository.save(tfDhiOrgMgtTargetWriteup_a);

        //delete all sub targets by targerId first and save below
        for (OrgMgtSubTargetDto subTargetDto : orgMgtTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTargetDb = tfDhiOrgMgtSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTarget = new ModelMapper().map(subTargetDto, TfDhiOrgMgtSubTarget.class);
            if (tfDhiOrgMgtSubTarget.getSubTargetId() == null) {
                tfDhiOrgMgtSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfDhiOrgMgtSubTarget.setCreatedBy(currentUser.getUserId());
                tfDhiOrgMgtSubTarget.setCreatedDate(new Date());
                tfDhiOrgMgtSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfDhiOrgMgtSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfDhiOrgMgtSubTarget.setCreatedBy(tfDhiOrgMgtSubTargetDb.getCreatedBy());
                tfDhiOrgMgtSubTarget.setCreatedDate(tfDhiOrgMgtSubTargetDb.getCreatedDate());
                tfDhiOrgMgtSubTarget.setUpdatedBy(currentUser.getUserId());
                tfDhiOrgMgtSubTarget.setCreatedDate(new Date());
            }
            tfDhiOrgMgtSubTarget.setTargetId(tfDhiOrgMgtTargetActivity.getTargetId());

            tfDhiOrgMgtSubTargetRepository.save(tfDhiOrgMgtSubTarget);
            TfDhiOrgMgtSubTarget_a tfDhiOrgMgtSubTarget_a = new ModelMapper().map(tfDhiOrgMgtSubTarget, TfDhiOrgMgtSubTarget_a.class);
            tfDhiOrgMgtSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiOrgMgtSubTarget_a.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
            tfDhiOrgMgtSubTarget_aRepository.save(tfDhiOrgMgtSubTarget_a);

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
                        TfDhiOrgMgtSubTargetDoc tfDhiOrgMgtSubTargetDoc = new TfDhiOrgMgtSubTargetDoc();
                        tfDhiOrgMgtSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiOrgMgtSubTargetDoc.setSubTargetId(tfDhiOrgMgtSubTarget.getSubTargetId());
                        tfDhiOrgMgtSubTargetDoc.setFileName(filename);
                        tfDhiOrgMgtSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiOrgMgtSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiOrgMgtSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiOrgMgtSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiOrgMgtSubTargetDoc.setCreatedDate(new Date());
                        tfDhiOrgMgtSubTargetDocRepository.save(tfDhiOrgMgtSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfDhiOrgMgtTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfDhiOrgMgtEditTargetDao.getTargetVersionNo(targetId);
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfDhiOrgMgtTargetStatus tfDhiOrgMgtTargetStatus = tfDhiOrgMgtTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfDhiOrgMgtTargetStage != null) {
                if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfDhiOrgMgtTargetStatus.getStatusFlag() == 'X') {
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
            if (tfDhiOrgMgtTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
