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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.jarvis.model.User;
import ua.jarvis.service.FileFormatter;
import ua.jarvis.service.FileService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

	private final String photoPath;

	private final FileFormatter pdfFormatter;

	private final FileFormatter docxFormatter;

	public FileServiceImpl(
		@Value("${photo.path}") final String photoPath,
		final PDFFileFormatterImpl pdfFormatter,
		final DOCXFileFormatterImpl docxFormatter

	) {
		this.photoPath = photoPath;
		this.pdfFormatter = pdfFormatter;
		this.docxFormatter = docxFormatter;
	}

	@Override
	public byte[] createDOCXFromUser(final User user) throws IOException {
		final XWPFDocument document = new XWPFDocument();
		final XWPFParagraph paragraph = document.createParagraph();
		final XWPFRun run = paragraph.createRun();
		CTDocument1 CTDdocument = document.getDocument();
		CTBody body = CTDdocument.getBody();
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		CTPageSz pageSize = section.getPgSz();
		pageSize.setW(BigInteger.valueOf(13700)); // width
		pageSize.setH(BigInteger.valueOf(15840)); // height

		if (user.getPhoto() != null) {
			try {
				byte[] photoBytes = Files.readAllBytes(Paths.get(photoPath + user.getPhoto().getFileName()));
				final InputStream photoInputStream = new ByteArrayInputStream(photoBytes);
				run.addPicture(photoInputStream, XWPFDocument.PICTURE_TYPE_JPEG, user.getPhoto().getFileName(),
					Units.toEMU(170), Units.toEMU(170)
				);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				throw new RuntimeException(e);
			}
		}

		final List<XWPFParagraph> paragraphs = (List<XWPFParagraph>) docxFormatter.format(user);

		int index = 1;
		for (XWPFParagraph p : paragraphs) {
			document.createParagraph();
			document.setParagraph(p, index++);

		}

		try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			document.write(out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			document.close();
		}
	}


	@Override
	public File createUserPdf(final User user) throws IOException {
		final File file = new File(user.getSurName() + "_" + user.getName() + "_" + user.getMidlName() + ".pdf");
		final PdfWriter writer = new PdfWriter(file);
		final PdfDocument pdf = new PdfDocument(writer);
		final PageSize pageSize = new PageSize(1000, 1500);
		pdf.setDefaultPageSize(pageSize);
		final Document document = new Document(pdf);

		// Add user's photo if available
		if (user.getPhoto() != null) {
			final Image image = new Image(ImageDataFactory.create(photoPath + user.getPhoto().getFileName()));
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
