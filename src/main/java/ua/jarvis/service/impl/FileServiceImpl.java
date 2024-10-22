package ua.jarvis.service.impl;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

	private static final String PHOTO_PATH = "/app/photos/";

	private final FileFormatterService pdfFormatter;

	private final FileFormatterService docxFormatter;

	private final FileFormatterService shortDOCXFormatter;

	private final PhotoService photoService;

	public FileServiceImpl(
		final PDFFileFormatterServiceImpl pdfFormatter,
		final DOCXFileFormatterServiceImpl docxFormatter,
		final ShortDataDOCXFileFormatterServiceImpl shortDOCXFormatter,
		final PhotoService photoService

	) {
		this.pdfFormatter = pdfFormatter;
		this.docxFormatter = docxFormatter;
		this.photoService = photoService;
		this.shortDOCXFormatter = shortDOCXFormatter;
	}

	@Override
	public byte[] createDOCXFromUser(final User user) throws IOException, InvalidFormatException {
		final XWPFDocument document = new XWPFDocument();
		final XWPFParagraph paragraph = document.createParagraph();
		final XWPFRun run = paragraph.createRun();
		final CTDocument1 CTDdocument = document.getDocument();
		final CTBody body = CTDdocument.getBody();
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		final CTSectPr section = body.getSectPr();
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		final CTPageSz pageSize = section.getPgSz();
		pageSize.setW(BigInteger.valueOf(13700)); // width
		pageSize.setH(BigInteger.valueOf(15840)); // height

		if (user.getPhoto() != null) {
			final byte[] photoBytes = photoService.findPhotoByName(user.getPhoto().getFileName());
			final InputStream photoInputStream = new ByteArrayInputStream(photoBytes);
			run.addPicture(
				photoInputStream, XWPFDocument.PICTURE_TYPE_JPEG, user.getPhoto().getFileName(),
					Units.toEMU(170), Units.toEMU(170)
			);
		}

		final List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) docxFormatter.format(user);

		int index = 1;
		for (final XWPFParagraph p : paragraphs) {
			document.createParagraph();
			document.setParagraph(p, index++);

		}

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.write(out);
			return out.toByteArray();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			document.close();
		}
	}

	@Override
	public byte[] createShortDOCXDocument(final List<User> users) throws IOException, InvalidFormatException {
		final XWPFDocument document = new XWPFDocument();
		final CTDocument1 CTDdocument = document.getDocument();
		final CTBody body = CTDdocument.getBody();
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		final CTSectPr section = body.getSectPr();
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		final XWPFParagraph paragraph = (XWPFParagraph) shortDOCXFormatter.format(users);
		document.createParagraph();
		document.setParagraph(paragraph, 0);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.write(out);
			return out.toByteArray();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			document.close();
		}
	}

	@Override
	public File createUserPdf(final User user) throws IOException {
		final File file = new File(user.getSurName() + "_" + user.getName() + "_" + user.getMiddleName() + ".pdf");
		final PdfWriter writer = new PdfWriter(file);
		final PdfDocument pdf = new PdfDocument(writer);
		final PageSize pageSize = new PageSize(1000, 1500);
		pdf.setDefaultPageSize(pageSize);
		final Document document = new Document(pdf);

		// Add user's photo if available
		if (user.getPhoto() != null) {
			final Image image = new Image(ImageDataFactory.create(PHOTO_PATH + user.getPhoto().getFileName()));
			image.setWidth(170);
			image.setHeight(170);

			document.add(image);
		}

		final List<IBlockElement> pdfElements = (List<IBlockElement>) pdfFormatter.format(user);
		pdfElements.forEach(document::add);
		document.close();

		return file;
	}
}
