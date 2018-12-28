package com.http.webservice.ctl;


import com.cer.SM2CaCert;
import com.common.utils.ParamsUtil;
import com.pe.entity.ApySealObj;
import com.pe.entity.SealItem;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@RestController
public class ESealPreController {

    @RequestMapping(value = "/onsst")
    public Object queryInfoOnsystem(){
        System.out.println("I got you in time: "+new Date());
        String s = "seal pyl ss";
        return s;
    }


    @RequestMapping(value = "/apySeals/reg")
    public Object applySeal(@RequestBody ApySealObj apySealObj){

        System.out.println(net.sf.json.JSONObject.fromObject(apySealObj));

        return apySealObj;
    }

    @RequestMapping(value = "/cert/apy2Rtca",  method = {RequestMethod.POST})
    @ResponseBody
    public Object exportSealEncData(@RequestParam("file") MultipartFile file, @RequestParam("sealItem") String sealItem){
        byte[] byteContent = null;
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        try {
            byteContent = file.getBytes();
        }catch (IOException e) {
            e.printStackTrace();
        }
        if(null == byteContent ){
            return "未上传";
        }
        if(null != sealItem){
            System.out.println(JSONObject.fromObject(sealItem));
        }
//        String fileStr = new String(byteContent, "UTF-8");
//        System.out.println(fileStr);
        try {
//            Date[] valids = SM2CaCert.getSM2ValidTime(Base64.getDecoder().decode(fileStr.trim()));
            Date[] valids = SM2CaCert.getSM2ValidTime(byteContent);
            Calendar cld  = Calendar.getInstance();
            cld.setTime(valids[0]);
            int stYear = cld.get(Calendar.YEAR);
            cld.setTime(valids[1]);
            int age = cld.get(Calendar.YEAR) - stYear;
            System.out.println(String.format("有效期%d年", age) );
            return String.format("有效期从%s到%s", ParamsUtil.formatTime19(valids[0]),  ParamsUtil.formatTime19(valids[1]));
        }catch (Exception e){
            return e.getMessage();
        }
    }








    }
