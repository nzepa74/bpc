package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.dto.FileUploadDTO;
import com.spring.project.development.voler.dao.TfDhiProdSaleEditTargetDao;
import com.spring.project.development.voler.dto.EditHistoryDto;
import com.spring.project.development.voler.dto.ProdSaleSubTargetDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.TargetStatusDto;
import com.spring.project.development.voler.entity.prodSale.ProdSaleTargetComment;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.*;
import com.spring.project.development.voler.repository.prodSale.ProdSaleTargetCommentRepository;
import com.spring.project.development.voler.repository.prodSale.formulation.dhi.*;
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
public class TfDhiProdSaleEditTargetService {
    private final TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository;
    private final TfDhiProdSaleTarget_aRepository tfDhiProdSaleTarget_aRepository;
    private final TfDhiProdSaleSubTargetRepository tfDhiProdSaleSubTargetRepository;
    private final TfDhiProdSaleSubTarget_aRepository tfDhiProdSaleSubTarget_aRepository;
    private final TfDhiProdSaleTargetWriteupRepository tfDhiProdSaleTargetWriteupRepository;
    private final TfDhiProdSaleTargetWriteup_aRepository tfDhiProdSaleTargetWriteup_aRepository;
    private final TfDhiProdSaleSubTargetDocRepository tfDhiProdSaleSubTargetDocRepository;
    private final TfDhiProdSaleTargetStatusRepository tfDhiProdSaleTargetStatusRepository;
    private final ProdSaleTargetCommentRepository prodSaleTargetCommentRepository;
    private final TfDhiProdSaleEditTargetDao tfDhiProdSaleEditTargetDao;
    private final TfDhiProdSaleSubTargetReviewerRemarkRepository tfDhiProdSaleSubTargetReviewerRemarkRepository;
    private final TfDhiProdSaleTargetStageRepository tfDhiProdSaleTargetStageRepository;
    private final CommonService commonService;

    public TfDhiProdSaleEditTargetService(TfDhiProdSaleTargetRepository tfDhiProdSaleTargetRepository, TfDhiProdSaleTarget_aRepository tfDhiProdSaleTarget_aRepository, TfDhiProdSaleSubTargetRepository tfDhiProdSaleSubTargetRepository, TfDhiProdSaleSubTarget_aRepository tfDhiProdSaleSubTarget_aRepository, TfDhiProdSaleTargetWriteupRepository tfDhiProdSaleTargetWriteupRepository, TfDhiProdSaleTargetWriteup_aRepository tfDhiProdSaleTargetWriteup_aRepository, TfDhiProdSaleSubTargetDocRepository tfDhiProdSaleSubTargetDocRepository, TfDhiProdSaleTargetStatusRepository tfDhiProdSaleTargetStatusRepository, ProdSaleTargetCommentRepository prodSaleTargetCommentRepository, TfDhiProdSaleEditTargetDao tfDhiProdSaleEditTargetDao, TfDhiProdSaleSubTargetReviewerRemarkRepository tfDhiProdSaleSubTargetReviewerRemarkRepository, TfDhiProdSaleTargetStageRepository tfDhiProdSaleTargetStageRepository, CommonService commonService) {
        this.tfDhiProdSaleTargetRepository = tfDhiProdSaleTargetRepository;
        this.tfDhiProdSaleTarget_aRepository = tfDhiProdSaleTarget_aRepository;
        this.tfDhiProdSaleSubTargetRepository = tfDhiProdSaleSubTargetRepository;
        this.tfDhiProdSaleSubTarget_aRepository = tfDhiProdSaleSubTarget_aRepository;
        this.tfDhiProdSaleTargetWriteupRepository = tfDhiProdSaleTargetWriteupRepository;
        this.tfDhiProdSaleTargetWriteup_aRepository = tfDhiProdSaleTargetWriteup_aRepository;
        this.tfDhiProdSaleSubTargetDocRepository = tfDhiProdSaleSubTargetDocRepository;
        this.tfDhiProdSaleTargetStatusRepository = tfDhiProdSaleTargetStatusRepository;
        this.prodSaleTargetCommentRepository = prodSaleTargetCommentRepository;
        this.tfDhiProdSaleEditTargetDao = tfDhiProdSaleEditTargetDao;
        this.tfDhiProdSaleSubTargetReviewerRemarkRepository = tfDhiProdSaleSubTargetReviewerRemarkRepository;
        this.tfDhiProdSaleTargetStageRepository = tfDhiProdSaleTargetStageRepository;
        this.commonService = commonService;
    }

