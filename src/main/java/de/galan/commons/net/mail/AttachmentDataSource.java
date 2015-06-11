package de.galan.commons.net.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;


/**
 * Datasource for mail attachments
 *
 * @author galan
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
		return getAttachment().getContentType();
	}


	@Override
	public InputStream getInputStream() throws IOException {
		if ((attachment.getAttachmentData() == null) || (attachment.getAttachmentData().length == 0)) {
			throw new IOException("No attachmentdata available");
		}
		return new ByteArrayInputStream(attachment.getAttachmentData());
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
