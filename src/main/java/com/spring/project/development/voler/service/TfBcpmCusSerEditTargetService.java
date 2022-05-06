package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmCusSerEditTargetDao;
import com.spring.project.development.voler.dto.CustomerServiceSubTargetDto;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.cusService.CusSerTargetComment;
import com.spring.project.development.voler.entity.cusService.formulation.bcpm.*;
import com.spring.project.development.voler.repository.cusService.CusSerTargetCommentRepository;
import com.spring.project.development.voler.repository.cusService.formulation.bcpm.*;
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
public class TfBcpmCusSerEditTargetService {
    private final TfBcpmCusSerTargetActivityRepository tfBcpmCusSerTargetActivityRepository;
    private final TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository;
    private final TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository;
    private final TfBcpmCusSerSubTarget_aRepository tfBcpmCusSerSubTarget_aRepository;
    private final TfBcpmCusSerTargetWriteupRepository tfBcpmCusSerTargetWriteupRepository;
    private final TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository;
    private final TfBcpmCusSerSubTargetDocRepository tfBcpmCusSerSubTargetDocRepository;
    private final TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository;
    private final CusSerTargetCommentRepository cusSerTargetCommentRepository;
    private final TfBcpmCusSerEditTargetDao tfBcpmCusSerEditTargetDao;
    private final TfBcpmCusSerSubTargetReviewerRemarkRepository tfBcpmCusSerSubTargetReviewerRemarkRepository;
    private final CommonService commonService;
    private final TfBcpmCusSerTargetStageRepository tfBcpmCusSerTargetStageRepository;

