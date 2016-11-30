package de.galan.commons.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A list with the typical and most used MIME Types, see also http://www.iana.org/assignments/media-types
 *
 * @author galan
 */
public enum MimeType {

	/** text/html */
	TEXT_HTML("text/html", "html, htm, shtml"),
	/** text/css */
	TEXT_CSS("text/css", "css"),
	/** text/xml */
	TEXT_XML("text/xml", "xml", "rss"),
	/** text/plain */
	TEXT_PLAIN("text/plain", "txt"),
	/** text/mathml */
	TEXT_MATHML("text/mathml", "mml"),
	/** text/vnd.sun.j2me.app-descriptor */
	TEXT_J2ME_JAD("text/vnd.sun.j2me.app-descriptor", "jad"),
	/** text/vnd.wap.wml */
	TEXT_WML("text/vnd.wap.wml", "wml"),
	/** text/x-component */
	TEXT_HTC("text/x-component", "htc"),
	/** image/gif */
	IMAGE_GIF("image/gif", "gif"),
	/** image/jpeg */
	IMAGE_JPEG("image/jpeg", "jpg", "jpeg"),
	/** image/png */
	IMAGE_PNG("image/png", "png"),
	/** image/tiff */
	IMAGE_TIFF("image/tiff", "tiff", "tif"),
	/** image/vnd.wap.wbmp */
	IMAGE_WBMP("image/vnd.wap.wbmp", "wbmp"),
	/** image/x-icon */
	IMAGE_ICO("image/x-icon", "ico"),
	/** image/x-jng */
	IMAGE_JNG("image/x-jng", "jng"),
	/** image/x-ms-bmp */
	IMAGE_BMP("image/x-ms-bmp", "bmp"),
	/** image/svg+xml */
	IMAGE_SVG("image/svg+xml", "svg"),
	/** application/json */
	APPLICATION_JSON("application/json", "json"),
	/** application/x-javascript */
	APPLICATION_JAVASCRIPT("application/x-javascript", "js"),
	/** application/atom+xml */
	APPLICATION_ATOM("application/atom+xml", "atom"),
	/** application/java-archive */
	APPLICATION_JAR("application/java-archive", "jar", "war", "ear", "sar"),
	/** application/mac-binhex40 */
	APPLICATION_HQX("application/mac-binhex40", "hqx"),
	/** application/msword */
	APPLICATION_MSWORD("application/msword", "doc"),
	/** application/vnd.ms-excel */
	APPLICATION_MSEXCEL("application/vnd.ms-excel", "xls"),
	/** application/vnd.ms-powerpoint */
	APPLICATION_MSPOWERPOINT("application/vnd.ms-powerpoint", "ppt"),
	/** application/pdf */
	APPLICATION_PDF("application/pdf", "pdf"),
	/** application/postscript */
	APPLICATION_POSTSCRIPT("application/postscript", "ps", "eps", "ai"),
	/** application/rtf */
	APPLICATION_RTF("application/rtf", "rtf"),
	/** application/zip */
	APPLICATION_ZIP("application/zip", "zip"),
	/** application/vnd.wap.wmlc */
	APPLICATION_WMLC("application/vnd.wap.wmlc", "wmlc"),
	/** application/vnd.wap.xhtml+xml */
	APPLICATION_XHTML("application/vnd.wap.xhtml+xml", "xhtml"),
	/** application/x-cocoa */
	APPLICATION_COCOA("application/x-cocoa", "cco"),
	/** application/x-java-archive-diff */
	APPLICATION_JARDIFF("application/x-java-archive-diff", "jardiff"),
	/** application/x-java-jnlp-file */
	APPLICATION_JNLP("application/x-java-jnlp-file", "jnlp"),
	/** application/x-makeself */
	APPLICATION_RUN("application/x-makeself", "run"),
	/** application/x-perl */
	APPLICATION_PERL("application/x-perl", "pl", "pm"),
	/** application/x-pilot */
	APPLICATION_PALM("application/x-pilot", "prc", "pdb"),
	/** application/x-rar-compressed */
	APPLICATION_RAR("application/x-rar-compressed", "rar"),
	/** application/x-redhat-package-manager */
	APPLICATION_RPM("application/x-redhat-package-manager", "rpm"),
	/** application/x-sea */
	APPLICATION_SEA("application/x-sea", "sea"),
	/** application/x-shockwave-flash */
	APPLICATION_SHOCKWAVE("application/x-shockwave-flash", "swf"),
	/** application/x-stuffit */
	APPLICATION_SIT("application/x-stuffit", "sit"),
	/** application/x-tcl */
	APPLICATION_TCL("application/x-tcl", "tcl", "tk"),
	/** application/x-x509-ca-cert */
	APPLICATION_CACERT("application/x-x509-ca-cert", "der", "pem", "crt"),
	/** application/x-xpinstall */
	APPLICATION_XPINSTALL("application/x-xpinstall", "xpi"),
	/** application/x-java-serialized-object */
	APPLICATION_JAVA_SERIALIZED("application/x-java-serialized-object"),
	/** application/octet-stream */
	APPLICATION_OCTETSTREAM("application/octet-stream", "bin", "exe", "dll", "deb", "dmg", "eot", "iso", "img", "msi", "msp", "msm"),
	/** application/x-gzip */
	APPLICATION_GZIP("application/x-gzip", "gz", "gzip"),
	/** audio/midi */
	AUDIO_MIDI("audio/midi", "mid", "midi", "kar"),
	/** audio/mpeg */
	AUDIO_MPEG("audio/mpeg", "mp3"),
	/** audio/x-realaudio */
	AUDIO_REALAUDIO("audio/x-realaudio", "ra"),
	/** video/3gpp */
	VIDEO_3GPP("video/3gpp", "3gpp", "3gp"),
	/** video/mpeg */
	VIDEO_MPEG("video/mpeg", "mpeg", "mpg"),
	/** video/quicktime */
	VIDEO_QUICKTIME("video/quicktime", "mov", "qt"),
	/** video/x-flv */
	VIDEO_FLV("video/x-flv", "flv"),
	/** video/x-mng */
	VIDEO_MNG("video/x-mng", "mng"),
	/** video/mp4 */
	VIDEO_MP4("video/mp4", "mp4", "divx"),
	/** video/x-ms-asf */
	VIDEO_ASF("video/x-ms-asf", "asx", "asf"),
	/** video/x-ms-wmv */
	VIDEO_WMV("video/x-ms-wmv", "wmv"),
	/** video/x-msvideo */
	VIDEO_AVI("video/x-msvideo", "avi"),
	/** video/x-m4v */
	VIDEO_M4V("video/x-m4v", "m4v");

