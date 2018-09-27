package com.cer;

public enum CertOidEnum {

    SM2("SM2", "1.2.156.10197.1.501"),
    SM3("SM3", ""),
    SM4("SM4", ""),
    SM9("SM9", "1.2.156.10197.1.502"),

    SM2_ECPK("公钥oid", "1.2.840.10045.2.1"),
    SM2_ECPK_EX("公钥oid(扩展)", "1.2.156.10197.1.301"),




    // total 54 in 2.5.4.x and some duplicate
    OBJCLZ("对象类", "2.5.4.0"),
    ALIASED("别名", "2.5.4.1"),
    KLINFO("知识信息", "2.5.4.2"),
    COMMON("通用", "2.5.4.3"),
    SURNAME("姓氏", "2.5.4.4"),
    SERIALNUM("序列号", "2.5.4.5"),
    COUNTRY("国家", "2.5.4.6"),
    LOCALITY("地区", "2.5.4.7"),
    STATE("州或省", "2.5.4.8"),
    STREET("街道地址", "2.5.4.9"),
    ORGANIZATION("组织机构名", "2.5.4.10"),
    ORGUNIT("组织机构部门", "2.5.4.11"),
    TITLE("标题", "2.5.4.12"),
    DESCRIPTION("描述", "2.5.4.13"),
    SEARCHGUIDE("searchGuide", "2.5.4.14"),
    BUSCATEG("businessCategory", "2.5.4.15"),
    POSTALADDR("邮政地址", "2.5.4.16"),
    POSTALCODE("邮政编码", "2.5.4.17"),
    POSTOFFICEBOX("邮政信箱", "2.5.4.18"),
    PHYSICALOFFICE("实际交货办公室名", "2.5.4.19"),
    TELEPHONE("办公电话号码", "2.5.4.20"),
    TELEXNUM("电传号码", "2.5.4.21"),
    TELETEX_TERM_ID("电传终端标识 ", "2.5.4.22"),
    FACSIMILE_TELNUM("传真电话机号码", "2.5.4.23"),
    X121ADDRESS("x121Address", "2.5.4.24"),
    INTERNATIONAL_ISDN_NUMBER("国际综合业务数字网号码", "2.5.4.25"),
    REGISTERED_ADDRESS("注册地址", "2.5.4.26"),
    DEST_INDICATOR("目的地标识", "2.5.4.27"),
    PREF_DELIVE_METHOD("优先提供的方法", "2.5.4.28"),
    PRESENT_ADDRESS("现行地址地址", "2.5.4.29"),
    SUPAPPCTX("支持的应用内容", "2.5.4.30"),
    MEMBER("成员", "2.5.4.31"),
    OWNER("拥有者", "2.5.4.32"),
    ROLE_Occupant("居住者/占有者", "2.5.4.33"),
    SEEALSO("seeAlso", "2.5.4.34"),
    USERPWD("userPassword", "2.5.4.35"),
    USER_CERTIFICATE("userCertificate", "2.5.4.36"),
    CA_CAERTIFICATE("caCertificate", "2.5.4.37"),
    AUTHORITY_REVOCATION_LIST("authorityRevocationList", "2.5.4.38"),
    CERT_REVOCATION_LIST("certificateRevocationList", "2.5.4.39"),
    CROSS_CERT_PAIR("交叉证书对", "2.5.4.40"),
    NAME("名称", "2.5.4.41"),
    GIVENNAME("givenName", "2.5.4.42"),
    INITIALS("initials", "2.5.4.43"),
    GENERATION_QUALIFIER("generationQualifier", "2.5.4.44"),
    UNIQUE_IDENTIFIER("uniqueIdentifier", "2.5.4.45"),
    DN_QUALIFIER("dnQulifier", "2.5.4.46"),
    ENHANCED_SEARCH_GUIDE("enhanceSearchGuide", "2.5.4.47"),
    PROTOCAL_INFORMATION("protocolInformation", "2.5.4.48"),
    DISGNAME("distionguishedName", "2.5.4.49"),
    UNIQUE_MEMBER("uniqueMember", "2.5.4.50"),
    HOUSE_IDENTIFIER("houseIdentifier", "2.5.4.51"),
    SUP_ALGM("supportedAlgorithms", "2.5.4.52"),
    DELTA_REVOCATION_LIST("deltaRevocationList", "2.5.4.53"),
    CLEARANCE("clearance", "2.5.4.54"),









    ;


    CertOidEnum(String name, String oid){
        this.name = name;
        this.oid = oid;
    }
    String name;
    String oid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
