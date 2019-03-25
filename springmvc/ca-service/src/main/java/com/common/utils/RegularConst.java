package com.common.utils;


/**
 * 正则匹配常量
 *
 * @author WangChengyu
 * 2018/7/25 18:13
 */
public class RegularConst {

    /**
     * ^    　　　 匹配开始
     * $    　　   匹配结束
     * +           表示多个
     * { a, b}     a-b 个字符
     */

    /**
     * 匹配：只能汉字
     */
    public final static String M_CN = "^[\u4E00-\u9FA5]+";

    /**
     * 匹配：只能汉字、括号
     */
    public final static String M_CN_kh = "^[\u4E00-\u9FA5()（）]+";

    /**
     * 匹配：只能汉字、字母、括号
     */
    public final static String M_CN_khl = "^[\u4E00-\u9FA5()（）a-zA-Z]+";
    /**
     * 匹配：只能汉字、字母、数子、括号
     */
    public final static String M_CN_khld = "^[\u4E00-\u9FA5()（）a-zA-Z0-9]+";

    /**
     * 匹配：只能汉字、字母、数子、括号、 空格
     */
    public final static String M_CN_khlds = "^[\u4E00-\u9FA5()（）a-zA-Z0-9 ]+";

    /**
     * 匹配只含字母和数字
     */
    public final static String M_LD = "^[a-zA-Z0-9]+";

    /**
     * 统一社会信用代码 old
     */
    public final static String M_UCODE = "[A-Z0-9]{18}";

    /**
     * 统一社会信用代码 new
     */
    public final static String M_UCODE_gm = "^[0-9A-HJ-NPQRTUWXY]{2}\\d{6}[0-9A-HJ-NPQRTUWXY]{10}$";



    /**
     * 匹配只含字母和空格
     */
    public final static String M_LB = "^[a-zA-Z ]+";

    /**
     * 匹配：只能汉字字母数字
     */
    public final static String M_CNLD = "^[\u4E00-\u9FA5A-Za-z0-9]+";

    /**
     * 匹配：只能汉字字母数字、逗号和句号
     */
    public final static String M_CNLD_DE = "^[\u4E00-\u9FA5A-Za-z0-9,.，。]+";

    /**
     * 只有字母、数字和下划线且不能以下划线开头和结尾的正则表达式：^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$
     */
    public final static String M_CNLD_LIM = "^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$";


    /**
     * 含特殊符号
     */
    public final static String M_SPC = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 含特殊符号
     */
    public final static String M_SPC_el_bracket = "[`~!@#$%^&*+=|{}':;',\\[\\].<>/?~！@#￥%……&*——+|{}【】‘；：”“’。，、？]";


    /**
     * 身份证号
     */
    public final static String M_ID_FORMAT = "(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)";


    /**
     * 手机号
     */
    public final static String cellPhoneRegex = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(17[7])|(18[0,0-9]))\\d{8}$";

    /**
     * 固定电话，非分机号
     */
    public final static String telePhoneRegex = "(^(0\\d{2})-(\\d{8})$)|(^(0\\d{3})-(\\d{7})$)|(^(0\\d{3})-(\\d{8})$)|(^(0\\d{2})-(\\d{8})-(\\d+)$)|(^(0\\d{3})-(\\d{7})-(\\d+)$)";

    public static void main(String[] args) {
        String src = "10ABCD101010PPPPPP";

        System.out.printf("满足条件否\t%s\n",


src.matches(M_UCODE)

//src.matches("\\d{4}-\\d{2}-\\d{2}")&&
//src.matches(ParamsUtil.el_yyyyMMdd)

//src.matches("[0-9]{1,2}")
//                src.matches(M_CNLD_DE)
//          ParamsUtil.checkHasSpecCtx(src, M_SPC)
//          M_CNLD)
//          src.matches(M_LD)
                );


    }







}
