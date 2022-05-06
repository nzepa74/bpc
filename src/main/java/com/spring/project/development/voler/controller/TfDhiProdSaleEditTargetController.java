package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.entity.prodSale.ProdSaleTargetComment;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleSubTargetReviewerRemark;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfDhiProdSaleEditTargetService;
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
@RequestMapping("api/tfDhiProdSaleEditTarget")
public class TfDhiProdSaleEditTargetController {
    private final CommonService commonService;
    private final TfDhiProdSaleEditTargetService tfDhiProdSaleEditTargetService;

    public TfDhiProdSaleEditTargetController(CommonService commonService, TfDhiProdSaleEditTargetService tfDhiProdSaleEditTargetService) {
        this.commonService = commonService;
        this.tfDhiProdSaleEditTargetService = tfDhiProdSaleEditTargetService;
    }

    @RequestMapping(value = "/getTarget", method = RequestMethod.GET)
    public ResponseMessage getTarget(String targetAuditId) {
        return tfDhiProdSaleEditTargetService.getTarget(targetAuditId);
    }

    @RequestMapping(value = "/getSubTarget", method = RequestMethod.GET)
    public ResponseMessage getSubTarget(String targetAuditId) {
        return tfDhiProdSaleEditTargetService.getSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/getTargetEditHistory", method = RequestMethod.GET)
    public ResponseMessage getTargetEditHistory(String targetId) {
        return tfDhiProdSaleEditTargetService.getTargetEditHistory(targetId);
    }

    @RequestMapping(value = "/getEditHistoryDetail", method = RequestMethod.GET)
    public ResponseMessage getEditHistoryDetail(String targetAuditId) {
        return tfDhiProdSaleEditTargetService.getEditHistoryDetail(targetAuditId);
    }

    @RequestMapping(value = "/getSubTargetHistory", method = RequestMethod.GET)
    public ResponseMessage getSubTargetHistory(String targetAuditId) {
        return tfDhiProdSaleEditTargetService.getSubTargetHistory(targetAuditId);
    }

    @RequestMapping(value = "/getTargetStatus", method = RequestMethod.GET)
    public ResponseMessage getTargetStatus(String targetId) {
        return tfDhiProdSaleEditTargetService.getTargetStatus(targetId);
    }

    @RequestMapping(value = "/getAttachments", method = RequestMethod.GET)
    public ResponseMessage getAttachments(String subTargetId) {
        return tfDhiProdSaleEditTargetService.getAttachments(subTargetId);
    }

    //    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfDhiProdSaleEditTargetService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return tfDhiProdSaleEditTargetService.downloadFile(fileId, response);
    }

    @RequestMapping(value = "getRemark", method = RequestMethod.GET)
    public ResponseMessage getRemark(String subTargetId) {
        return tfDhiProdSaleEditTargetService.getRemark(subTargetId);
    }

    @RequestMapping(value = "/addRemark", method = RequestMethod.POST)
    public ResponseMessage addRemark(HttpServletRequest request, TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.addRemark(currentUser, tfDhiProdSaleSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/editRemark", method = RequestMethod.POST)
    public ResponseMessage editRemark(HttpServletRequest request, TfDhiProdSaleSubTargetReviewerRemark tfDhiProdSaleSubTargetReviewerRemark) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.editRemark(currentUser, tfDhiProdSaleSubTargetReviewerRemark);
    }

    @RequestMapping(value = "/addComment", method = RequestMethod.POST)
    public ResponseMessage addComment(HttpServletRequest request, ProdSaleTargetComment prodSaleTargetComment) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.addComment(currentUser, prodSaleTargetComment);
    }

    @RequestMapping(value = "getProdSaleComment", method = RequestMethod.GET)
    public ResponseMessage getProdSaleComment(String targetId) {
        return commonService.getProdSaleComment(targetId);
    }

    @RequestMapping(value = "/closeTarget", method = RequestMethod.POST)
    public ResponseMessage closeTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.closeTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/reopenTarget", method = RequestMethod.POST)
    public ResponseMessage reopenTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.reopenTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteTarget", method = RequestMethod.POST)
    public ResponseMessage deleteTarget(HttpServletRequest request, String targetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.deleteTarget(currentUser, targetId);
    }

    @RequestMapping(value = "/deleteSubTarget", method = RequestMethod.POST)
    public ResponseMessage deleteSubTarget(HttpServletRequest request, String subTargetId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.deleteSubTarget(currentUser, subTargetId);
    }

    @RequestMapping(value = "/editTarget", method = RequestMethod.POST)
    public ResponseMessage editTarget(HttpServletRequest request, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleEditTargetService.editTarget(request, currentUser, prodSaleTargetDto);
    }
}
