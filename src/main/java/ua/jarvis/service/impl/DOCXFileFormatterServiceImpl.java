package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import ua.jarvis.model.Address;
import ua.jarvis.model.BirthCertificate;
import ua.jarvis.model.Car;
import ua.jarvis.model.DriverLicense;
import ua.jarvis.model.DriverLicenseCategory;
import ua.jarvis.model.Email;
import ua.jarvis.model.ForeignPassport;
import ua.jarvis.model.JuridicalPerson;
import ua.jarvis.model.Passport;
import ua.jarvis.model.Phone;
import ua.jarvis.model.User;
import ua.jarvis.service.FileFormatterService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;

@Service
public class DOCXFileFormatterServiceImpl implements FileFormatterService<List<XWPFParagraph>, User> {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final String DOT_WHITE_SPACE = ". ";
	private static final String WHITE_SPACE = " ";
	private static final String DOT = ".";

	private XWPFDocument document;

	private User user;

	@Override
	public List<XWPFParagraph> format(final User user) throws IOException {
		this.user = user;
		this.document = new XWPFDocument();
		final List<XWPFParagraph> docxParagraphs = new ArrayList<>();

		docxParagraphs.add(getBasicInfoParagraph());
		docxParagraphs.add(getPhonesInfoParagraph());

		docxParagraphs.add(getBirthAddressInfoParagraph());
		docxParagraphs.add(getAddressesInfoParagraph());
		docxParagraphs.add(getBirthCertificateInfoParagraph());

		docxParagraphs.add(getUserJuridicalPersonsInfoParagraph());
		docxParagraphs.add(getPassportsInfoParagraph());
		docxParagraphs.add(getForeignPassportInfoParagraph());
		docxParagraphs.add(getDriverLicenseInfoParagraph());
		docxParagraphs.add(getUserCarsInfoParagraph());
		docxParagraphs.add(getUserEmailsInfoParagraph());

		docxParagraphs.add(getIllegalActionsInfoParagraph());

		document.close();

		return docxParagraphs;
	}

