package com.http.webservice;

import com.common.utils.ParamsUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;


/**
 * 测试上传文件并解析
 * @author WangChengyu
 * 2019/3/18 11:22
 */
@RestController
public class ImportFileController {

    static Logger logger = LoggerFactory.getLogger(ImportFileController.class);


    @RequestMapping(value = "/mutualbyhand/importMspFullFile", method = RequestMethod.POST)
    @ResponseBody
    public Object mutualImportByFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String text = request.getParameter("text");
        // 接收导入的制作点文件： base64字符数据、签名值、证书（或从参数表取管理系统的证书）

        try {
            String contentType = file.getContentType();
            String fileName = file.getOriginalFilename();
            byte[] bytTxt =  file.getBytes();// 上传的文件的内容
            String txt = new String(bytTxt, "utf-8");
            logger.info("导入的文件名：fileName=" + fileName);
            logger.info("导入的文件内容类型：ContentType=" + contentType);
            logger.info("导入的文件内容：Content=" + txt);

            // 解析获得： 签名值和Base64字符
            if (ParamsUtil.checkNull(txt)) {
                return "上传的文件为空";
            }
            JSONObject jsonRoot = JSONObject.fromObject(txt);
            if(!jsonRoot.containsKey("signValue")){
                // 格式错误
                return "字符文本解析格式错误:签名值signValue";
            }
            if(!jsonRoot.containsKey("makerPointInfo")){
                // 格式错误
                return "字符文本解析格式错误:制作点makerPointInfo";
            }
            String bsv = jsonRoot.getString("signValue");
            String bjson = jsonRoot.getString("makerPointInfo");
            logger.info(String.format("签名值:%s\n数据:\t%s", bsv, bjson));

            // 调用管理系统证书进行验签

            // 传递给制作点导入的处理接口
            byte[] context = Base64.getDecoder().decode(bjson);
            String json = new String(context, "utf-8");
            logger.info("数据");
            logger.info(json);
            byte[] sv = Base64.getDecoder().decode(bsv);
            logger.info("签名值长度=" + sv.length);
        }catch (IllegalArgumentException e){
            String errorMsg = "Base64解码错误";
            logger.error(">>>>> 导入管理系统推送信息时：数据解析错误-"+ errorMsg, e);
            return errorMsg;
        } catch (IOException e) {
            logger.error("待处理异常", e);
            return "exception";
        }

        return 0;
    }
}
