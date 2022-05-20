package com.spring.project.development.voler;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.Enum.ApproveStatus;
import com.spring.project.development.helper.Enum.SystemRoles;
import com.spring.project.development.helper.LoginErrorCode;
import com.spring.project.development.voler.entity.sa.RequestPasswordChange;
import com.spring.project.development.voler.entity.sa.SaRole;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.sa.RequestPasswordChangeRepository;
import com.spring.project.development.voler.repository.sa.SaRoleRepository;
import com.spring.project.development.voler.repository.sa.SaUserRepository;
import com.spring.project.development.voler.service.CommonService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller

//@PreAuthorize("isAuthenticated()")
public class MainController {
    private final SaUserRepository saUserRepository;
    private final SaRoleRepository saRoleRepository;
    private final CommonService commonService;
    private final RequestPasswordChangeRepository requestPasswordChangeRepository;

    public MainController(SaUserRepository saUserRepository, SaRoleRepository saRoleRepository, CommonService commonService, RequestPasswordChangeRepository requestPasswordChangeRepository) {
        this.saUserRepository = saUserRepository;
        this.saRoleRepository = saRoleRepository;
        this.commonService = commonService;
        this.requestPasswordChangeRepository = requestPasswordChangeRepository;
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request, Authentication authentication, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
            model.addAttribute("isError", Boolean.TRUE);
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
            model.addAttribute("isLogout", Boolean.TRUE);
        }
        if (authentication != null) {
            return "home";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, Authentication authentication) {
        CurrentUser currentUser = new CurrentUser();
        if (authentication != null) {
            currentUser.setUsername(authentication.getName());//it is username
            request.getSession().setAttribute("currentUser", currentUser);
            SaUser saUser = saUserRepository.findByUsername(authentication.getName());
            SaRole saRole = saRoleRepository.findByRoleId(saUser.getRoleId());
            currentUser.setUserId(saUser.getUserId());
            currentUser.setUsername(saUser.getUsername());
            currentUser.setEmail(saUser.getEmail());
            currentUser.setFullName(saUser.getFullName());
            currentUser.setShortName(getShortName(saUser.getFullName()));
            currentUser.setMobileNo(saUser.getMobileNo());
            currentUser.setRoleId(saUser.getRoleId());
            currentUser.setRoleName(saRole.getRoleName());
            currentUser.setCompanyId(saUser.getCompanyId());
        }
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        if (currentUser.getRoleId().equals(SystemRoles.Creator.getValue())) {
            return "score";
        } else {
            return "home";
        }
    }

    //    @PreAuthorize("permitAll()")
    @RequestMapping("/")
    public String publicIndex(HttpServletRequest request, Model model, Authentication authentication) {
        CurrentUser currentUser = new CurrentUser();
        if (authentication == null) {
            return "login";
        } else {
            currentUser.setUsername(authentication.getName());//it is username
            request.getSession().setAttribute("currentUser", currentUser);
            SaUser saUser = saUserRepository.findByUsername(authentication.getName());
            SaRole saRole = saRoleRepository.findByRoleId(saUser.getRoleId());
            currentUser.setUserId(saUser.getUserId());
            currentUser.setUsername(saUser.getUsername());
            currentUser.setEmail(saUser.getEmail());
            currentUser.setFullName(saUser.getFullName());
            currentUser.setShortName(getShortName(saUser.getFullName()));
            currentUser.setMobileNo(saUser.getMobileNo());
            currentUser.setRoleId(saUser.getRoleId());
            currentUser.setRoleName(saRole.getRoleName());
            currentUser.setCompanyId(saUser.getCompanyId());
            model.addAttribute("companyList", commonService.getCompanies(currentUser));
            model.addAttribute("yearList", commonService.getYearList());
            if (currentUser.getRoleId().equals(SystemRoles.Creator.getValue())) {
                return "score";
            } else {
                return "home";
            }
        }
    }

