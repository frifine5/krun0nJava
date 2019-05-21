
package cn.org.hb.back.jws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.org.hb.back.jws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SignDataByteExArg1_QNAME = new QName("", "arg1");
    private final static QName _CreateTimeStamp_QNAME = new QName("http://jws.back.hb.org.cn/", "createTimeStamp");
    private final static QName _GenRandom_QNAME = new QName("http://jws.back.hb.org.cn/", "genRandom");
    private final static QName _SignDataP7DetachResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataP7DetachResponse");
    private final static QName _SignDataEx_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataEx");
    private final static QName _CreateTimeStampResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "createTimeStampResponse");
    private final static QName _VerifyTimeStamp_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyTimeStamp");
    private final static QName _GetSealImage_QNAME = new QName("http://jws.back.hb.org.cn/", "getSealImage");
    private final static QName _SignDataExResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataExResponse");
    private final static QName _GetServerCertResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "getServerCertResponse");
    private final static QName _GenRandomResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "genRandomResponse");
    private final static QName _SignDataByteEx_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataByteEx");
    private final static QName _CreateTimeStampRequest_QNAME = new QName("http://jws.back.hb.org.cn/", "createTimeStampRequest");
    private final static QName _CreateTimeStampRequestResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "createTimeStampRequestResponse");
    private final static QName _SignData_QNAME = new QName("http://jws.back.hb.org.cn/", "signData");
    private final static QName _VerifyDetachSignDataResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyDetachSignDataResponse");
    private final static QName _SignDataByteExResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataByteExResponse");
    private final static QName _GetServerCert_QNAME = new QName("http://jws.back.hb.org.cn/", "getServerCert");
    private final static QName _VerifyCertAndSignedData_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyCertAndSignedData");
    private final static QName _SignDataResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataResponse");
    private final static QName _SignDataByteExRaw_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataByteEx_raw");
    private final static QName _GetSealImageResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "getSealImageResponse");
    private final static QName _VerifyDetachSignDataEx_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyDetachSignDataEx");
    private final static QName _VerifyDetachSignData_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyDetachSignData");
    private final static QName _Hello_QNAME = new QName("http://jws.back.hb.org.cn/", "hello");
    private final static QName _SignDataP7Detach_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataP7Detach");
    private final static QName _VerifyDetachSignDataExResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyDetachSignDataExResponse");
    private final static QName _VerifySignedDataResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "verifySignedDataResponse");
    private final static QName _HelloResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "helloResponse");
    private final static QName _VerifySignedData_QNAME = new QName("http://jws.back.hb.org.cn/", "verifySignedData");
    private final static QName _VerifyTimeStampResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyTimeStampResponse");
    private final static QName _GetServerCertExResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "getServerCertExResponse");
    private final static QName _SignDataByteExRawResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "signDataByteEx_rawResponse");
    private final static QName _VerifyCertAndSignedDataResponse_QNAME = new QName("http://jws.back.hb.org.cn/", "verifyCertAndSignedDataResponse");
    private final static QName _GetServerCertEx_QNAME = new QName("http://jws.back.hb.org.cn/", "getServerCertEx");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.org.hb.back.jws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SignDataByteEx }
     * 
     */
    public SignDataByteEx createSignDataByteEx() {
        return new SignDataByteEx();
    }

    /**
     * Create an instance of {@link CreateTimeStamp }
     * 
     */
    public CreateTimeStamp createCreateTimeStamp() {
        return new CreateTimeStamp();
    }

    /**
     * Create an instance of {@link VerifyDetachSignDataResponse }
     * 
     */
    public VerifyDetachSignDataResponse createVerifyDetachSignDataResponse() {
        return new VerifyDetachSignDataResponse();
    }

    /**
     * Create an instance of {@link GenRandomResponse }
     * 
     */
    public GenRandomResponse createGenRandomResponse() {
        return new GenRandomResponse();
    }

    /**
     * Create an instance of {@link VerifyTimeStampResponse }
     * 
     */
    public VerifyTimeStampResponse createVerifyTimeStampResponse() {
        return new VerifyTimeStampResponse();
    }

    /**
     * Create an instance of {@link SignDataEx }
     * 
     */
    public SignDataEx createSignDataEx() {
        return new SignDataEx();
    }

    /**
     * Create an instance of {@link SignDataByteExRawResponse }
     * 
     */
    public SignDataByteExRawResponse createSignDataByteExRawResponse() {
        return new SignDataByteExRawResponse();
    }

    /**
     * Create an instance of {@link SignDataP7DetachResponse }
     * 
     */
    public SignDataP7DetachResponse createSignDataP7DetachResponse() {
        return new SignDataP7DetachResponse();
    }

    /**
     * Create an instance of {@link VerifyDetachSignData }
     * 
     */
    public VerifyDetachSignData createVerifyDetachSignData() {
        return new VerifyDetachSignData();
    }

    /**
     * Create an instance of {@link VerifySignedDataResponse }
     * 
     */
    public VerifySignedDataResponse createVerifySignedDataResponse() {
        return new VerifySignedDataResponse();
    }

    /**
     * Create an instance of {@link SignDataByteExResponse }
     * 
     */
    public SignDataByteExResponse createSignDataByteExResponse() {
        return new SignDataByteExResponse();
    }

    /**
     * Create an instance of {@link Hello }
     * 
     */
    public Hello createHello() {
        return new Hello();
    }

    /**
     * Create an instance of {@link SignData }
     * 
     */
    public SignData createSignData() {
        return new SignData();
    }

    /**
     * Create an instance of {@link SignDataExResponse }
     * 
     */
    public SignDataExResponse createSignDataExResponse() {
        return new SignDataExResponse();
    }

    /**
     * Create an instance of {@link HelloResponse }
     * 
     */
    public HelloResponse createHelloResponse() {
        return new HelloResponse();
    }

    /**
     * Create an instance of {@link CreateTimeStampRequest }
     * 
     */
    public CreateTimeStampRequest createCreateTimeStampRequest() {
        return new CreateTimeStampRequest();
    }

    /**
     * Create an instance of {@link VerifyDetachSignDataExResponse }
     * 
     */
    public VerifyDetachSignDataExResponse createVerifyDetachSignDataExResponse() {
        return new VerifyDetachSignDataExResponse();
    }

    /**
     * Create an instance of {@link CreateTimeStampRequestResponse }
     * 
     */
    public CreateTimeStampRequestResponse createCreateTimeStampRequestResponse() {
        return new CreateTimeStampRequestResponse();
    }

    /**
     * Create an instance of {@link CreateTimeStampResponse }
     * 
     */
    public CreateTimeStampResponse createCreateTimeStampResponse() {
        return new CreateTimeStampResponse();
    }

    /**
     * Create an instance of {@link SignDataP7Detach }
     * 
     */
    public SignDataP7Detach createSignDataP7Detach() {
        return new SignDataP7Detach();
    }

    /**
     * Create an instance of {@link GetSealImageResponse }
     * 
     */
    public GetSealImageResponse createGetSealImageResponse() {
        return new GetSealImageResponse();
    }

    /**
     * Create an instance of {@link SignDataByteExRaw }
     * 
     */
    public SignDataByteExRaw createSignDataByteExRaw() {
        return new SignDataByteExRaw();
    }

    /**
     * Create an instance of {@link GetServerCertResponse }
     * 
     */
    public GetServerCertResponse createGetServerCertResponse() {
        return new GetServerCertResponse();
    }

    /**
     * Create an instance of {@link GenRandom }
     * 
     */
    public GenRandom createGenRandom() {
        return new GenRandom();
    }

    /**
     * Create an instance of {@link VerifySignedData }
     * 
     */
    public VerifySignedData createVerifySignedData() {
        return new VerifySignedData();
    }

    /**
     * Create an instance of {@link VerifyCertAndSignedDataResponse }
     * 
     */
    public VerifyCertAndSignedDataResponse createVerifyCertAndSignedDataResponse() {
        return new VerifyCertAndSignedDataResponse();
    }

    /**
     * Create an instance of {@link GetSealImage }
     * 
     */
    public GetSealImage createGetSealImage() {
        return new GetSealImage();
    }

    /**
     * Create an instance of {@link VerifyDetachSignDataEx }
     * 
     */
    public VerifyDetachSignDataEx createVerifyDetachSignDataEx() {
        return new VerifyDetachSignDataEx();
    }

    /**
     * Create an instance of {@link GetServerCertExResponse }
     * 
     */
    public GetServerCertExResponse createGetServerCertExResponse() {
        return new GetServerCertExResponse();
    }

    /**
     * Create an instance of {@link VerifyCertAndSignedData }
     * 
     */
    public VerifyCertAndSignedData createVerifyCertAndSignedData() {
        return new VerifyCertAndSignedData();
    }

    /**
     * Create an instance of {@link GetServerCert }
     * 
     */
    public GetServerCert createGetServerCert() {
        return new GetServerCert();
    }

    /**
     * Create an instance of {@link SignDataResponse }
     * 
     */
    public SignDataResponse createSignDataResponse() {
        return new SignDataResponse();
    }

    /**
     * Create an instance of {@link GetServerCertEx }
     * 
     */
    public GetServerCertEx createGetServerCertEx() {
        return new GetServerCertEx();
    }

    /**
     * Create an instance of {@link VerifyTimeStamp }
     * 
     */
    public VerifyTimeStamp createVerifyTimeStamp() {
        return new VerifyTimeStamp();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = SignDataByteEx.class)
    public JAXBElement<byte[]> createSignDataByteExArg1(byte[] value) {
        return new JAXBElement<byte[]>(_SignDataByteExArg1_QNAME, byte[].class, SignDataByteEx.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTimeStamp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "createTimeStamp")
    public JAXBElement<CreateTimeStamp> createCreateTimeStamp(CreateTimeStamp value) {
        return new JAXBElement<CreateTimeStamp>(_CreateTimeStamp_QNAME, CreateTimeStamp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenRandom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "genRandom")
    public JAXBElement<GenRandom> createGenRandom(GenRandom value) {
        return new JAXBElement<GenRandom>(_GenRandom_QNAME, GenRandom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataP7DetachResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataP7DetachResponse")
    public JAXBElement<SignDataP7DetachResponse> createSignDataP7DetachResponse(SignDataP7DetachResponse value) {
        return new JAXBElement<SignDataP7DetachResponse>(_SignDataP7DetachResponse_QNAME, SignDataP7DetachResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataEx")
    public JAXBElement<SignDataEx> createSignDataEx(SignDataEx value) {
        return new JAXBElement<SignDataEx>(_SignDataEx_QNAME, SignDataEx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTimeStampResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "createTimeStampResponse")
    public JAXBElement<CreateTimeStampResponse> createCreateTimeStampResponse(CreateTimeStampResponse value) {
        return new JAXBElement<CreateTimeStampResponse>(_CreateTimeStampResponse_QNAME, CreateTimeStampResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyTimeStamp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyTimeStamp")
    public JAXBElement<VerifyTimeStamp> createVerifyTimeStamp(VerifyTimeStamp value) {
        return new JAXBElement<VerifyTimeStamp>(_VerifyTimeStamp_QNAME, VerifyTimeStamp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSealImage }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getSealImage")
    public JAXBElement<GetSealImage> createGetSealImage(GetSealImage value) {
        return new JAXBElement<GetSealImage>(_GetSealImage_QNAME, GetSealImage.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataExResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataExResponse")
    public JAXBElement<SignDataExResponse> createSignDataExResponse(SignDataExResponse value) {
        return new JAXBElement<SignDataExResponse>(_SignDataExResponse_QNAME, SignDataExResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerCertResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getServerCertResponse")
    public JAXBElement<GetServerCertResponse> createGetServerCertResponse(GetServerCertResponse value) {
        return new JAXBElement<GetServerCertResponse>(_GetServerCertResponse_QNAME, GetServerCertResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GenRandomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "genRandomResponse")
    public JAXBElement<GenRandomResponse> createGenRandomResponse(GenRandomResponse value) {
        return new JAXBElement<GenRandomResponse>(_GenRandomResponse_QNAME, GenRandomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataByteEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataByteEx")
    public JAXBElement<SignDataByteEx> createSignDataByteEx(SignDataByteEx value) {
        return new JAXBElement<SignDataByteEx>(_SignDataByteEx_QNAME, SignDataByteEx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTimeStampRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "createTimeStampRequest")
    public JAXBElement<CreateTimeStampRequest> createCreateTimeStampRequest(CreateTimeStampRequest value) {
        return new JAXBElement<CreateTimeStampRequest>(_CreateTimeStampRequest_QNAME, CreateTimeStampRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTimeStampRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "createTimeStampRequestResponse")
    public JAXBElement<CreateTimeStampRequestResponse> createCreateTimeStampRequestResponse(CreateTimeStampRequestResponse value) {
        return new JAXBElement<CreateTimeStampRequestResponse>(_CreateTimeStampRequestResponse_QNAME, CreateTimeStampRequestResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signData")
    public JAXBElement<SignData> createSignData(SignData value) {
        return new JAXBElement<SignData>(_SignData_QNAME, SignData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyDetachSignDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyDetachSignDataResponse")
    public JAXBElement<VerifyDetachSignDataResponse> createVerifyDetachSignDataResponse(VerifyDetachSignDataResponse value) {
        return new JAXBElement<VerifyDetachSignDataResponse>(_VerifyDetachSignDataResponse_QNAME, VerifyDetachSignDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataByteExResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataByteExResponse")
    public JAXBElement<SignDataByteExResponse> createSignDataByteExResponse(SignDataByteExResponse value) {
        return new JAXBElement<SignDataByteExResponse>(_SignDataByteExResponse_QNAME, SignDataByteExResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerCert }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getServerCert")
    public JAXBElement<GetServerCert> createGetServerCert(GetServerCert value) {
        return new JAXBElement<GetServerCert>(_GetServerCert_QNAME, GetServerCert.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyCertAndSignedData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyCertAndSignedData")
    public JAXBElement<VerifyCertAndSignedData> createVerifyCertAndSignedData(VerifyCertAndSignedData value) {
        return new JAXBElement<VerifyCertAndSignedData>(_VerifyCertAndSignedData_QNAME, VerifyCertAndSignedData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataResponse")
    public JAXBElement<SignDataResponse> createSignDataResponse(SignDataResponse value) {
        return new JAXBElement<SignDataResponse>(_SignDataResponse_QNAME, SignDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataByteExRaw }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataByteEx_raw")
    public JAXBElement<SignDataByteExRaw> createSignDataByteExRaw(SignDataByteExRaw value) {
        return new JAXBElement<SignDataByteExRaw>(_SignDataByteExRaw_QNAME, SignDataByteExRaw.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSealImageResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getSealImageResponse")
    public JAXBElement<GetSealImageResponse> createGetSealImageResponse(GetSealImageResponse value) {
        return new JAXBElement<GetSealImageResponse>(_GetSealImageResponse_QNAME, GetSealImageResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyDetachSignDataEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyDetachSignDataEx")
    public JAXBElement<VerifyDetachSignDataEx> createVerifyDetachSignDataEx(VerifyDetachSignDataEx value) {
        return new JAXBElement<VerifyDetachSignDataEx>(_VerifyDetachSignDataEx_QNAME, VerifyDetachSignDataEx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyDetachSignData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyDetachSignData")
    public JAXBElement<VerifyDetachSignData> createVerifyDetachSignData(VerifyDetachSignData value) {
        return new JAXBElement<VerifyDetachSignData>(_VerifyDetachSignData_QNAME, VerifyDetachSignData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Hello }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "hello")
    public JAXBElement<Hello> createHello(Hello value) {
        return new JAXBElement<Hello>(_Hello_QNAME, Hello.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataP7Detach }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataP7Detach")
    public JAXBElement<SignDataP7Detach> createSignDataP7Detach(SignDataP7Detach value) {
        return new JAXBElement<SignDataP7Detach>(_SignDataP7Detach_QNAME, SignDataP7Detach.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyDetachSignDataExResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyDetachSignDataExResponse")
    public JAXBElement<VerifyDetachSignDataExResponse> createVerifyDetachSignDataExResponse(VerifyDetachSignDataExResponse value) {
        return new JAXBElement<VerifyDetachSignDataExResponse>(_VerifyDetachSignDataExResponse_QNAME, VerifyDetachSignDataExResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifySignedDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifySignedDataResponse")
    public JAXBElement<VerifySignedDataResponse> createVerifySignedDataResponse(VerifySignedDataResponse value) {
        return new JAXBElement<VerifySignedDataResponse>(_VerifySignedDataResponse_QNAME, VerifySignedDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "helloResponse")
    public JAXBElement<HelloResponse> createHelloResponse(HelloResponse value) {
        return new JAXBElement<HelloResponse>(_HelloResponse_QNAME, HelloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifySignedData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifySignedData")
    public JAXBElement<VerifySignedData> createVerifySignedData(VerifySignedData value) {
        return new JAXBElement<VerifySignedData>(_VerifySignedData_QNAME, VerifySignedData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyTimeStampResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyTimeStampResponse")
    public JAXBElement<VerifyTimeStampResponse> createVerifyTimeStampResponse(VerifyTimeStampResponse value) {
        return new JAXBElement<VerifyTimeStampResponse>(_VerifyTimeStampResponse_QNAME, VerifyTimeStampResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerCertExResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getServerCertExResponse")
    public JAXBElement<GetServerCertExResponse> createGetServerCertExResponse(GetServerCertExResponse value) {
        return new JAXBElement<GetServerCertExResponse>(_GetServerCertExResponse_QNAME, GetServerCertExResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SignDataByteExRawResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "signDataByteEx_rawResponse")
    public JAXBElement<SignDataByteExRawResponse> createSignDataByteExRawResponse(SignDataByteExRawResponse value) {
        return new JAXBElement<SignDataByteExRawResponse>(_SignDataByteExRawResponse_QNAME, SignDataByteExRawResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VerifyCertAndSignedDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "verifyCertAndSignedDataResponse")
    public JAXBElement<VerifyCertAndSignedDataResponse> createVerifyCertAndSignedDataResponse(VerifyCertAndSignedDataResponse value) {
        return new JAXBElement<VerifyCertAndSignedDataResponse>(_VerifyCertAndSignedDataResponse_QNAME, VerifyCertAndSignedDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServerCertEx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jws.back.hb.org.cn/", name = "getServerCertEx")
    public JAXBElement<GetServerCertEx> createGetServerCertEx(GetServerCertEx value) {
        return new JAXBElement<GetServerCertEx>(_GetServerCertEx_QNAME, GetServerCertEx.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "arg1", scope = SignDataByteExRaw.class)
    public JAXBElement<byte[]> createSignDataByteExRawArg1(byte[] value) {
        return new JAXBElement<byte[]>(_SignDataByteExArg1_QNAME, byte[].class, SignDataByteExRaw.class, ((byte[]) value));
    }

}
