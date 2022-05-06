package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.entity.prodSale.ProdSaleTargetComment;
import com.spring.project.development.voler.entity.prodSale.formulation.bcpm.TfBcpmProdSaleSubTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmProdSaleEditTargetService;
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
@RequestMapping("api/tfBcpmProdSaleEditTarget")
public class TfBcpmProdSaleEditTargetController {
    private final CommonService commonService;
    private final TfBcpmProdSaleEditTargetService tfBcpmProdSaleEditTargetService;

    public TfBcpmProdSaleEditTargetController(CommonService commonService, TfBcpmProdSaleEditTargetService tfBcpmProdSaleEditTargetService) {
        this.commonService = commonService;
        this.tfBcpmProdSaleEditTargetService = tfBcpmProdSaleEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfBcpmProdSaleEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getSubTarget", method = RequestMethod.GET)
    public ResponseMessage getSubTarget(String targetAuditId) {
        return tfBcpmProdSaleEditTargetService.getSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfBcpmProdSaleEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfBcpmProdSaleEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getSubTargetHistory", method = RequestMethod.GET)
    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        return tfBcpmProdSaleEditTargetService.getSubTargetHistory(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfBcpmProdSaleEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String subTargetId) {
        return tfBcpmProdSaleEditTargetService.getAttachments(subTargetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmProdSaleEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfBcpmProdSaleEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String subTargetId) {
        return tfBcpmProdSaleEditTargetService.getRemark(subTargetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.addRemark(currentUser, tfBcpmProdSaleSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfBcpmProdSaleSubTargetReviewerRemark tfBcpmProdSaleSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.editRemark(currentUser, tfBcpmProdSaleSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, ProdSaleTargetComment prodSaleTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.addComment(currentUser, prodSaleTargetComment);
    }

    @RequestMapping(value = "getProdSaleComment", method = RequestMethod.GET)
    public ResponseMessage getProdSaleComment(String targetId) {
        return commonService.getProdSaleComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteSubTarget", method = RequestMethod.POST)
    public ResponseMessage deleteSubTarget(HttpServletRequest request, String subTargetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.deleteSubTarget(currentUser, subTargetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleEditTargetService.editTarget(request, currentUser, prodSaleTargetDto);
    }
}
