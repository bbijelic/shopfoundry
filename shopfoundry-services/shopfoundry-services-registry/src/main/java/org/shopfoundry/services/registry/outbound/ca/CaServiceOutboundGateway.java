package org.shopfoundry.services.registry.outbound.ca;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.shopfoundry.core.service.gateway.outbound.OutboundGateway;

/**
 * Outbound Gateway for interaction with Certificate Authority service.
 *
 * @author Bojan Bijlic
 * 
 *         TODO: To be replaced with CMP client implementation instead of
 *         HTTP(S)
 */
public interface CaServiceOutboundGateway extends OutboundGateway {

	/**
	 * Pulls certificate chain from the Certificate Authority (CA)
	 * 
	 * @return the list of certificate authority certificates (chain)
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws CertificateException
	 */
	public List<X509Certificate> pullCertificateChain()
			throws ClientProtocolException, IOException, CertificateException;

	/**
	 * Requests certificate signing from the Certificate Authority (CA)
	 * 
	 * @param certificateSubjectInformation
	 * @return the signed certificate
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws CertificateException
	 */
	public X509Certificate requestCertificateSign(
			CertificateSubjectInformation certificateSubjectInformation)
			throws UnsupportedEncodingException, ClientProtocolException,
			IOException, CertificateException;

	/**
	 * Requests certificate revocation.
	 * 
	 * @param certificate
	 */
	public void requestCertificateRevocation(X509Certificate certificate);
}
