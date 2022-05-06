package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfDhiFinListDao;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.*;
import com.spring.project.development.voler.entity.financial.formulation.dhi.*;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.repository.financial.formulation.bcpm.*;
import com.spring.project.development.voler.repository.financial.formulation.dhi.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 3/21/2022.
 */
@Service
public class TfDhiFinListService {
    private final TfDhiFinListDao tfDhiFinListDao;
    private final TfDhiFinTargetStageRepository tfDhiFinTargetStageRepository;
    private final TfDhiFinTargetStageDetailRepository tfDhiFinTargetStageDetailRepository;
    private final TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository;
    private final NotificationService notificationService;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository;
    private final TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository;

    private final TfDhiFinTargetRepository tfDhiFinTargetRepository;
    private final TfBcpmFinTargetRepository tfBcpmFinTargetRepository;
    private final TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository;
    private final TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository;
    private final TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository;
    private final TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository;
    private final TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository;
    private final TfDhiFinTargetReviewerRemarkRepository tfDhiFinTargetReviewerRemarkRepository;
    private final TfBcpmFinTargetReviewerRemarkRepository tfBcpmFinTargetReviewerRemarkRepository;
    private final TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository;
    private final TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository;

    public TfDhiFinListService(TfDhiFinListDao tfDhiFinListDao, TfDhiFinTargetStageRepository tfDhiFinTargetStageRepository, TfDhiFinTargetStageDetailRepository tfDhiFinTargetStageDetailRepository, TfDhiFinTargetWriteup_aRepository tfDhiFinTargetWriteup_aRepository, NotificationService notificationService, CommonService commonService, CommonDao commonDao, TfDhiFinTarget_aRepository tfDhiFinTarget_aRepository, TfDhiFinTargetStatusRepository tfDhiFinTargetStatusRepository, TfDhiFinTargetRepository tfDhiFinTargetRepository, TfBcpmFinTargetRepository tfBcpmFinTargetRepository, TfBcpmFinTarget_aRepository tfBcpmFinTarget_aRepository, TfDhiFinTargetWriteupRepository tfDhiFinTargetWriteupRepository, TfBcpmFinTargetWriteupRepository tfBcpmFinTargetWriteupRepository, TfBcpmFinTargetWriteup_aRepository tfBcpmFinTargetWriteup_aRepository, TfBcpmFinTargetStatusRepository tfBcpmFinTargetStatusRepository, TfDhiFinTargetReviewerRemarkRepository tfDhiFinTargetReviewerRemarkRepository, TfBcpmFinTargetReviewerRemarkRepository tfBcpmFinTargetReviewerRemarkRepository, TfDhiFinTargetDocRepository tfDhiFinTargetDocRepository, TfBcpmFinTargetDocRepository tfBcpmFinTargetDocRepository) {
        this.tfDhiFinListDao = tfDhiFinListDao;
        this.tfDhiFinTargetStageRepository = tfDhiFinTargetStageRepository;
        this.tfDhiFinTargetStageDetailRepository = tfDhiFinTargetStageDetailRepository;
        this.tfDhiFinTargetWriteup_aRepository = tfDhiFinTargetWriteup_aRepository;
        this.notificationService = notificationService;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.tfDhiFinTarget_aRepository = tfDhiFinTarget_aRepository;
        this.tfDhiFinTargetStatusRepository = tfDhiFinTargetStatusRepository;
        this.tfDhiFinTargetRepository = tfDhiFinTargetRepository;
        this.tfBcpmFinTargetRepository = tfBcpmFinTargetRepository;
        this.tfBcpmFinTarget_aRepository = tfBcpmFinTarget_aRepository;
        this.tfDhiFinTargetWriteupRepository = tfDhiFinTargetWriteupRepository;
        this.tfBcpmFinTargetWriteupRepository = tfBcpmFinTargetWriteupRepository;
        this.tfBcpmFinTargetWriteup_aRepository = tfBcpmFinTargetWriteup_aRepository;
        this.tfBcpmFinTargetStatusRepository = tfBcpmFinTargetStatusRepository;
        this.tfDhiFinTargetReviewerRemarkRepository = tfDhiFinTargetReviewerRemarkRepository;
        this.tfBcpmFinTargetReviewerRemarkRepository = tfBcpmFinTargetReviewerRemarkRepository;
        this.tfDhiFinTargetDocRepository = tfDhiFinTargetDocRepository;
        this.tfBcpmFinTargetDocRepository = tfBcpmFinTargetDocRepository;
    }

    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<FinancialTargetDto> financialTargetDtos = tfDhiFinListDao.searchTarget(year, companyId);
        if (financialTargetDtos != null) {
            responseMessage.setDTO(financialTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<FinancialTargetDto> financialTargetDtos = tfDhiFinListDao.searchByStage(stageId);
        if (financialTargetDtos != null) {
            responseMessage.setDTO(financialTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiFinTargetWriteup_a tDhiFinTargetWriteup_a = tfDhiFinTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tDhiFinTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tDhiFinTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfDhiFinListDao.getStages(year, companyId);
        if (targetStageDtos != null) {
            responseMessage.setDTO(targetStageDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    private ResponseMessage validateTotalWeight(TargetStageDto targetStageDto) {
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        ResponseMessage responseMessage = commonService.getAllocatedWt(year, companyId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        WeightageSetup weightageSetup = (WeightageSetup) responseMessage.getDTO();
        BigDecimal allocatedWeightage = weightageSetup.getFinancialWt();
        BigDecimal totalWeight = new BigDecimal("0.00");

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String targetAuditId = targetStageDetailDto.getTargetAuditId();
            TfDhiFinTarget_a tfDhiFinTarget_a = tfDhiFinTarget_aRepository.findByTargetAuditId(targetAuditId);
            BigDecimal weightage = tfDhiFinTarget_a.getWeightage();
            if (weightage.compareTo(BigDecimal.ZERO) > 0) {
                totalWeight = weightage.add(totalWeight);
            }
        }
        if (!totalWeight.equals(allocatedWeightage)) {
            responseMessage.setText("Total weight must be equal to " + allocatedWeightage + "%");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            return responseMessage;
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeSubmit(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only creator should able to submit
        if (!myRoleId.equals(SystemRoles.Creator.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to submit.");
            return responseMessage;
        }
        //check if it is creator's company or not
        if (!currentUser.getCompanyId().equals(targetStageDto.getCompanyId())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to submit because the target doesn't belong to you.");
            return responseMessage;
        }
        //check already submitted or not, creator should able to submit only if latest status is null or R=Reverted
        TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinListDao.getLatestStatus(year, companyId);
        if (tfDhiFinTargetStage != null) {
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeRevert(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only reviewer should able to revert
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert.");
            return responseMessage;
        }

        //check if it is reviewer belongs to selected company or not
        String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
        if (mappedCompanyId == null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert because selected company is not mapped to you.");
            return responseMessage;
        }
        //at least one target status need to be reopened or created
        boolean isOpen = false;
        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String targetAuditId = targetStageDetailDto.getTargetAuditId();
            TfDhiFinTarget_a tfDhiFinTarget_a = tfDhiFinTarget_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfDhiFinTarget_a.getTargetId();
            //get latest status by targetId
            TfDhiFinTargetStatus tfDhiFinTargetStatus = tfDhiFinTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfDhiFinTargetStatus.getStatusFlag();
            if (statusFlag == 'C' || statusFlag == 'R') {
                isOpen = true;
            }
        }
        if (!isOpen) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("All the targets are closed. Please reopen target(s) that need to be changed.");
            return responseMessage;
        }


        //check already reverted or not, reviewer should able to revert only if latest status is S=Submitted
        TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinListDao.getLatestStatus(year, companyId);
        if (tfDhiFinTargetStage != null) {
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Target is not submitted yet. Please refresh the page and try again.");
            return responseMessage;
        }
        return responseMessage;
    }

    private ResponseMessage validateBeforeApprove(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        Integer myRoleId = currentUser.getRoleId();
        //only reviewer should able to revert
        if (!myRoleId.equals(SystemRoles.Reviewer.getValue())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert.");
            return responseMessage;
        }

        //check if it is reviewer belongs to selected company or not
        String mappedCompanyId = commonService.getMappedCompanyId(currentUser.getUserId(), companyId);
        if (mappedCompanyId == null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("You are not permitted to revert because selected company is not mapped to you.");
            return responseMessage;
        }

        //check already reverted or not, reviewer should able to revert only if latest status is S=Submitted
        TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinListDao.getLatestStatus(year, companyId);
        if (tfDhiFinTargetStage != null) {
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiFinTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already approved. Please refresh the page and try again.");
                return responseMessage;
            }
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText("Target is not submitted yet. Please refresh the page and try again.");
            return responseMessage;
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage submit(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateTotalWeight(targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        responseMessage = validateBeforeSubmit(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfDhiFinTargetStage tfDhiFinTargetStage = new TfDhiFinTargetStage();
        tfDhiFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiFinTargetStage.setYear(year);
        tfDhiFinTargetStage.setCompanyId(companyId);
        tfDhiFinTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfDhiFinTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiFinTargetStage.setCreatedDate(new Date());
        tfDhiFinTargetStageRepository.save(tfDhiFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfDhiFinTargetStageDetail tfDhiFinTargetStageDetail = new TfDhiFinTargetStageDetail();
            tfDhiFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiFinTargetStageDetail.setStageId(tfDhiFinTargetStage.getStageId());
            tfDhiFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiFinTargetStageDetail.setCreatedDate(new Date());
            tfDhiFinTargetStageDetailRepository.save(tfDhiFinTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getReviewerId(companyId, SystemRoles.Reviewer.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("Submitted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage revert(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeRevert(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfDhiFinTargetStage tfDhiFinTargetStage = new TfDhiFinTargetStage();
        tfDhiFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiFinTargetStage.setYear(year);
        tfDhiFinTargetStage.setCompanyId(companyId);
        tfDhiFinTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfDhiFinTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfDhiFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiFinTargetStage.setCreatedDate(new Date());
        tfDhiFinTargetStageRepository.save(tfDhiFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfDhiFinTargetStageDetail tfDhiFinTargetStageDetail = new TfDhiFinTargetStageDetail();
            tfDhiFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiFinTargetStageDetail.setStageId(tfDhiFinTargetStage.getStageId());
            tfDhiFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiFinTargetStageDetail.setCreatedDate(new Date());
            tfDhiFinTargetStageDetailRepository.save(tfDhiFinTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("Reverted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage approve(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeApprove(currentUser, targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfDhiFinTargetStage tfDhiFinTargetStage = new TfDhiFinTargetStage();
        tfDhiFinTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiFinTargetStage.setYear(year);
        tfDhiFinTargetStage.setCompanyId(companyId);
        tfDhiFinTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfDhiFinTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiFinTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiFinTargetStage.setCreatedDate(new Date());
        tfDhiFinTargetStageRepository.save(tfDhiFinTargetStage);

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            TfDhiFinTargetStageDetail tfDhiFinTargetStageDetail = new TfDhiFinTargetStageDetail();
            tfDhiFinTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiFinTargetStageDetail.setStageId(tfDhiFinTargetStage.getStageId());
            tfDhiFinTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiFinTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiFinTargetStageDetail.setCreatedDate(new Date());
            tfDhiFinTargetStageDetailRepository.save(tfDhiFinTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Financial Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiFinList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

//TODO: move all target to bcpm
        List<TfDhiFinTarget> tfDhiFinTargets = tfDhiFinTargetRepository.findByYearAndCompanyId(year, companyId);
        for (TfDhiFinTarget tfDhiFinTarget : tfDhiFinTargets) {
            TfBcpmFinTarget tfBcpmFinTarget = new ModelMapper().map(tfDhiFinTarget, TfBcpmFinTarget.class);
            tfBcpmFinTarget.setVersionNo(BigInteger.ONE);
            tfBcpmFinTargetRepository.save(tfBcpmFinTarget);
            TfBcpmFinTarget_a tfBcpmFinTarget_a = new ModelMapper().map(tfBcpmFinTarget, TfBcpmFinTarget_a.class);
            tfBcpmFinTarget_a.setTargetAuditId(UuidGenerator.generateUuid());
            tfBcpmFinTarget_aRepository.save(tfBcpmFinTarget_a);

            TfDhiFinTargetWriteup tfDhiFinTargetWriteup = tfDhiFinTargetWriteupRepository.findByTargetId(tfDhiFinTarget.getTargetId());
            TfBcpmFinTargetWriteup tfBcpmFinTargetWriteup = new ModelMapper().map(tfDhiFinTargetWriteup, TfBcpmFinTargetWriteup.class);
            tfBcpmFinTargetWriteupRepository.save(tfBcpmFinTargetWriteup);

            TfBcpmFinTargetWriteup_a tfBcpmFinTargetWriteup_a = new ModelMapper().map(tfBcpmFinTargetWriteup, TfBcpmFinTargetWriteup_a.class);
            tfBcpmFinTargetWriteup_a.setWriteupAuditId(UuidGenerator.generateUuid());
            tfBcpmFinTargetWriteup_a.setTargetAuditId(tfBcpmFinTarget_a.getTargetAuditId());
            tfBcpmFinTargetWriteup_aRepository.save(tfBcpmFinTargetWriteup_a);

            TfBcpmFinTargetStatus tfBcpmFinTargetStatus = new TfBcpmFinTargetStatus();
            tfBcpmFinTargetStatus.setStatusId(UuidGenerator.generateUuid());
            tfBcpmFinTargetStatus.setTargetId(tfDhiFinTarget.getTargetId());
            tfBcpmFinTargetStatus.setStatusFlag('X');// R= Reopen,X=Closed,C=Created
            tfBcpmFinTargetStatus.setCreatedBy(currentUser.getUserId());
            tfBcpmFinTargetStatus.setCreatedDate(new Date());
            tfBcpmFinTargetStatusRepository.save(tfBcpmFinTargetStatus);

            TfDhiFinTargetReviewerRemark tfDhiFinTargetReviewerRemark = tfDhiFinTargetReviewerRemarkRepository.findByTargetId(tfDhiFinTarget.getTargetId());
            if (tfDhiFinTargetReviewerRemark != null) {
                TfBcpmFinTargetReviewerRemark tfBcpmFinTargetReviewerRemark = new ModelMapper().map(tfDhiFinTargetReviewerRemark, TfBcpmFinTargetReviewerRemark.class);
                tfBcpmFinTargetReviewerRemarkRepository.save(tfBcpmFinTargetReviewerRemark);
            }

            List<TfDhiFinTargetDoc> tfDhiFinTargetDocs = tfDhiFinTargetDocRepository.findByTargetId(tfDhiFinTarget.getTargetId());
            for (TfDhiFinTargetDoc tfDhiFinTargetDoc : tfDhiFinTargetDocs) {
                TfBcpmFinTargetDoc tfBcpmFinTargetDoc = new ModelMapper().map(tfDhiFinTargetDoc, TfBcpmFinTargetDoc.class);
                tfBcpmFinTargetDocRepository.save(tfBcpmFinTargetDoc);
            }
        }

        responseMessage.setText("approved successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage checkPermission(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiFinTargetStage tfDhiFinTargetStage = tfDhiFinTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfDhiFinTargetStage != null) {
            responseMessage.setDTO(tfDhiFinTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
