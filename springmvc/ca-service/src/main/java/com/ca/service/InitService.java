package com.ca.service;

import com.cer.SM2CaCert;
import com.sm2.GMTSM2;
import com.sm3.SM3Util;
import com.sm3.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.*;

/**
 * 系统初始化相关功能
 * @author WangChengyu
 * 2019/3/25 16:41
 */
@Service
public class InitService {

    Logger log = LoggerFactory.getLogger(InitService.class);

    @Value("${spring.datasource.url}")
    String dburl;
    @Value("${spring.datasource.driverClassName}")
    String driveName;
    @Value("${spring.datasource.username}")
    String name;
    @Value("${spring.datasource.password}")
    String pwd;

    private String[] sqlTables = {
            "CREATE TABLE IF NOT EXISTS sys_info (\n" +
                    "  code varchar(50) NOT NULL,\n" +
                    "  value varchar(255) NOT NULL,\n" +
                    "  ent varchar(255) DEFAULT NULL,\n" +
                    "  md varchar(50) DEFAULT NULL,\n" +
                    "   PRIMARY KEY (code)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
            "CREATE TABLE IF NOT EXISTS sys_user (\n" +
                    "  id int(11) NOT NULL,\n" +
                    "  name varchar(50) NOT NULL,\n" +
                    "  md varchar(50) NOT NULL,\n" +
                    "  type tinyint(4) DEFAULT NULL,\n" +
                    "  auth tinyint(4) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
            "CREATE TABLE IF NOT EXISTS agcrls (\n" +
                    "  id bigint(20) NOT NULL,\n" +
                    "  data longblob NOT NULL,\n" +
                    "  rdtime datetime NOT NULL,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
            "CREATE TABLE IF NOT EXISTS certs (\n" +
                    "  id bigint(20) NOT NULL,\n" +
                    "  type tinyint(4) NOT NULL DEFAULT '0' COMMENT '0: sign, 1: enc; no else',\n" +
                    "  dn text NOT NULL,\n" +
                    "  prtk longblob,\n" +
                    "  ft bigint(20) DEFAULT NULL,\n" +
                    "  cert longblob,\n" +
                    "  rdtime datetime,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
            "CREATE TABLE IF NOT EXISTS feerd (\n" +
                    "  id bigint(20) NOT NULL,\n" +
                    "  sn bigint(20) DEFAULT NULL,\n" +
                    "  coe tinyint(4) NOT NULL DEFAULT '1' COMMENT 'coefficient',\n" +
                    "  rdtime datetime NOT NULL,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
            "CREATE TABLE dlcert_request (\n" +
                    "  id bigint(20) NOT NULL,\n" +
                    "  reqtime datetime NOT NULL,\n" +
                    "  status tinyint(4) NOT NULL DEFAULT '0' COMMENT '0: req, 1: pre, 2: refuse, 9:finish',\n" +
                    "  unit_name varchar(50) DEFAULT NULL,\n" +
                    "  unit_ucode varchar(18) DEFAULT NULL,\n" +
                    "  unit_address varchar(150) DEFAULT NULL,\n" +
                    "  validstart datetime DEFAULT NULL,\n" +
                    "  validend datetime DEFAULT NULL,\n" +
                    "  age tinyint(4) DEFAULT '1',\n" +
                    "  pk varchar(100) DEFAULT NULL,\n" +
                    "  p10 longblob,\n" +
                    "  PRIMARY KEY (id)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4"


    };

