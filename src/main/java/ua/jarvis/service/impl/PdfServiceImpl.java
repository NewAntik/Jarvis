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


import java.io.File;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

	private final String photoPath;

	public PdfServiceImpl(@Value("${photo.path}")final String photoPath){
		this.photoPath = photoPath;
	}

	@Override
	public File createUserPdf(final User user) throws IOException {
		final File file = new File(user.getSurName() + user.getName() + user.getMidlName() + ".pdf");
		final PdfWriter writer = new PdfWriter(file);
		final PdfDocument pdf = new PdfDocument(writer);
		final Document document = new Document(pdf);

		final PdfFont boldFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf");
		final PdfFont regularFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans.ttf");


		// Add user's photo if available
		if (user.getPhoto() != null) {
			final Image image = new Image(ImageDataFactory.create(photoPath + user.getPhoto().getFileName()));
			image.setWidth(170);
			image.setHeight(170);

			document.add(image);
		}

		// Add user's basic information
		document.add(new Paragraph().add(new Text("Ім'я: ").setFont(boldFont)).add(new Text(user.getName()).setFont(
			regularFont)).setMarginBottom(10).setMarginTop(50));
		document.add(new Paragraph().add(new Text("По батькові: ").setFont(boldFont)).add(new Text(user.getMidlName()).setFont(
			regularFont)).setMarginBottom(10));
		document.add(new Paragraph().add(new Text("Прізвище: ").setFont(boldFont)).add(new Text(user.getSurName()).setFont(
			regularFont)).setMarginBottom(10));
		document.add(new Paragraph().add(new Text("РНОКПП: ").setFont(boldFont)).add(new Text(user.getRnokpp()).setFont(
			regularFont)).setMarginBottom(10));
		document.add(new Paragraph().add(new Text("Стать: ").setFont(boldFont)).add(new Text(user.getSex()).setFont(
			regularFont)).setMarginBottom(10));

		// Add user's driver type if available
		if (user.getDriverType() != null) {
			document.add(new Paragraph().add(new Text("Водій чи власник авто: ").setFont(boldFont)).add(new Text(user.getDriverType().toString()).setFont(
				regularFont)).setMarginBottom(10));
		}

		// Add user's cars if available
		for (Car car : user.getCars()) {
			document.add(new Paragraph().add(new Text("Засоби пересування: ").setFont(boldFont)).add(new Text(car.toString()).setFont(
				regularFont)).setMarginBottom(10));
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
}
