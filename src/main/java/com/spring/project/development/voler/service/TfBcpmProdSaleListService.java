package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfBcpmProdSaleListDao;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.*;
import com.spring.project.development.voler.repository.prodSale.formulation.bcpm.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created By zepaG on 3/28/2022.
 */
@Service
public class TfBcpmProdSaleListService {
    private final TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository;
    private final TfBcpmProdSaleTargetStageRepository tfBcpmProdSaleTargetStageRepository;
    private final TfBcpmProdSaleTargetStageDetailRepository tfBcpmProdSaleTargetStageDetailRepository;
    private final TfBcpmProdSaleListDao tfBcpmProdSaleListDao;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final NotificationService notificationService;
    private final TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository;
    private final TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository;
    private final TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository;

    public TfBcpmProdSaleListService(TfBcpmProdSaleTargetWriteup_aRepository tfBcpmProdSaleTargetWriteup_aRepository, TfBcpmProdSaleTargetStageRepository tfBcpmProdSaleTargetStageRepository, TfBcpmProdSaleTargetStageDetailRepository tfBcpmProdSaleTargetStageDetailRepository, TfBcpmProdSaleListDao tfBcpmProdSaleListDao, CommonService commonService, CommonDao commonDao, NotificationService notificationService, TfBcpmProdSaleSubTargetRepository tfBcpmProdSaleSubTargetRepository, TfBcpmProdSaleTarget_aRepository tfBcpmProdSaleTarget_aRepository, TfBcpmProdSaleTargetStatusRepository tfBcpmProdSaleTargetStatusRepository) {
        this.tfBcpmProdSaleTargetWriteup_aRepository = tfBcpmProdSaleTargetWriteup_aRepository;
        this.tfBcpmProdSaleTargetStageRepository = tfBcpmProdSaleTargetStageRepository;
        this.tfBcpmProdSaleTargetStageDetailRepository = tfBcpmProdSaleTargetStageDetailRepository;
        this.tfBcpmProdSaleListDao = tfBcpmProdSaleListDao;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.notificationService = notificationService;
        this.tfBcpmProdSaleSubTargetRepository = tfBcpmProdSaleSubTargetRepository;
        this.tfBcpmProdSaleTarget_aRepository = tfBcpmProdSaleTarget_aRepository;
        this.tfBcpmProdSaleTargetStatusRepository = tfBcpmProdSaleTargetStatusRepository;
    }

    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleTargetDto> prodSaleTargetDtos = tfBcpmProdSaleListDao.searchTarget(year, companyId);
        if (prodSaleTargetDtos != null) {
            responseMessage.setDTO(prodSaleTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchSubTarget(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleTargetDto> prodSaleTargetDtos = tfBcpmProdSaleListDao.searchSubTarget(targetAuditId);
        if (prodSaleTargetDtos != null) {
            responseMessage.setDTO(prodSaleTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<ProdSaleTargetDto> prodSaleTargetDtos = tfBcpmProdSaleListDao.searchByStage(stageId);
        if (prodSaleTargetDtos != null) {
            responseMessage.setDTO(prodSaleTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmProdSaleTargetWriteup_a tfBcpmProdSaleTargetWriteup_a = tfBcpmProdSaleTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmProdSaleTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tfBcpmProdSaleTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfBcpmProdSaleListDao.getStages(year, companyId);
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
        BigDecimal allocatedWeightage = weightageSetup.getProductionWt();
        BigDecimal totalWeight = new BigDecimal("0.00");

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String subTargetId = targetStageDetailDto.getSubTargetId();
            if (subTargetId != null) {
                TfBcpmProdSaleSubTarget bcpmProdSaleSubTarget = tfBcpmProdSaleSubTargetRepository.findBySubTargetId(subTargetId);
                BigDecimal weightage = bcpmProdSaleSubTarget.getWeightage();
                if (weightage.compareTo(BigDecimal.ZERO) > 0) {
                    totalWeight = weightage.add(totalWeight);
                }
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleListDao.getLatestStatus(year, companyId);
        if (tfBcpmProdSaleTargetStage != null) {
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmProdSaleTarget_a tfBcpmProdSaleTarget_a = tfBcpmProdSaleTarget_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfBcpmProdSaleTarget_a.getTargetId();
            //get latest status by targetId
            TfBcpmProdSaleTargetStatus tfBcpmProdSaleTargetStatus = tfBcpmProdSaleTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfBcpmProdSaleTargetStatus.getStatusFlag();
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleListDao.getLatestStatus(year, companyId);
        if (tfBcpmProdSaleTargetStage != null) {
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleListDao.getLatestStatus(year, companyId);
        if (tfBcpmProdSaleTargetStage != null) {
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmProdSaleTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = new TfBcpmProdSaleTargetStage();
        tfBcpmProdSaleTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStage.setYear(year);
        tfBcpmProdSaleTargetStage.setCompanyId(companyId);
        tfBcpmProdSaleTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfBcpmProdSaleTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmProdSaleTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStage.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStageRepository.save(tfBcpmProdSaleTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmProdSaleTargetStageDetail tfBcpmProdSaleTargetStageDetail = new TfBcpmProdSaleTargetStageDetail();
            tfBcpmProdSaleTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmProdSaleTargetStageDetail.setStageId(tfBcpmProdSaleTargetStage.getStageId());
            tfBcpmProdSaleTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmProdSaleTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmProdSaleTargetStageDetail.setCreatedDate(new Date());
            tfBcpmProdSaleTargetStageDetailRepository.save(tfBcpmProdSaleTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Production and Sales Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmProdSaleList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = new TfBcpmProdSaleTargetStage();
        tfBcpmProdSaleTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStage.setYear(year);
        tfBcpmProdSaleTargetStage.setCompanyId(companyId);
        tfBcpmProdSaleTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfBcpmProdSaleTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfBcpmProdSaleTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStage.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStageRepository.save(tfBcpmProdSaleTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmProdSaleTargetStageDetail tfBcpmProdSaleTargetStageDetail = new TfBcpmProdSaleTargetStageDetail();
            tfBcpmProdSaleTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmProdSaleTargetStageDetail.setStageId(tfBcpmProdSaleTargetStage.getStageId());
            tfBcpmProdSaleTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmProdSaleTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmProdSaleTargetStageDetail.setCreatedDate(new Date());
            tfBcpmProdSaleTargetStageDetailRepository.save(tfBcpmProdSaleTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Production and Sales Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmProdSaleList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = new TfBcpmProdSaleTargetStage();
        tfBcpmProdSaleTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmProdSaleTargetStage.setYear(year);
        tfBcpmProdSaleTargetStage.setCompanyId(companyId);
        tfBcpmProdSaleTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfBcpmProdSaleTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmProdSaleTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmProdSaleTargetStage.setCreatedDate(new Date());
        tfBcpmProdSaleTargetStageRepository.save(tfBcpmProdSaleTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmProdSaleTargetStageDetail tfBcpmProdSaleTargetStageDetail = new TfBcpmProdSaleTargetStageDetail();
            tfBcpmProdSaleTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmProdSaleTargetStageDetail.setStageId(tfBcpmProdSaleTargetStage.getStageId());
            tfBcpmProdSaleTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmProdSaleTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmProdSaleTargetStageDetail.setCreatedDate(new Date());
            tfBcpmProdSaleTargetStageDetailRepository.save(tfBcpmProdSaleTargetStageDetail);
        }
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Production and Sales Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmProdSaleList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);

        responseMessage.setText("Approved successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    public ResponseMessage checkPermission(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmProdSaleTargetStage tfBcpmProdSaleTargetStage = tfBcpmProdSaleTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfBcpmProdSaleTargetStage != null) {
            responseMessage.setDTO(tfBcpmProdSaleTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
