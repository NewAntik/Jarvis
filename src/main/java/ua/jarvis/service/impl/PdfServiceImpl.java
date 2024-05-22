package ua.jarvis.service.impl;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.jarvis.model.User;
import ua.jarvis.service.PdfService;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class PdfServiceImpl implements PdfService {

	private final String photoPath;

	public PdfServiceImpl(@Value("${photo.path}")final String photoPath) {
		this.photoPath = photoPath;
	}

	@Override
	public File createUserPdf(final User user) {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		final PDFont font = new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);

		try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
			contentStream.setFont(font, 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(25, 750);
			contentStream.showText("User Details:");
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Name: " + user.getName());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Middle Name: " + user.getMidlName());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Surname: " + user.getSurName());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("RNOKPP: " + user.getRnokpp());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Sex: " + user.getSex());
			contentStream.newLineAtOffset(0, -15);
			contentStream.showText("Driver Type: " + (user.getType() != null ? user.getType().name() : "N/A"));
			contentStream.endText();

			// Load and add photo
			if (user.getPhoto() != null && user.getPhoto().getFileName() != null) {
				String photoFilePath = Paths.get(photoPath, user.getPhoto().getFileName()).toString();
				PDImageXObject pdImage = PDImageXObject.createFromFile(photoFilePath, document);
				contentStream.drawImage(pdImage, 100, 400, 200, 200);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		final File pdfFile = new File("user_" + user.getId() + ".pdf");

		try {
			document.save(pdfFile);
		} catch (IOException e) {
			throw new RuntimeException("Error saving PDF document", e);
		} finally {
			try {
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return pdfFile;
	}
}
