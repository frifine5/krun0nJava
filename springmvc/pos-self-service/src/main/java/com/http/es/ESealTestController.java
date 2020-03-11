package com.http.es;


import com.esr.entity.GmtESignSeal;
import com.esr.entity.GmtEseal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
public class ESealTestController {


    @RequestMapping(value = "/eseal/data")
    @ResponseBody
    public Object applySeal(@RequestBody String param, HttpServletRequest request) {

        System.out.printf("request param:\t%s\n", param);

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");


        System.out.println("inner get request..." + new Date());
        GmtEseal seal = new GmtEseal();
        seal.setID("11010100000001");
        seal.setVersion(1);
        seal.setVid("CYK");
        seal.setEsID("CYK00000001");
        seal.setName("CYK(GMT)ELECTRONIC-SEAL");
        try {
            seal.setCreateDate(sdf.parse("2020-01-01"));
            seal.setValidStart(sdf.parse("2020-01-01"));
            seal.setValidEnd(sdf.parse("2025-12-31"));
        } catch (Exception e) {
        }

        List<String> certList = new ArrayList<>();
        certList.add("cert1");
        certList.add("cert2");
        seal.setCertList(certList);

        String imageData = "iVBORw0KGgoAAAANSUhEUgAAAfAAAAHwCAMAAABucs3UAAADAFBMVEX/////AAD//v7/9/f/8vL/7+//7e3/6+v/6en/6Oj/3t7/xMT/qqr/kpL/hIT/d3f/amr/Xl7/UVH/RET/Nzf/Kir/HR3/EBD/AwP/Cwv/CQn/a2v/6ur/yMj/rq7/lJT/enr/YGD/Rkb/LCz/EhL/Bwf/+Pj/1dX/h4f/ODj/ERH/BQX/OTn/ycn/mZn/cnL/S0v/IyP/AgL/JCT/+vr/z8//mpr/Zmb/MjL/BAT/AQH/m5v//Pz/oKD/bGz/CAj/zMz/ior/SEj/DAz/SUn/i4v/09P/UFD/1NT/29v/V1f/FRX/Fhb/+/v/v7//b2//ICD/9PT/nZ3/TU3/DQ3/Ghr/Jyf/NDT/PDz/Pz//QUH/Q0P/RUX/Pj7/5OT/jY3/W1v/dXX/j4//pKT/srL/2dn/5ub/8/P/jo7/dHT/Wlr/QED/Jib/PT3/5eX/o6P/R0f/bW3/paX/2Nj/vr7/vLz/Dg7/NTX/XFz/q6v/0tL/9vb/g4P/X1//HBz/kJD/5+f/eHj/7u7/kZH/MzP/ISH/VVX/8PD/iYn/0dH/ZWX/uLj/7Oz/QkL/xcX/goL/Ojr/fHz/Gxv/x8f/Hx//bm7/trb/Hh7//f3/rKz/MDD/f3//Ly//ra3/KSn/39//IiL/oaH/9fX/nJz/8fH/iIj/JSX/aWn/xsb/GBj/TEz/GRn/r6//KCj/dnb/np7/MTH/19f/WVn/gYH/jIz/+fn/wsL/p6f/pqb/w8P/sLD/lpb/ubn/ysr/y8v/wcH/sbH/Fxf/lZX/t7f/ZGT/qan/Ozv/n5//FBT/aGj/Skr/vb3/Njb/Dw//qKj/cHD/e3v/T0//LS3/Li7/hob/3d3/Bgb/2tr/l5f/c3P/U1P/4OD/VFT/XV3/4uL/zc3/4eH/urr/0ND/Y2P/s7P/gID/wMD/eXn/mJj/Kyv/4+P/Cgr/oqL/Z2f/UlL/hYX/fn7/1tb/tLT/cXH/YWH/fX3/k5P/u7v/tbX/3Nz/YmL/WFj/zs7/ExP/Tk4AAABJPdHuAAABAHRSTlMA////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////Cpf0PAAAIABJREFUeJztnXd8FMUXwO8thISulCQEpCYRCDXUhECogkDoJTTpRUCKQoJSjBGkSAfh6AKRIiCCRAFFQaUJWEBCU0EUUQIoYld+n99ecsm92X63s+2y3z8gdzs7+2be3u6UVxwOGxsbGxsbGxsbG7PB5MsfUCAwKCUlKLBAQP58jNHy2NAmX6HCRYoWK/7QwyVKliodHBIKBKEhwaVLlSzxcNkXihUtUrhQPqOltfERptwj5StUrFS5Sjh4RXiVypUqVij/SDn7p28VIh8tX7VStere6VlA8zUqVS3/aLrRrbGRIF/NWrXr1I1Wq2pMaL06tWvVtB/0pqN+g4aN3oyhqWpMVONGTRrUN7qNNtnEtWvarFSEVrr2EF+tWdNH44xubR4nrnmLlq2017WHVq3bXLWVbgzMYy+2DdFT2TmEtC3azh7D6wvzeK32HY1Qdg4d29d63Fa6TiR06tzFSGXn0KVzpwSj+8L/6Vr+YS+XUrQkfHr5rkb3iD8T1K17vCoFxfbo2at3Yp++/fpPmNC/X98+ib179ewRq6rK+P91K2d0v/gnTwxM9EU1PXr1HTR4yNBhnZoPHzHSKVSxc+SI4c07DRs66snRY8aW8eES0U8NHKd3b/g7Xcf3907bsV0mTGzzdKfh+b29Uv5nJg1tMXFCF+8uF115qP1sp0bc5Ie9+OElJX9d7Nnnpqq96NTnikybkpyk/Lqtpk+2Z+g0eLT4UqV93mvQ851SqM6WmJROQwb1Unr9GS88SvPieZHUF2cq6+tZL7WYNFsrKWZPmvPSLGVyzHwxVSsp8gBz54Up6OMyLYu9PF97YQJfLtZSyZsls9Fc7YXxR+oXeUq+dyMWFNum4zZW/YXT+irYpxlQxN5a85aURT3lujVm8ZJJBuxUTz31+eJQOdmWTiuov2TWhVk2KEqmR5e/ssLrGRc98q+YuFJGwKhB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";
        seal.setImageType("png");
        seal.setImageData(imageData);
        seal.setImageWidth(482);
        seal.setImageHeight(482);

        seal.setMkCert("");
        seal.setMkSignAlgID("");
        seal.setSignData("");

        GmtESignSeal esign = new GmtESignSeal();
        esign.setEseal(seal);
        esign.setTimeInfo(new Date());

        Map<String, Object> data = new HashMap<>();
        data.put("seal", seal);
        data.put("eSign", esign);

        Map<String, Object> result = new HashMap<>();

        result.put("code", 0);
        result.put("message", "success");
        result.put("data", data);

        return result;
    }


}