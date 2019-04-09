package com.http.webservice;

import com.ca.service.CertService;
import com.common.utils.ParamsUtil;
import com.common.utils.Result;
import com.fasterxml.jackson.core.JsonParseException;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;


/**
 *
 * 证书申请制作-免审
 * @author WangChengyu
 * 2019/4/9 9:13
 */
@RestController
public class CertController {
    static Logger log = LoggerFactory.getLogger(CertController.class);

    @Autowired
    CertService certService;

    @RequestMapping(value = "/app/reqCertByCsr", method = { RequestMethod.POST})
    public Object reqCertOnCsr(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        // 从request中获得证书主题参数
        String params = request.getParameter("params");
        log.info(">>>params:"+params);
        try{
            JSONObject root = JSONObject.fromObject(params);
            // 制定接口：主题信息，有效期， 申请者信息， 其它信息


        }catch (JSONException je){
            return new Result<>( -1, "参数格式错误", je.getMessage());
        }


        // 解析
        try {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            byte[] bytTxt = file.getBytes();// 上传的文件的内容
            String txt = new String(bytTxt, "utf-8");
            log.info("导入的文件名：fileName=" + fileName);
            log.info("导入的文件内容类型：ContentType=" + contentType);
            log.info("导入的文件内容：Content=" + new String(bytTxt, "utf-8"));
            // 解析获得： 签名值和Base64字符
            if (ParamsUtil.checkNull(txt)) {
                return new Result<>( -1, "上传的文件为空", null);
            }
            byte[] csr = Base64.getDecoder().decode(txt);
            byte[] bytCert = certService.generateCertByCsr(csr, 1);
            if(null == bytCert || bytCert.length<=0){
                return new Result<>(-1, "", null);
            }
            String data = Base64.getEncoder().encodeToString(bytCert);

            return new Result<>( 0, "执行成功", data);
        }catch (Exception e){
            log.error("执行失败", e);
            return new Result<>( -1, "执行失败", e.getMessage());
        }

    }

    @RequestMapping(value = "/app/reqCertByBpk", method = { RequestMethod.POST})
    public Object reqCertOnBpk(@RequestParam("file") MultipartFile file, HttpServletRequest request){

        // 从request中获得证书主题
        String params = request.getParameter("params");
        log.info(">>>params:"+params);

        try {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            byte[] bytTxt = file.getBytes();// 上传的文件的内容
            String txt = new String(bytTxt, "utf-8");
            log.info("导入的文件名：fileName=" + fileName);
            log.info("导入的文件内容类型：ContentType=" + contentType);
            log.info("导入的文件内容：Content=" + new String(bytTxt, "utf-8"));
            // 解析获得： 签名值和Base64字符
            if (ParamsUtil.checkNull(txt)) {
                return new Result<>( -1, "上传的文件为空", null);
            }
            byte[] pk = Base64.getDecoder().decode(txt);
            byte[] bytCert = certService.generateCertByPk(pk, 1);
            if(null == bytCert || bytCert.length<=0){
                return new Result<>(-1, "", null);
            }
            String data = Base64.getEncoder().encodeToString(bytCert);

            return new Result<>( 0, "执行成功", data);
        }catch (Exception e){
            log.error("执行失败", e);
            return new Result<>( -1, "执行失败", e.getMessage());
        }

    }


}
