package org.shopfoundry.core.security.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Permissive trust manager. Trusts all certificates. Please note, this trust
 * manager is unsecure and shall be used with caution.
 *
 * @author Bojan Bijelic
 */
public class PermissiveTrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// Intentionally not implemented
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// Intentionally not implemented
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}

}
