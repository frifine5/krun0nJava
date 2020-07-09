package com.http.webservice;

import com.ca.entity.CertReqRdEntity;
import com.ca.service.CertApyRdService;
import com.ca.service.CertService;
import com.ca.service.GenNumberService;
import com.common.utils.ParamsUtil;
import com.common.utils.RegularConst;
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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;


/**
 *
 * 证书申请
 * <p>
 *     <li> 记录印章的申请参数到申请表（0），稍后制作 | 检查后记录：必要主题系统和公钥 </li>
 *     <li> 记录印章的申请参数到申请表，立即制作（1），并下载，只适用于单证书 | 需要的双证可以在制作服务中认证后使用 </li>
 * </p>
 * @author WangChengyu
 * 2019/4/9 9:13
 */
@RestController
public class CertApplyController {
    static Logger log = LoggerFactory.getLogger(CertApplyController.class);



    @Autowired
    GenNumberService numberService;

    @Autowired
    CertService certService;

    @Autowired
    CertApyRdService certApyRdService;


    /**
     * 申请： 验主题信息，验公钥文件及长度； 记录到库
     * @param file 公钥文件
     * @param request 参数信息params， 文件类型fileType
     * @return 记录情况
     */
    @RequestMapping(value = "/app/certApply", method = { RequestMethod.POST})
    public Object reqCertOn2Rd(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        // 从request中获得证书主题参数
        String params = request.getParameter("params");
        String fileType = request.getParameter("fileType");
        log.info(">>>fileType = "+fileType);
        log.info(">>>params:\t"+params);
        if (ParamsUtil.checkNull(fileType, params)) {
            return new Result<>( -1, "申请参数为空", null);
        }
        // 解析申请参数
        CertReqRdEntity apyRnd;
        try{
            apyRnd = parseParam2ApyReq(params);
            if(null == apyRnd){
                return new Result<>( -1, "申请参数解析结果为空", null);
            }
        }catch (Exception e){
            return new Result<>( -1, e.getMessage(), null);
        }

        // 解析
        String txt = "";
        try {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            byte[] bytTxt = file.getBytes();// 上传的文件的内容
            txt = new String(bytTxt, "utf-8");
            log.info("导入的文件名：fileName=" + fileName);
            log.info("导入的文件内容类型：ContentType=" + contentType);
            log.info("导入的文件内容：Content=" + new String(bytTxt, "utf-8"));
            // 解析获得： 签名值和Base64字符
            if (ParamsUtil.checkNull(txt)) {
                return new Result<>(-1, "上传的文件为空", null);
            }
        }catch (Exception e){
            log.error("上传的文件解析失败", e);
            return new Result<>( -1, "上传的文件解析失败", e.getMessage());
        }
        // 服务处理
        try {
            String bpkStr = certApyRdService.parsePk(fileType, txt);
            if (null == bpkStr) {
                return new Result<>(-1, "上传的文件解析公钥为空", null);
            } else {
                apyRnd.setPk(bpkStr);
                if ("1".equals(fileType)) {
                    apyRnd.setP10(Base64.getDecoder().decode(txt));
                }
            }
            boolean isRd = certApyRdService.rdApplyRnd(apyRnd);
            if (!isRd) {
                return new Result<>(-1, "申请信息添加失败", null);
            }
        }catch (Exception e){
            return new Result<>(-1, e.getMessage(), null);
        }
        return new Result<>( 0, "申请单上传成功", apyRnd.getId()+"");
    }

    CertReqRdEntity parseParam2ApyReq(String params){
        try{
            JSONObject root = JSONObject.fromObject(params);
            // 制定接口：主题信息，有效期， 申请者信息， 其它信息
            // 检查必要项： 单位名，统一社会信用代码， 有效期， 地区区划码； 申请者信息， 单位详细地址， <非>联系电话
            CertReqRdEntity nrd = new CertReqRdEntity();
            // 必选项
            if(!root.containsKey("entName") ||!root.containsKey("uniSocCode") ||!root.containsKey("address") ){
                throw new RuntimeException("参数{单位/机构名称，统一社会信用代码，详细地址}不可为空");
            }else{
                String entName = root.getString("entName");
                String scid = root.getString("uniSocCode");
                String address = root.getString("address");
                if(ParamsUtil.checkNull(entName, scid, address)){
                    throw new RuntimeException("参数{单位/机构名称，统一社会信用代码，详细地址}不可为空");
                }else{
                    // 检查统一社会信用代码规则
                    if(!scid.matches(RegularConst.M_UCODE_gm)){
                       log.info(">>>> scid={}不符合统一社会信用代码的规则", scid);
//                        throw new RuntimeException("统一社会信用代码格式错误");
                    }
                    long apyId = numberService.getNumber();
                    nrd.setId(apyId);
                    nrd.setUnitName(entName);
                    nrd.setUnitUCode(scid);
                    nrd.setUnitAddr(address);
                }
                if(!root.containsKey("certName")||ParamsUtil.checkNull(root.getString("certName"))){
                    String certName = scid +""+ entName;
                    nrd.setCertName(certName);
                }else{
                    nrd.setCertName(root.getString("certName"));
                }
            }
            // 地区项
            if(!root.containsKey("province") ||!root.containsKey("city") ||!root.containsKey("county") ){
                throw new RuntimeException("参数{省/市/区}都不可为空");
            }else{
                String province = root.getString("province");
                String city = root.getString("city");
                String county = root.getString("county");
                if(ParamsUtil.checkNull(province, city, county)){
                    throw new RuntimeException("参数{省/市/区}都不可为空");
                }else{
                    nrd.setUnitDisCode(county);
                }
            }

            // 选必项
            if(root.containsKey("validEnd")&&!ParamsUtil.checkNull(root.getString("validEnd"))){
                String validEnd = root.getString("validEnd");
                // 检验日期格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try{
                    sdf.setLenient(false);
                    sdf.parse(validEnd);
                }catch (ParseException | NullPointerException e){
                    throw new RuntimeException("有效期格式错误");
                }
                nrd.setValidEnd(validEnd);
            }else if(root.containsKey("validAge")){
                String ageStr = root.get("validAge").toString();
                try{
                    int age = Integer.parseInt(ageStr);
                    nrd.setAge(age);
                }catch (NumberFormatException e){
                    log.error("有效期年限转换失败，采用默认1年的方式：e="+e.getMessage());
                    nrd.setAge(1);
                }
            }else{
                throw new RuntimeException("有效期（止）和有效年限不可都为空");
            }

            // 选填项
            if(root.containsKey("telephone")){
                String tel = root.getString("telephone");
                nrd.setUnitTelephone(tel);
            }
            // 返回申请实例
            String nDt = ParamsUtil.formatTime19(new Date());
            nrd.setReqTime(nDt);
            nrd.setValidStart(nDt);

            return nrd;
        }catch (JSONException je){
            throw new RuntimeException("参数格式错误", je);
        }
    }



    @RequestMapping(value = "/app/reqCertByCsr", method = { RequestMethod.POST})
    public Object reqCertOnCsr(@RequestParam("file") MultipartFile file, HttpServletRequest request){


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
