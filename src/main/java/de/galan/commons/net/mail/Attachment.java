package de.galan.commons.net.mail;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.galan.commons.net.MimeType;


/**
 * Represention of a Mail-Attachment
 *
 * @author galan
 */
public class Attachment {

	private String filename;
	@JsonProperty("data")
	private byte[] attachmentData;
	private String contentType;


	public Attachment(String filename, byte[] data) {
		this(filename, data, MimeType.APPLICATION_OCTETSTREAM);
	}


	public Attachment(String filename, byte[] data, MimeType contentType) {
		this(filename, data, contentType.getContentType());
	}


	@JsonCreator
	public Attachment(@JsonProperty("filename") String filename, @JsonProperty("data") byte[] data, @JsonProperty("contentType") String contentType) {
		setFilename(filename);
		setAttachmentData(data);
		setContentType(contentType);
	}


	public byte[] getAttachmentData() {
		return attachmentData;
	}


	@JsonProperty
	public void setAttachmentData(byte[] attachmentData) {
		this.attachmentData = attachmentData;
	}


	public void setAttachmentData(InputStream stream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(stream, 4096);
		int read = -1;
		while((read = bis.read()) != -1) {
			baos.write(read);
		}
		bis.close();
		attachmentData = baos.toByteArray();
	}


	public String getContentType() {
		return (contentType == null) ? MimeType.APPLICATION_OCTETSTREAM.getContentType() : contentType;
	}


	public void setContentType(String contentType) {
		this.contentType = contentType;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}

}
