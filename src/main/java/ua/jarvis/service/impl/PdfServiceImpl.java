package ua.jarvis.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.jarvis.model.User;
import ua.jarvis.service.FileFormatter;
import ua.jarvis.service.PdfService;

import java.io.File;
import java.io.IOException;

@Service
public class PdfServiceImpl implements PdfService {

	private final String photoPath;

	private final FileFormatter formatter;

	public PdfServiceImpl(
		@Value("${photo.path}") final String photoPath,
		final FileFormatter formatter
	){
		this.photoPath = photoPath;
		this.formatter = formatter;
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

		formatter.format(user).forEach(document::add);

//		// Add user's juridicalPerson if available
//		if (user.getJuridicalPerson() != null) {
//			document.add(new Paragraph().add(new Text("Інфо про юридичну особу : ").setFont(boldFont)).add(new Text(user.getJuridicalPerson().toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's addresses if available
//		for (Address address : user.getAddresses()) {
//			document.add(new Paragraph().add(new Text("Адреса: ").setFont(boldFont)).add(new Text(address.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's passports if available
//		for (Passport passport : user.getPassports()) {
//			document.add(new Paragraph().add(new Text("Паспорт: ").setFont(boldFont)).add(new Text(passport.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's driver licenses if available
//		for (DriverLicense license : user.getDriverLicense()) {
//			document.add(new Paragraph().add(new Text("Водійське посвідчення: ").setFont(boldFont)).add(new Text(license.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's foreign passports if available
//		for (ForeignPassport passport : user.getForeignPassports()) {
//			document.add(new Paragraph().add(new Text("Закордонний паспорт: ").setFont(boldFont)).add(new Text(passport.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's emails if available
//		for (Email email : user.getEmails()) {
//			document.add(new Paragraph().add(new Text("Email: ").setFont(boldFont)).add(new Text(email.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's phones if available
//		for (Phone phone : user.getPhones()) {
//			document.add(new Paragraph().add(new Text("Телефон: ").setFont(boldFont)).add(new Text(phone.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}
//
//		// Add user's emails if available
//		for (Email email : user.getEmails()) {
//			document.add(new Paragraph().add(new Text("Телефон: ").setFont(boldFont)).add(new Text(email.toString()).setFont(
//				regularFont)).setMarginBottom(10));
//		}

		document.close();

		return file;
	}
}
