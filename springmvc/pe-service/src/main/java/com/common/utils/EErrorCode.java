package com.common.utils;

public enum EErrorCode {
    /*
     制章系统后端输出给前端的错误码 枚举信息
      */
    // 全局
    // 操作成功
    MS_DEF_SUCCESS(0, "操作成功"),
    // 错误
    MS_ERROR(200000, "未知错误"),
    MS_EXCEPTION(200001, "未处理异常或运行期错误"),


    /* 印章模块（申请<5>, 处理<3>), 系统参数（4）,发布异常（2）, 统计（2）
    * */
    MS_F_SEAL(201000, "印章模块错误"),

    MS_F_SEAL_Q_p(201001, "印章查询必要参数不足"),
    MS_F_SEAL_Q_pt(201001, "印章查询参数格式错误"),

    MS_F_SEAL_APY_mk(201100, "印章制作申请错误"),
    MS_F_SEAL_APY_mk_p(201101, "印章制作申请必要参数不足"),
    MS_F_SEAL_APY_mk_pt(201102, "印章制作申请参数格式错误"),
    MS_F_SEAL_APY_mk_pe(201103, "印章制作申请必要参数值错误"),

    MS_F_SEAL_APY_md(201200, "印章变更申请错误"),
    MS_F_SEAL_APY_md_p(201201, "印章制作申请必要参数不足"),
    MS_F_SEAL_APY_md_pt(201202, "印章制作申请参数格式错误"),
    MS_F_SEAL_APY_md_pe(201203, "印章制作申请必要参数值错误"),

    MS_F_SEAL_APY_ls(201300, "印章挂失申请错误"),
    MS_F_SEAL_APY_ls_p(201301, "印章挂失申请必要参数不足"),
    MS_F_SEAL_APY_ls_pt(201302, "印章挂失申请参数格式错误"),
    MS_F_SEAL_APY_ls_pe(201303, "印章挂失申请必要参数值错误"),

    MS_F_SEAL_APY_rk(201400, "印章撤销申请错误"),
    MS_F_SEAL_APY_rk_p(201401, "印章撤销申请必要参数不足"),
    MS_F_SEAL_APY_rk_pt(201402, "印章撤销申请参数格式错误"),
    MS_F_SEAL_APY_rk_pe(201403, "印章撤销申请必要参数值错误"),

    MS_F_SEAL_APY_rs(201500, "印章补发申请错误"),
    MS_F_SEAL_APY_rs_p(201501, "印章补发申请必要参数不足"),
    MS_F_SEAL_APY_rs_pt(201502, "印章补发申请参数格式错误"),
    MS_F_SEAL_APY_rs_pe(201503, "印章补发申请必要参数值错误"),

    MS_F_SEAL_PIC_upd(201600, "印章印模上传错误"),
    MS_F_SEAL_PIC_upd_p(201601, "印章印模上传参数错误"),

    MS_F_SEAL_DWLD(201701, "印章下载印章错误"),
    MS_F_SEAL_EXPORT(201801, "印章导出印章错误"),

    MS_F_PARAM_AREA_q(202101, "地区列表查询失败"),
    MS_F_PARAM_AREA_a(202102, "地区参数增加失败"),
    MS_F_PARAM_AREA_d(202103, "地区参数删除失败"),
    MS_F_PARAM_AREA_p(202104, "地区参数禁用失败"),

    MS_F_PARAM_UIT_q(202201, "单位类型列表查询失败"),
    MS_F_PARAM_UIT_a(202202, "单位类型参数增加失败"),
    MS_F_PARAM_UIT_d(202203, "单位类型参数删除失败"),
    MS_F_PARAM_UIT_p(202204, "单位类型参数禁用失败"),

    MS_F_PARAM_SLT_q(202301, "印章类型列表查询失败"),
    MS_F_PARAM_SLT_a(202302, "印章类型参数增加失败"),
    MS_F_PARAM_SLT_d(202303, "印章类型参数删除失败"),
    MS_F_PARAM_SLT_p(202304, "印章类型参数禁用失败"),