    /**
     * 初始化数据库表; 如果存在则无需创建，否则创建
     * @return boolean true-库表都已完成|否则看异常信息
     */
    public boolean initDbSchema(){
        Connection conn = null;
        Statement stat = null;
        boolean rsIsDbExist = false;
        try {
            Class.forName(driveName);
            String[] urlSpilt = dburl.split("\\?");
            String defaultSchema = urlSpilt[0];
            String dbName = defaultSchema.substring(defaultSchema.lastIndexOf("/") + 1);
            String preDb = defaultSchema.substring(0, defaultSchema.lastIndexOf("/") + 1);
            defaultSchema = preDb + "mysql";

            if (urlSpilt.length > 1 && null != urlSpilt[1]) {
                defaultSchema = defaultSchema + "?" + urlSpilt[1];
            }
            conn = DriverManager.getConnection(defaultSchema, name, pwd);
            stat = conn.createStatement();
            // 查询数据库是否存在：
//            SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = 'crab_ca'
            String sqlDbExist = "SELECT COUNT(*) FROM information_schema.schemata WHERE schema_name = '" + dbName + "'";

            ResultSet rs1 = stat.executeQuery(sqlDbExist);
            if (rs1.next()) {
                if (rs1.getInt(1) > 0) {
                    rsIsDbExist = true;
                }
            }
            if(!rsIsDbExist){
                //创建数据库
                stat.executeUpdate("create database " + dbName);
            }
        } catch (Exception e) {
            log.error(">>创建数据库失败", e);
            throw new RuntimeException("创建数据库失败");
        }finally {
            if(null != stat) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }
            if(null != conn) {
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
        try{
            //打开创建的数据库
            conn = DriverManager.getConnection(dburl, name, pwd);
            conn.setAutoCommit(false);
            stat = conn.createStatement();
            for(String s : sqlTables){
                stat.addBatch(s);
            }
            stat.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
            }
            throw new RuntimeException("创建数据表失败");
        }finally {
            if(null != stat) {
                try {
                    stat.close();
                } catch (Exception e) {
                }
            }
            if(null != conn) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    conn.close();
                } catch (Exception e) {
                }
            }
        }
        return true;
    }


    /**
     * 查询系统密钥keystore文件是否存在
     * @return
     */
    public boolean isKeyStore(){

        File f = new File("/var/app/cas.abc.ks");
        if(!f.exists()){
            throw new RuntimeException("系统密钥不存在，请联系密管中心KMC");
        }else if(f.length()<= 0){
            throw new RuntimeException("系统密钥未初始化，请联系密管中心KMC");
        }
        f.setReadOnly();
        return true;
    }


    public boolean checkSmkMatch(){
        String txt = "123456789abcdef0";// 测试数据

        // 完善后更改为密文解析
        byte[] pk = Util.hexToByte("044D4F588B76888D9D74785DB87A18FD346743602070DD21D824B8E4027452D68D90B6339CF86E48278ABD7B2FC249094FF31CD45C59EDCE0B21169AF505FA0ED8");
        byte[] sk = Util.hexToByte("5A325ABF49F554EF1508F11D4CA8513288600B144C01D7B9B7F8EB3425C2B41B");
        byte[] asnCert = null;

        try {
            byte[] md = SM3Util.sm3Digest(txt.getBytes("utf-8"), pk);
            GMTSM2 sm2 = GMTSM2.getInstance();

            // 自签自验
            byte[] sv = sm2.sm2Sign(md, sk);
            boolean v1 = sm2.sm2Verify(md, sv, pk);
            log.info("mk签名校验结果：" + v1);
            // 验证自签证书
            boolean v2 = checkSelfCert(asnCert);
            log.info("mk证书校验结果：" + v2);
            return v1 && v2;

        } catch (Exception e) {
            throw new RuntimeException("校验过程异常：失败", e);
        }
    }

    private boolean checkSelfCert(byte[] cert){
        if(null == cert || cert.length<=0) return true;// 测试
        GMTSM2 sm2 = GMTSM2.getInstance();
        try {
            byte[] ctx = SM2CaCert.getSM2TBSCertificateDate(cert);
            byte[] pk = SM2CaCert.getSM2PublicKey(cert);
            byte[] sv = SM2CaCert.getSM2signatureValue(cert);
            byte[] md = SM3Util.sm3Digest(ctx, pk);
            return sm2.sm2Verify(md, sv, pk);
        } catch (IOException e) {
            log.error("根证书解析失败", e);
        }
        return false;
    }






}
