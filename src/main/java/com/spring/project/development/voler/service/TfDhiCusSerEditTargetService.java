package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiCusSerEditTargetDao;
import com.spring.project.development.voler.dto.CustomerServiceSubTargetDto;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.cusService.CusSerTargetComment;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.*;
import com.spring.project.development.voler.repository.cusService.CusSerTargetCommentRepository;
import com.spring.project.development.voler.repository.cusService.formulation.dhi.*;
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
public class TfDhiCusSerEditTargetService {
    private final TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository;
    private final TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository;
    private final TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository;
    private final TfDhiCusSerSubTarget_aRepository tfDhiCusSerSubTarget_aRepository;
    private final TfDhiCusSerTargetWriteupRepository tfDhiCusSerTargetWriteupRepository;
    private final TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository;
    private final TfDhiCusSerSubTargetDocRepository tfDhiCusSerSubTargetDocRepository;
    private final TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository;
    private final CusSerTargetCommentRepository cusSerTargetCommentRepository;
    private final TfDhiCusSerEditTargetDao tfDhiCusSerEditTargetDao;
    private final TfDhiCusSerSubTargetReviewerRemarkRepository tfDhiCusSerSubTargetReviewerRemarkRepository;
    private final CommonService commonService;
    private final TfDhiCusSerTargetStageRepository tfDhiCusSerTargetStageRepository;

