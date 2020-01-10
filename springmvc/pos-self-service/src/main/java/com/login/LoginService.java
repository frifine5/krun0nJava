package com.login;

import com.common.ParamsUtil;
import com.common.globel.LocalCacheRepo;
import com.user.dao.UserDao;
import com.user.entity.AUser;
import com.user.entity.PUser;
import com.user.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class LoginService {

    private Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    UserDao userDao;

    @Value("${login.token.cache.time:1800}")
    int loginCacheTime;

    public Map<String, String> lgiPsUser(UserRequest userParam, String token) {
        String account = userParam.getAccount();
        String pwd = userParam.getPwd();
        if (ParamsUtil.checkNull(account, pwd)) {
            throw new RuntimeException("请检查参数");
        }
        // 设置用户登录次数及超次
        String lgAccTimeKey = "LGT-" + account;
        chechAndUptCache(account, lgAccTimeKey);

        PUser oldUser = userDao.findPAccExist(account);
        if (oldUser == null) {
            throw new RuntimeException("用户账号不存在！");
        }if(ParamsUtil.checkNull(oldUser.getPwd())){
            throw new RuntimeException("首次登录前，请初始化用户密码！");
        }
        if (!pwd.equals(oldUser.getPwd())) {
            throw new RuntimeException("用户账号密码错误");
        }
        // 登录成功，登录次数重置为1
        LocalCacheRepo.putCache(lgAccTimeKey, 1, 10 * 60);
        LocalCacheRepo.putCache(token, account, loginCacheTime * 60);
        logger.info("account=" + account + "登录成功");
        Map<String, String> data = new HashMap<>();
        data.put("name", oldUser.getName());
        data.put("account", account);
        data.put("token", token);
        return data;
    }

    public Map<String, String> lgiAdmUser(UserRequest userParam, String token) {
        String account = userParam.getAccount();
        String pwd = userParam.getPwd();
        if (ParamsUtil.checkNull(account, pwd)) {
            throw new RuntimeException("请检查参数");
        }
        String lgAccTimeKey = "LGT-" + account;
        chechAndUptCache(account, lgAccTimeKey);
        AUser oldUser = userDao.findAdmAccExist(account);
        if (oldUser == null) {
            throw new RuntimeException("用户账号不存在！");
        }
        if(ParamsUtil.checkNull(oldUser.getPwd())){
            throw new RuntimeException("请联系管理员初始化用户密码！");
        }
        if (!pwd.equals(oldUser.getPwd())) {
            throw new RuntimeException("用户账号密码错误");
        }
        // 登录成功，登录次数重置为1
        LocalCacheRepo.putCache(lgAccTimeKey, 1, 10 * 60);
        // 管理员的token失效时间为普通用户的2倍
        LocalCacheRepo.putCache(token, account, loginCacheTime * 2 * 60);
        logger.info("account=" + account + "登录成功");

        Map<String, String> data = new HashMap<>();
        data.put("name", oldUser.getName());
        data.put("account", account);
        data.put("token", token);
        return data;
    }

    public void logoutUser(UserRequest userParam){
        String account = userParam.getAccount();
        String token = userParam.getToken();
        if(ParamsUtil.checkNull(account, token)){
            throw new RuntimeException("请检查参数");
        }
        String lgAccTimeKey = "LGT-" + account;
        if(null == LocalCacheRepo.getCache(token)){
            throw new RuntimeException("身份凭证错误，等待凭证自行失效");
        }
        LocalCacheRepo.rmCache(token);
        LocalCacheRepo.rmCache(lgAccTimeKey);
    }


    void chechAndUptCache(String account, String lgAccTimeKey){
        String errmsg;
        // 设置用户登录次数及抄次
        Object loginCache = LocalCacheRepo.getCache(lgAccTimeKey);
        if (null == loginCache) {
            LocalCacheRepo.putCache(lgAccTimeKey, 1, 10 * 60);// 锁定十分钟
        } else {
            int times = (int) loginCache;
            if (times > 5) {
                logger.info(errmsg = "账户锁定中,请过几分钟后登录！");
                throw new RuntimeException(errmsg);
            }
            times += 1;
            LocalCacheRepo.putCache(lgAccTimeKey, times, 10 * 60);// 锁定十分钟
        }
    }


}