	private XWPFParagraph getIllegalActionsInfoParagraph() {
		final XWPFParagraph illegalActionsInfo = document.createParagraph();
		illegalActionsInfo.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = illegalActionsInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");

		basicInfoRun.addBreak();
		basicInfoRun.setText("Причетність до протиправної діяльності: ");
		basicInfoRun.setBold(true);

		if(user.getIllegalActions() != null){
			basicInfoRun.addBreak();
			basicInfoRun  = illegalActionsInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");

			basicInfoRun.setText(user.getIllegalActions() + DOT_WHITE_SPACE);

		} else {
			basicInfoRun  = illegalActionsInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return illegalActionsInfo;
	}

	private XWPFParagraph getBirthCertificateInfoParagraph() {
		final XWPFParagraph bithInfo = document.createParagraph();
		bithInfo.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = bithInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Місце проживання/перебування: ");
		basicInfoRun.setBold(true);


		if(user.getBirthCertificate() != null){
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun = bithInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");

			if(certificate.getNumber() != null){
				basicInfoRun.setText(certificate.getNumber() + DOT_WHITE_SPACE);
			}
			if(certificate.getIssueDate() != null){
				basicInfoRun.setText("Дата видачі: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
			}
			if(certificate.isUnlimited()){
				basicInfoRun.setText("Дійсний до: необмежений" + DOT_WHITE_SPACE);
			} else if(certificate.getValidUntil() != null){
				basicInfoRun.setText("Дійсний до: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
			}
			if(certificate.getAuthority() != null){
				basicInfoRun.addBreak();
				basicInfoRun.setText("Орган видачі:" + certificate.getAuthority() + DOT_WHITE_SPACE);
			}
		}else{
			basicInfoRun  = bithInfo.createRun();
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return bithInfo;
	}

	private XWPFParagraph getBirthAddressInfoParagraph() {
		final XWPFParagraph addressInfo = document.createParagraph();
		addressInfo.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = addressInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Місце народження: ");
		basicInfoRun.setBold(true);

		if(user.getBirthCertificate() != null){
			final BirthCertificate certificate = user.getBirthCertificate();

			if(certificate.getBirthAddress() != null){
				final Address address = certificate.getBirthAddress();
				basicInfoRun = addressInfo.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
				if(address.getStreet() != null){
					basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
				}
				if(address.getHomeNumber() != null){
					basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
				}
				if(address.getFlatNumber() != null){
					basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
				}
			}
		} else {
			basicInfoRun  = addressInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return addressInfo;
	}

	private XWPFParagraph getUserJuridicalPersonsInfoParagraph() {
		final XWPFParagraph jurInfo = document.createParagraph();
		jurInfo.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = jurInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Юридична особа: ");
		basicInfoRun.setBold(true);

		if(user.getJuridicalPerson() != null){
			final JuridicalPerson person = user.getJuridicalPerson();
			basicInfoRun = jurInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText("РДПО: " + person.getErdpo() + DOT_WHITE_SPACE);
			basicInfoRun.setText("Вид діяльності: " + person.getTypeActivity() + DOT_WHITE_SPACE);
			basicInfoRun.addBreak();

			if(person.getJurAddress() != null){
				final Address address = person.getJurAddress();
					basicInfoRun = jurInfo.createRun();
					basicInfoRun.setFontFamily("Times New Roman");
					basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
				if(address.getStreet() != null){
					basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
				}
				if(address.getHomeNumber() != null){
					basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
				}
				if(address.getFlatNumber() != null){
					basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
				}
			}
		}else{
			basicInfoRun  = jurInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return jurInfo;
	}

	private XWPFParagraph getUserEmailsInfoParagraph() {
		final XWPFParagraph emails = document.createParagraph();
		emails.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = emails.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Електронна адреса: ");
		basicInfoRun.setBold(true);

		if(!user.getEmails().isEmpty()){
			for(Email email : user.getEmails()){
				basicInfoRun.addBreak();
				basicInfoRun = emails.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText(email.getEmailAddress() + DOT_WHITE_SPACE);
			}
		} else {
			basicInfoRun  = emails.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return emails;
	}

	private XWPFParagraph getUserCarsInfoParagraph() {
		final XWPFParagraph passports = document.createParagraph();
		passports.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = passports.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Машини: ");
		basicInfoRun.setBold(true);

		if(!user.getCars().isEmpty()){
			for(Car car : user.getCars()){
				basicInfoRun.addBreak();
				basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText("Номерний знак: " + car.getPlateNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Колір: " + car.getColor() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Модель: " + car.getModel() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Тип авто: " + car.getType() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Власник чи водій: ");
				basicInfoRun.setText(car.getCarOwnerId().equals(user.getId()) ? "Власник" : "Водій");
			}
		} else {
			basicInfoRun  = passports.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return passports;
	}

	private XWPFParagraph getDriverLicenseInfoParagraph() {
		final XWPFParagraph driverLicense = document.createParagraph();
		driverLicense.setSpacingBetween(1.0);

		if(!user.getPassports().isEmpty()){
			for(DriverLicense license : user.getDriverLicense()){
				XWPFRun basicInfoRun = driverLicense.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.addBreak();
				basicInfoRun.setText("Водійське посвідчення: ");
				basicInfoRun.setBold(true);
				basicInfoRun = driverLicense.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText("Номер: " + license.getLicenseNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Категорія: ");

				for(DriverLicenseCategory category : license.getCategories()){
					basicInfoRun.setText(category.getCategoryType() + DOT_WHITE_SPACE);
				}
				if(license.getIssueDate() != null){
					basicInfoRun.setText("Дата видачі: " + license.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(license.getValidUntil() != null){
					basicInfoRun.setText("Дійсний до: " + license.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(license.getAuthority() != null){
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + license.getAuthority() + DOT);
				}
			}
		} else {
			XWPFRun basicInfoRun = driverLicense.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText("Водійське посвідчення: ");
			basicInfoRun.setColor("000000");
			basicInfoRun.setBold(true);

			basicInfoRun = driverLicense.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);

			basicInfoRun.setColor("FF0000");
		}

		return driverLicense;
	}

	private XWPFParagraph getForeignPassportInfoParagraph() {
		final XWPFParagraph passports = document.createParagraph();
		passports.setSpacingBetween(1.0);

		if(!user.getPassports().isEmpty()){
			for(ForeignPassport passport : user.getForeignPassports()){
				XWPFRun basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.addBreak();
				basicInfoRun.setText("Закордонний паспорт: ");
				basicInfoRun.setBold(true);
				basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				if(passport.getIssueDate() != null){
					basicInfoRun.setText("Дата видачі: " + passport.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(passport.getValidUntil() != null){
					basicInfoRun.setText("Дійсний до: " + passport.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(passport.getAuthority() != null){
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + passport.getAuthority() + DOT);
				}
			}
		} else {
			XWPFRun basicInfoRun = passports.createRun();
			basicInfoRun.setText("Закордонний паспорт: ");
			basicInfoRun.setColor("000000");
			basicInfoRun.setBold(true);

			basicInfoRun = passports.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);

			basicInfoRun.setColor("FF0000");
		}

		return passports;
	}

	private XWPFParagraph getPassportsInfoParagraph() {
		final XWPFParagraph passports = document.createParagraph();
		passports.setSpacingBetween(1.0);

		if(!user.getPassports().isEmpty()){
			for(Passport passport : user.getPassports()){
				XWPFRun basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.addBreak();
				basicInfoRun.setText("Паспорт: ");
				basicInfoRun.setBold(true);
				basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				if(passport.getIssueDate() != null){
					basicInfoRun.setText("Дата видачі: " + passport.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(passport.getValidUntil() != null){
					basicInfoRun.setText("Дійсний до: " + passport.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if(passport.getAuthority() != null){
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + passport.getAuthority() + DOT);
				}
			}
		}else{
			XWPFRun basicInfoRun = passports.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText("Паспорт: ");
			basicInfoRun.setColor("000000");
			basicInfoRun.setBold(true);

			basicInfoRun = passports.createRun();
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);

			basicInfoRun.setColor("FF0000");
		}

		return passports;
	}

	private XWPFParagraph getAddressesInfoParagraph() {
		final XWPFParagraph addresses = document.createParagraph();
		addresses.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = addresses.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Місце проживання/перебування: ");
		basicInfoRun.setBold(true);

		if(!user.getAddresses().isEmpty()){
			for(Address address : user.getAddresses()){
				if(address.getCity() != null){
					basicInfoRun = addresses.createRun();
					basicInfoRun.setFontFamily("Times New Roman");
					basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
				}
				if(address.getStreet() != null){
					basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
				}
				if(address.getHomeNumber() != null){
					basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
				}
				if(address.getFlatNumber() != null){
					basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
				}
			}
		}else{
			basicInfoRun = addresses.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return addresses;
	}

	private XWPFParagraph getPhonesInfoParagraph() {
		final XWPFParagraph phones = document.createParagraph();
		phones.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = phones.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.addBreak();
		basicInfoRun.setText("Телефони: ");
		basicInfoRun.setBold(true);

		if(!user.getPhones().isEmpty()){
			for(Phone phone : user.getPhones()){
				basicInfoRun = phones.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setText(phone.getNumber() + DOT_WHITE_SPACE);

			}
		}else{
			basicInfoRun = phones.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return phones;
	}

	private XWPFParagraph getBasicInfoParagraph(){
		final XWPFParagraph basicInfoParagraph = document.createParagraph();
		basicInfoParagraph.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = basicInfoParagraph.createRun();
		basicInfoRun.setFontFamily("Times New Roman");

		if(user.getSurName() != null){
			basicInfoRun.setText(user.getSurName().toUpperCase(Locale.ROOT) + WHITE_SPACE);
		}

		if(user.getName() != null){
			basicInfoRun.setText(user.getName() + WHITE_SPACE);
		}

		if(user.getMidlName() != null){
			basicInfoRun.setText(user.getMidlName() + WHITE_SPACE);
		}

		if(user.getBirthCertificate() != null){
			basicInfoRun.setText(user.getBirthCertificate().getBirthday().format(DATE_FORMATTER) + WHITE_SPACE);
		}

		if(user.getRnokpp() != null){
			basicInfoRun.addBreak();
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setText("РНОКПП: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getRnokpp() + DOT);
		}

		return basicInfoParagraph;
	}
}
