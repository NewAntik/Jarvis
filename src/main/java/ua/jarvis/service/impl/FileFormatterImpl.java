package ua.jarvis.service.impl;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;
import ua.jarvis.model.Address;
import ua.jarvis.model.Car;
import ua.jarvis.model.JuridicalPerson;
import ua.jarvis.model.User;
import ua.jarvis.service.FileFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;
import static ua.jarvis.model.enums.Sex.MALE;

@Service
public class FileFormatterImpl implements FileFormatter {
	private static final UnitValue TABLE_SIZE = UnitValue.createPercentValue(100);
	private static final DeviceRgb greenColor = new DeviceRgb(185, 200, 185); // green color
	private static final DeviceRgb redColor = new DeviceRgb(211, 185, 185); // Light red color
	private static final DeviceRgb whiteColor = new DeviceRgb(255, 255, 255); // White color

	private PdfFont boldFont;

	private PdfFont regularFont;

	private User user;

	@Override
	public List<IBlockElement> format(final User user) throws IOException {
		setupUser(user);
		boldFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf");
		regularFont = PdfFontFactory.createFont("src/main/resources/fonts/DejaVuSans.ttf");

		final List<IBlockElement> elements = new ArrayList<>();
		elements.addAll(createUserBasicInfoTable());
		elements.addAll(getJuridicalPersonInfoElements());
		elements.addAll(getResidenceAddress());
		elements.addAll(getUserCarsInfoElements());

		return elements;
	}