	private final static Map<String, MimeType> MIME_TYPES_BY_EXTENSION = new HashMap<String, MimeType>();

	private String contentType;
	private final List<String> fileExtensions = new ArrayList<String>();


	MimeType(String contentType, String... fileExtension) {
		this.contentType = contentType;
		for (String extension: fileExtension) {
			fileExtensions.add(extension);
		}
	}


	public String getContentType() {
		return contentType;
	}


	public List<String> getFileExtensions() {
		return fileExtensions;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getContentType());
		if (!getFileExtensions().isEmpty()) {
			builder.append(" (");
			int count = 0;
			for (String extension: getFileExtensions()) {
				if (count++ == 1) {
					builder.append(", ");
				}
				builder.append(extension);
			}
			builder.append(")");
		}
		return builder.toString();
	}

	// Initialize reverse search by extension.
	static {
		for (MimeType type: MimeType.values()) {
			for (String extension: type.getFileExtensions()) {
				MIME_TYPES_BY_EXTENSION.put(extension, type);
			}
		}
	}


	/** Returns the MimeType depending on the file-extension. */
	public static MimeType getMimeType(String extension) {
		MimeType result = null;
		if (extension != null) {
			result = MIME_TYPES_BY_EXTENSION.get(extension.toLowerCase());
		}
		return result;
	}

}
