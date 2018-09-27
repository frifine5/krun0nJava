package com.http.webservice.ctl;


import com.pe.entity.ApySealObj;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ESealPreController {

    @RequestMapping(value = "/onsst")
    public Object queryInfoOnsystem(){
        String s = "seal pyl ss";
        return s;
    }


    @RequestMapping(value = "/apySeals/reg")
    public Object applySeal(@RequestBody ApySealObj apySealObj){

        System.out.println(net.sf.json.JSONObject.fromObject(apySealObj));

        return apySealObj;
    }







}
