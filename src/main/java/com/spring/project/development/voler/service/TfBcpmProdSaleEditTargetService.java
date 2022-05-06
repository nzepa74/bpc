package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfBcpmProdSaleEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.ProdSaleSubTargetDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.prodSale.ProdSaleTargetComment;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.*;
import com.spring.project.development.voler.repository.prodSale.ProdSaleTargetCommentRepository;
import com.spring.project.development.voler.repository.prodSale.formulation.bcpm.*;
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
public class TfBcpmProdSaleEditTargetService {
    private final TfBcpmProdSaleTargetRepository tfBcpmProdSaleTargetRepository;
    private final TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository;
    private final TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository;
    private final TfBcpmProdSaleSubTarget_aRepository tfBcpmProdSaleSubTarget_aRepository;
    private final TfBcpmProdSaleTargetWriteupRepository tfBcpmProdSaleTargetWriteupRepository;
    private final TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository;
    private final TfBcpmProdSaleSubTargetDocRepository tfBcpmProdSaleSubTargetDocRepository;
    private final TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository;
    private final ProdSaleTargetCommentRepository prodSaleTargetCommentRepository;
    private final TfBcpmProdSaleEditTargetDao tfBcpmProdSaleEditTargetDao;
    private final TfBcpmProdSaleSubTargetReviewerRemarkRepository tfBcpmProdSaleSubTargetReviewerRemarkRepository;
    private final TfBcpmProdSaleTargetStageRepository tfBcpmProdSaleTargetStageRepository;
    private final CommonService commonService;