    public ResponseMessage getTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        ProdSaleTargetDto prodSaleTargetDto = tfDhiProdSaleEditTargetDao.getTarget(targetAuditId);
        if (prodSaleTargetDto != null) {
            responseMessage.setDTO(prodSaleTargetDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleSubTargetDto> prodSaleSubTargetDtos = tfDhiProdSaleEditTargetDao.getSubTarget(targetAuditId);
        if (prodSaleSubTargetDtos != null) {
            responseMessage.setDTO(prodSaleSubTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetEditHistory(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<EditHistoryDto> editHistoryDtos = tfDhiProdSaleEditTargetDao.getTargetEditHistory(targetId);
        if (editHistoryDtos != null) {
            responseMessage.setDTO(editHistoryDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiProdSaleTarget_a tfDhiProdSaleTarget_a = tfDhiProdSaleTarget_aRepository.findByTargetAuditId(targetAuditId);
        TfDhiProdSaleTargetWriteup_a tfDhiProdSaleTargetWriteup_a = tfDhiProdSaleTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiProdSaleTarget_a != null) {
            responseMessage.setDTO(tfDhiProdSaleTarget_a);
            responseMessage.setWriteupDto(tfDhiProdSaleTargetWriteup_a);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiProdSaleSubTarget_a> tfDhiProdSaleSubTarget_as = tfDhiProdSaleSubTarget_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiProdSaleSubTarget_as.size() > 0) {
            responseMessage.setDTO(tfDhiProdSaleSubTarget_as);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getTargetStatus(String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStatusDto> targetStatusDtos = tfDhiProdSaleEditTargetDao.getTargetStatus(targetId);
        if (targetStatusDtos != null) {
            responseMessage.setDTO(targetStatusDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAttachments(String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TfDhiProdSaleSubTargetDoc> tfDhiProdSaleSubTargetDocs = tfDhiProdSaleSubTargetDocRepository.findBySubTargetId(subTargetId);
        if (tfDhiProdSaleSubTargetDocs.size() > 0) {
            responseMessage.setDTO(tfDhiProdSaleSubTargetDocs);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteFile(CurrentUser currentUser, String fileId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiProdSaleSubTargetDoc tfDhiProdSaleSubTargetDocDb = tfDhiProdSaleSubTargetDocRepository.findByFileId(fileId);
        if (tfDhiProdSaleSubTargetDocDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiProdSaleSubTarget tfDhiProdSaleSubTargetDb = tfDhiProdSaleSubTargetRepository.findBySubTargetId(tfDhiProdSaleSubTargetDocDb.getSubTargetId());
        TfDhiProdSaleTarget tfDhiProdSaleTargetDb = tfDhiProdSaleTargetRepository.findByTargetId(tfDhiProdSaleSubTargetDb.getTargetId());

        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(tfDhiProdSaleSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfDhiProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiProdSaleSubTargetDoc document = new TfDhiProdSaleSubTargetDoc();
        document.setFileId(fileId);
        tfDhiProdSaleSubTargetDocRepository.delete(document);
        responseMessage.setText("Deleted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage viewFile(String fileId, HttpServletResponse response) throws IOException {
        ResponseMessage responseMessage;
        TfDhiProdSaleSubTargetDoc document = tfDhiProdSaleSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiProdSaleSubTargetDoc document = tfDhiProdSaleSubTargetDocRepository.findByFileId(fileId);
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
        TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemark
                = tfDhiProdSaleSubTargetReviewerRemarkRepository.findBySubTargetId(subTargetId);

        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        if (tfDhiProdSaleSubTargetReviewerRemark != null) {
            responseMessage.setDTO(tfDhiProdSaleSubTargetReviewerRemark);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());

        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage addRemark(CurrentUser currentUser, TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        tfDhiProdSaleSubTargetReviewerRemark.setRemarkId(UuidGenerator.generateUuid());
        tfDhiProdSaleSubTargetReviewerRemark.setCmdFlag(CmdFlag.CREATE.getValue());
        tfDhiProdSaleSubTargetReviewerRemark.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleSubTargetReviewerRemark.setCreatedDate(new Date());
        tfDhiProdSaleSubTargetReviewerRemarkRepository.save(tfDhiProdSaleSubTargetReviewerRemark);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Added successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editRemark(CurrentUser currentUser, TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemark) {
        ResponseMessage responseMessage = new ResponseMessage();
        TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemarkDb
                = tfDhiProdSaleSubTargetReviewerRemarkRepository.findByRemarkId(tfDhiProdSaleSubTargetReviewerRemark.getRemarkId());
        tfDhiProdSaleSubTargetReviewerRemark.setSubTargetId(tfDhiProdSaleSubTargetReviewerRemarkDb.getSubTargetId());
        tfDhiProdSaleSubTargetReviewerRemark.setCreatedBy(tfDhiProdSaleSubTargetReviewerRemarkDb.getCreatedBy());
        tfDhiProdSaleSubTargetReviewerRemark.setCreatedDate(tfDhiProdSaleSubTargetReviewerRemarkDb.getCreatedDate());
        tfDhiProdSaleSubTargetReviewerRemark.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiProdSaleSubTargetReviewerRemark.setUpdatedBy(currentUser.getUserId());
        tfDhiProdSaleSubTargetReviewerRemark.setUpdatedDate(new Date());
        tfDhiProdSaleSubTargetReviewerRemarkRepository.save(tfDhiProdSaleSubTargetReviewerRemark);
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
        TfDhiProdSaleTargetStatus tfDhiProdSaleTargetStatus = new TfDhiProdSaleTargetStatus();
        tfDhiProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetStatus.setTargetId(targetId);
        tfDhiProdSaleTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
        tfDhiProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetStatus.setCreatedDate(new Date());
        tfDhiProdSaleTargetStatusRepository.save(tfDhiProdSaleTargetStatus);
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
        TfDhiProdSaleTargetStatus tfDhiProdSaleTargetStatus = new TfDhiProdSaleTargetStatus();
        tfDhiProdSaleTargetStatus.setStatusId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetStatus.setTargetId(targetId);
        tfDhiProdSaleTargetStatus.setStatusFlag('R');// R= Reopen,X=Closed,C=Created
        tfDhiProdSaleTargetStatus.setCreatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetStatus.setCreatedDate(new Date());
        tfDhiProdSaleTargetStatusRepository.save(tfDhiProdSaleTargetStatus);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Reopened successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteTarget(CurrentUser currentUser, String targetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiProdSaleTarget tfDhiProdSaleTargetDb = tfDhiProdSaleTargetRepository.findByTargetId(targetId);
        if (tfDhiProdSaleTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(targetId);
        orgMgtTargetDto.setCompanyId(tfDhiProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        TfDhiProdSaleTarget tfDhiProdSaleTarget = new TfDhiProdSaleTarget();
        tfDhiProdSaleTarget.setTargetId(targetId);
        tfDhiProdSaleTargetRepository.delete(tfDhiProdSaleTarget);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage deleteSubTarget(CurrentUser currentUser, String subTargetId) {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if fileId is valid or not
        TfDhiProdSaleSubTarget tfDhiProdSaleSubTargetDb = tfDhiProdSaleSubTargetRepository.findBySubTargetId(subTargetId);
        if (tfDhiProdSaleSubTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        TfDhiProdSaleTarget tfDhiProdSaleTargetDb = tfDhiProdSaleTargetRepository.findByTargetId(tfDhiProdSaleSubTargetDb.getTargetId());

        ProdSaleTargetDto orgMgtTargetDto = new ProdSaleTargetDto();
        orgMgtTargetDto.setTargetId(tfDhiProdSaleSubTargetDb.getTargetId());
        orgMgtTargetDto.setCompanyId(tfDhiProdSaleTargetDb.getCompanyId());
        orgMgtTargetDto.setYear(tfDhiProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, orgMgtTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        TfDhiProdSaleTarget_a tfDhiProdSaleTarget_a = new ModelMapper().map(tfDhiProdSaleTargetDb, TfDhiProdSaleTarget_a.class);
        tfDhiProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiProdSaleTarget_a.setUpdatedBy(currentUser.getUserId());
        tfDhiProdSaleTarget_a.setUpdatedDate(new Date());
        tfDhiProdSaleTarget_aRepository.save(tfDhiProdSaleTarget_a);

        TfDhiProdSaleTargetWriteup tfDhiProdSaleTargetWriteupDb = tfDhiProdSaleTargetWriteupRepository.findByTargetId(tfDhiProdSaleSubTargetDb.getTargetId());
        TfDhiProdSaleTargetWriteup_a tfDhiProdSaleTargetWriteup_a = new ModelMapper().map(tfDhiProdSaleTargetWriteupDb, TfDhiProdSaleTargetWriteup_a.class);
        tfDhiProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetWriteup_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
        tfDhiProdSaleTargetWriteup_a.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiProdSaleTargetWriteup_a.setUpdatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetWriteup_a.setUpdatedDate(new Date());
        tfDhiProdSaleTargetWriteup_aRepository.save(tfDhiProdSaleTargetWriteup_a);

        TfDhiProdSaleSubTarget tfDhiProdSaleSubTargetDelete = new TfDhiProdSaleSubTarget();
        tfDhiProdSaleSubTargetDelete.setSubTargetId(subTargetId);
        tfDhiProdSaleSubTargetRepository.delete(tfDhiProdSaleSubTargetDelete);

        List<TfDhiProdSaleSubTarget> tfDhiProdSaleSubTargets = tfDhiProdSaleSubTargetRepository.findByTargetId(tfDhiProdSaleTargetDb.getTargetId());
        for (TfDhiProdSaleSubTarget tfDhiProdSaleSubTarget : tfDhiProdSaleSubTargets) {
            TfDhiProdSaleSubTarget_a tfDhiProdSaleSubTarget_a = new ModelMapper().map(tfDhiProdSaleSubTarget, TfDhiProdSaleSubTarget_a.class);
            tfDhiProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiProdSaleSubTarget_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
            tfDhiProdSaleSubTarget_a.setCmdFlag(CmdFlag.MODIFY.getValue());
            tfDhiProdSaleSubTarget_a.setUpdatedBy(currentUser.getUserId());
            tfDhiProdSaleSubTarget_a.setUpdatedDate(new Date());
            tfDhiProdSaleSubTarget_aRepository.save(tfDhiProdSaleSubTarget_a);
        }

        responseMessage.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Deleted successfully.");
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage editTarget(HttpServletRequest request, CurrentUser currentUser, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        ResponseMessage responseMessage = new ResponseMessage();
        //check if targetId is valid or not
        TfDhiProdSaleTarget tfDhiProdSaleTargetDb = tfDhiProdSaleTargetRepository.findByTargetId(prodSaleTargetDto.getTargetId());
        if (tfDhiProdSaleTargetDb == null) {
            responseMessage.setText("Something went wrong. Target ID doesn't exist.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        prodSaleTargetDto.setCompanyId(tfDhiProdSaleTargetDb.getCompanyId());
        prodSaleTargetDto.setYear(tfDhiProdSaleTargetDb.getYear());
        responseMessage = validateBeforeEdit(currentUser, prodSaleTargetDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }

        //if creator set dhi proposal from tfDhiFinTargetDb
        int myRoleId = currentUser.getRoleId();
        int creator = SystemRoles.Creator.getValue();

        TfDhiProdSaleTarget tfDhiProdSaleTarget = new ModelMapper().map(prodSaleTargetDto, TfDhiProdSaleTarget.class);

        tfDhiProdSaleTarget.setCompanyId(tfDhiProdSaleTargetDb.getCompanyId());
        tfDhiProdSaleTarget.setYear(tfDhiProdSaleTargetDb.getYear());
        BigInteger versionNo = getTargetVersionNo(prodSaleTargetDto.getTargetId());
        tfDhiProdSaleTarget.setVersionNo(versionNo);
        tfDhiProdSaleTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiProdSaleTarget.setCreatedBy(tfDhiProdSaleTargetDb.getCreatedBy());
        tfDhiProdSaleTarget.setCreatedDate(tfDhiProdSaleTargetDb.getCreatedDate());
        tfDhiProdSaleTarget.setUpdatedBy(currentUser.getUserId());
        tfDhiProdSaleTarget.setUpdatedDate(new Date());
        tfDhiProdSaleTargetRepository.save(tfDhiProdSaleTarget);

        TfDhiProdSaleTarget_a tfDhiProdSaleTarget_a = new ModelMapper().map(tfDhiProdSaleTarget, TfDhiProdSaleTarget_a.class);
        tfDhiProdSaleTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTarget_aRepository.save(tfDhiProdSaleTarget_a);

        TfDhiProdSaleTargetWriteup tfDhiProdSaleTargetWriteupDb = tfDhiProdSaleTargetWriteupRepository.findByTargetId(prodSaleTargetDto.getTargetId());
        TfDhiProdSaleTargetWriteup tfDhiProdSaleTargetWriteup = new ModelMapper().map(prodSaleTargetDto, TfDhiProdSaleTargetWriteup.class);
        tfDhiProdSaleTargetWriteup.setWriteupId(tfDhiProdSaleTargetWriteupDb.getWriteupId());
        tfDhiProdSaleTargetWriteup.setTargetId(tfDhiProdSaleTarget.getTargetId());
        tfDhiProdSaleTargetWriteup.setCmdFlag(CmdFlag.MODIFY.getValue());
        tfDhiProdSaleTargetWriteup.setCreatedBy(tfDhiProdSaleTargetWriteupDb.getCreatedBy());
        tfDhiProdSaleTargetWriteup.setCreatedDate(tfDhiProdSaleTargetWriteupDb.getCreatedDate());
        tfDhiProdSaleTargetWriteup.setUpdatedBy(currentUser.getUserId());
        tfDhiProdSaleTargetWriteup.setUpdatedDate(new Date());

        tfDhiProdSaleTargetWriteupRepository.save(tfDhiProdSaleTargetWriteup);
        TfDhiProdSaleTargetWriteup_a tfDhiProdSaleTargetWriteup_a = new ModelMapper().map(tfDhiProdSaleTargetWriteup, TfDhiProdSaleTargetWriteup_a.class);
        tfDhiProdSaleTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
        tfDhiProdSaleTargetWriteup_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
        tfDhiProdSaleTargetWriteup_aRepository.save(tfDhiProdSaleTargetWriteup_a);

        for (ProdSaleSubTargetDto subTargetDto : prodSaleTargetDto.getSubTargetDtos()) {
            TfDhiProdSaleSubTarget tfDhiProdSaleSubTargetDb = tfDhiProdSaleSubTargetRepository.findBySubTargetId(subTargetDto.getSubTargetId());
            if (myRoleId == creator) {
                if (tfDhiProdSaleSubTargetDb != null) {
                    subTargetDto.setCurYearDhiProposal(tfDhiProdSaleSubTargetDb.getCurYearDhiProposal());
                } else {
                    subTargetDto.setCurYearDhiProposal(null);
                }
            }
            TfDhiProdSaleSubTarget tfDhiProdSaleSubTarget = new ModelMapper().map(subTargetDto, TfDhiProdSaleSubTarget.class);
            if (tfDhiProdSaleSubTarget.getSubTargetId() == null) {
                tfDhiProdSaleSubTarget.setSubTargetId(UuidGenerator.generateUuid());
                tfDhiProdSaleSubTarget.setCreatedBy(currentUser.getUserId());
                tfDhiProdSaleSubTarget.setCreatedDate(new Date());
                tfDhiProdSaleSubTarget.setCmdFlag(CmdFlag.CREATE.getValue());
            } else {
                tfDhiProdSaleSubTarget.setCmdFlag(CmdFlag.MODIFY.getValue());
                tfDhiProdSaleSubTarget.setCreatedBy(tfDhiProdSaleSubTargetDb.getCreatedBy());
                tfDhiProdSaleSubTarget.setCreatedDate(tfDhiProdSaleSubTargetDb.getCreatedDate());
                tfDhiProdSaleSubTarget.setUpdatedBy(currentUser.getUserId());
                tfDhiProdSaleSubTarget.setCreatedDate(new Date());
            }
            tfDhiProdSaleSubTarget.setTargetId(tfDhiProdSaleTarget.getTargetId());

            tfDhiProdSaleSubTargetRepository.save(tfDhiProdSaleSubTarget);
            TfDhiProdSaleSubTarget_a tfDhiProdSaleSubTarget_a = new ModelMapper().map(tfDhiProdSaleSubTarget, TfDhiProdSaleSubTarget_a.class);
            tfDhiProdSaleSubTarget_a.setSubTargetAuditId(UuidGenerator.generateUuid());
            tfDhiProdSaleSubTarget_a.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
            tfDhiProdSaleSubTarget_aRepository.save(tfDhiProdSaleSubTarget_a);

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
                        TfDhiProdSaleSubTargetDoc tfDhiProdSaleSubTargetDoc = new TfDhiProdSaleSubTargetDoc();
                        tfDhiProdSaleSubTargetDoc.setFileId(UuidGenerator.generateUuid());
                        tfDhiProdSaleSubTargetDoc.setSubTargetId(tfDhiProdSaleSubTarget.getSubTargetId());
                        tfDhiProdSaleSubTargetDoc.setFileName(filename);
                        tfDhiProdSaleSubTargetDoc.setFileExtension(fileExtension);
                        tfDhiProdSaleSubTargetDoc.setFileUrl(fileUrl);
                        tfDhiProdSaleSubTargetDoc.setFileSize(fileSize.toString());
                        tfDhiProdSaleSubTargetDoc.setCreatedBy(currentUser.getUserId());
                        tfDhiProdSaleSubTargetDoc.setCreatedDate(new Date());
                        tfDhiProdSaleSubTargetDocRepository.save(tfDhiProdSaleSubTargetDoc);
                    }
                }
            }
        }

        responseMessage.setTargetAuditId(tfDhiProdSaleTarget_a.getTargetAuditId());
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("Updated successfully.");
        return responseMessage;
    }

    private BigInteger getTargetVersionNo(String targetId) {
        BigInteger versionNo = tfDhiProdSaleEditTargetDao.getTargetVersionNo(targetId);
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
        TfDhiProdSaleTargetStage tfDhiProdSaleTargetStage = tfDhiProdSaleTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        TfDhiProdSaleTargetStatus tfDhiProdSaleTargetStatus = tfDhiProdSaleTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
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
            if (tfDhiProdSaleTargetStage != null) {
                if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target already submitted. " +
                            "Please request reviewer to make changes.");
                    return responseMessage;
                }
                if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    responseMessage.setText("You are not permitted to edit/delete because target is already approved. Please request " +
                            "administrator make changes.");
                    return responseMessage;
                }
            }
            if (tfDhiProdSaleTargetStatus.getStatusFlag() == 'X') {
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
            if (tfDhiProdSaleTargetStage == null) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is not submitted yet.");
                return responseMessage;
            }
            if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already reverted.");
                return responseMessage;
            }
            if (tfDhiProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("You are not permitted to edit/delete because the target is already approved.");
                return responseMessage;
            }
        }
        return responseMessage;
    }
}
