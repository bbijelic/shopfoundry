package org.shopfoundry.core.service.gateway.outbound.smtp;

import java.util.ArrayList;
import java.util.List;

/**
 * Email message
 *
 * @author Bojan Bijelic
 */
public class EmailRequest {

	/**
	 * List of TO receivers
	 */
	private List<String> to = new ArrayList<>();

	/**
	 * List of BCC receivers
	 */
	private List<String> bcc = new ArrayList<>();

	/**
	 * List of CC receivers
	 */
	private List<String> cc = new ArrayList<>();

	/**
	 * List of ReplyTo addresses
	 */
	private List<String> replyTo = new ArrayList<>();

	/**
	 * FROM address
	 */
	private String from;

	/**
	 * Message subject
	 */
	private String subject;

	/**
	 * Message body
	 */
	private String message;

	/**
	 * @return the replyTo
	 */
	public List<String> getReplyTo() {
		return replyTo;
	}

	/**
	 * @param replyTo
	 *            the replyTo to set
	 */
	public void setReplyTo(List<String> replyTo) {
		this.replyTo = replyTo;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @return the bcc
	 */
	public List<String> getBcc() {
		return bcc;
	}

	/**
	 * @param bcc
	 *            the bcc to set
	 */
	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	/**
	 * @return the cc
	 */
	public List<String> getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", bcc=" + bcc + ", cc=" + cc + ", replyTo=" + replyTo + ", from=" + from
				+ ", subject=" + subject + ", message=" + message + "]";
	}

}
