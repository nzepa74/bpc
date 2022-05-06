package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfBcpmOrgMgtListDao;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.entity.orgMgt.formulation.bcpm.*;
import com.spring.project.development.voler.repository.orgMgt.formulation.bcpm.*;
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
public class TfBcpmOrgMgtListService {
    private final TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository;
    private final TfBcpmOrgMgtTargetStageRepository tfBcpmOrgMgtTargetStageRepository;
    private final TfBcpmOrgMgtTargetStageDetailRepository tfBcpmOrgMgtTargetStageDetailRepository;
    private final TfBcpmOrgMgtListDao tfBcpmOrgMgtListDao;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final NotificationService notificationService;
    private final TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository;
    private final TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository;
    private final TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository;

    public TfBcpmOrgMgtListService(TfBcpmOrgMgtTargetWriteup_aRepository tfBcpmOrgMgtTargetWriteup_aRepository, TfBcpmOrgMgtTargetStageRepository tfBcpmOrgMgtTargetStageRepository, TfBcpmOrgMgtTargetStageDetailRepository tfBcpmOrgMgtTargetStageDetailRepository, TfBcpmOrgMgtListDao tfBcpmOrgMgtListDao, CommonService commonService, CommonDao commonDao, NotificationService notificationService, TfBcpmOrgMgtSubTargetRepository tfBcpmOrgMgtSubTargetRepository, TfBcpmOrgMgtTargetActivity_aRepository tfBcpmOrgMgtTargetActivity_aRepository, TfBcpmOrgMgtTargetStatusRepository tfBcpmOrgMgtTargetStatusRepository) {
        this.tfBcpmOrgMgtTargetWriteup_aRepository = tfBcpmOrgMgtTargetWriteup_aRepository;
        this.tfBcpmOrgMgtTargetStageRepository = tfBcpmOrgMgtTargetStageRepository;
        this.tfBcpmOrgMgtTargetStageDetailRepository = tfBcpmOrgMgtTargetStageDetailRepository;
        this.tfBcpmOrgMgtListDao = tfBcpmOrgMgtListDao;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.notificationService = notificationService;
        this.tfBcpmOrgMgtSubTargetRepository = tfBcpmOrgMgtSubTargetRepository;
        this.tfBcpmOrgMgtTargetActivity_aRepository = tfBcpmOrgMgtTargetActivity_aRepository;
        this.tfBcpmOrgMgtTargetStatusRepository = tfBcpmOrgMgtTargetStatusRepository;
    }

    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtTargetDto> orgMgtTargetDtos = tfBcpmOrgMgtListDao.searchTarget(year, companyId);
        if (orgMgtTargetDtos != null) {
            responseMessage.setDTO(orgMgtTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtTargetDto> orgMgtTargetDtos = tfBcpmOrgMgtListDao.searchByStage(stageId);
        if (orgMgtTargetDtos != null) {
            responseMessage.setDTO(orgMgtTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfBcpmOrgMgtTargetWriteup_a tfBcpmOrgMgtTargetWriteup_a = tfBcpmOrgMgtTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfBcpmOrgMgtTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tfBcpmOrgMgtTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfBcpmOrgMgtListDao.getStages(year, companyId);
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
        BigDecimal allocatedWeightage = weightageSetup.getOrgManagementWt();
        BigDecimal totalWeight = new BigDecimal("0.00");

        for (TargetStageDetailDto targetStageDetailDto : targetStageDto.getTargetStageDetailDtos()) {
            String subTargetId = targetStageDetailDto.getSubTargetId();
            TfBcpmOrgMgtSubTarget tfBcpmOrgMgtSubTarget = tfBcpmOrgMgtSubTargetRepository.findBySubTargetId(subTargetId);
            BigDecimal weightage = tfBcpmOrgMgtSubTarget.getWeightage();
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
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfBcpmOrgMgtTargetStage != null) {
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfBcpmOrgMgtTargetActivity_a tfBcpmOrgMgtTargetActivity_a = tfBcpmOrgMgtTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfBcpmOrgMgtTargetActivity_a.getTargetId();
            //get latest status by targetId
            TfBcpmOrgMgtTargetStatus tfBcpmOrgMgtTargetStatus = tfBcpmOrgMgtTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfBcpmOrgMgtTargetStatus.getStatusFlag();
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
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfBcpmOrgMgtTargetStage != null) {
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfBcpmOrgMgtTargetStage != null) {
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfBcpmOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = new TfBcpmOrgMgtTargetStage();
        tfBcpmOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStage.setYear(year);
        tfBcpmOrgMgtTargetStage.setCompanyId(companyId);
        tfBcpmOrgMgtTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfBcpmOrgMgtTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStage.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStageRepository.save(tfBcpmOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmOrgMgtTargetStageDetail tfBcpmOrgMgtTargetStageDetail = new TfBcpmOrgMgtTargetStageDetail();
            tfBcpmOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtTargetStageDetail.setStageId(tfBcpmOrgMgtTargetStage.getStageId());
            tfBcpmOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfBcpmOrgMgtTargetStageDetailRepository.save(tfBcpmOrgMgtTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmOrgMgtList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getReviewerId(companyId, SystemRoles.Reviewer.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);
        responseMessage.setText("Submitted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage revert(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeRevert(currentUser,targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = new TfBcpmOrgMgtTargetStage();
        tfBcpmOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStage.setYear(year);
        tfBcpmOrgMgtTargetStage.setCompanyId(companyId);
        tfBcpmOrgMgtTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfBcpmOrgMgtTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfBcpmOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStage.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStageRepository.save(tfBcpmOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmOrgMgtTargetStageDetail tfBcpmOrgMgtTargetStageDetail = new TfBcpmOrgMgtTargetStageDetail();
            tfBcpmOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtTargetStageDetail.setStageId(tfBcpmOrgMgtTargetStage.getStageId());
            tfBcpmOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfBcpmOrgMgtTargetStageDetailRepository.save(tfBcpmOrgMgtTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmOrgMgtList?yId=" + year + "&&cId=" + companyId);
        List<String> notifyToList = commonDao.getCreatorId(companyId, SystemRoles.Creator.getValue());
        notificationDto.setNotifyTo(notifyToList);
        notificationService.saveNotification(currentUser, notificationDto);
        
        responseMessage.setText("Reverted successfully.");
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage approve(CurrentUser currentUser, TargetStageDto targetStageDto) {
        ResponseMessage responseMessage = validateBeforeApprove(currentUser,targetStageDto);
        if (responseMessage.getStatus() == SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value()) {
            return responseMessage;
        }
        String year = targetStageDto.getYear();
        String companyId = targetStageDto.getCompanyId();
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = new TfBcpmOrgMgtTargetStage();
        tfBcpmOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfBcpmOrgMgtTargetStage.setYear(year);
        tfBcpmOrgMgtTargetStage.setCompanyId(companyId);
        tfBcpmOrgMgtTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfBcpmOrgMgtTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfBcpmOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfBcpmOrgMgtTargetStage.setCreatedDate(new Date());
        tfBcpmOrgMgtTargetStageRepository.save(tfBcpmOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfBcpmOrgMgtTargetStageDetail tfBcpmOrgMgtTargetStageDetail = new TfBcpmOrgMgtTargetStageDetail();
            tfBcpmOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfBcpmOrgMgtTargetStageDetail.setStageId(tfBcpmOrgMgtTargetStage.getStageId());
            tfBcpmOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfBcpmOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfBcpmOrgMgtTargetStageDetailRepository.save(tfBcpmOrgMgtTargetStageDetail);
        }
//TODO: move all target to bcpm
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfBcpmOrgMgtList?yId=" + year + "&&cId=" + companyId);
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
        TfBcpmOrgMgtTargetStage tfBcpmOrgMgtTargetStage = tfBcpmOrgMgtTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfBcpmOrgMgtTargetStage != null) {
            responseMessage.setDTO(tfBcpmOrgMgtTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
