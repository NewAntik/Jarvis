package ua.jarvis.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.jarvis.model.Address;
import ua.jarvis.model.Car;
import ua.jarvis.model.DriverLicense;
import ua.jarvis.model.Email;
import ua.jarvis.model.ForeignPassport;
import ua.jarvis.model.Passport;
import ua.jarvis.model.Phone;
import ua.jarvis.model.User;
import ua.jarvis.service.PdfService;
import com.itextpdf.layout.element.Cell;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;

@Service
public class PdfServiceImpl implements PdfService {
	private static final Paragraph INFO_NOT_PRESENT_PARAGRAPH = new Paragraph().add(new Text(INFO_NOT_PRESENT_MESSAGE));

	private final String photoPath;

	private PdfFont boldFont;

	private PdfFont regularFont;

	private User user;

	public PdfServiceImpl(@Value("${photo.path}")final String photoPath){
		this.photoPath = photoPath;
	}

	@Override
	public File createUserPdf(final User user) throws IOException {
		setupUser(user);
		final File file = new File(user.getSurName() + "_" + user.getName() + "_" + user.getMidlName() + ".pdf");
		final PdfWriter writer = new PdfWriter(file);
		final PdfDocument pdf = new PdfDocument(writer);
		final PageSize pageSize = new PageSize(1000, 1500);
		pdf.setDefaultPageSize(pageSize);
		final Document document = new Document(pdf);

		boldFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf");
		regularFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans.ttf");


		// Add user's photo if available
		if (user.getPhoto() != null) {
			final Image image = new Image(ImageDataFactory.create(photoPath + user.getPhoto().getFileName()));
			image.setWidth(170);
			image.setHeight(170);

			document.add(image);
		}

		final List<IBlockElement> documentParagraphs = new ArrayList<>();
		documentParagraphs.add(createUserBasicInfoTable());
//		documentParagraphs.addAll();
		documentParagraphs.add(getUserDriverInfoParagraphs());
		documentParagraphs.addAll(getUserCarsInfoParagraphs());


		for (IBlockElement element : documentParagraphs) {
			document.add(element);
		}

		// Add user's juridicalPerson if available
		if (user.getJuridicalPerson() != null) {
			document.add(new Paragraph().add(new Text("Інфо про юридичну особу : ").setFont(boldFont)).add(new Text(user.getJuridicalPerson().toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's addresses if available
		for (Address address : user.getAddresses()) {
			document.add(new Paragraph().add(new Text("Адреса: ").setFont(boldFont)).add(new Text(address.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's passports if available
		for (Passport passport : user.getPassports()) {
			document.add(new Paragraph().add(new Text("Паспорт: ").setFont(boldFont)).add(new Text(passport.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's driver licenses if available
		for (DriverLicense license : user.getDriverLicense()) {
			document.add(new Paragraph().add(new Text("Водійське посвідчення: ").setFont(boldFont)).add(new Text(license.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's foreign passports if available
		for (ForeignPassport passport : user.getForeignPassports()) {
			document.add(new Paragraph().add(new Text("Закордонний паспорт: ").setFont(boldFont)).add(new Text(passport.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's emails if available
		for (Email email : user.getEmails()) {
			document.add(new Paragraph().add(new Text("Email: ").setFont(boldFont)).add(new Text(email.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's phones if available
		for (Phone phone : user.getPhones()) {
			document.add(new Paragraph().add(new Text("Телефон: ").setFont(boldFont)).add(new Text(phone.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's emails if available
		for (Email email : user.getEmails()) {
			document.add(new Paragraph().add(new Text("Телефон: ").setFont(boldFont)).add(new Text(email.toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		document.close();

		return file;
	}

	private List<IBlockElement> getUserCarsInfoParagraphs() {
		final List<IBlockElement> carsInfo = new ArrayList<>();
		carsInfo.add(new Paragraph().add(new Text("Засоби пересування: ").setFont(boldFont).setFontSize(15)).setMarginTop(40));

		for (Car car : user.getCars()) {
			carsInfo.add(
				new Paragraph().add(
					new Text("Марка: ").setFont(boldFont))
					.add(new Text(car.getModel() + ". ").setFont(regularFont)).setMarginBottom(10)
					.add(new Text("Тип: ").setFont(boldFont))
					.add(new Text(car.getType() + ". ").setFont(regularFont)).setMarginBottom(10)
					.add(new Text("Держ. номер: ").setFont(boldFont))
					.add(new Text(car.getPlateNumber() + ". ").setFont(regularFont)).setMarginBottom(10)
					.add(new Text("Колір: ").setFont(boldFont))
					.add(new Text(car.getColor() + ". ").setFont(regularFont)).setMarginBottom(10)
					.add(new Text("Він код: ").setFont(boldFont))
					.add(new Text(car.getVinCode() + ". ").setFont(regularFont)).setMarginBottom(10)
					.add(new Text("Дата випуску: ").setFont(boldFont))
					.add(new Text(car.getIssueDate() + ". ").setFont(regularFont)).setMarginBottom(10)

			);
			carsInfo.add(new LineSeparator(new SolidLine()));
		}

		if(carsInfo.size() == 1){
			carsInfo.add(INFO_NOT_PRESENT_PARAGRAPH);
		}

		return carsInfo;
	}

	private void setupUser(final User user) {
		if(user != null){
			this.user = user;
		}
	}

	private Paragraph getUserDriverInfoParagraphs() {
		final Map<String, String> driverType = Map.of("OWNER", "Власник","DRIVER", "Водій");

		if (user.getDriverType() != null) {
			return new Paragraph().add(new Text("Водій чи власник авто: ").setFont(boldFont)).add(new Text(driverType.get(user.getDriverType().toString())).setFont(
				regularFont)).setMarginBottom(10);
		}

		return INFO_NOT_PRESENT_PARAGRAPH;
	}

	private IBlockElement createUserBasicInfoTable() {
		// Create a table with 2 columns
		Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1})).useAllAvailableWidth();

		// Add header cells
		table.addHeaderCell(new Cell().add(new Paragraph("Ім'я").setFont(boldFont).setFontSize(16)));
		table.addHeaderCell(new Cell().add(new Paragraph("По батькові").setFont(boldFont).setFontSize(16)));
		table.addHeaderCell(new Cell().add(new Paragraph("Прізвище").setFont(boldFont).setFontSize(16)));
		table.addHeaderCell(new Cell().add(new Paragraph("РНОКПП").setFont(boldFont).setFontSize(16)));
		table.addHeaderCell(new Cell().add(new Paragraph("Стать").setFont(boldFont).setFontSize(16)));

		// Add user information cells
		table.addCell(new Cell().add(new Paragraph(user.getName()).setFont(regularFont).setFontSize(14)));
		table.addCell(new Cell().add(new Paragraph(user.getMidlName()).setFont(regularFont).setFontSize(14)));
		table.addCell(new Cell().add(new Paragraph(user.getSurName()).setFont(regularFont).setFontSize(14)));
		table.addCell(new Cell().add(new Paragraph(user.getRnokpp()).setFont(regularFont).setFontSize(14)));
		table.addCell(new Cell().add(new Paragraph(user.getSex()).setFont(regularFont).setFontSize(14)));

		return table;
	}
}