	private List<IBlockElement> getResidenceAddress() {
		final List<IBlockElement> residenceInfo = new ArrayList<>();
		residenceInfo.add(new Paragraph().add(
			new Text("Адреси").setFont(boldFont).setFontSize(25)).setMarginTop(35).setTextAlignment(TextAlignment.CENTER)
		);

		if(!user.getAddresses().isEmpty()){
			final Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1}));
			table.setWidth(TABLE_SIZE);

			table.addHeaderCell(new Cell().add(new Paragraph("Місто: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Вулиця: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Номер будинку: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Номер квартири: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Данні на станом на (рік): ").setFont(boldFont)).setBackgroundColor(greenColor));

			int i = 0; // Counter to determine cell color

			for(Address address : user.getAddresses()){
				final DeviceRgb cellColor = (i % 2 == 0) ? whiteColor : greenColor;

				table.addCell(new Cell().add(new Paragraph(address.getCity()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(address.getStreet()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(address.getHomeNumber()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(address.getFlatNumber()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(String.valueOf(address.getUpdatedDate().getYear())).setFont(regularFont)).setBackgroundColor(cellColor));

				i++;
			}

			residenceInfo.add(table);
		}

		if(residenceInfo.size() == 1){
			residenceInfo.add(getInfoNotPresentElement());
		}

		return residenceInfo;
	}

	private List<IBlockElement> getUserCarsInfoElements() {
		final List<IBlockElement> carsInfo = new ArrayList<>();
		carsInfo.add(new Paragraph().add(
			new Text("Транспорт").setFont(boldFont).setFontSize(25)).setMarginTop(35).setTextAlignment(TextAlignment.CENTER)
		);

		if(!user.getCars().isEmpty()){
			final Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1, 1}));
			table.setWidth(TABLE_SIZE);

			// Add header cells
			table.addHeaderCell(new Cell().add(new Paragraph("Марка: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Тип: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Держ. номер: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Колір: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Він код: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Дата випуску: ").setFont(boldFont)).setBackgroundColor(greenColor));
			table.addHeaderCell(new Cell().add(new Paragraph("Водій чи власник: ").setFont(boldFont)).setBackgroundColor(greenColor));

			// Add data cells for each car
			int i = 0; // Counter to determine cell color
			for (Car car : user.getCars()) {
				final DeviceRgb cellColor = (i % 2 == 0) ? whiteColor : greenColor;

				table.addCell(new Cell().add(new Paragraph(car.getModel()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(car.getType().toString()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(car.getPlateNumber()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(car.getColor()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(car.getVinCode()).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(String.valueOf(car.getIssueDate().getYear())).setFont(regularFont)).setBackgroundColor(cellColor));
				table.addCell(new Cell().add(new Paragraph(car.getCarOwnerId().equals(user.getId()) ? "Власник" : "Водій").setFont(regularFont)).setBackgroundColor(cellColor));

				i++; // Increment the counter
			}
			carsInfo.add(table);
		}

		if(carsInfo.size() == 1){
			carsInfo.add(getInfoNotPresentElement());
		}

		return carsInfo;
	}

	private List<IBlockElement> getJuridicalPersonInfoElements() {
		final List<IBlockElement> jurInfo = new ArrayList<>();
		jurInfo.add(new Paragraph().add(
			new Text("Юридична особа").setFont(boldFont).setFontSize(25)).setMarginTop(35).setTextAlignment(TextAlignment.CENTER)
		);

		if (user.getJuridicalPerson() != null) {
			final JuridicalPerson jurPerson = user.getJuridicalPerson();

			jurInfo.add(new Paragraph().add(new Text("ЕРДПО: ").setFont(boldFont).setFontSize(16))
				.add(jurPerson.getErdpo()).setFont(regularFont).setFontSize(14));

			jurInfo.add(new Paragraph().add(new Text("Вид діяльності: ").setFont(boldFont).setFontSize(16))
				.add(jurPerson.getTypeActivity()).setFont(regularFont).setFontSize(14));

			if(jurPerson.getJurAddress() != null){
				jurInfo.add(new Paragraph().add(new Text("Адреса: ").setFont(boldFont).setFontSize(18)).setMarginTop(15));

				jurInfo.add(getAddressInfoElements(jurPerson.getJurAddress()));
			}
		}

		if(jurInfo.size() == 1){
			jurInfo.add(getInfoNotPresentElement());
		}

		return jurInfo;
	}

	private IBlockElement getAddressInfoElements(final Address address) {
		Paragraph addressInfo = new Paragraph();

		if (address.getCity() != null) {
			addressInfo.add(new Text("Місто: ").setFont(boldFont).setFontSize(16))
				.add(new Text(address.getCity()).setFont(regularFont).setFontSize(14))
				.add(new Text(". "));
		}
		if (address.getStreet() != null) {
			addressInfo.add(new Text("Вулиця: ").setFont(boldFont).setFontSize(16))
				.add(new Text(address.getStreet()).setFont(regularFont).setFontSize(14))
				.add(new Text(". "));
		}
		if (address.getHomeNumber() != null) {
			addressInfo.add(new Text("Номер будинку: ").setFont(boldFont).setFontSize(16))
				.add(new Text(address.getHomeNumber()).setFont(regularFont).setFontSize(14))
				.add(new Text(". "));
		}
		if (address.getFlatNumber() != null) {
			addressInfo.add(new Text("Номер квартири: ").setFont(boldFont).setFontSize(16))
				.add(new Text(address.getFlatNumber()).setFont(regularFont).setFontSize(14))
				.add(new Text(". "));
		}

		if (addressInfo.isEmpty()) {
			return getInfoNotPresentElement();
		}

		return addressInfo;
	}

	private IBlockElement getInfoNotPresentElement(){
		return new Paragraph().add(
				new Text(INFO_NOT_PRESENT_MESSAGE).setFont(boldFont).setFontSize(14)).setMarginTop(10)
			.setTextAlignment(TextAlignment.CENTER).setBackgroundColor(redColor);
	}

	private List<IBlockElement> createUserBasicInfoTable() {
		final List<IBlockElement> basicInfo = new ArrayList<>();

		// Add header cells with grey background
		basicInfo.add(new Paragraph().add(new Text("Ім'я: ").setFont(boldFont).setFontSize(16))
			.add(user.getName()).setFont(regularFont).setFontSize(14));

		basicInfo.add(new Paragraph().add(new Text("По батькові: ").setFont(boldFont).setFontSize(16))
			.add(new Paragraph(user.getMidlName()).setFont(regularFont).setFontSize(14)));

		basicInfo.add(new Paragraph().add(new Text("Прізвище: ").setFont(boldFont).setFontSize(16))
			.add(user.getSurName()).setFont(regularFont).setFontSize(14));

		basicInfo.add(new Paragraph().add(new Text("РНОКПП: ").setFont(boldFont).setFontSize(16))
			.add(new Paragraph(user.getRnokpp()).setFont(regularFont).setFontSize(14)));

		basicInfo.add(new Paragraph().add(new Text("Стать: ").setFont(boldFont).setFontSize(16))
			.add(new Paragraph(user.getSex() == MALE ? "Чоловік" : "Жінка").setFont(regularFont).setFontSize(14)));

		return basicInfo;
	}

	private void setupUser(final User user) {
		if(user != null){
			this.user = user;
		}
	}
}
