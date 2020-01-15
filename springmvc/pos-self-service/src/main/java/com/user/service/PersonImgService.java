package com.user.service;


import com.common.ParamsUtil;
import com.pdfpng.PersonImageService;
import com.user.dao.PsSigDao;
import com.user.dao.UserDao;
import com.user.entity.PUser;
import com.user.entity.PsSigImg;
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

    @Autowired
    PsSigDao psSigDao;

    @Autowired
    PersonImageService psImageService;


    @Transactional(rollbackFor = {Exception.class})
    public String getPersonSealImage(PersonSealRequest param){
        String account = param.getAccount();
        if(ParamsUtil.checkNull(account)){
            throw new RuntimeException("请检查参数");
        }
        PUser person = userDao.findPAccExist(account);
        if( person == null){
            throw new RuntimeException("用户账号不存在");
        }
        String name = person.getName();
        if(ParamsUtil.checkNull(name) || name.length()>16){
            throw new RuntimeException("用户名超长(16)");
        }
        PsSigImg psSigImg = psSigDao.findPersonSigImg(account);
        if(null == psSigImg){
            psSigImg = new PsSigImg();
            psSigImg.setAccount(account);
        }else if(!ParamsUtil.checkNull(psSigImg.getSeal())){
            return psSigImg.getSeal();
        }
        // 需重新制作名章印模
        int nameLen = name.length();
        byte[] sealData = null;
        if(nameLen<5){
            sealData = psImageService.genPsSquareSeal(name, 1.0f);
        }else if(nameLen<7){
            sealData = psImageService.genPsSquareSealSix(name, 1.0f);
        }else{
            sealData = psImageService.genPsRectSealLonger(name, 1.0f, 1.0f);
        }
        if(null == sealData){
            throw new RuntimeException("生成个人名章失败");
        }
        String sealDataStr = Base64.getEncoder().encodeToString(sealData);
        psSigImg.setSeal(sealDataStr);
        int ru = psSigDao.iouPersonSigImgByAccount(psSigImg);
        if(ru<1){
            throw new RuntimeException("生成个人名章保存失败");
        }
        return sealDataStr;
    }


    /**
     * 清除指定的手写签图片
     */
    @Transactional(rollbackFor = {Exception.class})
    public void clrCfPsSigImg(PersonSealRequest param) {
        String account = param.getAccount();
        int num = param.getSigNo();
        if (ParamsUtil.checkNull(account)) {
            throw new RuntimeException("参数错误");
        }
        if (num < 1 || num > 3) {
            throw new RuntimeException("参数错误");
        }
        PUser person = userDao.findPAccExist(account);
        if (person == null) {
            throw new RuntimeException("用户不存在");
        }
        PsSigImg oldSigImg = psSigDao.findPersonSigImg(account);
        boolean isNExOfimg = (null == oldSigImg);
        if(isNExOfimg){
            throw new RuntimeException("用户尚未上传手写签名");
        }
        switch (num){
            case 1:
                oldSigImg.setSig1(null);break;
            case 2:
                oldSigImg.setSig2(null);break;
            case 3:
                oldSigImg.setSig3(null);break;
        }
        psSigDao.uptPersonSigImg(oldSigImg);
    }

    /**
     * 查询用户和添加其手写签图片
     */
    @Transactional(rollbackFor = {Exception.class})
    public void pushPsSigImg(PersonSealRequest param){
        String account = param.getAccount();
        String sigBase64 = param.getSig();
        int num = param.getSigNo();
        String imgType = param.getImgType();
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

        // 存储手写签名图到用户上
        String fsigImgStr = Base64.getEncoder().encodeToString(data);
        PsSigImg oldSigImg = psSigDao.findPersonSigImg(account);
        PsSigImg theSigImg = null;
        boolean isNExOfimg = (null == oldSigImg);
        if(isNExOfimg){
            theSigImg = new PsSigImg();
        }else{
            theSigImg = oldSigImg;
        }
        theSigImg.setAccount(account);
        switch (num){
            case 1:
                theSigImg.setSig1(fsigImgStr);
                break;
            case 2:
                theSigImg.setSig2(fsigImgStr);
                break;
            case 3:
                theSigImg.setSig3(fsigImgStr);
                break;
        }
        int r = -1;
        if(isNExOfimg){
            r = psSigDao.addPersonSigImg(theSigImg);
        }else{
            r = psSigDao.uptPersonSigImg(theSigImg);
        }
        if(r != 1){
            throw new RuntimeException("手写签名保存失败");
        }
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
