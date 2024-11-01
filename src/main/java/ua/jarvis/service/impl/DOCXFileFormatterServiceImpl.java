package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.Address;
import ua.jarvis.core.model.BirthCertificate;
import ua.jarvis.core.model.Car;
import ua.jarvis.core.model.DriverLicense;
import ua.jarvis.core.model.DriverLicenseCategory;
import ua.jarvis.core.model.Email;
import ua.jarvis.core.model.ForeignPassport;
import ua.jarvis.core.model.JuridicalPerson;
import ua.jarvis.core.model.Passport;
import ua.jarvis.core.model.Phone;
import ua.jarvis.core.model.User;
import ua.jarvis.core.model.enums.BooleanType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static ua.jarvis.core.constant.Constants.FileFormatter.COMA_WHITE_SPACE;
import static ua.jarvis.core.constant.Constants.FileFormatter.DATE_FORMATTER;
import static ua.jarvis.core.constant.Constants.FileFormatter.DOT;
import static ua.jarvis.core.constant.Constants.FileFormatter.DOT_WHITE_SPACE;
import static ua.jarvis.core.constant.Constants.FileFormatter.WHITE_SPACE;

@Service
public class DOCXFileFormatterServiceImpl implements AbstractDOCXFormatterService {

	private XWPFDocument document;

	private User user;
// TODO Rewrite all methods to use the same XWPFParagraph no not create it for each get method.

	@Override
	public List<XWPFParagraph> format(final User user) throws IOException {
		this.user = user;
		this.document = new XWPFDocument();
		final List<XWPFParagraph> docxParagraphs = new ArrayList<>();

		docxParagraphs.add(getBasicInfoParagraph());
		docxParagraphs.add(getPhonesInfoParagraph());

		docxParagraphs.add(getBirthAddressInfoParagraph());
		docxParagraphs.add(getAddressesInfoParagraph());
		docxParagraphs.add(getIndividualEntrepreneurAddressesInfoParagraph());
		docxParagraphs.add(getBirthCertificateInfoParagraph());

		docxParagraphs.add(getUserJuridicalPersonsInfoParagraph());
		docxParagraphs.add(getPassportsInfoParagraph());
		docxParagraphs.add(getForeignPassportInfoParagraph());
		docxParagraphs.add(getDriverLicenseInfoParagraph());
		docxParagraphs.add(getUserCarsInfoParagraph());
		docxParagraphs.add(getUserEmailsInfoParagraph());

		docxParagraphs.add(getIllegalActionsInfoParagraph());
		docxParagraphs.add(getRelationshipInfoParagraph());

		document.close();

		return docxParagraphs;
	}

