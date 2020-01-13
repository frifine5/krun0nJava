package com.user.service;


import com.common.ParamsUtil;
import com.user.dao.UserDao;
import com.user.entity.PUser;
import com.user.request.PersonSealRequest;
import com.user.request.UserRequest;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class PersonImgService {

    private Logger logger = LoggerFactory.getLogger(PersonImgService.class);


    @Autowired
    UserDao userDao;

    /**
     * 查询用户和添加其手写签图片
     */
    @Transactional(rollbackFor = {Exception.class})
    public void pushPsSigImg(PersonSealRequest ppsParam){
        String account = ppsParam.getAccount();
        String sigBase64 = ppsParam.getSig();
        int num = ppsParam.getSigNo();
        String imgType = ppsParam.getImgType();
        if(ParamsUtil.checkNull(account, imgType, sigBase64)){
            throw new RuntimeException("参数错误");
        }
        if(num <1 || num > 3){
            throw new RuntimeException("参数错误");
        }
        PUser person = userDao.findPAccExist(account);
        if(person == null){
            throw new RuntimeException("用户不存在");
        }
        byte[] data = Base64.getDecoder().decode(sigBase64);
        if("svg".equalsIgnoreCase(imgType)){
            data = convSvg2Png(data);
        }
        if(data == null ){
            throw new RuntimeException("图片数据为空");
        }

        // 存储手写签名图到用户上 todo






    }


    byte[] convSvg2Png(byte[] svgData){
        byte[] data = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(svgData));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            t.transcode(input, output);
            data = outputStream.toByteArray();
            outputStream.flush();
            return data;
        }catch (Exception e){
            logger.error("图片[svg->png]转换失败", e);
            throw new RuntimeException("图片格式错误，转换保存失败");
        }
    }




}
