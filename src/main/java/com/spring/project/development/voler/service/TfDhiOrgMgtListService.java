package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.CommonDao;
import com.spring.project.development.voler.dao.TfDhiOrgMgtListDao;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.dto.TargetStageDetailDto;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.entity.masterData.WeightageSetup;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.*;
import com.spring.project.development.voler.repository.orgMgt.formulation.dhi.*;
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
public class TfDhiOrgMgtListService {
    private final TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository;
    private final TfDhiOrgMgtTargetStageRepository tfDhiOrgMgtTargetStageRepository;
    private final TfDhiOrgMgtTargetStageDetailRepository tfDhiOrgMgtTargetStageDetailRepository;
    private final TfDhiOrgMgtListDao tfDhiOrgMgtListDao;
    private final CommonService commonService;
    private final CommonDao commonDao;
    private final NotificationService notificationService;
    private final TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository;
    private final TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository;
    private final TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository;

    public TfDhiOrgMgtListService(TfDhiOrgMgtTargetWriteup_aRepository tfDhiOrgMgtTargetWriteup_aRepository, TfDhiOrgMgtTargetStageRepository tfDhiOrgMgtTargetStageRepository, TfDhiOrgMgtTargetStageDetailRepository tfDhiOrgMgtTargetStageDetailRepository, TfDhiOrgMgtListDao tfDhiOrgMgtListDao, CommonService commonService, CommonDao commonDao, NotificationService notificationService, TfDhiOrgMgtSubTargetRepository tfDhiOrgMgtSubTargetRepository, TfDhiOrgMgtTargetActivity_aRepository tfDhiOrgMgtTargetActivity_aRepository, TfDhiOrgMgtTargetStatusRepository tfDhiOrgMgtTargetStatusRepository) {
        this.tfDhiOrgMgtTargetWriteup_aRepository = tfDhiOrgMgtTargetWriteup_aRepository;
        this.tfDhiOrgMgtTargetStageRepository = tfDhiOrgMgtTargetStageRepository;
        this.tfDhiOrgMgtTargetStageDetailRepository = tfDhiOrgMgtTargetStageDetailRepository;
        this.tfDhiOrgMgtListDao = tfDhiOrgMgtListDao;
        this.commonService = commonService;
        this.commonDao = commonDao;
        this.notificationService = notificationService;
        this.tfDhiOrgMgtSubTargetRepository = tfDhiOrgMgtSubTargetRepository;
        this.tfDhiOrgMgtTargetActivity_aRepository = tfDhiOrgMgtTargetActivity_aRepository;
        this.tfDhiOrgMgtTargetStatusRepository = tfDhiOrgMgtTargetStatusRepository;
    }


