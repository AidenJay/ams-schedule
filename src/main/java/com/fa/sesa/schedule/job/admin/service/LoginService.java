package com.fa.sesa.schedule.job.admin.service;

import com.fa.sesa.schedule.job.admin.core.model.XxlJobGroup;
import com.fa.sesa.schedule.job.admin.core.model.XxlJobUser;
import com.fa.sesa.schedule.job.admin.core.util.CookieUtil;
import com.fa.sesa.schedule.job.admin.core.util.I18nUtil;
import com.fa.sesa.schedule.job.admin.core.util.JacksonUtil;
import com.fa.sesa.schedule.job.admin.dao.XxlJobGroupDao;
import com.fa.sesa.schedule.job.admin.dao.XxlJobUserDao;
import com.fa.sesa.schedule.job.core.biz.model.ReturnT;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private XxlJobUserDao xxlJobUserDao;

    @Resource
    private XxlJobGroupDao xxlJobGroupDao;

    @Resource
    private PasswordEncoder passwordEncoder;

    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username,
                                 String password, boolean ifRemember) {

        // param
        if (username == null || username.trim().length() == 0 || password == null || password.trim().length() == 0) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        XxlJobUser xxlJobUser = xxlJobUserDao.loadByUserName(username);
        if (xxlJobUser == null) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }
        if (!passwordEncoder.matches(password,xxlJobUser.getPassword())) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(xxlJobUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public XxlJobUser ifLogin(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        String isAdminStr = request.getParameter("isAdmin");
        Boolean isAdmin = Boolean.valueOf(isAdminStr);

        boolean isAnExternalSystemLogin = StringUtils.hasText(userName) && StringUtils.hasText(isAdminStr);

        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            XxlJobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);

                if (isAnExternalSystemLogin) {
                    // 外部系统check
                    if (!userName.equals(cookieUser.getUsername())) {
                        logout(request, response);
                        cookieToken = null;
                        cookieUser = null;
                    }
                }
            } catch (Exception e) {
                logout(request, response);
            }

            if (cookieUser != null) {
                XxlJobUser dbUser = xxlJobUserDao.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }

        if (cookieToken == null && isAnExternalSystemLogin) {
            // check用户是否存在
            XxlJobUser user = xxlJobUserDao.loadByUserName(userName);

            boolean needAdd = false;
            if (user == null) {
                user = new XxlJobUser();
                user.setUsername(userName);
                user.setPassword(passwordEncoder.encode(userName));
                needAdd = true;
            }
            // 每次登陆，更新用户信息
            // 获取所有的执行器
            List<XxlJobGroup> jobGroups = xxlJobGroupDao.findAll();
            String permissionIds = jobGroups.stream().map(x -> String.valueOf(x.getId()))
                                            .collect(Collectors.joining(","));
            user.setPermission(permissionIds);
            user.setRole(Boolean.TRUE.equals(isAdmin) ? 1 : 0);
            if (needAdd) {
                // 创建用户
                xxlJobUserDao.save(user);
            } else {
                xxlJobUserDao.update(user);
            }

            login(request, response, userName, userName, false);
            user = xxlJobUserDao.loadByUserName(userName);
            return user;
        }

        return null;
    }

    private String makeToken(XxlJobUser xxlJobUser) {
        String tokenJson = JacksonUtil.writeValueAsString(xxlJobUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }

    private XxlJobUser parseToken(String tokenHex) {
        XxlJobUser xxlJobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            xxlJobUser = JacksonUtil.readValue(tokenJson, XxlJobUser.class);
        }
        return xxlJobUser;
    }


}
