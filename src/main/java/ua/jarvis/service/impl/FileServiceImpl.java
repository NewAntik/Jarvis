package ua.jarvis.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.User;
import ua.jarvis.service.FileFormatterService;
import ua.jarvis.service.FileService;
import ua.jarvis.service.PhotoService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

	private final FileFormatterService docxFormatter;

	private final FileFormatterService shortDOCXFormatter;

	private final PhotoService photoService;

	public FileServiceImpl(
		final DOCXFileFormatterServiceImpl docxFormatter,
		final ShortDataDOCXFileFormatterServiceImpl shortDOCXFormatter,
		final PhotoService photoService

	) {
		this.docxFormatter = docxFormatter;
		this.photoService = photoService;
		this.shortDOCXFormatter = shortDOCXFormatter;
	}

	@Override
	public byte[] createDOCXFromUser(final User user) throws IOException, InvalidFormatException {
		final XWPFDocument document = new XWPFDocument();
		final CTDocument1 CTDdocument = document.getDocument();

		final CTBody body = CTDdocument.getBody();
		final CTSectPr section = body.getSectPr();

		setPageSize(body);
		setProperties(section);

		final CTPageSz pageSize = section.getPgSz();
		pageSize.setW(BigInteger.valueOf(13700)); // width
		pageSize.setH(BigInteger.valueOf(15840)); // height

		addPhoto(user, document);

		final List<XWPFParagraph> paragraphs = docxFormatter.format(user);

		addParagraphs(paragraphs, document, 1);

		return write(document);
	}

	@Override
	public byte[] createShortDOCXDocument(final List<User> users) throws IOException {
		final XWPFDocument document = new XWPFDocument();
		final CTDocument1 CTDdocument = document.getDocument();
		final CTBody body = CTDdocument.getBody();

		setPageSize(body);
		setProperties(body.getSectPr());

		final List<XWPFParagraph> paragraphs = new ArrayList<>();
		for(final User user: users){
			paragraphs.addAll(shortDOCXFormatter.format(user));
		}
		addParagraphs(paragraphs,document,0);

		return write(document);
	}

	private void addPhoto(final User user, final XWPFDocument document) throws IOException, InvalidFormatException {
		if (user.getPhoto() != null) {
			final XWPFParagraph paragraph = document.createParagraph();
			final XWPFRun run = paragraph.createRun();

			final byte[] photoBytes = photoService.findPhotoByName(user.getPhoto().getFileName());
			final InputStream photoInputStream = new ByteArrayInputStream(photoBytes);
			run.addPicture(
				photoInputStream, XWPFDocument.PICTURE_TYPE_JPEG, user.getPhoto().getFileName(),
				Units.toEMU(170), Units.toEMU(170)
			);
		}
	}

	@SuppressWarnings("checkstyle:FinalParameters")
	private void addParagraphs(
		final List<XWPFParagraph> paragraphs,
		final XWPFDocument document,
		int startIndex
		){
		for (final XWPFParagraph p : paragraphs) {
			document.createParagraph();
			document.setParagraph(p, startIndex++);
		}
	}

	private void setPageSize(final CTBody body){
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
	}

	private void setProperties(final CTSectPr section){
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
	}

	private byte[] write(final XWPFDocument document) {
		try (document; ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.write(out);
			return out.toByteArray();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
