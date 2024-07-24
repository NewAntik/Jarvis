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
import ua.jarvis.model.OwnFamily;
import ua.jarvis.model.ForeignPassport;
import ua.jarvis.model.JuridicalPerson;
import ua.jarvis.model.ParentalFamily;
import ua.jarvis.model.Passport;
import ua.jarvis.model.Phone;
import ua.jarvis.model.User;
import ua.jarvis.model.enums.FamilyStatus;
import ua.jarvis.service.FileFormatterService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;

@Service
public class DOCXFileFormatterServiceImpl implements FileFormatterService<List<XWPFParagraph>, User> {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final String DOT_WHITE_SPACE = ". ";
	private static final String WHITE_SPACE = " ";
	private static final String DOT = ".";
	private static final String COMA_WHITE_SPACE = ", ";
	private static final int FONT_SIZE = 14;

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

		docxParagraphs.add(getOwnFamilyInfoParagraph());
		document.close();

		return docxParagraphs;
	}

	private void setUserFamilyInfo(final XWPFRun infoRun, final User user) {
		if (user.getSurName() != null) {
			infoRun.setText(user.getSurName() + WHITE_SPACE);
		}
		if (user.getName() != null) {
			infoRun.setText(user.getName() + WHITE_SPACE);
		}
		if (user.getMiddleName() != null) {
			infoRun.setText(user.getMiddleName() + WHITE_SPACE);
		}
		if (user.getRnokpp() != null) {
			infoRun.setText("РНОКПП:" + user.getRnokpp() + COMA_WHITE_SPACE);
		}
		if (!user.getPhones().isEmpty()) {
			infoRun.setText("телефон: " + user.getPhones().stream().findFirst().map(Phone::getNumber).orElse("") + COMA_WHITE_SPACE);
		}
		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			if (certificate.getDay() != null) {
				infoRun.setText(certificate.getDay() + DOT);
			}
			if (certificate.getMonth() != null) {
				infoRun.setText(certificate.getMonth() + DOT);
			}
			if (certificate.getYear() != null) {
				infoRun.setText(certificate.getYear());
			}
			infoRun.setText(" р.н.");
		}
	}

	private void addParentalFamilyInfoParagraph(final XWPFRun infoRun) {
		if (user.getParentalFamily() != null) {
			final ParentalFamily parentalFamily = user.getParentalFamily();
			if (parentalFamily.getFather() != null) {
				infoRun.addBreak();
				final User father = parentalFamily.getFather();
				infoRun.setText("Батько: ");
				setUserFamilyInfo(infoRun, father);
			}
			if (parentalFamily.getMother() != null) {
				infoRun.addBreak();
				infoRun.addBreak();
				final User mother = parentalFamily.getMother();
				infoRun.setText("Мати: ");
				setUserFamilyInfo(infoRun, mother);
			}
			if (parentalFamily.getBrother() != null) {
				infoRun.addBreak();
				infoRun.addBreak();
				final User brother = parentalFamily.getBrother();
				infoRun.setText("Брат: ");
				setUserFamilyInfo(infoRun, brother);
			}
			if (parentalFamily.getSister() != null) {
				infoRun.addBreak();
				infoRun.addBreak();
				final User sister = parentalFamily.getSister();
				infoRun.setText("Сестра: ");
				setUserFamilyInfo(infoRun, sister);
			}
		} else {
			setNotPresentMessage(infoRun);
		}
	}

	private XWPFParagraph getOwnFamilyInfoParagraph() {
		final XWPFParagraph familyInfo = document.createParagraph();
		familyInfo.setSpacingBetween(1.0);
		XWPFRun infoRun = familyInfo.createRun();
		infoRun.setFontFamily("Times New Roman");
		infoRun.setFontSize(FONT_SIZE);
		infoRun.addBreak();
		infoRun.setText("Родинні зв’язки: ");
		infoRun.setBold(true);
		infoRun = familyInfo.createRun();
		infoRun.setFontFamily("Times New Roman");
		infoRun.setFontSize(FONT_SIZE);
		addParentalFamilyInfoParagraph(infoRun);

		if (user.getOwnFamilies() != null) {
			for (OwnFamily ownFamily : user.getOwnFamilies()) {
				infoRun = familyInfo.createRun();
				infoRun.setFontFamily("Times New Roman");
				infoRun.setFontSize(FONT_SIZE);

				if (ownFamily.getFamilyStatus() != null) {
					infoRun.addBreak();
					infoRun.addBreak();
					infoRun.setText("Сімейний статус: ");
					if (ownFamily.getFamilyStatus() == FamilyStatus.DIVORCED) {
						infoRun.setText("розведені.");
					} else if (ownFamily.getFamilyStatus() == FamilyStatus.MARRIED) {
						infoRun.setText("одружені.");
					} else if (ownFamily.getFamilyStatus() == FamilyStatus.WIDOWER) {
						infoRun.setText("вдовець(ва).");
					} else if (ownFamily.getFamilyStatus() == FamilyStatus.UNMARRIED) {
						infoRun.setText("холостий(а).");
					}
				}
				if (ownFamily.getWife() != null) {
					infoRun.addBreak();
					infoRun.addBreak();
					final User wife = ownFamily.getWife();
					infoRun.setText("Дружина: ");
					setUserFamilyInfo(infoRun, wife);
				}
				if (ownFamily.getHusband() != null) {
					infoRun.addBreak();
					infoRun.addBreak();
					final User husband = ownFamily.getHusband();
					infoRun.setText("Чоловік: ");
					setUserFamilyInfo(infoRun, husband);
				}
				if (ownFamily.getChildren() != null) {
					infoRun.addBreak();
					infoRun.addBreak();
					final Set<User> children = ownFamily.getChildren();
					infoRun.setText("Діти: ");
					infoRun.addBreak();
					for (User child : children) {
						setUserFamilyInfo(infoRun, child);
						infoRun.addBreak();
						infoRun.addBreak();
					}
				}
			}
		} else {
			setNotPresentMessage(familyInfo.createRun());
		}

		return familyInfo;
	}

	private XWPFParagraph getIllegalActionsInfoParagraph() {
		final XWPFParagraph actionsInfo = document.createParagraph();
		actionsInfo.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = actionsInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Причетність до протиправної діяльності: ");
		basicInfoRun.setBold(true);

		if (user.getIllegalActions() != null) {
			basicInfoRun.addBreak();
			basicInfoRun = actionsInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setFontSize(FONT_SIZE);

			basicInfoRun.setText(user.getIllegalActions());
		} else {
			setNotPresentMessage(actionsInfo.createRun());
		}

		return actionsInfo;
	}

	private XWPFParagraph getBirthCertificateInfoParagraph() {
		final XWPFParagraph bithInfo = document.createParagraph();
		bithInfo.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = bithInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);
		basicInfoRun.addBreak();
		basicInfoRun.setText("Свідотство про народження: ");
		basicInfoRun.setBold(true);

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun = bithInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setFontSize(FONT_SIZE);

			if (certificate.getNumber() != null) {
				basicInfoRun.setText(certificate.getNumber() + DOT_WHITE_SPACE);
			}
			if (certificate.getIssueDate() != null) {
				basicInfoRun.setText("Дата видачі: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
			}
			if (certificate.isUnlimited()) {
				basicInfoRun.setText("Дійсний до: необмежений" + DOT_WHITE_SPACE);
			} else if (certificate.getValidUntil() != null) {
				basicInfoRun.setText("Дійсний до: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
			}
			if (certificate.getAuthority() != null) {
				basicInfoRun.addBreak();
				basicInfoRun.setText("Орган видачі:" + certificate.getAuthority() + DOT_WHITE_SPACE);
			}
		} else {
			setNotPresentMessage(bithInfo.createRun());
		}

		return bithInfo;
	}

	private XWPFParagraph getBirthAddressInfoParagraph() {
		final XWPFParagraph addressInfo = document.createParagraph();
		addressInfo.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = addressInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);
		basicInfoRun.addBreak();
		basicInfoRun.setText("Місце народження: ");
		basicInfoRun.setBold(true);

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();

			if (certificate.getBirthAddress() != null) {
				final Address address = certificate.getBirthAddress();
				basicInfoRun = addressInfo.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
				if (address.getStreet() != null) {
					basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
				}
				if (address.getHomeNumber() != null) {
					basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
				}
				if (address.getFlatNumber() != null) {
					basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
				}
			}
		} else {
			setNotPresentMessage(addressInfo.createRun());
		}

		return addressInfo;
	}

	private XWPFParagraph getUserJuridicalPersonsInfoParagraph() {
		final XWPFParagraph jurInfo = document.createParagraph();
		jurInfo.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = jurInfo.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);
		basicInfoRun.addBreak();
		basicInfoRun.setText("Пов’язані юридичні особи: ");
		basicInfoRun.setBold(true);
		basicInfoRun.addBreak();

		if (user.getJuridicalPersons() != null) {
			final Set<JuridicalPerson> persons = user.getJuridicalPersons();
			basicInfoRun = jurInfo.createRun();
			basicInfoRun.setFontFamily("Times New Roman");
			basicInfoRun.setFontSize(FONT_SIZE);
			for (JuridicalPerson person : persons) {
				basicInfoRun.setText("ЄРДПО: " + person.getErdpo() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Вид діяльності: " + person.getTypeActivity() + DOT_WHITE_SPACE);
				basicInfoRun.addBreak();

				if (person.getJurAddress() != null) {
					final Address address = person.getJurAddress();
					basicInfoRun = jurInfo.createRun();
					basicInfoRun.setFontFamily("Times New Roman");
					basicInfoRun.setFontSize(FONT_SIZE);
					basicInfoRun.setText("Адреса: ");

					basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
					if (address.getStreet() != null) {
						basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
					}
					if (address.getHomeNumber() != null) {
						basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
					}
					if (address.getFlatNumber() != null) {
						basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
					}
				}
				basicInfoRun.addBreak();
				basicInfoRun.addBreak();
			}
		} else {
			setNotPresentMessage(jurInfo.createRun());
		}

		return jurInfo;
	}

	private XWPFParagraph getUserEmailsInfoParagraph() {
		final XWPFParagraph emails = document.createParagraph();
		emails.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = emails.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Електронна адреса: ");
		basicInfoRun.setBold(true);

		if (!user.getEmails().isEmpty()) {
			for (Email email : user.getEmails()) {
				basicInfoRun.addBreak();
				basicInfoRun = emails.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText(email.getEmailAddress() + DOT_WHITE_SPACE);
			}
		} else {
			setNotPresentMessage(emails.createRun());
		}

		return emails;
	}

	private XWPFParagraph getUserCarsInfoParagraph() {
		final XWPFParagraph cars = document.createParagraph();
		cars.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = cars.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Машини: ");
		basicInfoRun.setBold(true);

		if (!user.getCars().isEmpty()) {
			for (Car car : user.getCars()) {
				basicInfoRun.addBreak();
				basicInfoRun = cars.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("Номерний знак: " + car.getPlateNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Колір: " + car.getColor() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Модель: " + car.getModel() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Тип авто: " + car.getType() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Власник чи водій: ");
				basicInfoRun.setText(car.getCarOwnerId().equals(user.getId()) ? "Власник" : "Водій");
			}
		} else {
			setNotPresentMessage(cars.createRun());
		}

		return cars;
	}

	private XWPFParagraph getDriverLicenseInfoParagraph() {
		final XWPFParagraph driverLicense = document.createParagraph();
		driverLicense.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = driverLicense.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Водійське посвідчення: ");
		basicInfoRun.setBold(true);

		if (!user.getPassports().isEmpty()) {
			for (DriverLicense license : user.getDriverLicense()) {
				basicInfoRun = driverLicense.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("Номер: " + license.getLicenseNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Категорія: ");

				for (DriverLicenseCategory category : license.getCategories()) {
					basicInfoRun.setText(category.getCategoryType() + DOT_WHITE_SPACE);
				}
				if (license.getIssueDate() != null) {
					basicInfoRun.setText("Дата видачі: " + license.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (license.getValidUntil() != null) {
					basicInfoRun.setText("Дійсний до: " + license.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (license.getAuthority() != null) {
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + license.getAuthority() + DOT);
				}
			}
		} else {
			setNotPresentMessage(driverLicense.createRun());
		}

		return driverLicense;
	}

	private XWPFParagraph getForeignPassportInfoParagraph() {
		final XWPFParagraph passports = document.createParagraph();
		passports.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = passports.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Закордонний паспорт: ");
		basicInfoRun.setBold(true);

		if (!user.getPassports().isEmpty()) {
			for (ForeignPassport passport : user.getForeignPassports()) {
				basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				if (passport.getIssueDate() != null) {
					basicInfoRun.setText("Дата видачі: " + passport.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (passport.getValidUntil() != null) {
					basicInfoRun.setText("Дійсний до: " + passport.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (passport.getAuthority() != null) {
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + passport.getAuthority() + DOT);
				}
			}
		} else {
			setNotPresentMessage(passports.createRun());
		}

		return passports;
	}

	private XWPFParagraph getPassportsInfoParagraph() {
		final XWPFParagraph passports = document.createParagraph();
		passports.setSpacingBetween(1.0);
		XWPFRun basicInfoRun = passports.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.setText("Паспорт: ");
		basicInfoRun.setBold(true);

		if (!user.getPassports().isEmpty()) {
			for (Passport passport : user.getPassports()) {
				basicInfoRun = passports.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				if (passport.getIssueDate() != null) {
					basicInfoRun.setText("Дата видачі: " + passport.getIssueDate().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (passport.getValidUntil() != null) {
					basicInfoRun.setText("Дійсний до: " + passport.getValidUntil().format(DATE_FORMATTER) +
						DOT_WHITE_SPACE);
				}
				if (passport.getAuthority() != null) {
					basicInfoRun.addBreak();
					basicInfoRun.setText("Орган видачі: " + passport.getAuthority() + DOT);
				}
			}
		} else {
			setNotPresentMessage(passports.createRun());
		}

		return passports;
	}

	private XWPFParagraph getAddressesInfoParagraph() {
		final XWPFParagraph addresses = document.createParagraph();
		addresses.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = addresses.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Місце проживання/перебування: ");
		basicInfoRun.setBold(true);

		if (!user.getAddresses().isEmpty()) {
			for (Address address : user.getAddresses()) {
				if (address.getCity() != null) {
					basicInfoRun = addresses.createRun();
					basicInfoRun.setFontFamily("Times New Roman");
					basicInfoRun.setText("  м." + address.getCity() + DOT_WHITE_SPACE);
				}
				if (address.getStreet() != null) {
					basicInfoRun.setText("вул." + address.getStreet() + DOT_WHITE_SPACE);
				}
				if (address.getHomeNumber() != null) {
					basicInfoRun.setText("буд." + address.getHomeNumber() + DOT_WHITE_SPACE);
				}
				if (address.getFlatNumber() != null) {
					basicInfoRun.setText("кв." + address.getFlatNumber() + DOT_WHITE_SPACE);
				}
			}
		} else {
			setNotPresentMessage(addresses.createRun());
		}

		return addresses;
	}

	private XWPFParagraph getPhonesInfoParagraph() {
		final XWPFParagraph phones = document.createParagraph();
		phones.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = phones.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.addBreak();
		basicInfoRun.setText("Телефони: ");
		basicInfoRun.setBold(true);

		if (!user.getPhones().isEmpty()) {
			for (Phone phone : user.getPhones()) {
				basicInfoRun = phones.createRun();
				basicInfoRun.setFontFamily("Times New Roman");
				basicInfoRun.setFontSize(FONT_SIZE);

				basicInfoRun.setText("38" + phone.getNumber() + DOT_WHITE_SPACE);
			}
		} else {
			setNotPresentMessage(phones.createRun());
		}

		return phones;
	}

	private XWPFParagraph getBasicInfoParagraph() {
		final XWPFParagraph basicInfoParagraph = document.createParagraph();
		basicInfoParagraph.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = basicInfoParagraph.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		if (user.getSurName() != null) {
			basicInfoRun.setText(user.getSurName().toUpperCase(Locale.ROOT) + WHITE_SPACE);
		}

		if (user.getName() != null) {
			basicInfoRun.setText(user.getName() + WHITE_SPACE);
		}

		if (user.getMiddleName() != null) {
			basicInfoRun.setText(user.getMiddleName() + WHITE_SPACE);
		}

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun.setText(
				certificate.getDay() + DOT + certificate.getMonth() + DOT + certificate.getYear()
					+ " р.н."
			);
		}
		basicInfoRun.addBreak();
		basicInfoRun.addBreak();
		basicInfoRun = basicInfoParagraph.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.setText("РНОКПП: ");
		if (user.getRnokpp() != null) {

			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getRnokpp() + DOT);
		} else {
			setNotPresentMessage(basicInfoParagraph.createRun());
		}

		return basicInfoParagraph;
	}

	private void setNotPresentMessage(final XWPFRun basicInfoRun) {
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
		basicInfoRun.setColor("FF0000");
	}
}