	private void setShortUserInfo(final XWPFRun infoRun, final User user) {
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

	private XWPFParagraph getRelationshipInfoParagraph(){
		final XWPFParagraph familyInfo = document.createParagraph();
		XWPFRun infoRun = createTitleRun("Родинні зв’язки: ", familyInfo, true);
		infoRun = createTitleRun("Батько(и): ", familyInfo, false);
		infoRun.addBreak();

		if(!user.getParents().isEmpty()){
	    	infoRun = createRun(familyInfo);
			for(final User parent : user.getParents()){
				infoRun.addBreak();
				setShortUserInfo(infoRun, parent);
			}
		} else {
			setNotPresentMessage(familyInfo.createRun());
		}

		infoRun.addBreak();
		infoRun.addBreak();
		infoRun.setText("Брат/Сестра: ");
		if(!user.getSiblings().isEmpty()){
			infoRun = createRun(familyInfo);

			for(final User sibling : user.getSiblings()){
				infoRun.addBreak();
				setShortUserInfo(infoRun, sibling);
			}
		} else {
			setNotPresentMessage(familyInfo.createRun());
		}

		infoRun.addBreak();
		infoRun.addBreak();
		infoRun.setText("Діти: ");
		if(!user.getChildren().isEmpty()){
			infoRun = createRun(familyInfo);

			for(final User child : user.getChildren()){
				infoRun.addBreak();
				setShortUserInfo(infoRun, child);
			}
		} else {
			setNotPresentMessage(familyInfo.createRun());
		}

		return familyInfo;
	}

	private XWPFParagraph getIllegalActionsInfoParagraph() {
		final XWPFParagraph actionsInfo = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Причетність до протиправної діяльності: ", actionsInfo, true);

		if (user.getIllegalActions() != null) {
			basicInfoRun.addBreak();
			basicInfoRun = createRun(actionsInfo);

			basicInfoRun.setText(user.getIllegalActions());
		} else {
			setNotPresentMessage(actionsInfo.createRun());
		}

		return actionsInfo;
	}

	private XWPFParagraph getBirthCertificateInfoParagraph() {
		final XWPFParagraph bithInfo = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Свідотство про народження: ", bithInfo, true);

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun = createRun(bithInfo);

			if (certificate.getNumber() != null) {
				basicInfoRun.setText(certificate.getNumber() + DOT_WHITE_SPACE);
			}
			if (certificate.getIssueDate() != null) {
				basicInfoRun.setText("Дата видачі: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
			}
			if (certificate.getUnlimited().equals(BooleanType.YES)) {
				basicInfoRun.setText("Дійсний до: необмежений" + DOT_WHITE_SPACE);
			} else if (certificate.getUnlimited().equals(BooleanType.NO)) {
				basicInfoRun.setText("Дійсний до: " +
					certificate.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
				);
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
		XWPFRun basicInfoRun = createTitleRun("Місце народження: ", addressInfo, true);

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();

			if (certificate.getBirthAddress() != null) {
				final Address address = certificate.getBirthAddress();
				basicInfoRun = createRun(addressInfo);

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
		XWPFRun basicInfoRun = createTitleRun("Пов’язані юридичні особи: ", jurInfo, true);
		basicInfoRun.addBreak();

		if (user.getJuridicalPersons() != null) {
			final Set<JuridicalPerson> persons = user.getJuridicalPersons();
			basicInfoRun = createRun(jurInfo);

			for (final JuridicalPerson person : persons) {
				basicInfoRun.setText("ЄРДПО: " + person.getErdpo() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Вид діяльності: " + person.getTypeActivity() + DOT_WHITE_SPACE);
				basicInfoRun.addBreak();

				if(!person.getJurAddresses().isEmpty()){
					basicInfoRun = createRun(jurInfo);
					basicInfoRun.setText("Адреси: ");

					for(final Address address : person.getJurAddresses()){
						basicInfoRun.addBreak();
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
				}
				basicInfoRun.addBreak();
			}
		} else {
			setNotPresentMessage(jurInfo.createRun());
		}

		return jurInfo;
	}

	private XWPFParagraph getUserEmailsInfoParagraph() {
		final XWPFParagraph emails = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Електронна адреса: ", emails, true);

		if (!user.getEmails().isEmpty()) {
			for (final Email email : user.getEmails()) {
				basicInfoRun.addBreak();
				basicInfoRun = createRun(emails);
				basicInfoRun.setText(email.getEmailAddress() + DOT_WHITE_SPACE);
			}
		} else {
			setNotPresentMessage(emails.createRun());
		}

		return emails;
	}

	private XWPFParagraph getUserCarsInfoParagraph() {
		final XWPFParagraph cars = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Машини: ", cars, true);

		if (!user.getCars().isEmpty()) {
			for (final Car car : user.getCars()) {
				basicInfoRun.addBreak();
				basicInfoRun = createRun(cars);

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
		XWPFRun basicInfoRun = createTitleRun("Водійське посвідчення: ", driverLicense, true);

		if (!user.getPassports().isEmpty()) {
			for (final DriverLicense license : user.getDriverLicense()) {
				basicInfoRun = createRun(driverLicense);

				basicInfoRun.setText("Номер: " + license.getLicenseNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Категорія: ");

				for (final DriverLicenseCategory category : license.getCategories()) {
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
		XWPFRun basicInfoRun = createTitleRun("Закордонний паспорт: ", passports, true);

		if (!user.getPassports().isEmpty()) {
			for (final ForeignPassport passport : user.getForeignPassports()) {
				basicInfoRun = createRun(passports);

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
		XWPFRun basicInfoRun = createTitleRun("Паспорт: ", passports, true);

		if (!user.getPassports().isEmpty()) {
			for (final Passport passport : user.getPassports()) {
				basicInfoRun = createRun(passports);
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

	private XWPFParagraph getIndividualEntrepreneurAddressesInfoParagraph(){
		final XWPFParagraph addresses = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("ФОП Адреси: ", addresses, true);

		if (!user.getAddresses().isEmpty()) {
			for (final Address address : user.getAddresses()) {
				if (address.getCity() != null) {
					basicInfoRun = createRun(addresses);
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

	private XWPFParagraph getAddressesInfoParagraph() {
		final XWPFParagraph addresses = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Місце проживання/перебування: ", addresses, true);

		if (!user.getAddresses().isEmpty()) {
			for (final Address address : user.getAddresses()) {
				basicInfoRun.addBreak();
				basicInfoRun = createRun(addresses);

				if (address.getRegion() != null) {
					basicInfoRun.setText("  обл." + address.getRegion() + DOT);
				}
				if (address.getCity() != null) {
					basicInfoRun.setText("  нп." + address.getCity() + DOT);
				}
				if (address.getDistrict() != null) {
					basicInfoRun.setText("  р-н." + address.getDistrict() + DOT);
				}
				if (address.getStreet() != null) {
					basicInfoRun.setText(" вул." + address.getStreet() + DOT);
				}
				if (address.getCorpus() != null) {
					basicInfoRun.setText("  кор." + address.getCorpus() + DOT);
				}
				if (address.getOther() != null) {
					basicInfoRun.setText("  кор." + address.getOther());
					if (address.getOtherNum() != null){
						basicInfoRun.setText(" ." + address.getOtherNum());
					}
					basicInfoRun.setText(DOT);
				}
				if (address.getHomeNumber() != null) {
					basicInfoRun.setText(" буд." + address.getHomeNumber() + DOT);
				}
				if (address.getFlatNumber() != null) {
					basicInfoRun.setText(" кв." + address.getFlatNumber() + DOT);
				}
			}
		} else {
			setNotPresentMessage(addresses.createRun());
		}

		return addresses;
	}

	private XWPFParagraph getPhonesInfoParagraph() {
		final XWPFParagraph phones = document.createParagraph();
		XWPFRun basicInfoRun = createTitleRun("Телефони: ", phones, true);

		if (!user.getPhones().isEmpty()) {
			for (final Phone phone : user.getPhones()) {
				basicInfoRun = createRun(phones);
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

		XWPFRun basicInfoRun = createRun(basicInfoParagraph);

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
		basicInfoRun = createTitleRun("РНОКПП: ", basicInfoParagraph, true);

		if (user.getRnokpp() != null) {

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getRnokpp() + DOT);
		} else {
			setNotPresentMessage(basicInfoParagraph.createRun());
		}

		return basicInfoParagraph;
	}
}
