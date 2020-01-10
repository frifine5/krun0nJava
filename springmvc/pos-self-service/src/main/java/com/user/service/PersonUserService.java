package com.user.service;


import com.common.ParamsUtil;
import com.user.dao.UserDao;
import com.user.entity.PUser;
import com.user.request.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PersonUserService {

    private Logger logger = LoggerFactory.getLogger(PersonUserService.class);


    @Autowired
    UserDao userDao;

    /**
     * 添加用户
     */
    @Transactional(rollbackFor = {Exception.class})
    public void addPsUser(UserRequest userParam){
        String name = userParam.getName();
        String code = userParam.getCode();
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account, name, code)){
            throw new RuntimeException("请检查参数");
        }
        if(userDao.findPAccExist(account) != null){
            throw new RuntimeException("用户账号已存在");
        }
        if(userDao.findPUserExist(name, code)!=null){
            throw new RuntimeException("用户身份已注册");
        }
        PUser neUser = new PUser();
        neUser.setAccount(account);
        neUser.setName(name);
        neUser.setCode(code);
        neUser.setCodeType(userParam.getCodeType());
        neUser.setSexCode(userParam.getSexCode());
        neUser.setCrtTime(new Date());
        int ra = userDao.addOnePsUser(neUser);
        if(ra != 1){
            throw new RuntimeException("用户注册失败");
        }

    }


    /**
     * 查询用户列表
     */
    @Transactional(rollbackFor = {Exception.class})
    public List<PUser> getPUserList(UserRequest userParam){
        // 查询条件
        List<PUser> aUsers = userDao.listAllPersonUsers();

        return ParamsUtil.checkListNull(aUsers)? null: aUsers;
    }


    public int getPsTotal(){
        return userDao.listPsTotalMount();
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<PUser> getPUserListOnPage(UserRequest userParam){
        // 查询条件
        int pageNo = userParam.getPageNo();
        int pageSize = userParam.getPageSize();
        if(pageNo<1|| pageSize<1){
            throw new RuntimeException("无效页码");
        }
        List<PUser> aUsers = userDao.listPagePersonUsers((pageNo-1)*pageSize, pageSize);

        return ParamsUtil.checkListNull(aUsers)? null: aUsers;
    }


    /**
     * 修改用户
     */
    public void modifyPsUser(UserRequest userParam){
        String name = userParam.getName();
        String code = userParam.getCode();
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account, name, code)){
            throw new RuntimeException("请检查参数");
        }
        PUser oldUser = userDao.findPAccExist(account);
        if( oldUser == null){
            throw new RuntimeException("用户账号不存在");
        }
        if(userDao.findPUserExist(name, code)!=null){
            throw new RuntimeException("用户身份已注册");
        }
        PUser neUser = oldUser;
        if(!oldUser.getName().equals(name) || !oldUser.getCode().equals(code)){
            neUser.setName(name);
            neUser.setCode(code);
            neUser.setCodeType(userParam.getCodeType());
            neUser.setSexCode(userParam.getSexCode());
        }
        if(ParamsUtil.checkNull(userParam.getPwd())){
            neUser.setPwd(userParam.getPwd());
        }
        // 更新账户
        int ra = userDao.addOnePsUser(neUser);
        if(!(ra==1|| ra==0)){
            throw new RuntimeException("用户修改失败");
        }

    }


    /**
     * 删除用户
     * */
    @Transactional(rollbackFor = {Exception.class})
    public void delPsUser(UserRequest userParam){
        String account = userParam.getAccount();
        if(ParamsUtil.checkNull(account)){
            throw new RuntimeException("请检查参数");
        }
        PUser oldUser = userDao.findPAccExist(account);
        if( oldUser == null){
            throw new RuntimeException("用户账号已删除，请勿重复操作！");
        }
        int rd = userDao.delOnePsUser(account);
        if(rd != 1){
            throw new RuntimeException("删除用户失败");
        }

    }

}