    public ResponseMessage searchTarget(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtTargetDto> orgMgtTargetDtos = tfDhiOrgMgtListDao.searchTarget(year, companyId);
        if (orgMgtTargetDtos != null) {
            responseMessage.setDTO(orgMgtTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage searchByStage(String stageId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<OrgMgtTargetDto> orgMgtTargetDtos = tfDhiOrgMgtListDao.searchByStage(stageId);
        if (orgMgtTargetDtos != null) {
            responseMessage.setDTO(orgMgtTargetDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getWriteup(String targetAuditId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        TfDhiOrgMgtTargetWriteup_a tfDhiOrgMgtTargetWriteup_a = tfDhiOrgMgtTargetWriteup_aRepository.findByTargetAuditId(targetAuditId);
        if (tfDhiOrgMgtTargetWriteup_a != null) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
            responseMessage.setDTO(tfDhiOrgMgtTargetWriteup_a);
        }
        return responseMessage;
    }

    public ResponseMessage getStages(String year, String companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        List<TargetStageDto> targetStageDtos = tfDhiOrgMgtListDao.getStages(year, companyId);
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
            TfDhiOrgMgtSubTarget tfDhiOrgMgtSubTarget = tfDhiOrgMgtSubTargetRepository.findBySubTargetId(subTargetId);
            BigDecimal weightage = tfDhiOrgMgtSubTarget.getWeightage();
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfDhiOrgMgtTargetStage != null) {
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Submitted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already submitted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
            TfDhiOrgMgtTargetActivity_a tfDhiOrgMgtTargetActivity_a = tfDhiOrgMgtTargetActivity_aRepository.findByTargetAuditId(targetAuditId);
            String targetId = tfDhiOrgMgtTargetActivity_a.getTargetId();
            //get latest status by targetId
            TfDhiOrgMgtTargetStatus tfDhiOrgMgtTargetStatus = tfDhiOrgMgtTargetStatusRepository.findTop1ByTargetIdOrderByCreatedDateDesc(targetId);
            Character statusFlag = tfDhiOrgMgtTargetStatus.getStatusFlag();
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfDhiOrgMgtTargetStage != null) {
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtListDao.getLatestStatus(year, companyId);
        if (tfDhiOrgMgtTargetStage != null) {
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Reverted.getValue()) {
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                responseMessage.setText("Already reverted. Please refresh the page and try again.");
                return responseMessage;
            }
            if (tfDhiOrgMgtTargetStage.getStatus() == ApproveStatus.Approved.getValue()) {
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = new TfDhiOrgMgtTargetStage();
        tfDhiOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStage.setYear(year);
        tfDhiOrgMgtTargetStage.setCompanyId(companyId);
        tfDhiOrgMgtTargetStage.setStatus(ApproveStatus.Submitted.getValue());
        tfDhiOrgMgtTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStage.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStageRepository.save(tfDhiOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiOrgMgtTargetStageDetail tfDhiOrgMgtTargetStageDetail = new TfDhiOrgMgtTargetStageDetail();
            tfDhiOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiOrgMgtTargetStageDetail.setStageId(tfDhiOrgMgtTargetStage.getStageId());
            tfDhiOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfDhiOrgMgtTargetStageDetailRepository.save(tfDhiOrgMgtTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Submitted Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiOrgMgtList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = new TfDhiOrgMgtTargetStage();
        tfDhiOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStage.setYear(year);
        tfDhiOrgMgtTargetStage.setCompanyId(companyId);
        tfDhiOrgMgtTargetStage.setStatus(ApproveStatus.Reverted.getValue());
        tfDhiOrgMgtTargetStage.setRoleId(SystemRoles.Creator.getValue());
        tfDhiOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStage.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStageRepository.save(tfDhiOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiOrgMgtTargetStageDetail tfDhiOrgMgtTargetStageDetail = new TfDhiOrgMgtTargetStageDetail();
            tfDhiOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiOrgMgtTargetStageDetail.setStageId(tfDhiOrgMgtTargetStage.getStageId());
            tfDhiOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfDhiOrgMgtTargetStageDetailRepository.save(tfDhiOrgMgtTargetStageDetail);
        }

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Reverted Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiOrgMgtList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = new TfDhiOrgMgtTargetStage();
        tfDhiOrgMgtTargetStage.setStageId(UuidGenerator.generateUuid());
        tfDhiOrgMgtTargetStage.setYear(year);
        tfDhiOrgMgtTargetStage.setCompanyId(companyId);
        tfDhiOrgMgtTargetStage.setStatus(ApproveStatus.Approved.getValue());
        tfDhiOrgMgtTargetStage.setRoleId(SystemRoles.Reviewer.getValue());
        tfDhiOrgMgtTargetStage.setCreatedBy(currentUser.getUserId());
        tfDhiOrgMgtTargetStage.setCreatedDate(new Date());
        tfDhiOrgMgtTargetStageRepository.save(tfDhiOrgMgtTargetStage);

        List<TargetStageDetailDto> targetStageDetailDtos = targetStageDto.getTargetStageDetailDtos().stream()
                .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(TargetStageDetailDto::getTargetAuditId))),
                        ArrayList::new));
        for (TargetStageDetailDto targetStageDetailDto : targetStageDetailDtos) {
            TfDhiOrgMgtTargetStageDetail tfDhiOrgMgtTargetStageDetail = new TfDhiOrgMgtTargetStageDetail();
            tfDhiOrgMgtTargetStageDetail.setStageDetailId(UuidGenerator.generateUuid());
            tfDhiOrgMgtTargetStageDetail.setStageId(tfDhiOrgMgtTargetStage.getStageId());
            tfDhiOrgMgtTargetStageDetail.setTargetAuditId(targetStageDetailDto.getTargetAuditId());
            tfDhiOrgMgtTargetStageDetail.setCreatedBy(currentUser.getUserId());
            tfDhiOrgMgtTargetStageDetail.setCreatedDate(new Date());
            tfDhiOrgMgtTargetStageDetailRepository.save(tfDhiOrgMgtTargetStageDetail);
        }
//TODO: move all target to bcpm
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNoticeMessage("Approved Organizational Management Target - Formulation 1 for the year " + year);
        notificationDto.setUrl("tfDhiOrgMgtList?yId=" + year + "&&cId=" + companyId);
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
        TfDhiOrgMgtTargetStage tfDhiOrgMgtTargetStage = tfDhiOrgMgtTargetStageRepository.findTop1ByYearAndCompanyIdOrderByCreatedDateDesc(year, companyId);
        if (tfDhiOrgMgtTargetStage != null) {
            responseMessage.setDTO(tfDhiOrgMgtTargetStage);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }
}