    MS_F_PARAM_OILT_q(202401, "印章颜色列表查询失败"),
    MS_F_PARAM_OILT_a(202402, "印章颜色参数增加失败"),
    MS_F_PARAM_OILT_d(202403, "印章颜色参数删除失败"),
    MS_F_PARAM_OILT_p(202404, "印章颜色参数禁用失败"),

    MS_F_PUB_Q_qlist(203101, "发布异常列表查询失败"),
    MS_F_PUB_Q_detail(203102, "发布记录查询失败"),
    MS_F_PUB_R_redo(203201, "发布任务重试失败"),


    MS_F_STAT_APY_p(204101, "申请单统计参数错误"),
    MS_F_STAT_APY_qlist(204102, "申请单统计失败"),
    MS_F_STAT_SEAL_p(204201, "印章统计参数错误"),
    MS_F_STAT_SEAL_qlist(204202, "印章统计失败"),








    // 请求参数检查
    MS_REQ_PNULL(2001000, "请求参数为空"),
    MS_REQ_PARTLOSS(2001001, "请求参数不全"),
    MS_REQ_FORMATF(2001002, "参数格式/类型错误"),
    MS_REQ_OL(2001003, "参数超出长度限制"),
    MS_REQ_SPECIAL(2001004, "禁止特殊符号"),
    MS_REQ_LIMIT(2001005, "输入参数为禁止字符"),


    // 数据库操作问题
    MS_DB_QN_PRT(2001010, "系统保护参数不可增加/删除/修改"),
    MS_DB_DUPPK(2001011, "数据记录唯一性条件重复（主键或索引"),
    MS_DB_AF(2001012, "增加记录失败"),
    MS_DB_DF(2001013, "删除记录失败"),
    MS_DB_UPN(2001014, "更新记录失败,记录不存在"),
    MS_DB_UPE(2001015, "更新记录失败"),
    MS_DB_QN(2001016, "查询结果为空"),
    MS_DB_BAK_F(2001017, "备份（历史）记录失败"),
    MS_DB_CLN_L(2001018, "增加记录失败,数据项超出长度限制"),
    MS_DB_QN_SEAL(2001019, "查询指定印章结果为空"),





    // 组件功能问题
    MS_UNIT_ASYN_GF(2001041, "生成异步任务失败"),
    MS_UNIT_ASYN_EF(2001042, "异步任务执行失败"),
    MS_UNIT_ASYN_NON(2001043, "异步任务/结果不存在"),

    // 用户 制作点 业务
    MS_USE_UEFF(2001050, "登录的userId已失效"),
    MS_USE_MSP_NM(2001051, "未查询到制作点或制作点与当前UKey不符"),


    MS_BIZ_SEAL_EPT(2001061, "印章（子表）数据为空"),
    MS_BIZ_MSP_EPT(2001062, "无效的制作点标识"),





    // 数据交互中心 （三方）
    MS_DIC_CHE_F(2001101, "法人库验证失败"),

    // 密码服务问题
    MS_MMS_SIGN_F(2001201, "签名服务失败"),
    MS_MMS_VSIGN_F(2001202, "验签服务失败"),
    MS_MMS_ENC_F(2001203, "非对称加密服务失败"),
    MS_MMS_DEC_F(2001204, "非对称解密服务失败"),
    MS_MMS_EAD_F(2001205, "对称加解密服务失败"),
    MS_MMS_DEL_F(2001206, "数字信封服务失败"),
    MS_MMS_DIGEST_F(2001207, "SM3摘要服务失败"),
    MS_MMS_RAN_F(2001208, "随机数服务失败"),


    ;


    private int code;
    private String message;

    public int c(){
        return getCode();
    }

    public String m(){
        return getMessage();
    }

    EErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[code:"+code+", message:"+message+"]";
    }
}