    public TfBcpmProdSaleEditTargetService(TfBcpmProdSaleTargetRepository tfBcpmProdSaleTargetRepository, TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository, TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository, TfBcpmProdSaleSubTarget_aRepository tfBcpmProdSaleSubTarget_aRepository, TfBcpmProdSaleTargetWriteupRepository tfBcpmProdSaleTargetWriteupRepository, TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository, TfBcpmProdSaleSubTargetDocRepository tfBcpmProdSaleSubTargetDocRepository, TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository, ProdSaleTargetCommentRepository prodSaleTargetCommentRepository, TfBcpmProdSaleEditTargetDao tfBcpmProdSaleEditTargetDao, TfBcpmProdSaleSubTargetReviewerRemarkRepository tfBcpmProdSaleSubTargetReviewerRemarkRepository, TfBcpmProdSaleTargetStageRepository tfBcpmProdSaleTargetStageRepository, CommonService commonService) {
        this.tfBcpmProdSaleTargetRepository = tfBcpmProdSaleTargetRepository;
        this.tfBcpmProdSaleTarget_aRepository = tfBcpmProdSaleTarget_aRepository;
        this.tfBcpmProdSaleSubTargetRepository = tfBcpmProdSaleSubTargetRepository;
        this.tfBcpmProdSaleSubTarget_aRepository = tfBcpmProdSaleSubTarget_aRepository;
        this.tfBcpmProdSaleTargetWriteupRepository = tfBcpmProdSaleTargetWriteupRepository;
        this.tfBcpmProdSaleTargetWriteup_aRepository = tfBcpmProdSaleTargetWriteup_aRepository;
        this.tfBcpmProdSaleSubTargetDocRepository = tfBcpmProdSaleSubTargetDocRepository;
        this.tfBcpmProdSaleTargetStatusRepository = tfBcpmProdSaleTargetStatusRepository;
        this.prodSaleTargetCommentRepository = prodSaleTargetCommentRepository;
        this.tfBcpmProdSaleEditTargetDao = tfBcpmProdSaleEditTargetDao;
        this.tfBcpmProdSaleSubTargetReviewerRemarkRepository = tfBcpmProdSaleSubTargetReviewerRemarkRepository;
        this.tfBcpmProdSaleTargetStageRepository = tfBcpmProdSaleTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        ProdSaleTargetDto prodSaleTargetDto = tfBcpmProdSaleEditTargetDao.getTarget(targetAuditId);
        if (prodSaleTargetDto != null) {
            responseMessage.setDTO(prodSaleTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleSubTargetDto> prodSaleSubTargetDtos = tfBcpmProdSaleEditTargetDao.getSubTarget(targetAuditId);
        if (prodSaleSubTargetDtos != null) {
            responseMessage.setDTO(prodSaleSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfBcpmProdSaleEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmProdSaleTarget_a tfBcpmProdSaleTarget_a = tfBcpmProdSaleTarget_aRepository.findByTargetAuditId(targetAuditId);
        TfBcpmProdSaleTargetWriteup_a tfBcpmProdSaleTargetWriteup_a = tfBcpmProdSaleTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmProdSaleTarget_a != null) {
            responseMessage.setDTO(tfBcpmProdSaleTarget_a);
            responseMessage.setWriteupDto(tfBcpmProdSaleTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmProdSaleSubTarget_a> tfBcpmProdSaleSubTarget_as = tfBcpmProdSaleSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmProdSaleSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfBcpmProdSaleSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfBcpmProdSaleEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfBcpmProdSaleSubTargetDoc> tfBcpmProdSaleSubTargetDocs = tfBcpmProdSaleSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfBcpmProdSaleSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfBcpmProdSaleSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmProdSaleSubTargetDoc tfBcpmProdSaleSubTargetDocDb = tfBcpmProdSaleSubTargetDocRepository.findByFileId(fileId);
        if (tfBcpmProdSaleSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTargetDb = tfBcpmProdSaleSubTargetRepository.findBySubTargetId(tfBcpmProdSaleSubTargetDocDb.getSubTargetId());
        TfBcpmProdSaleTarget tfBcpmProdSaleTargetDb = tfBcpmProdSaleTargetRepository.findByTargetId(tfBcpmProdSaleSubTargetDb.getTargetId());

        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(tfBcpmProdSaleSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfBcpmProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmProdSaleSubTargetDoc document = new TfBcpmProdSaleSubTargetDoc();
        document.setFileId(fileId);
        tfBcpmProdSaleSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfBcpmProdSaleSubTargetDoc document = tfBcpmProdSaleSubTargetDocRepository.findByFileId(fileId);
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
        TfBcpmProdSaleSubTargetDoc document = tfBcpmProdSaleSubTargetDocRepository.findByFileId(fileId);
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
        TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemark
                = tfBcpmProdSaleSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfBcpmProdSaleSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfBcpmProdSaleSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfBcpmProdSaleSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfBcpmProdSaleSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfBcpmProdSaleSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleSubTargetReviewerRemark.setCreatedDate(new Date());
        tfBcpmProdSaleSubTargetReviewerRemarkRepository.save(tfBcpmProdSaleSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemarkDb
                = tfBcpmProdSaleSubTargetReviewerRemarkRepository.findByRemarkId(tfBcpmProdSaleSubTargetReviewerRemark.getRemarkId());
        tfBcpmProdSaleSubTargetReviewerRemark.setSubTargetId(tfBcpmProdSaleSubTargetReviewerRemarkDb.getSubTargetId());
        tfBcpmProdSaleSubTargetReviewerRemark.setCreatedBy(tfBcpmProdSaleSubTargetReviewerRemarkDb.getCreatedBy());
        tfBcpmProdSaleSubTargetReviewerRemark.setCreatedDate(tfBcpmProdSaleSubTargetReviewerRemarkDb.getCreatedDate());
        tfBcpmProdSaleSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmProdSaleSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfBcpmProdSaleSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfBcpmProdSaleSubTargetReviewerRemarkRepository.save(tfBcpmProdSaleSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addComment(CurrentUser currentUser, ProdSaleTargetComment prodSaleTargetComment) {
        ResponseMessage responseMessage = new ResponseMessage();
        prodSaleTargetComment.setCommentId(UuidGenerator.generateUuid());
        prodSaleTargetComment.setCreatedBy(currentUser.getUserId());
        prodSaleTargetComment.setCreatedDate(new Date());
        prodSaleTargetCommentRepository.save(prodSaleTargetComment);
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
        TfBcpmProdSaleTargetStatus tfBcpmProdSaleTargetStatus = new TfBcpmProdSaleTargetStatus();
        tfBcpmProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStatus.setTargetId(targetId);
        tfBcpmProdSaleTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfBcpmProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStatus.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStatusRepository.save(tfBcpmProdSaleTargetStatus);
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
        TfBcpmProdSaleTargetStatus tfBcpmProdSaleTargetStatus = new TfBcpmProdSaleTargetStatus();
        tfBcpmProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStatus.setTargetId(targetId);
        tfBcpmProdSaleTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfBcpmProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStatus.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStatusRepository.save(tfBcpmProdSaleTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmProdSaleTarget tfBcpmProdSaleTargetDb = tfBcpmProdSaleTargetRepository.findByTargetId(targetId);
        if (tfBcpmProdSaleTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(targetId);
        orgMgtTargetDto.setCompanyId(tfBcpmProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfBcpmProdSaleTarget tfBcpmProdSaleTarget = new TfBcpmProdSaleTarget();
        tfBcpmProdSaleTarget.setTargetId(targetId);
        tfBcpmProdSaleTargetRepository.delete(tfBcpmProdSaleTarget);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTargetDb = tfBcpmProdSaleSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfBcpmProdSaleSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfBcpmProdSaleTarget tfBcpmProdSaleTargetDb = tfBcpmProdSaleTargetRepository.findByTargetId(tfBcpmProdSaleSubTargetDb.getTargetId());

        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(tfBcpmProdSaleSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfBcpmProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfBcpmProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfBcpmProdSaleTarget_a tfBcpmProdSaleTarget_a = new ModelMapper().map(tfBcpmProdSaleTargetDb, TfBcpmProdSaleTarget_a.class);
        tfBcpmProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmProdSaleTarget_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmProdSaleTarget_a.setUpdatedDate(new Date());
        tfBcpmProdSaleTarget_aRepository.save(tfBcpmProdSaleTarget_a);

        TfBcpmProdSaleTargetWriteup tfBcpmProdSaleTargetWriteupDb = tfBcpmProdSaleTargetWriteupRepository.findByTargetId(tfBcpmProdSaleSubTargetDb.getTargetId());
        TfBcpmProdSaleTargetWriteup_a tfBcpmProdSaleTargetWriteup_a = new ModelMapper().map(tfBcpmProdSaleTargetWriteupDb, TfBcpmProdSaleTargetWriteup_a.class);
        tfBcpmProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetWriteup_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
        tfBcpmProdSaleTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmProdSaleTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetWriteup_a.setUpdatedDate(new Date());
        tfBcpmProdSaleTargetWriteup_aRepository.save(tfBcpmProdSaleTargetWriteup_a);

        TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTargetDelete = new TfBcpmProdSaleSubTarget();
        tfBcpmProdSaleSubTargetDelete.setSubTargetId(subTargetId);
        tfBcpmProdSaleSubTargetRepository.delete(tfBcpmProdSaleSubTargetDelete);

        List<TfBcpmProdSaleSubTarget> tfBcpmProdSaleSubTargets = tfBcpmProdSaleSubTargetRepository.findByTargetId(tfBcpmProdSaleTargetDb.getTargetId());
        for (TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTarget : tfBcpmProdSaleSubTargets) {
            TfBcpmProdSaleSubTarget_a tfBcpmProdSaleSubTarget_a = new ModelMapper().map(tfBcpmProdSaleSubTarget, TfBcpmProdSaleSubTarget_a.class);
            tfBcpmProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmProdSaleSubTarget_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
            tfBcpmProdSaleSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfBcpmProdSaleSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfBcpmProdSaleSubTarget_a.setUpdatedDate(new Date());
            tfBcpmProdSaleSubTarget_aRepository.save(tfBcpmProdSaleSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfBcpmProdSaleTarget tfBcpmProdSaleTargetDb = tfBcpmProdSaleTargetRepository.findByTargetId(prodSaleTargetDto.getTargetId());
        if (tfBcpmProdSaleTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        prodSaleTargetDto.setCompanyId(tfBcpmProdSaleTargetDb.getCompanyId());
        prodSaleTargetDto.setYear(tfBcpmProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, prodSaleTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        //if creator set bcpm proposal from tfBcpmFinTargetDb
        int myRoleId = currentUser.getRoleId();
        int creator = SystemRoles.Creator.getValue();

        TfBcpmProdSaleTarget tfBcpmProdSaleTarget = new ModelMapper().map(prodSaleTargetDto, TfBcpmProdSaleTarget.class);

        tfBcpmProdSaleTarget.setCompanyId(tfBcpmProdSaleTargetDb.getCompanyId());
        tfBcpmProdSaleTarget.setYear(tfBcpmProdSaleTargetDb.getYear());
        BigInteger versionNo = getTargetVersionNo(prodSaleTargetDto.getTargetId());
        tfBcpmProdSaleTarget.setVersionNo(versionNo);
        tfBcpmProdSaleTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmProdSaleTarget.setCreatedBy(tfBcpmProdSaleTargetDb.getCreatedBy());
        tfBcpmProdSaleTarget.setCreatedDate(tfBcpmProdSaleTargetDb.getCreatedDate());
        tfBcpmProdSaleTarget.setUpdatedBy(currentUser.getUserId());
        tfBcpmProdSaleTarget.setUpdatedDate(new Date());
        tfBcpmProdSaleTargetRepository.save(tfBcpmProdSaleTarget);

        TfBcpmProdSaleTarget_a tfBcpmProdSaleTarget_a = new ModelMapper().map(tfBcpmProdSaleTarget, TfBcpmProdSaleTarget_a.class);
        tfBcpmProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTarget_aRepository.save(tfBcpmProdSaleTarget_a);

        TfBcpmProdSaleTargetWriteup tfBcpmProdSaleTargetWriteupDb = tfBcpmProdSaleTargetWriteupRepository.findByTargetId(prodSaleTargetDto.getTargetId());
        TfBcpmProdSaleTargetWriteup tfBcpmProdSaleTargetWriteup = new ModelMapper().map(prodSaleTargetDto, TfBcpmProdSaleTargetWriteup.class);
        tfBcpmProdSaleTargetWriteup.setWriteupId(tfBcpmProdSaleTargetWriteupDb.getWriteupId());
        tfBcpmProdSaleTargetWriteup.setTargetId(tfBcpmProdSaleTarget.getTargetId());
        tfBcpmProdSaleTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfBcpmProdSaleTargetWriteup.setCreatedBy(tfBcpmProdSaleTargetWriteupDb.getCreatedBy());
        tfBcpmProdSaleTargetWriteup.setCreatedDate(tfBcpmProdSaleTargetWriteupDb.getCreatedDate());
        tfBcpmProdSaleTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetWriteup.setUpdatedDate(new Date());

        tfBcpmProdSaleTargetWriteupRepository.save(tfBcpmProdSaleTargetWriteup);
        TfBcpmProdSaleTargetWriteup_a tfBcpmProdSaleTargetWriteup_a = new ModelMapper().map(tfBcpmProdSaleTargetWriteup, TfBcpmProdSaleTargetWriteup_a.class);
        tfBcpmProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetWriteup_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
        tfBcpmProdSaleTargetWriteup_aRepository.save(tfBcpmProdSaleTargetWriteup_a);

        for (ProdSaleSubTargetDto subTargetDto : prodSaleTargetDto.getSubTargetDtos()) {
            TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTargetDb = tfBcpmProdSaleSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            if (myRoleId == creator) {
                if (tfBcpmProdSaleSubTargetDb != null) {
                    subTargetDto.setCurYearDhiProposal(tfBcpmProdSaleSubTargetDb.getCurYearDhiProposal());
                } else {
                    subTargetDto.setCurYearDhiProposal(null);
                }
            }
            TfBcpmProdSaleSubTarget tfBcpmProdSaleSubTarget = new ModelMapper().map(subTargetDto, TfBcpmProdSaleSubTarget.class);
            if (tfBcpmProdSaleSubTarget.getSubTargetId() == null) {
                tfBcpmProdSaleSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfBcpmProdSaleSubTarget.setCreatedBy(currentUser.getUserId());
                tfBcpmProdSaleSubTarget.setCreatedDate(new Date());
                tfBcpmProdSaleSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfBcpmProdSaleSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfBcpmProdSaleSubTarget.setCreatedBy(tfBcpmProdSaleSubTargetDb.getCreatedBy());
                tfBcpmProdSaleSubTarget.setCreatedDate(tfBcpmProdSaleSubTargetDb.getCreatedDate());
                tfBcpmProdSaleSubTarget.setUpdatedBy(currentUser.getUserId());
                tfBcpmProdSaleSubTarget.setCreatedDate(new Date());
            }
            tfBcpmProdSaleSubTarget.setTargetId(tfBcpmProdSaleTarget.getTargetId());

            tfBcpmProdSaleSubTargetRepository.save(tfBcpmProdSaleSubTarget);
            TfBcpmProdSaleSubTarget_a tfBcpmProdSaleSubTarget_a = new ModelMapper().map(tfBcpmProdSaleSubTarget, TfBcpmProdSaleSubTarget_a.class);
            tfBcpmProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmProdSaleSubTarget_a.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
            tfBcpmProdSaleSubTarget_aRepository.save(tfBcpmProdSaleSubTarget_a);

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
                        TfBcpmProdSaleSubTargetDoc tfBcpmProdSaleSubTargetDoc = new TfBcpmProdSaleSubTargetDoc();
                        tfBcpmProdSaleSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfBcpmProdSaleSubTargetDoc.setSubTargetId(tfBcpmProdSaleSubTarget.getSubTargetId());
                        tfBcpmProdSaleSubTargetDoc.setFileName(filename);
                        tfBcpmProdSaleSubTargetDoc.setFileExtension(fileExtension);
                        tfBcpmProdSaleSubTargetDoc.setFileUrl(fileUrl);
                        tfBcpmProdSaleSubTargetDoc.setFileSize(fileSize.toString());
                        tfBcpmProdSaleSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfBcpmProdSaleSubTargetDoc.setCreatedDate(new Date());
                        tfBcpmProdSaleSubTargetDocRepository.save(tfBcpmProdSaleSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfBcpmProdSaleTarget_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfBcpmProdSaleEditTargetDao.getTargetVersionNo(targetId);
        versionNo = versionNo == null ? BigInteger.ZERO : versionNo;
        return versionNo.add(BigInteger.ONE);
    }

    private ResponseMessage validateBeforeEdit(CurrentUser currentUser, ProdSaleTargetDto prodSaleTargetDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = prodSaleTargetDto.getYear();
        String companyId = prodSaleTargetDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        Integer creatorId = SystemRoles.Creator.getValue();
        Integer reviewerId = SystemRoles.Reviewer.getValue();
        String myCompanyId = currentUser.getCompanyId();
        String targetId = prodSaleTargetDto.getTargetId();
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfBcpmProdSaleTargetStatus tfBcpmProdSaleTargetStatus = tfBcpmProdSaleTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfBcpmProdSaleTargetStage != null) {
                if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfBcpmProdSaleTargetStatus.getStatusFlag() == 'X') {
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
            if (tfBcpmProdSaleTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
