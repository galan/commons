package de.galan.commons.net.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


/**
 * Datasource for mail attachments
 */
public class AttachmentDataSource implements DataSource {

	private Attachment attachment;


	public AttachmentDataSource(Attachment attachment) {
		this.attachment = attachment;
	}


	public Attachment getAttachment() {
		return attachment;
	}


	@Override
	public String getContentType() {
		return getAttachment().getMimeType();
	}


	@Override
	public InputStream getInputStream() throws IOException {
		if ((attachment.getData() == null) || (attachment.getData().length == 0)) {
			throw new IOException("No attachmentdata available");
		}
		return new ByteArrayInputStream(attachment.getData());
	}


	@Override
	public String getName() {
		return getAttachment().getFilename();
	}


	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}

}
