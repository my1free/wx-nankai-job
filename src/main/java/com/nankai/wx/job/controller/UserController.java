package com.nankai.wx.job.controller;

import com.alibaba.fastjson.JSONObject;
import com.nankai.wx.job.db.domain.Eduexp;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.db.domain.Workexp;
import com.nankai.wx.job.dto.CompanyDto;
import com.nankai.wx.job.dto.EduexpDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.ResumeDto;
import com.nankai.wx.job.dto.UserDto;
import com.nankai.wx.job.dto.WorkexpDto;
import com.nankai.wx.job.service.UserInfoService;
import com.nankai.wx.job.util.CommonUtil;
import com.nankai.wx.job.util.HttpBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserInfoService userInfoService;

    @RequestMapping("/login")
    @ResponseBody
    public JSONObject onLogin(String code) {
        ResultDto<String> resultDto = userInfoService.onLogin(code);
        return HttpBuilder.genJson(resultDto);
    }

    @RequestMapping("/update")
    @ResponseBody
    public JSONObject onUpdate(String sessionId, User user) {
        try {
            logger.info("[concernedJobList] sessionId={}, user={}", sessionId, user);

            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> userRes = userInfoService.updateUserInfo(openid, user);
            return HttpBuilder.genJson(userRes);
        } catch (Exception e) {
            logger.error("[concernedJobList] [update user info exception] user={}", user, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/info")
    @ResponseBody
    public JSONObject userInfo(String sessionId) {
        try {
            logger.info("[userInfo] sessionId={}", sessionId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<UserDto> userRes = userInfoService.userInfo(openid);
            return HttpBuilder.genJson(userRes);
        } catch (Exception e) {
            logger.error("[concernedJobList] [get user info exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping(value = {"/doCollection", "/collection/confirm"})
    @ResponseBody
    public JSONObject doCollection(String sessionId, Integer jobId) {
        try {
            logger.info("[doCollection] sessionId={}, jobId={}", sessionId, jobId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> resultDto = userInfoService.opsCollection(openid, jobId, false);
            resultDto.setMsg(resultDto.isSuccess() ? "收藏成功" : "收藏失败");
            return HttpBuilder.genJson(resultDto);
        } catch (Exception e) {
            logger.error("[doCollection] [collection job exception] jobId={}", jobId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping(value = {"/cancelCollection", "/collection/cancel"})
    @ResponseBody
    public JSONObject cancelCollection(String sessionId, Integer jobId) {
        try {
            logger.info("[cancelCollection] sessionId={}, jobId={}", sessionId, jobId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> resultDto = userInfoService.opsCollection(openid, jobId, true);
            resultDto.setMsg(resultDto.isSuccess() ? "收藏成功" : "收藏失败");
            return HttpBuilder.genJson(resultDto);
        } catch (Exception e) {
            logger.error("[cancelCollection] [collection job exception] jobId={}", jobId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/collection/list")
    @ResponseBody
    public JSONObject collectionList(String sessionId, Integer maxId, Integer limit) {
        try {
            logger.info("[collectionList] sessionId={}, maxId={}, limit={}", sessionId, maxId, limit);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<List<JobInfoDto>> jobListRes = userInfoService.getCollectionJobs(openid, maxId, limit);
            return HttpBuilder.genJson(jobListRes);
        } catch (Exception e) {
            logger.error("[collectionList] [collection list exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping(value = {"/concerned/confirm"})
    @ResponseBody
    public JSONObject doConcerned(String sessionId, Integer companyId) {
        try {
            logger.info("[doConcerned] sessionId={}, companyId={}", sessionId, companyId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> resultDto = userInfoService.opsConcerned(openid, companyId, false);
            resultDto.setMsg(resultDto.isSuccess() ? "关注成功" : "关注失败");
            return HttpBuilder.genJson(resultDto);
        } catch (Exception e) {
            logger.error("[doConcerned] [concern company exception] jobId={}", companyId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping(value = {"/concerned/cancel"})
    @ResponseBody
    public JSONObject cancelConcerned(String sessionId, Integer companyId) {
        try {
            logger.info("[cancelConcerned] sessionId={}, companyId={}", sessionId, companyId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> resultDto = userInfoService.opsConcerned(openid, companyId, true);
            resultDto.setMsg(resultDto.isSuccess() ? "关注成功" : "关注失败");
            return HttpBuilder.genJson(resultDto);
        } catch (Exception e) {
            logger.error("[cancelConcerned] [concern job exception] jobId={}", companyId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/concerned/list")
    @ResponseBody
    public JSONObject concernedList(String sessionId, Integer maxId, Integer limit) {
        try {
            logger.info("[concernedList] sessionId={}, maxId={}, limit={}", sessionId, maxId, limit);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<List<CompanyDto>> companyDtoRes = userInfoService.getConcernedCompanies(openid, maxId, limit);
            return HttpBuilder.genJson(companyDtoRes);
        } catch (Exception e) {
            logger.error("[concernedList] [concerned list exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/concerned/job/list")
    @ResponseBody
    public JSONObject concernedJobList(String sessionId, Integer maxId, Integer limit) {
        try {
            logger.info("[concernedJobList] sessionId={}, maxId={}, limit={}", sessionId, maxId, limit);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<List<JobInfoDto>> jobRes = userInfoService.getConcernedJobList(openid, maxId, limit);
            return HttpBuilder.genJson(jobRes);
        } catch (Exception e) {
            logger.error("[concernedJobList] [concerned job list exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/resume")
    @ResponseBody
    public JSONObject resume(String sessionId) {
        try {
            logger.info("[resume] sessionId={}", sessionId);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<ResumeDto> resumeRes = userInfoService.getUserResume(openid);
            return HttpBuilder.genJson(resumeRes);
        } catch (Exception e) {
            logger.error("[resume] [get resume exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/workexp")
    @ResponseBody
    public JSONObject getWorkexp(String sessionId, Integer id) {
        try {
            logger.info("[getWorkexp] sessionId={}, id={}", sessionId, id);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<WorkexpDto> workexpRes = userInfoService.getWorkexp(openid, id);
            return HttpBuilder.genJson(workexpRes);
        } catch (Exception e) {
            logger.error("[getWorkexp] [get workexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/resume/workexp/save")
    @ResponseBody
    public JSONObject saveWorkexp(String sessionId, Workexp workexp) {
        try {
            logger.info("[saveWorkexp] sessionId={}, workexp", sessionId, workexp);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> saveRes = userInfoService.saveWorkexp(openid, workexp);
            return HttpBuilder.genJson(saveRes);
        } catch (Exception e) {
            logger.error("[saveWorkexp] [save workexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/resume/workexp/update")
    @ResponseBody
    public JSONObject updateWorkexp(String sessionId, Workexp workexp) {
        try {
            logger.info("[updateWorkexp] sessionId={}, workexp={}", sessionId, workexp);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> saveRes = userInfoService.updateWorkexp(openid, workexp);
            return HttpBuilder.genJson(saveRes);
        } catch (Exception e) {
            logger.error("[updateWorkexp] [update workexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/eduexp")
    @ResponseBody
    public JSONObject getEduexp(String sessionId, Integer id) {
        try {
            logger.info("[getEduexp] sessionId={}, id={}", sessionId, id);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<EduexpDto> eduexpRes = userInfoService.getEduexp(openid, id);
            return HttpBuilder.genJson(eduexpRes);
        } catch (Exception e) {
            logger.error("[getEduexp] [get eduexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/resume/eduexp/save")
    @ResponseBody
    public JSONObject saveEduexp(String sessionId, Eduexp eduexp) {
        try {
            logger.info("[saveEduexp] sessionId={}, eduexp", sessionId, eduexp);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> saveRes = userInfoService.saveEduexp(openid, eduexp);
            return HttpBuilder.genJson(saveRes);
        } catch (Exception e) {
            logger.error("[saveEduexp] [save eduexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/resume/eduexp/update")
    @ResponseBody
    public JSONObject updateEduexp(String sessionId, Eduexp eduexp) {
        try {
            logger.info("[updateEduexp] sessionId={}, eduexp={}", sessionId, eduexp);
            String openid = CommonUtil.getOpenid(sessionId);
            if (StringUtils.isBlank(openid)) {
                return HttpBuilder.genCode(401, "invalid session id");
            }
            ResultDto<Boolean> saveRes = userInfoService.updateEduexp(openid, eduexp);
            return HttpBuilder.genJson(saveRes);
        } catch (Exception e) {
            logger.error("[updateEduexp] [update eduexp exception] sessionId={}", sessionId, e);
            return HttpBuilder.genError(e);
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public JSONObject user() {
        return HttpBuilder.genJson(userInfoService.getAllUsers());
    }
}