    public TfDhiCusSerEditTargetService(TfDhiCusSerTargetActivityRepository tfDhiCusSerTargetActivityRepository, TfDhiCusSerTargetActivity_aRepository tfDhiCusSerTargetActivity_aRepository, TfDhiCusSerSubTargetRepository tfDhiCusSerSubTargetRepository, TfDhiCusSerSubTarget_aRepository tfDhiCusSerSubTarget_aRepository, TfDhiCusSerTargetWriteupRepository tfDhiCusSerTargetWriteupRepository, TfDhiCusSerTargetWriteup_aRepository tfDhiCusSerTargetWriteup_aRepository, TfDhiCusSerSubTargetDocRepository tfDhiCusSerSubTargetDocRepository, TfDhiCusSerTargetStatusRepository tfDhiCusSerTargetStatusRepository, CusSerTargetCommentRepository cusSerTargetCommentRepository, TfDhiCusSerEditTargetDao tfDhiCusSerEditTargetDao, TfDhiCusSerSubTargetReviewerRemarkRepository tfDhiCusSerSubTargetReviewerRemarkRepository, CommonService commonService, TfDhiCusSerTargetStageRepository tfDhiCusSerTargetStageRepository) {
        this.tfDhiCusSerTargetActivityRepository = tfDhiCusSerTargetActivityRepository;
        this.tfDhiCusSerTargetActivity_aRepository = tfDhiCusSerTargetActivity_aRepository;
        this.tfDhiCusSerSubTargetRepository = tfDhiCusSerSubTargetRepository;
        this.tfDhiCusSerSubTarget_aRepository = tfDhiCusSerSubTarget_aRepository;
        this.tfDhiCusSerTargetWriteupRepository = tfDhiCusSerTargetWriteupRepository;
        this.tfDhiCusSerTargetWriteup_aRepository = tfDhiCusSerTargetWriteup_aRepository;
        this.tfDhiCusSerSubTargetDocRepository = tfDhiCusSerSubTargetDocRepository;
        this.tfDhiCusSerTargetStatusRepository = tfDhiCusSerTargetStatusRepository;
        this.cusSerTargetCommentRepository = cusSerTargetCommentRepository;
        this.tfDhiCusSerEditTargetDao = tfDhiCusSerEditTargetDao;
        this.tfDhiCusSerSubTargetReviewerRemarkRepository = tfDhiCusSerSubTargetReviewerRemarkRepository;
        this.commonService = commonService;
        this.tfDhiCusSerTargetStageRepository = tfDhiCusSerTargetStageRepository;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        CustomerServiceTargetDto customerServiceTargetDto = tfDhiCusSerEditTargetDao.getTarget(targetAuditId);
        if (customerServiceTargetDto != null) {
            responseMessage.setDTO(customerServiceTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceSubTargetDto> customerServiceSubTargetDtos = tfDhiCusSerEditTargetDao.getSubTarget(targetAuditId);
        if (customerServiceSubTargetDtos != null) {
            responseMessage.setDTO(customerServiceSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfDhiCusSerEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiCusSerTargetActivity_a tfDhiCusSerTargetActivity_a = tfDhiCusSerTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
        TfDhiCusSerTargetWriteup_a tfDhiCusSerTargetWriteup_a = tfDhiCusSerTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiCusSerTargetActivity_a != null) {
            responseMessage.setDTO(tfDhiCusSerTargetActivity_a);
            responseMessage.setWriteupDto(tfDhiCusSerTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiCusSerSubTarget_a> tfDhiCusSerSubTarget_as = tfDhiCusSerSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiCusSerSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfDhiCusSerSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfDhiCusSerEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiCusSerSubTargetDoc> tfDhiCusSerSubTargetDocs = tfDhiCusSerSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfDhiCusSerSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfDhiCusSerSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiCusSerSubTargetDoc tfDhiCusSerSubTargetDocDb = tfDhiCusSerSubTargetDocRepository.findByFileId(fileId);
        if (tfDhiCusSerSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiCusSerSubTarget tfDhiCusSerSubTargetDb = tfDhiCusSerSubTargetRepository.findBySubTargetId(tfDhiCusSerSubTargetDocDb.getSubTargetId());
        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivityDb = tfDhiCusSerTargetActivityRepository.findByTargetId(tfDhiCusSerSubTargetDb.getTargetId());

        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(tfDhiCusSerSubTargetDb.getTargetId());
        customerServiceTargetDto.setCompanyId(tfDhiCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfDhiCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiCusSerSubTargetDoc document = new TfDhiCusSerSubTargetDoc();
        document.setFileId(fileId);
        tfDhiCusSerSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfDhiCusSerSubTargetDoc document = tfDhiCusSerSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiCusSerSubTargetDoc document = tfDhiCusSerSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiCusSerSubTargetReviewerRemark tfDhiCusSerSubTargetReviewerRemark
                = tfDhiCusSerSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfDhiCusSerSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfDhiCusSerSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfDhiCusSerSubTargetReviewerRemark tfDhiCusSerSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfDhiCusSerSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfDhiCusSerSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiCusSerSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerSubTargetReviewerRemark.setCreatedDate(new Date());
        tfDhiCusSerSubTargetReviewerRemarkRepository.save(tfDhiCusSerSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser,TfDhiCusSerSubTargetReviewerRemark tfDhiCusSerSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfDhiCusSerSubTargetReviewerRemark tfDhiCusSerSubTargetReviewerRemarkDb
                = tfDhiCusSerSubTargetReviewerRemarkRepository.findByRemarkId(tfDhiCusSerSubTargetReviewerRemark.getRemarkId());
        tfDhiCusSerSubTargetReviewerRemark.setSubTargetId(tfDhiCusSerSubTargetReviewerRemarkDb.getSubTargetId());
        tfDhiCusSerSubTargetReviewerRemark.setCreatedBy(tfDhiCusSerSubTargetReviewerRemarkDb.getCreatedBy());
        tfDhiCusSerSubTargetReviewerRemark.setCreatedDate(tfDhiCusSerSubTargetReviewerRemarkDb.getCreatedDate());
        tfDhiCusSerSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiCusSerSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfDhiCusSerSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfDhiCusSerSubTargetReviewerRemarkRepository.save(tfDhiCusSerSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addComment(CurrentUser currentUser, CusSerTargetComment cusSerTargetComment) {
        ResponseMessage responseMessage = new ResponseMessage();
        cusSerTargetComment.setCommentId(UuidGenerator.generateUuid());
        cusSerTargetComment.setCreatedBy(currentUser.getUserId());
        cusSerTargetComment.setCreatedDate(new Date());
        cusSerTargetCommentRepository.save(cusSerTargetComment);
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
        TfDhiCusSerTargetStatus tfDhiCusSerTargetStatus = new TfDhiCusSerTargetStatus();
        tfDhiCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStatus.setTargetId(targetId);
        tfDhiCusSerTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfDhiCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStatus.setCreatedDate(new Date());
        tfDhiCusSerTargetStatusRepository.save(tfDhiCusSerTargetStatus);
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
        TfDhiCusSerTargetStatus tfDhiCusSerTargetStatus = new TfDhiCusSerTargetStatus();
        tfDhiCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetStatus.setTargetId(targetId);
        tfDhiCusSerTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfDhiCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiCusSerTargetStatus.setCreatedDate(new Date());
        tfDhiCusSerTargetStatusRepository.save(tfDhiCusSerTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivityDb = tfDhiCusSerTargetActivityRepository.findByTargetId(targetId);
        if (tfDhiCusSerTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(targetId);
        customerServiceTargetDto.setCompanyId(tfDhiCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfDhiCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivity = new TfDhiCusSerTargetActivity();
        tfDhiCusSerTargetActivity.setTargetId(targetId);
        tfDhiCusSerTargetActivityRepository.delete(tfDhiCusSerTargetActivity);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiCusSerSubTarget tfDhiCusSerSubTargetDb = tfDhiCusSerSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfDhiCusSerSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivityDb = tfDhiCusSerTargetActivityRepository.findByTargetId(tfDhiCusSerSubTargetDb.getTargetId());
        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(tfDhiCusSerSubTargetDb.getTargetId());
        customerServiceTargetDto.setCompanyId(tfDhiCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfDhiCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiCusSerTargetActivity_a tfDhiCusSerTargetActivity_a = new ModelMapper().map(tfDhiCusSerTargetActivityDb, TfDhiCusSerTargetActivity_a.class);
        tfDhiCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetActivity_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiCusSerTargetActivity_a.setUpdatedBy(currentUser.getUserId());
        tfDhiCusSerTargetActivity_a.setUpdatedDate(new Date());
        tfDhiCusSerTargetActivity_aRepository.save(tfDhiCusSerTargetActivity_a);

        TfDhiCusSerTargetWriteup tfDhiCusSerTargetWriteupDb = tfDhiCusSerTargetWriteupRepository.findByTargetId(tfDhiCusSerSubTargetDb.getTargetId());
        TfDhiCusSerTargetWriteup_a tfDhiCusSerTargetWriteup_a = new ModelMapper().map(tfDhiCusSerTargetWriteupDb, TfDhiCusSerTargetWriteup_a.class);
        tfDhiCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetWriteup_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
        tfDhiCusSerTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiCusSerTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfDhiCusSerTargetWriteup_a.setUpdatedDate(new Date());
        tfDhiCusSerTargetWriteup_aRepository.save(tfDhiCusSerTargetWriteup_a);

        TfDhiCusSerSubTarget tfDhiCusSerSubTargetDelete = new TfDhiCusSerSubTarget();
        tfDhiCusSerSubTargetDelete.setSubTargetId(subTargetId);
        tfDhiCusSerSubTargetRepository.delete(tfDhiCusSerSubTargetDelete);

        List<TfDhiCusSerSubTarget> tfDhiCusSerSubTargets = tfDhiCusSerSubTargetRepository.findByTargetId(tfDhiCusSerTargetActivityDb.getTargetId());
        for (TfDhiCusSerSubTarget tfDhiCusSerSubTarget : tfDhiCusSerSubTargets) {
            TfDhiCusSerSubTarget_a tfDhiCusSerSubTarget_a = new ModelMapper().map(tfDhiCusSerSubTarget, TfDhiCusSerSubTarget_a.class);
            tfDhiCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiCusSerSubTarget_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
            tfDhiCusSerSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfDhiCusSerSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfDhiCusSerSubTarget_a.setUpdatedDate(new Date());
            tfDhiCusSerSubTarget_aRepository.save(tfDhiCusSerSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, CustomerServiceTargetDto customerServiceTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivityDb = tfDhiCusSerTargetActivityRepository.findByTargetId(customerServiceTargetDto.getTargetId());
        if (tfDhiCusSerTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        customerServiceTargetDto.setCompanyId(tfDhiCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfDhiCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiCusSerTargetActivity tfDhiCusSerTargetActivity = new ModelMapper().map(customerServiceTargetDto, TfDhiCusSerTargetActivity.class);

        tfDhiCusSerTargetActivity.setCompanyId(tfDhiCusSerTargetActivityDb.getCompanyId());
        tfDhiCusSerTargetActivity.setYear(tfDhiCusSerTargetActivityDb.getYear());
        BigInteger versionNo = getTargetVersionNo(customerServiceTargetDto.getTargetId());
        tfDhiCusSerTargetActivity.setVersionNo(versionNo);
        tfDhiCusSerTargetActivity.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiCusSerTargetActivity.setCreatedBy(tfDhiCusSerTargetActivityDb.getCreatedBy());
        tfDhiCusSerTargetActivity.setCreatedDate(tfDhiCusSerTargetActivityDb.getCreatedDate());
        tfDhiCusSerTargetActivity.setUpdatedBy(currentUser.getUserId());
        tfDhiCusSerTargetActivity.setUpdatedDate(new Date());
        tfDhiCusSerTargetActivityRepository.save(tfDhiCusSerTargetActivity);

        TfDhiCusSerTargetActivity_a tfDhiCusSerTargetActivity_a = new ModelMapper().map(tfDhiCusSerTargetActivity, TfDhiCusSerTargetActivity_a.class);
        tfDhiCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetActivity_aRepository.save(tfDhiCusSerTargetActivity_a);

        TfDhiCusSerTargetWriteup tfDhiCusSerTargetWriteupDb = tfDhiCusSerTargetWriteupRepository.findByTargetId(customerServiceTargetDto.getTargetId());
        TfDhiCusSerTargetWriteup tfDhiCusSerTargetWriteup = new ModelMapper().map(customerServiceTargetDto, TfDhiCusSerTargetWriteup.class);
        tfDhiCusSerTargetWriteup.setWriteupId(tfDhiCusSerTargetWriteupDb.getWriteupId());
        tfDhiCusSerTargetWriteup.setTargetId(tfDhiCusSerTargetActivity.getTargetId());
        tfDhiCusSerTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiCusSerTargetWriteup.setCreatedBy(tfDhiCusSerTargetWriteupDb.getCreatedBy());
        tfDhiCusSerTargetWriteup.setCreatedDate(tfDhiCusSerTargetWriteupDb.getCreatedDate());
        tfDhiCusSerTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfDhiCusSerTargetWriteup.setUpdatedDate(new Date());

        tfDhiCusSerTargetWriteupRepository.save(tfDhiCusSerTargetWriteup);
        TfDhiCusSerTargetWriteup_a tfDhiCusSerTargetWriteup_a = new ModelMapper().map(tfDhiCusSerTargetWriteup, TfDhiCusSerTargetWriteup_a.class);
        tfDhiCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiCusSerTargetWriteup_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
        tfDhiCusSerTargetWriteup_aRepository.save(tfDhiCusSerTargetWriteup_a);

        //delete all sub targets by targerId first and save below
        for (CustomerServiceSubTargetDto subTargetDto : customerServiceTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfDhiCusSerSubTarget tfDhiCusSerSubTargetDb = tfDhiCusSerSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            TfDhiCusSerSubTarget tfDhiCusSerSubTarget = new ModelMapper().map(subTargetDto, TfDhiCusSerSubTarget.class);
            if (tfDhiCusSerSubTarget.getSubTargetId() == null) {
                tfDhiCusSerSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfDhiCusSerSubTarget.setCreatedBy(currentUser.getUserId());
                tfDhiCusSerSubTarget.setCreatedDate(new Date());
                tfDhiCusSerSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfDhiCusSerSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfDhiCusSerSubTarget.setCreatedBy(tfDhiCusSerSubTargetDb.getCreatedBy());
                tfDhiCusSerSubTarget.setCreatedDate(tfDhiCusSerSubTargetDb.getCreatedDate());
                tfDhiCusSerSubTarget.setUpdatedBy(currentUser.getUserId());
                tfDhiCusSerSubTarget.setCreatedDate(new Date());
            }
            tfDhiCusSerSubTarget.setTargetId(tfDhiCusSerTargetActivity.getTargetId());

            tfDhiCusSerSubTargetRepository.save(tfDhiCusSerSubTarget);
            TfDhiCusSerSubTarget_a tfDhiCusSerSubTarget_a = new ModelMapper().map(tfDhiCusSerSubTarget, TfDhiCusSerSubTarget_a.class);
            tfDhiCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiCusSerSubTarget_a.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
            tfDhiCusSerSubTarget_aRepository.save(tfDhiCusSerSubTarget_a);

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
                        TfDhiCusSerSubTargetDoc tfDhiCusSerSubTargetDoc = new TfDhiCusSerSubTargetDoc();
                        tfDhiCusSerSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiCusSerSubTargetDoc.setSubTargetId(tfDhiCusSerSubTarget.getSubTargetId());
                        tfDhiCusSerSubTargetDoc.setFileName(filename);
                        tfDhiCusSerSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiCusSerSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiCusSerSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiCusSerSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiCusSerSubTargetDoc.setCreatedDate(new Date());
                        tfDhiCusSerSubTargetDocRepository.save(tfDhiCusSerSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfDhiCusSerTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfDhiCusSerEditTargetDao.getTargetVersionNo(targetId);
        versionNo = versionNo == null ? BigInteger.ZERO : versionNo;
        return versionNo.add(BigInteger.ONE);
    }

    private ResponseMessage validateBeforeEdit(CurrentUser currentUser, CustomerServiceTargetDto customerServiceTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = customerServiceTargetDto.getYear();
        String companyId = customerServiceTargetDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        Integer creatorId = SystemRoles.Creator.getValue();
        Integer reviewerId = SystemRoles.Reviewer.getValue();
        String myCompanyId = currentUser.getCompanyId();
        String targetId = customerServiceTargetDto.getTargetId();
        TfDhiCusSerTargetStage tfDhiCusSerTargetStage = tfDhiCusSerTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfDhiCusSerTargetStatus tfDhiCusSerTargetStatus = tfDhiCusSerTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfDhiCusSerTargetStage != null) {
                if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfDhiCusSerTargetStatus.getStatusFlag() == 'X') {
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
            if (tfDhiCusSerTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfDhiCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
