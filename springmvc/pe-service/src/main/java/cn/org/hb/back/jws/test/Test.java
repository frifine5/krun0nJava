package cn.org.hb.back.jws.test;

import cn.org.hb.back.jws.HbcaServiceService;

public class Test {
	public static void main(String[] args){
		try {
			HbcaServiceService client = new HbcaServiceService();
			String signData = "MIIEfgYJKoZIhvcNAQcCoIIEbzCCBGsCAQExDjAMBggqgRzPVQGDEQUAMAIGAKCCA0AwggM8MIIC4aADAgECAhAr2Ba6ttG1J2iFPRYg8fMAMAwGCCqBHM9VAYN1BQAwgYcxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIDAVIdWJlaTEOMAwGA1UEBwwFV3VoYW4xOzA5BgNVBAoMMkh1YmVpIERpZ2l0YWwgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IENlbnRlciBDTyBMdGQuMQwwCgYDVQQLDANFQ0MxDTALBgNVBAMMBEhCQ0EwHhcNMTgwNTA4MDI1NDQ0WhcNMTkwNTA4MDI1NDQ0WjBMMQswCQYDVQQGEwJDTjEPMA0GA1UECAwG5rmW5YyXMQ8wDQYDVQQHDAbmrabmsYkxGzAZBgNVBAMMEuilhOmYs+a1i+ivleivgeS5pjBZMBMGByqGSM49AgEGCCqBHM9VAYItA0IABNZAXTqNTHCj9Qh0VqEEYKfjC/OOI3p/mTQfD0semkqCCLxEpYOhCo6yy/9I/u0pk94QQCgZdJ+I+pQCc+GsogijggFlMIIBYTAfBgNVHSMEGDAWgBRGDmdWcCqp/+HZhkfvTjhI23GycjAdBgVUEAsHAgQUDBIwMDAwMDAwMDAwMDAwMDA4ODkwEAYFVBALBwEEBwwFKjk5QiowgbMGA1UdHwSBqzCBqDAyoDCgLqQsMCoxCzAJBgNVBAYTAkNOMQwwCgYDVQQLDANDUkwxDTALBgNVBAMMBGNybDcwcqBwoG6GbGxkYXA6Ly8xNzIuMTYuMzkuMTk3OjM4OS9DTj1jcmw3LE9VPUNSTCxDPUNOP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RjbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDAdBgUqVgsHBQQUExIwMDAwMDAwMDAwMDAwMDA4ODgwDAYFKlYLBwMEAxMBODALBgNVHQ8EBAMCBsAwHQYDVR0OBBYEFGyW3HrimV4xQr7LIy+uKiZMr/NsMAwGCCqBHM9VAYN1BQADRwAwRAIgSY58fsC8WcwQ3t1bdPX3efV2nSYJa1mYK+HgHn5KUQ4CIC0M2kqMLQp9SnnEzdqWG13/S+BfTKb9dvkXjcmNcKBnMYIBDDCCAQgCAQEwgZwwgYcxCzAJBgNVBAYTAkNOMQ4wDAYDVQQIDAVIdWJlaTEOMAwGA1UEBwwFV3VoYW4xOzA5BgNVBAoMMkh1YmVpIERpZ2l0YWwgQ2VydGlmaWNhdGUgQXV0aG9yaXR5IENlbnRlciBDTyBMdGQuMQwwCgYDVQQLDANFQ0MxDTALBgNVBAMMBEhCQ0ECECvYFrq20bUnaIU9FiDx8wAwDAYIKoEcz1UBgxEFADANBgkqgRzPVQGCLQEFAARHMEUCIQDHyf7JrEfbfN7Ejy9RF7IOPP3Dua2NyMM7oQn6hMqAfAIgd8JRR9Tx+CbhNWFdMPC/rDG5did+pQJJ+UAd5NpQM8w=";
			String data = "qBwxdIXA6QffMCiwdyGrvuA7O9lZBEw6";
//			String result = client.getHbcaServicePort().verifyDetachSignDataEx(data, signData, false, false, false);
//			String result = client.getHbcaServicePort().signDataEx("12421200MB0X28740Pxnsbdcdjzx", "123");
			byte[] data1 = "123456789".getBytes();
//			String result =client.getHbcaServicePort().signDataByteExRaw("12421200MB0X28740Pxnsbdcdjzx", data1);
//			String result =client.getHbcaServicePort().getServerCertEx("12421200MB0X28740Pxnsbdcdjzx");
			String result =client.getHbcaServicePort().signDataEx("hbstyyzglpt", "123");
			System.out.println(result);
			//client.getServerCert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