    @RequestMapping("/score")
    public String score(HttpServletRequest request, Model model, String yId, String sName) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        String companyId = null;
        if (sName != null) {
            companyId = commonService.getCompanyIdByShortName(sName);
        }
        model.addAttribute("companyId", companyId);
        return "score";
    }

    @RequestMapping("/permission")
    public String permission(Model model) {
        model.addAttribute("roleList", commonService.roleList());
        return "permission";
    }

    @RequestMapping("/report")
    public String report(Model model) {
        return "report";
    }

    @PreAuthorize("permitAll()")
    @RequestMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(String requestId, Model model) {
        RequestPasswordChange requestPasswordChange = requestPasswordChangeRepository.findByRequestId(requestId);
        if (requestPasswordChange != null) {
            if (requestPasswordChange.getStatus() == 'P') {
                model.addAttribute("pending", true);
            } else {
                model.addAttribute("changed", true);
            }
            model.addAttribute("requestId", requestId);
            model.addAttribute("email", requestPasswordChange.getEmail());
            return "resetPassword";
        } else {
            return "404";
        }
    }

    @RequestMapping("/company")
    public String company(Model model) {
        model.addAttribute("statusList", commonService.getStatus());
        return "company";
    }

    @RequestMapping("/user")
    public String user(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("statusList", commonService.getStatus());
        model.addAttribute("roleList", commonService.roleList());
        return "user";
    }

    @RequestMapping("/myProfile")
    public String myProfile() {
        return "myProfile";
    }

    @RequestMapping("/commentPolicy")
    public String commentPolicy() {
        return "commentPolicy";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/help")
    public String help() {
        return "help";
    }

    @RequestMapping("/year")
    public String year(Model model) {
        model.addAttribute("statusList", commonService.getStatus());
        return "year";
    }

    @RequestMapping("/weightageSetup")
    public String weightageSetup(HttpServletRequest request, Model model) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        return "weightageSetup";
    }

    @RequestMapping("/targetDashboard")
    public String targetDashboard(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("myCompanyId", currentUser.getCompanyId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("yId", yId);
        model.addAttribute("cId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "targetDashboard";
    }

    @RequestMapping("/tfDhiFinAddTarget")
    public String tfDhiFinAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiFinAddTarget";
    }

    @RequestMapping("/tfBcpmFinAddTarget")
    public String tfBcpmFinAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmFinAddTarget";
    }

    @RequestMapping(value = "/tfDhiFinList", method = RequestMethod.GET)
    public String tfDhiFinList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiFinList";
    }

    @RequestMapping(value = "/tfBcpmFinList", method = RequestMethod.GET)
    public String tfBcpmFinList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmFinList";
    }

    @RequestMapping("/tfDhiFinEditTarget")
    public String tfDhiFinEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiFinEditTarget";
    }

    @RequestMapping("/tfBcpmFinEditTarget")
    public String tfBcpmFinEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmFinEditTarget";
    }

    @RequestMapping("/tfDhiCusSerAddTarget")
    public String tfDhiCsAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiCusSerAddTarget";
    }

    @RequestMapping("/tfBcpmCusSerAddTarget")
    public String tfBcpmCusSerAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmCusSerAddTarget";
    }

    @RequestMapping(value = "/tfDhiCusSerList", method = RequestMethod.GET)
    public String tfDhiCusSerList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiCusSerList";
    }

    @RequestMapping(value = "/tfBcpmCusSerList", method = RequestMethod.GET)
    public String tfBcpmCusSerList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmCusSerList";
    }

    @RequestMapping("/tfDhiCusSerEditTarget")
    public String tfDhiCusSerEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiCusSerEditTarget";
    }

    @RequestMapping("/tfBcpmCusSerEditTarget")
    public String tfBcpmCusSerEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmCusSerEditTarget";
    }

    @RequestMapping("/tfDhiOrgMgtAddTarget")
    public String tfDhiOrgMgtAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiOrgMgtAddTarget";
    }

    @RequestMapping("/tfBcpmOrgMgtAddTarget")
    public String tfBcpmOrgMgtAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmOrgMgtAddTarget";
    }

    @RequestMapping(value = "/tfDhiOrgMgtList", method = RequestMethod.GET)
    public String tfDhiOrgMgtList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiOrgMgtList";
    }

    @RequestMapping(value = "/tfBcpmOrgMgtList", method = RequestMethod.GET)
    public String tfBcpmOrgMgtList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmOrgMgtList";
    }

    @RequestMapping("/tfDhiOrgMgtEditTarget")
    public String tfDhiOrgMgtEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiOrgMgtEditTarget";
    }

    @RequestMapping("/tfBcpmOrgMgtEditTarget")
    public String tfBcpmOrgMgtEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmOrgMgtEditTarget";
    }


    @RequestMapping("/tfDhiProdSaleAddTarget")
    public String tfDhiProdSaleAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiProdSaleAddTarget";
    }

    @RequestMapping("/tfBcpmProdSaleAddTarget")
    public String tfBcpmProdSaleAddTarget(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmProdSaleAddTarget";
    }

    @RequestMapping(value = "/tfDhiProdSaleList", method = RequestMethod.GET)
    public String tfDhiProdSaleList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiProdSaleList";
    }

    @RequestMapping(value = "/tfBcpmProdSaleList", method = RequestMethod.GET)
    public String tfBcpmProdSaleList(HttpServletRequest request, Model model, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("year", yId);
        model.addAttribute("companyId", cId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmProdSaleList";
    }

    @RequestMapping("/tfDhiProdSaleEditTarget")
    public String tfDhiProdSaleEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfDhiProdSaleEditTarget";
    }

    @RequestMapping("/tfBcpmProdSaleEditTarget")
    public String tfBcpmProdSaleEditTarget(HttpServletRequest request, Model model, String taId, String tId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("companyList", commonService.getCompanies(currentUser));
        model.addAttribute("yearList", commonService.getYearList());
        model.addAttribute("targetAuditId", taId);
        model.addAttribute("targetId", tId);
        model.addAttribute("myRoleId", currentUser.getRoleId());
        model.addAttribute("adminRoleId", SystemRoles.Admin.getValue());
        model.addAttribute("creatorRoleId", SystemRoles.Creator.getValue());
        model.addAttribute("reviewerRoleId", SystemRoles.Reviewer.getValue());
        model.addAttribute("boardRoleId", SystemRoles.BoardMember.getValue());
        model.addAttribute("ceoRoleId", SystemRoles.CompanyCeo.getValue());
        model.addAttribute("submittedStage", ApproveStatus.Submitted.getValue());
        model.addAttribute("approvedStage", ApproveStatus.Approved.getValue());
        model.addAttribute("revertedStage", ApproveStatus.Reverted.getValue());
        return "tfBcpmProdSaleEditTarget";
    }

    @RequestMapping("/compactDoc")
    public String compactDoc(HttpServletRequest request, Model model, Integer stage, String yId, String cId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        model.addAttribute("stage", stage);
        model.addAttribute("yId", yId);
        model.addAttribute("cId", cId);
        return "compactDoc";
    }

    @RequestMapping("/accessDenied")
    @PreAuthorize("isAuthenticated()")
    public String accessDeniedErrorMsg(Model model) {
        return "403";
    }

    /**
     * this is a controller method to load jsp to mr browser
     * @param model
     * @return
     */
    @RequestMapping("/employee")//this is url
     public String employeeMethod(Model model) {
        model.addAttribute("dzongList", commonService.getDzongList());
        return "employee";//this is a name of jsp page
    }

    private String getShortName(String fullName) {
        String firstLetter = fullName.substring(0, 1);
        String lastLetter;
        StringBuilder reversedFullName = new StringBuilder(fullName).reverse();
        if (fullName.contains(" ")) {
            int indexOfWhiteSpace = reversedFullName.lastIndexOf(" ");
            lastLetter = reversedFullName.substring(indexOfWhiteSpace - 1, indexOfWhiteSpace);
        } else {
            lastLetter = reversedFullName.substring(0, 1);
        }
        return firstLetter.toUpperCase() + lastLetter.toUpperCase();
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        if (exception != null) {
            String message = exception.getMessage();
            if (message.equals(LoginErrorCode.FAILED.getCode()) || message.equals(LoginErrorCode.LOCKED.getCode())) {
                return message;
            }
            if (message.equals(LoginErrorCode.FAILEDs.getCode())) {
                return "Your username and password is invalid.";
            } else {
                return message;
            }
        } else {
            return null;
        }
    }
}
