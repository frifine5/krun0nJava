package com.user.service;


import com.common.ParamsUtil;
import com.user.dao.UserDao;
import com.user.entity.AUser;
import com.user.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AdmUserService {

    private Logger logger = LoggerFactory.getLogger(AdmUserService.class);


    @Autowired
    UserDao userDao;

    /**
     * 添加用户
     */
    @Transactional(rollbackFor = {Exception.class})
    public void addAdmUser(UserRequest userParam){
        String name = userParam.getName();
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account, name)){
            throw new RuntimeException("请检查参数");
        }
        if(userDao.findAdmAccExist(account) != null){
            throw new RuntimeException("用户账号已存在");
        }
        if(userDao.findAdmUserExist(name)!=null){
            throw new RuntimeException("用户名已注册");
        }
        AUser neUser = new AUser();
        neUser.setAccount(account);
        neUser.setName(name);
        neUser.setRoleId(userParam.getRoleId());
        neUser.setStatusCode(userParam.getStatus());
        Date crtTime = new Date();
        neUser.setCrtTime(crtTime);
        logger.info("创建系统用户成功，时间:{}", crtTime);
        int ra = userDao.addOneAdmUser(neUser);
        if(ra != 1){
            throw new RuntimeException("新增用户失败");
        }

    }


    /**
     * 查询用户列表
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<AUser> getAUserList(UserRequest userParam){
        // 查询条件
        List<AUser> aUsers = userDao.listAllAdmUsers();

        return ParamsUtil.checkListNull(aUsers)? null: aUsers;
    }


    public int getAdmTotal(){
        return userDao.listAdmTotalMount();
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<AUser> getAUserListOnPage(UserRequest userParam){
        // 查询条件
        int pageNo = userParam.getPageNo();
        int pageSize = userParam.getPageSize();
        if(pageNo<1|| pageSize<1){
            throw new RuntimeException("无效页码");
        }
        List<AUser> aUsers = userDao.listPageAdmUsers((pageNo-1)*pageSize, pageSize);

        return ParamsUtil.checkListNull(aUsers)? null: aUsers;
    }

    /**
     * 修改管理员用户
     */
    @Transactional(rollbackFor = {Exception.class})
    public void modifyAdmUser(UserRequest userParam){
        String name = userParam.getName();
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account, name)){
            throw new RuntimeException("请检查参数");
        }
        AUser oldUser = userDao.findAdmAccExist(account);
        if( oldUser == null){
            throw new RuntimeException("用户账号不存在");
        }
        if(userDao.findAdmUserExist(name)!=null){
            throw new RuntimeException("用户名已注册");
        }
        AUser neUser = oldUser;
        if(!oldUser.getName().equals(name)){
            neUser.setName(name);
            neUser.setRoleId(userParam.getRoleId());
            neUser.setStatusCode(userParam.getStatus());
        }
        if(ParamsUtil.checkNull(userParam.getPwd())){
            neUser.setPwd(userParam.getPwd());
        }
        int ra = userDao.addOneAdmUser(neUser);
        if(ra != 1){
            throw new RuntimeException("新增用户失败");
        }

    }


    @Transactional(rollbackFor = {Exception.class})
    public void delAdmUser(UserRequest userParam){
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account)){
            throw new RuntimeException("请检查参数");
        }
        AUser oldUser = userDao.findAdmAccExist(account);
        if( oldUser == null){
            throw new RuntimeException("用户账号已删除，请勿重复操作！");
        }
        int rd = userDao.delOneAdmUser(account);
        if(rd != 1){
            throw new RuntimeException("删除用户失败");
        }

    }


}
