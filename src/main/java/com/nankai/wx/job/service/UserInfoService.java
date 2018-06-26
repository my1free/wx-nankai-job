package com.nankai.wx.job.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.nankai.wx.job.db.domain.Collection;
import com.nankai.wx.job.db.domain.Company;
import com.nankai.wx.job.db.domain.Concerned;
import com.nankai.wx.job.db.domain.Job;
import com.nankai.wx.job.db.domain.User;
import com.nankai.wx.job.db.domain.Workexp;
import com.nankai.wx.job.db.service.CollectionService;
import com.nankai.wx.job.db.service.CompanyService;
import com.nankai.wx.job.db.service.ConcernedService;
import com.nankai.wx.job.db.service.IntegratedService;
import com.nankai.wx.job.db.service.JobService;
import com.nankai.wx.job.db.service.UserService;
import com.nankai.wx.job.db.service.WorkexpService;
import com.nankai.wx.job.dto.CompanyDto;
import com.nankai.wx.job.dto.JobInfoDto;
import com.nankai.wx.job.dto.ResultDto;
import com.nankai.wx.job.dto.ResumeDto;
import com.nankai.wx.job.dto.UserDto;
import com.nankai.wx.job.dto.WorkexpDto;
import com.nankai.wx.job.util.CacheUtil;
import com.nankai.wx.job.util.HttpUtil;
import com.nankai.wx.job.util.ResBuilder;
import com.nankai.wx.job.util.SessionIdUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/5/4
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Service
public class UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    private static final String appId = "wxe3f2f36da6b9e696";
    private static final String secret = "7717310a8e96d174cac4cf216cb5d09d";

    private static final String wechatApiHost = "https://api.weixin.qq.com/";
    private static String code2SessionUri = "sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    private static final Integer EXPIRE = 24 * 2600;

    @Resource
    private UserService userService;
    @Resource
    private JobService jobService;
    @Resource
    private CollectionService collectionService;
    @Resource
    private JobInfoService jobInfoService;
    @Resource
    private CompanyService companyService;
    @Resource
    private ConcernedService concernedService;
    @Resource
    private IntegratedService integratedService;
    @Resource
    private WorkexpService workexpService;

    /**
     * 根据小程序登录的code生成sessionId
     * @param code
     * @return
     */
    public ResultDto<String> onLogin(String code) {
        Preconditions.checkArgument(StringUtils.isNotBlank(code));
        String url = wechatApiHost +
                code2SessionUri.replace("APPID", appId)
                .replace("SECRET", secret)
                .replace("JSCODE", code);
        String res = HttpUtil.doGet(url);
        JSONObject data = JSONObject.parseObject(res);
        if (data.getInteger("errcode") != null) {
            return ResBuilder.genError(data.getString("errmsg"));
        }
        String openid = data.getString("openid");
        String sessionKey = data.getString("session_key");

        //缓存sessionId和openid的映射
        String sessionId = SessionIdUtil.genSessionId(code);
        JSONObject value = new JSONObject();
        value.put("openid", openid);
        value.put("sessionkey", sessionKey);
        CacheUtil.set(sessionId, value, EXPIRE);

        return ResBuilder.genData(sessionId);
    }

    /**
     * 新增用户或者更新用户
     * @param openid
     * @param name
     * @param avatar
     * @return
     */
    public ResultDto<User> update(String openid, String name, String avatar) {
        return userService.updateOrInsertUser(openid, name, avatar);
    }

    /**
     * 收藏/取消职位操作
     * @param openid
     * @param jobId
     * @param cancel 为true表示取消收藏，为false表示添加收藏
     * @return
     */
    public ResultDto<Boolean> opsCollection(String openid, Integer jobId, boolean cancel) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        Preconditions.checkArgument(jobId != null && jobId > 0);
        ResultDto<Job> jobRes = jobService.getJobById(jobId);
        if (!jobRes.isSuccess() || jobRes.getData() == null) {
            return ResBuilder.genError("invalid job id");
        }
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }

        Integer userId = userRes.getData().getId();

        Collection collection = new Collection(userId, jobId, cancel ? 0 : 1);
        return collectionService.insertOrUpdate(collection);
    }

    /**
     * 收藏/取消关注公司
     * @param openid
     * @param companyId
     * @param cancel
     * @return
     */
    public ResultDto<Boolean> opsConcerned(String openid, Integer companyId, boolean cancel) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        Preconditions.checkArgument(companyId != null && companyId > 0);
        ResultDto<Company> companyRes = companyService.getCompanyById(companyId);
        if (!companyRes.isSuccess() || companyRes.getData() == null) {
            return ResBuilder.genError("invalid company id");
        }
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }

        Integer userId = userRes.getData().getId();

        Concerned concerned = new Concerned(userId, companyId, cancel ? 0 : 1);
        return concernedService.insertOrUpdate(concerned);
    }

    /**
     * 查询收藏的job
     * @param openid
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<JobInfoDto>> getCollectionJobs (String openid, Integer maxId, Integer limit) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        ResultDto<List<Collection>> collections = collectionService.getCollectionsByPage(userId, maxId, limit);
        if (!collections.isSuccess()) {
            return ResBuilder.genError(collections.getMsg());
        }
        Set<Integer> jobIds = Sets.newHashSet();
        collections.getData().forEach(collection -> jobIds.add(collection.getJobId()));

        ResultDto<Map<Integer, Job>> jobMapRes = jobService.getJobMapByIds(jobIds);
        Map<Integer, Job> jobMap = jobMapRes.getData();

        List<Job> jobs = Lists.newArrayList(jobMap.values());
        ResultDto<List<JobInfoDto>> jobInfoDtoRes = jobInfoService.fillJobInfo(jobs);
        Map<Integer, JobInfoDto> jobInfoDtoMap = Maps.newHashMap();
        jobInfoDtoRes.getData().forEach(jobInfoDto -> jobInfoDtoMap.put(jobInfoDto.getId(), jobInfoDto));

        List<JobInfoDto> res = Lists.newArrayList();
        collections.getData().forEach(collection -> {
            JobInfoDto jobInfoDto = jobInfoDtoMap.get(collection.getJobId());
            if (jobInfoDto != null) {
                res.add(jobInfoDto);
            }
        });
        return ResBuilder.genData(res);
    }

    /**
     * 获取所关注的公司列表
     * @param openid
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<CompanyDto>> getConcernedCompanies(String openid, Integer maxId, Integer limit) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        ResultDto<List<Concerned>> concernedRes = concernedService.getConcernedByPage(userId, maxId, limit);
        if (!concernedRes.isSuccess()) {
            return ResBuilder.genError(concernedRes.getMsg());
        }

        Set<Integer> companyIds = Sets.newHashSet();
        concernedRes.getData().forEach(concerned -> companyIds.add(concerned.getCompanyId()));

        ResultDto<Map<Integer, Company>> companyMapRes = companyService.getCompanyMapByIds(companyIds);
        Map<Integer, Company> companyMap = companyMapRes.getData();
        List<CompanyDto> companyDtos = Lists.newArrayList();

        concernedRes.getData().forEach(concerned -> {
            Company company = companyMap.get(concerned.getCompanyId());
            if (company != null) {
                CompanyDto companyDto = new CompanyDto();
                BeanUtils.copyProperties(company, companyDto);
                companyDtos.add(companyDto);
            }
        });
        return ResBuilder.genData(companyDtos);
    }

    /**
     * 获取所关注公司的职位列表
     * @param openid
     * @param maxId
     * @param limit
     * @return
     */
    public ResultDto<List<JobInfoDto>> getConcernedJobList(String openid, Integer maxId, Integer limit) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();

        ResultDto<List<Job>> jobRes = integratedService.getConcernedJobList(userId, maxId, limit);
        if (!jobRes.isSuccess()) {
            return ResBuilder.genData(jobRes.getMsg());
        }
        List<Job> jobs = jobRes.getData();
        return jobInfoService.fillJobInfo(jobs);
    }

    public ResultDto<Boolean> updateUserInfo(String openid, User user){
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        user.setId(userId);
        return userService.updateUserInfo(user);
    }

    /**
     * get personal resume
     * @param openid
     * @return
     */
    public ResultDto<ResumeDto> getUserResume(String openid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        //get个人信息
        //get工作经历
        ResultDto<List<Workexp>> workexpRes = workexpService.getWorkexpsByUserId(userId);
        List<Workexp> workexps = workexpRes.isSuccess() ? workexpRes.getData() : Collections.emptyList();
        //get教育经历

        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setWorkexps(convertWorkexps(workexps));

        return ResBuilder.genData(resumeDto);
    }

    public ResultDto<WorkexpDto> getWorkexp(String openid, int id) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        ResultDto<List<Workexp>> workexpRes = workexpService.getWorkexpsById(userId, id);
        if (!workexpRes.isSuccess() || CollectionUtils.isEmpty(workexpRes.getData())) {
            return ResBuilder.genData("数据不存在");
        }
        return ResBuilder.genData(convertWorkexp(workexpRes.getData().get(0)));
    }

    /**
     * save workexp
     * @param openid
     * @param workexp
     * @return
     */
    public ResultDto<Boolean> saveWorkexp(String openid, Workexp workexp) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        return workexpService.insert(userId, workexp);
    }

    public ResultDto<Boolean> updateWorkexp(String openid, Workexp workexp) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        Integer userId = userRes.getData().getId();
        return workexpService.update(userId, workexp);
    }

    public ResultDto<UserDto> userInfo(String openid) {
        Preconditions.checkArgument(StringUtils.isNotBlank(openid));
        ResultDto<User> userRes = userService.getOrInsert(openid);
        if (!userRes.isSuccess() || userRes.getData() == null) {
            return ResBuilder.genError("invalid user");
        }
        return ResBuilder.genData(convert(userRes.getData()));
    }

    public ResultDto<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    public UserDto convert(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }

    public List<WorkexpDto> convertWorkexps(List<Workexp> workexps) {
        if (CollectionUtils.isEmpty(workexps)) {
            return Collections.emptyList();
        }
        List<WorkexpDto> workexpDtos = Lists.newArrayList();
        workexps.forEach(workexp -> {
            WorkexpDto workexpDto = new WorkexpDto();
            BeanUtils.copyProperties(workexp, workexpDto);
            workexpDtos.add(workexpDto);
        });
        return workexpDtos;
    }

    public WorkexpDto convertWorkexp(Workexp workexp) {
        if (workexp == null) {
            return null;
        }
        WorkexpDto workexpDto = new WorkexpDto();
        BeanUtils.copyProperties(workexp, workexpDto);
        return workexpDto;
    }
}
