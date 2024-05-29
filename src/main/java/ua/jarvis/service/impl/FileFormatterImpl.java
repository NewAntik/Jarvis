package ua.jarvis.service.impl;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.stereotype.Service;
import ua.jarvis.model.Car;
import ua.jarvis.model.User;
import ua.jarvis.service.FileFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;
import static ua.jarvis.model.enums.Sex.MALE;

@Service
public class FileFormatterImpl implements FileFormatter {
	private static final DeviceRgb greenColor = new DeviceRgb(185, 200, 185); // green color
	private static final DeviceRgb redColor = new DeviceRgb(211, 185, 185); // Light red color
	private static final DeviceRgb greyColor = new DeviceRgb(211, 211, 211); // Light grey color
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
		elements.addAll(getUserCarsInfoElements());

		return elements;
	}

	private List<IBlockElement> createUserBasicInfoTable() {
		final List<IBlockElement> basicInfo = new ArrayList<>();

		// Add header cells with grey background
		basicInfo.add(new Paragraph().add(new Text("Ім'я: ").setFont(boldFont).setFontSize(16)).setBackgroundColor(greenColor)
			.add(user.getName()).setFont(regularFont).setFontSize(14));

		basicInfo.add(new Paragraph().add(new Text("По батькові: ").setFont(boldFont).setFontSize(16)).setBackgroundColor(greenColor)
			.add(new Paragraph(user.getMidlName()).setFont(regularFont).setFontSize(14)));

		basicInfo.add(new Paragraph().add(new Text("Прізвище: ").setFont(boldFont).setFontSize(16)).setBackgroundColor(greenColor)
			.add(user.getSurName()).setFont(regularFont).setFontSize(14));

		basicInfo.add(new Paragraph().add(new Text("РНОКПП: ").setFont(boldFont).setFontSize(16)).setBackgroundColor(greenColor)
			.add(new Paragraph(user.getRnokpp()).setFont(regularFont).setFontSize(14)));

		basicInfo.add(new Paragraph().add(new Text("Стать: ").setFont(boldFont).setFontSize(16)).setBackgroundColor(greenColor)
			.add(new Paragraph(user.getSex() == MALE ? "Чоловік" : "Жінка").setFont(regularFont).setFontSize(14)));

		return basicInfo;
	}

	private List<IBlockElement> getUserCarsInfoElements() {
		final List<IBlockElement> carsInfo = new ArrayList<>();
		carsInfo.add(new Paragraph().add(
			new Text("Засоби пересування").setFont(boldFont).setFontSize(25)).setMarginTop(35).setTextAlignment(TextAlignment.CENTER)
		);

		if(!user.getCars().isEmpty()){
			final Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 1, 1, 1, 1, 1}));
			table.setWidth(UnitValue.createPercentValue(100));

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
				DeviceRgb cellColor = (i % 2 == 0) ? whiteColor : greenColor;

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
			carsInfo.add(new Paragraph().add(
				new Text(INFO_NOT_PRESENT_MESSAGE).setFont(boldFont).setFontSize(14)).setMarginTop(10)
				.setTextAlignment(TextAlignment.CENTER).setBackgroundColor(redColor)
			);
		}

		return carsInfo;
	}

	private void setupUser(final User user) {
		if(user != null){
			this.user = user;
		}
	}
}
