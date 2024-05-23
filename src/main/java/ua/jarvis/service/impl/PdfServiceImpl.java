package ua.jarvis.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
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
	private static final PDType1Font COURIER_BOLD_FORMAT = new PDType1Font(Standard14Fonts.FontName.COURIER_BOLD);
	private static final PDType1Font HELVETICA_FORMAT = new PDType1Font(Standard14Fonts.FontName.TIMES_ITALIC);
	private static final int LINE_OFFSET_TY = -30;
	private static final int LINE_OFFSET_TX = 0;
	private static final int FONT_SIZE = 20;

	private final String photoPath;

	private Document document;

	private PDPage page;

	public PdfServiceImpl(@Value("${photo.path}")final String photoPath) {
		this.photoPath = photoPath;
	}

	public File itx(final User user) throws IOException {
		File file = new File("user.pdf");
		PdfWriter writer = new PdfWriter(file);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf);

		PdfFont fontBold = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf");
		PdfFont fontRegular = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans.ttf");

		document.add(new Paragraph("Ім'я: ").setFont(fontBold).add(user.getName()).setFont(fontRegular));
		document.add(new Paragraph("По батькові: ").setFont(fontBold).add(user.getMidlName()));
		document.add(new Paragraph("Прізвище: ").setFont(fontBold).add(user.getSurName()));
		document.add(new Paragraph("РНОКПП: ").setFont(fontBold).add(user.getRnokpp()));
		document.add(new Paragraph("Стать: ").setFont(fontBold).add(user.getSex()));
		document.add(new Paragraph("Водій: ").setFont(fontBold).add(user.getType() != null ? user.getType().name() : "N/A"));

		document.close();
		return file;
	}

	@Override
	public File createUserPdf(final User user) throws IOException {
		final File file = new File(user.getSurName() + user.getName() + user.getMidlName() + ".pdf");
		PdfWriter writer = new PdfWriter(file);
		PdfDocument pdf = new PdfDocument(writer);
		document = new Document(pdf);

		PdfFont fontBold = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf");
		PdfFont fontRegular = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans.ttf");

		Image image = new Image(ImageDataFactory.create(photoPath + user.getPhoto().getFileName()));
		image.setWidth(200);
		image.setHeight(200);

		document.add(image);

		document.add(new Paragraph()
			.add(new Text("Ім'я: ").setFont(fontBold))
			.add(new Text(user.getName()).setFont(fontRegular))
			.setMarginBottom(10)
		);
		document.add(new Paragraph("По батькові: ").setFont(fontBold).add(user.getMidlName()));
		document.add(new Paragraph("Прізвище: ").setFont(fontBold).add(user.getSurName()));
		document.add(new Paragraph("РНОКПП: ").setFont(fontBold).add(user.getRnokpp()));
		document.add(new Paragraph("Стать: ").setFont(fontBold).add(user.getSex()));
		document.add(new Paragraph("Водій: ").setFont(fontBold).add(user.getType() != null ? user.getType().name() : "N/A"));


		document.close();

		return file;
	}
}
