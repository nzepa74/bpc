package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.entity.orgMgt.OrgMgtTargetComment;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtSubTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfDhiOrgMgtEditTargetService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created By zepaG on 3/21/2022.
 */
@RestController
@RequestMapping("api/tfDhiOrgMgtEditTarget")
public class TfDhiOrgMgtEditTargetController {
    private final CommonService commonService;
    private final TfDhiOrgMgtEditTargetService tfDhiOrgMgtEditTargetService;

    public TfDhiOrgMgtEditTargetController(CommonService commonService, TfDhiOrgMgtEditTargetService tfDhiOrgMgtEditTargetService) {
        this.commonService = commonService;
        this.tfDhiOrgMgtEditTargetService = tfDhiOrgMgtEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfDhiOrgMgtEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getSubTarget", method = RequestMethod.GET)
    public ResponseMessage getSubTarget(String targetAuditId) {
        return tfDhiOrgMgtEditTargetService.getSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfDhiOrgMgtEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfDhiOrgMgtEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getSubTargetHistory", method = RequestMethod.GET)
    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        return tfDhiOrgMgtEditTargetService.getSubTargetHistory(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfDhiOrgMgtEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String subTargetId) {
        return tfDhiOrgMgtEditTargetService.getAttachments(subTargetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfDhiOrgMgtEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfDhiOrgMgtEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String subTargetId) {
        return tfDhiOrgMgtEditTargetService.getRemark(subTargetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.addRemark(currentUser, tfDhiOrgMgtSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfDhiOrgMgtSubTargetReviewerRemark tfDhiOrgMgtSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.editRemark(currentUser, tfDhiOrgMgtSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, OrgMgtTargetComment orgMgtTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.addComment(currentUser, orgMgtTargetComment);
    }

    @RequestMapping(value = "getOrgMgtComment", method = RequestMethod.GET)
    public ResponseMessage getOrgMgtComment(String targetId) {
        return commonService.getOrgMgtComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteSubTarget", method = RequestMethod.POST)
    public ResponseMessage deleteSubTarget(HttpServletRequest request, String subTargetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.deleteSubTarget(currentUser, subTargetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtEditTargetService.editTarget(request, currentUser, orgMgtTargetDto);
    }
}