    public TfBcpmCusSerEditTargetService(TfBcpmCusSerTargetActivityRepository tfBcpmCusSerTargetActivityRepository, TfBcpmCusSerTargetActivity_aRepository tfBcpmCusSerTargetActivity_aRepository, TfBcpmCusSerSubTargetRepository tfBcpmCusSerSubTargetRepository, TfBcpmCusSerSubTarget_aRepository tfBcpmCusSerSubTarget_aRepository, TfBcpmCusSerTargetWriteupRepository tfBcpmCusSerTargetWriteupRepository, TfBcpmCusSerTargetWriteup_aRepository tfBcpmCusSerTargetWriteup_aRepository, TfBcpmCusSerSubTargetDocRepository tfBcpmCusSerSubTargetDocRepository, TfBcpmCusSerTargetStatusRepository tfBcpmCusSerTargetStatusRepository, CusSerTargetCommentRepository cusSerTargetCommentRepository, TfBcpmCusSerEditTargetDao tfBcpmCusSerEditTargetDao, TfBcpmCusSerSubTargetReviewerRemarkRepository tfBcpmCusSerSubTargetReviewerRemarkRepository, CommonService commonService, TfBcpmCusSerTargetStageRepository tfBcpmCusSerTargetStageRepository) {
        this.tfBcpmCusSerTargetActivityRepository = tfBcpmCusSerTargetActivityRepository;
        this.tfBcpmCusSerTargetActivity_aRepository = tfBcpmCusSerTargetActivity_aRepository;
        this.tfBcpmCusSerSubTargetRepository = tfBcpmCusSerSubTargetRepository;
        this.tfBcpmCusSerSubTarget_aRepository = tfBcpmCusSerSubTarget_aRepository;
        this.tfBcpmCusSerTargetWriteupRepository = tfBcpmCusSerTargetWriteupRepository;
        this.tfBcpmCusSerTargetWriteup_aRepository = tfBcpmCusSerTargetWriteup_aRepository;
        this.tfBcpmCusSerSubTargetDocRepository = tfBcpmCusSerSubTargetDocRepository;
        this.tfBcpmCusSerTargetStatusRepository = tfBcpmCusSerTargetStatusRepository;
        this.cusSerTargetCommentRepository = cusSerTargetCommentRepository;
        this.tfBcpmCusSerEditTargetDao = tfBcpmCusSerEditTargetDao;
        this.tfBcpmCusSerSubTargetReviewerRemarkRepository = tfBcpmCusSerSubTargetReviewerRemarkRepository;
        this.commonService = commonService;
        this.tfBcpmCusSerTargetStageRepository = tfBcpmCusSerTargetStageRepository;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        CustomerServiceTargetDto customerServiceTargetDto = tfBcpmCusSerEditTargetDao.getTarget(targetAuditId);
        if (customerServiceTargetDto != null) {
            responseMessage.setDTO(customerServiceTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<CustomerServiceSubTargetDto> customerServiceSubTargetDtos = tfBcpmCusSerEditTargetDao.getSubTarget(targetAuditId);
        if (customerServiceSubTargetDtos != null) {
            responseMessage.setDTO(customerServiceSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfBcpmCusSerEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmCusSerTargetActivity_a tfBcpmCusSerTargetActivity_a = tfBcpmCusSerTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
        TfBcpmCusSerTargetWriteup_a tfBcpmCusSerTargetWriteup_a = tfBcpmCusSerTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmCusSerTargetActivity_a != null) {
            responseMessage.setDTO(tfBcpmCusSerTargetActivity_a);
            responseMessage.setWriteupDto(tfBcpmCusSerTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmCusSerSubTarget_a> tfBcpmCusSerSubTarget_as = tfBcpmCusSerSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmCusSerSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfBcpmCusSerSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfBcpmCusSerEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmCusSerSubTargetDoc> tfBcpmCusSerSubTargetDocs = tfBcpmCusSerSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfBcpmCusSerSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfBcpmCusSerSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmCusSerSubTargetDoc tfBcpmCusSerSubTargetDocDb = tfBcpmCusSerSubTargetDocRepository.findByFileId(fileId);
        if (tfBcpmCusSerSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmCusSerSubTarget tfBcpmCusSerSubTargetDb = tfBcpmCusSerSubTargetRepository.findBySubTargetId(tfBcpmCusSerSubTargetDocDb.getSubTargetId());
        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivityDb = tfBcpmCusSerTargetActivityRepository.findByTargetId(tfBcpmCusSerSubTargetDb.getTargetId());

        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(tfBcpmCusSerSubTargetDb.getTargetId());
        customerServiceTargetDto.setCompanyId(tfBcpmCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfBcpmCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmCusSerSubTargetDoc document = new TfBcpmCusSerSubTargetDoc();
        document.setFileId(fileId);
        tfBcpmCusSerSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfBcpmCusSerSubTargetDoc document = tfBcpmCusSerSubTargetDocRepository.findByFileId(fileId);
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
        TfBcpmCusSerSubTargetDoc document = tfBcpmCusSerSubTargetDocRepository.findByFileId(fileId);
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
        TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemark
                = tfBcpmCusSerSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfBcpmCusSerSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfBcpmCusSerSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfBcpmCusSerSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfBcpmCusSerSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmCusSerSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerSubTargetReviewerRemark.setCreatedDate(new Date());
        tfBcpmCusSerSubTargetReviewerRemarkRepository.save(tfBcpmCusSerSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser,TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfBcpmCusSerSubTargetReviewerRemark tfBcpmCusSerSubTargetReviewerRemarkDb
                = tfBcpmCusSerSubTargetReviewerRemarkRepository.findByRemarkId(tfBcpmCusSerSubTargetReviewerRemark.getRemarkId());
        tfBcpmCusSerSubTargetReviewerRemark.setSubTargetId(tfBcpmCusSerSubTargetReviewerRemarkDb.getSubTargetId());
        tfBcpmCusSerSubTargetReviewerRemark.setCreatedBy(tfBcpmCusSerSubTargetReviewerRemarkDb.getCreatedBy());
        tfBcpmCusSerSubTargetReviewerRemark.setCreatedDate(tfBcpmCusSerSubTargetReviewerRemarkDb.getCreatedDate());
        tfBcpmCusSerSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmCusSerSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfBcpmCusSerSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfBcpmCusSerSubTargetReviewerRemarkRepository.save(tfBcpmCusSerSubTargetReviewerRemark);
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
        TfBcpmCusSerTargetStatus tfBcpmCusSerTargetStatus = new TfBcpmCusSerTargetStatus();
        tfBcpmCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStatus.setTargetId(targetId);
        tfBcpmCusSerTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfBcpmCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStatus.setCreatedDate(new Date());
        tfBcpmCusSerTargetStatusRepository.save(tfBcpmCusSerTargetStatus);
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
        TfBcpmCusSerTargetStatus tfBcpmCusSerTargetStatus = new TfBcpmCusSerTargetStatus();
        tfBcpmCusSerTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetStatus.setTargetId(targetId);
        tfBcpmCusSerTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfBcpmCusSerTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetStatus.setCreatedDate(new Date());
        tfBcpmCusSerTargetStatusRepository.save(tfBcpmCusSerTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivityDb = tfBcpmCusSerTargetActivityRepository.findByTargetId(targetId);
        if (tfBcpmCusSerTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(targetId);
        customerServiceTargetDto.setCompanyId(tfBcpmCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfBcpmCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivity = new TfBcpmCusSerTargetActivity();
        tfBcpmCusSerTargetActivity.setTargetId(targetId);
        tfBcpmCusSerTargetActivityRepository.delete(tfBcpmCusSerTargetActivity);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmCusSerSubTarget tfBcpmCusSerSubTargetDb = tfBcpmCusSerSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfBcpmCusSerSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivityDb = tfBcpmCusSerTargetActivityRepository.findByTargetId(tfBcpmCusSerSubTargetDb.getTargetId());
        CustomerServiceTargetDto customerServiceTargetDto = new CustomerServiceTargetDto();
        customerServiceTargetDto.setTargetId(tfBcpmCusSerSubTargetDb.getTargetId());
        customerServiceTargetDto.setCompanyId(tfBcpmCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfBcpmCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmCusSerTargetActivity_a tfBcpmCusSerTargetActivity_a = new ModelMapper().map(tfBcpmCusSerTargetActivityDb, TfBcpmCusSerTargetActivity_a.class);
        tfBcpmCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetActivity_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmCusSerTargetActivity_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetActivity_a.setUpdatedDate(new Date());
        tfBcpmCusSerTargetActivity_aRepository.save(tfBcpmCusSerTargetActivity_a);

        TfBcpmCusSerTargetWriteup tfBcpmCusSerTargetWriteupDb = tfBcpmCusSerTargetWriteupRepository.findByTargetId(tfBcpmCusSerSubTargetDb.getTargetId());
        TfBcpmCusSerTargetWriteup_a tfBcpmCusSerTargetWriteup_a = new ModelMapper().map(tfBcpmCusSerTargetWriteupDb, TfBcpmCusSerTargetWriteup_a.class);
        tfBcpmCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetWriteup_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
        tfBcpmCusSerTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmCusSerTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetWriteup_a.setUpdatedDate(new Date());
        tfBcpmCusSerTargetWriteup_aRepository.save(tfBcpmCusSerTargetWriteup_a);

        TfBcpmCusSerSubTarget tfBcpmCusSerSubTargetDelete = new TfBcpmCusSerSubTarget();
        tfBcpmCusSerSubTargetDelete.setSubTargetId(subTargetId);
        tfBcpmCusSerSubTargetRepository.delete(tfBcpmCusSerSubTargetDelete);

        List<TfBcpmCusSerSubTarget> tfBcpmCusSerSubTargets = tfBcpmCusSerSubTargetRepository.findByTargetId(tfBcpmCusSerTargetActivityDb.getTargetId());
        for (TfBcpmCusSerSubTarget tfBcpmCusSerSubTarget : tfBcpmCusSerSubTargets) {
            TfBcpmCusSerSubTarget_a tfBcpmCusSerSubTarget_a = new ModelMapper().map(tfBcpmCusSerSubTarget, TfBcpmCusSerSubTarget_a.class);
            tfBcpmCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmCusSerSubTarget_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
            tfBcpmCusSerSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfBcpmCusSerSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfBcpmCusSerSubTarget_a.setUpdatedDate(new Date());
            tfBcpmCusSerSubTarget_aRepository.save(tfBcpmCusSerSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, CustomerServiceTargetDto customerServiceTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivityDb = tfBcpmCusSerTargetActivityRepository.findByTargetId(customerServiceTargetDto.getTargetId());
        if (tfBcpmCusSerTargetActivityDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        customerServiceTargetDto.setCompanyId(tfBcpmCusSerTargetActivityDb.getCompanyId());
        customerServiceTargetDto.setYear(tfBcpmCusSerTargetActivityDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, customerServiceTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmCusSerTargetActivity tfBcpmCusSerTargetActivity = new ModelMapper().map(customerServiceTargetDto, TfBcpmCusSerTargetActivity.class);

        tfBcpmCusSerTargetActivity.setCompanyId(tfBcpmCusSerTargetActivityDb.getCompanyId());
        tfBcpmCusSerTargetActivity.setYear(tfBcpmCusSerTargetActivityDb.getYear());
        BigInteger versionNo = getTargetVersionNo(customerServiceTargetDto.getTargetId());
        tfBcpmCusSerTargetActivity.setVersionNo(versionNo);
        tfBcpmCusSerTargetActivity.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmCusSerTargetActivity.setCreatedBy(tfBcpmCusSerTargetActivityDb.getCreatedBy());
        tfBcpmCusSerTargetActivity.setCreatedDate(tfBcpmCusSerTargetActivityDb.getCreatedDate());
        tfBcpmCusSerTargetActivity.setUpdatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetActivity.setUpdatedDate(new Date());
        tfBcpmCusSerTargetActivityRepository.save(tfBcpmCusSerTargetActivity);

        TfBcpmCusSerTargetActivity_a tfBcpmCusSerTargetActivity_a = new ModelMapper().map(tfBcpmCusSerTargetActivity, TfBcpmCusSerTargetActivity_a.class);
        tfBcpmCusSerTargetActivity_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetActivity_aRepository.save(tfBcpmCusSerTargetActivity_a);

        TfBcpmCusSerTargetWriteup tfBcpmCusSerTargetWriteupDb = tfBcpmCusSerTargetWriteupRepository.findByTargetId(customerServiceTargetDto.getTargetId());
        TfBcpmCusSerTargetWriteup tfBcpmCusSerTargetWriteup = new ModelMapper().map(customerServiceTargetDto, TfBcpmCusSerTargetWriteup.class);
        tfBcpmCusSerTargetWriteup.setWriteupId(tfBcpmCusSerTargetWriteupDb.getWriteupId());
        tfBcpmCusSerTargetWriteup.setTargetId(tfBcpmCusSerTargetActivity.getTargetId());
        tfBcpmCusSerTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmCusSerTargetWriteup.setCreatedBy(tfBcpmCusSerTargetWriteupDb.getCreatedBy());
        tfBcpmCusSerTargetWriteup.setCreatedDate(tfBcpmCusSerTargetWriteupDb.getCreatedDate());
        tfBcpmCusSerTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfBcpmCusSerTargetWriteup.setUpdatedDate(new Date());

        tfBcpmCusSerTargetWriteupRepository.save(tfBcpmCusSerTargetWriteup);
        TfBcpmCusSerTargetWriteup_a tfBcpmCusSerTargetWriteup_a = new ModelMapper().map(tfBcpmCusSerTargetWriteup, TfBcpmCusSerTargetWriteup_a.class);
        tfBcpmCusSerTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmCusSerTargetWriteup_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
        tfBcpmCusSerTargetWriteup_aRepository.save(tfBcpmCusSerTargetWriteup_a);

        //delete all sub targets by targerId first and save below
        for (CustomerServiceSubTargetDto subTargetDto : customerServiceTargetDto.getSubTargetDtos()) {
            if (subTargetDto.getIsNegativeBoolean() == null) {
                subTargetDto.setIsNegative('N');
            } else {
                subTargetDto.setIsNegative('Y');
            }
            TfBcpmCusSerSubTarget tfBcpmCusSerSubTargetDb = tfBcpmCusSerSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            TfBcpmCusSerSubTarget tfBcpmCusSerSubTarget = new ModelMapper().map(subTargetDto, TfBcpmCusSerSubTarget.class);
            if (tfBcpmCusSerSubTarget.getSubTargetId() == null) {
                tfBcpmCusSerSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfBcpmCusSerSubTarget.setCreatedBy(currentUser.getUserId());
                tfBcpmCusSerSubTarget.setCreatedDate(new Date());
                tfBcpmCusSerSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfBcpmCusSerSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfBcpmCusSerSubTarget.setCreatedBy(tfBcpmCusSerSubTargetDb.getCreatedBy());
                tfBcpmCusSerSubTarget.setCreatedDate(tfBcpmCusSerSubTargetDb.getCreatedDate());
                tfBcpmCusSerSubTarget.setUpdatedBy(currentUser.getUserId());
                tfBcpmCusSerSubTarget.setCreatedDate(new Date());
            }
            tfBcpmCusSerSubTarget.setTargetId(tfBcpmCusSerTargetActivity.getTargetId());

            tfBcpmCusSerSubTargetRepository.save(tfBcpmCusSerSubTarget);
            TfBcpmCusSerSubTarget_a tfBcpmCusSerSubTarget_a = new ModelMapper().map(tfBcpmCusSerSubTarget, TfBcpmCusSerSubTarget_a.class);
            tfBcpmCusSerSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmCusSerSubTarget_a.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
            tfBcpmCusSerSubTarget_aRepository.save(tfBcpmCusSerSubTarget_a);

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
                        TfBcpmCusSerSubTargetDoc tfBcpmCusSerSubTargetDoc = new TfBcpmCusSerSubTargetDoc();
                        tfBcpmCusSerSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmCusSerSubTargetDoc.setSubTargetId(tfBcpmCusSerSubTarget.getSubTargetId());
                        tfBcpmCusSerSubTargetDoc.setFileName(filename);
                        tfBcpmCusSerSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmCusSerSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmCusSerSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmCusSerSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmCusSerSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmCusSerSubTargetDocRepository.save(tfBcpmCusSerSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfBcpmCusSerTargetActivity_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfBcpmCusSerEditTargetDao.getTargetVersionNo(targetId);
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
        TfBcpmCusSerTargetStage tfBcpmCusSerTargetStage = tfBcpmCusSerTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfBcpmCusSerTargetStatus tfBcpmCusSerTargetStatus = tfBcpmCusSerTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfBcpmCusSerTargetStage != null) {
                if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfBcpmCusSerTargetStatus.getStatusFlag() == 'X') {
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
            if (tfBcpmCusSerTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfBcpmCusSerTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
