package de.galan.commons.net.mail;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.galan.commons.net.MimeType;


/**
 * Represention of a Mail-Attachment
 */
public class Attachment {

	private String filename;
	private byte[] data;
	private String mimeType;


	public Attachment(String filename, byte[] data) {
		this(filename, data, MimeType.APPLICATION_OCTETSTREAM);
	}


	public Attachment(String filename, byte[] data, MimeType mimeType) {
		this(filename, data, mimeType.getMimeType());
	}


	@JsonCreator
	public Attachment(@JsonProperty("filename") String filename, @JsonProperty("data") byte[] data, @JsonProperty("mimetype") String mimeType) {
		setFilename(filename);
		setData(data);
		setMimeType(mimeType);
	}


	public Attachment(String filename, InputStream stream) throws IOException {
		this(filename, stream, MimeType.APPLICATION_OCTETSTREAM);
	}


	public Attachment(String filename, InputStream stream, MimeType mimeType) throws IOException {
		this(filename, stream, mimeType.getMimeType());
	}


	public Attachment(String filename, InputStream stream, String mimeType) throws IOException {
		setFilename(filename);
		setData(stream);
		setMimeType(mimeType);
	}


	public byte[] getData() {
		return data;
	}


	public void setData(byte[] attachmentData) {
		data = attachmentData;
	}


	public void setData(InputStream stream) throws IOException {
		data = IOUtils.toByteArray(stream);
	}


	public String getMimeType() {
		return (mimeType == null) ? MimeType.APPLICATION_OCTETSTREAM.getMimeType() : mimeType;
	}


	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}

}
