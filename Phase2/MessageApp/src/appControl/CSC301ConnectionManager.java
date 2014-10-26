package appControl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class CSC301ConnectionManager {

	private static boolean useHTTPS = true;
	private static String serverURL = "";
	private static CertificateFactory cf;
	private static Certificate caCert;
	private static KeyStore ks;
	private static TrustManagerFactory tmf;
	private static SSLContext sc;
	private static String caCertString = "-----BEGIN CERTIFICATE-----\n"+
"MIIETjCCAzagAwIBAgIBADANBgkqhkiG9w0BAQsFADB8MQswCQYDVQQGEwJDQTEQ\n"+
"MA4GA1UECBMHT250YXJpbzEQMA4GA1UEBxMHVG9yb250bzESMBAGA1UEChMJcnMt\n"+
"bnMtbmV0MR4wHAYJKoZIhvcNAQkBFg9hZG1pbkBycy1ucy5uZXQxFTATBgNVBAMT\n"+
"DHJzLW5zLW5ldC1jYTAeFw0xNDA1MDkwNjAxMzhaFw0yODAxMTYwNjAxMzhaMHwx\n"+
"CzAJBgNVBAYTAkNBMRAwDgYDVQQIEwdPbnRhcmlvMRAwDgYDVQQHEwdUb3JvbnRv\n"+
"MRIwEAYDVQQKEwlycy1ucy1uZXQxHjAcBgkqhkiG9w0BCQEWD2FkbWluQHJzLW5z\n"+
"Lm5ldDEVMBMGA1UEAxMMcnMtbnMtbmV0LWNhMIIBIjANBgkqhkiG9w0BAQEFAAOC\n"+
"AQ8AMIIBCgKCAQEAvE1q5vL96cWM/tfzRK2kk8bKLlWPzkGfslBRVFeVcyuK2WDN\n"+
"bL0mG7iHUbF8Iur3xux4X7nWcHa5eos/vv5MzHOHJDxWx67+mFifahWVAxum4aOn\n"+
"PlqyETjBscLzViF1CVIXlyiJKuxXu1la6rGu7xiDdoDqL2aXOccaSCHbimsRZ41b\n"+
"chbY5+xULYEHgwAnfb2OAe4odG12WfVrBqOtcQrReWTz8ofujevRPGVgvKEJgziA\n"+
"o4idrMHKXZ2WzyM1zyE28PVw0yF4OfKzrt/x15rcl/Bqxy36OwF5FcusAmBv4z4n\n"+
"BOLBC2/ngGaXsTNoQZ1vga8RyRvdVBoe6j2+CQIDAQABo4HaMIHXMB0GA1UdDgQW\n"+
"BBSMwVpwgGm0xeoBkyQs+EutOCK+pTCBpwYDVR0jBIGfMIGcgBSMwVpwgGm0xeoB\n"+
"kyQs+EutOCK+paGBgKR+MHwxCzAJBgNVBAYTAkNBMRAwDgYDVQQIEwdPbnRhcmlv\n"+
"MRAwDgYDVQQHEwdUb3JvbnRvMRIwEAYDVQQKEwlycy1ucy1uZXQxHjAcBgkqhkiG\n"+
"9w0BCQEWD2FkbWluQHJzLW5zLm5ldDEVMBMGA1UEAxMMcnMtbnMtbmV0LWNhggEA\n"+
"MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggEBABdLys6LDkE0Nu7jnpLr\n"+
"jOpHveQxbgZxLQBvWzEat7fFy2GkyGtujQW7qghaf6j2zGTWPOGaA3VqJhsKx8Dk\n"+
"TlZfqVuehi/eZcmz5ZiNyyo93zzjwPuVxdj4bp6WjqQZphHCLk3OJKpL+UcUVkud\n"+
"V0mA+iHo2pSfjphXAX50Wua3liGwkKAbGcIwVrF8uJn7pi8yJ6G1FZwNYMhLwD0/\n"+
"4RVLZ95iJrtb3ZPEQfqC9pom7gF8v18QOjhrjDH+46CgVYIUe5gnSgoYbLGKomqw\n"+
"t86LiGNAfSENFpt0edSdI68Q6LoxQiMaqACtSXwO7XxWetkTAS9DN9ereCJalBpn\n"+
"bb4=\n"+
"-----END CERTIFICATE-----";
	
	private static CSC301ConnectionManager instance = null;
	
	private CSC301ConnectionManager(){
		setUpSSL();
	}
	
	public HttpURLConnection getServerConnection(String path) throws IOException{
		HttpURLConnection conn;
		if(useHTTPS){
			conn = (HttpsURLConnection) (new URL(serverURL + path)).openConnection();
			((HttpsURLConnection)conn).setSSLSocketFactory(sc.getSocketFactory());
		} else {
			conn = (HttpURLConnection) (new URL(serverURL + path)).openConnection();
		}
		
		return conn;
		
	}
	
	public static CSC301ConnectionManager getInstance() {
		if(instance == null) {
			instance = new CSC301ConnectionManager();
		}
		
		return instance;
	}
	
	public void setUseHTTPS(boolean set){
		useHTTPS = set;
	}
	
	public void setServerURL(String url){
		serverURL = url;
	}
	
	private void setUpSSL() {
		//TODO: actually handle these exceptions...
		InputStream caStream = new ByteArrayInputStream(caCertString.getBytes());
		try {
			cf = CertificateFactory.getInstance("X.509");
			caCert = cf.generateCertificate(caStream);
			caStream.close();
			ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null, null);
			ks.setCertificateEntry("caCert", caCert);
			tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(ks);
			sc = SSLContext.getInstance("TLS");
			sc.init(null,  tmf.getTrustManagers(), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
