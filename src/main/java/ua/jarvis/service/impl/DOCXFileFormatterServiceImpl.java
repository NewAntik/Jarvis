package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.Address;
import ua.jarvis.core.model.BaseNameEntity;
import ua.jarvis.core.model.BirthCertificate;
import ua.jarvis.core.model.Car;
import ua.jarvis.core.model.DocumentEntity;
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

	private User user;

	@Override
	public List<XWPFParagraph> format(final User user) throws IOException {
		this.user = user;
		final XWPFDocument document = new XWPFDocument();
		final List<XWPFParagraph> docxParagraphs = new ArrayList<>();
		final XWPFParagraph reusableParagraph = document.createParagraph();

		getBasicInfoParagraph(reusableParagraph);
		getPhonesInfoParagraph(reusableParagraph);
		getBirthAddressInfoParagraph(reusableParagraph);
		getAddressesInfoParagraph(reusableParagraph);
		getIndividualEntrepreneurAddressesInfoParagraph(reusableParagraph);
		getBirthCertificateInfoParagraph(reusableParagraph);

		getUserJuridicalPersonsInfoParagraph(reusableParagraph);
		getPassportsInfoParagraph(reusableParagraph);
		getForeignPassportInfoParagraph(reusableParagraph);
		getDriverLicenseInfoParagraph(reusableParagraph);
		getUserCarsInfoParagraph(reusableParagraph);
		getUserEmailsInfoParagraph(reusableParagraph);

		getIllegalActionsInfoParagraph(reusableParagraph);
		getRelationshipInfoParagraph(reusableParagraph);

		docxParagraphs.add(reusableParagraph);

		document.close();

		return docxParagraphs;
	}

	private void getRelationshipInfoParagraph(final XWPFParagraph familyInfo){
		XWPFRun infoRun = createBoldTitleRun("Родинні зв’язки: ", familyInfo);
		infoRun = createTitleRun("Батько(и): ", familyInfo);

		if(!user.getParents().isEmpty()){
	    	infoRun = createRun(familyInfo);
			for(final User parent : user.getParents()){
				infoRun.addBreak();
				setShortUserInfo(infoRun, parent);
			}
		} else {
			setNotPresentMessage(familyInfo.createRun());
		}

		emptyLine(infoRun);
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

		emptyLine(infoRun);
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
	}

	private void getIllegalActionsInfoParagraph(final XWPFParagraph actionsInfo) {
		XWPFRun basicInfoRun = createBoldTitleRun("Причетність до протиправної діяльності: ", actionsInfo);

		if (user.getIllegalActions() != null) {
			basicInfoRun.addBreak();
			basicInfoRun = createRun(actionsInfo);

			basicInfoRun.setText(user.getIllegalActions());
		} else {
			setNotPresentMessage(actionsInfo.createRun());
		}
	}

	private void getUserJuridicalPersonsInfoParagraph(final XWPFParagraph jurInfo) {
		XWPFRun basicInfoRun = createBoldTitleRun("Пов’язані юридичні особи: ", jurInfo);
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
			}
		} else {
			setNotPresentMessage(jurInfo.createRun());
		}
	}

	private void getUserEmailsInfoParagraph(final XWPFParagraph emails) {
		XWPFRun basicInfoRun = createBoldTitleRun("Електронна адреса: ", emails);

		if (!user.getEmails().isEmpty()) {
			basicInfoRun.addBreak();

			for (final Email email : user.getEmails()) {
				basicInfoRun = createRun(emails);
				basicInfoRun.setText(email.getEmailAddress() + DOT_WHITE_SPACE);
				basicInfoRun.addBreak();
			}
		} else {
			setNotPresentMessage(emails.createRun());
		}
	}

	private void getUserCarsInfoParagraph(final XWPFParagraph cars) {
		XWPFRun basicInfoRun = createBoldTitleRun("Машини: ", cars);

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
	}

	private void getDriverLicenseInfoParagraph(final XWPFParagraph driverLicense) {
		XWPFRun basicInfoRun = createBoldTitleRun("Водійське посвідчення: ", driverLicense);
		basicInfoRun.addBreak();

		if (!user.getPassports().isEmpty()) {
			for (final DriverLicense license : user.getDriverLicense()) {
				basicInfoRun = createRun(driverLicense);

				basicInfoRun.setText("Номер: " + license.getLicenseNumber() + DOT_WHITE_SPACE);
				basicInfoRun.setText("Категорія: ");

				for (final DriverLicenseCategory category : license.getCategories()) {
					basicInfoRun.setText(category.getCategoryType() + DOT_WHITE_SPACE);
				}
				addDocumentInfo(basicInfoRun, license);

			}
		} else {
			setNotPresentMessage(driverLicense.createRun());
		}
	}

	private void getForeignPassportInfoParagraph(final XWPFParagraph passports) {
		XWPFRun basicInfoRun = createBoldTitleRun("Закордонний паспорт: ", passports);
		basicInfoRun.addBreak();

		if (!user.getPassports().isEmpty()) {
			for (final ForeignPassport passport : user.getForeignPassports()) {
				basicInfoRun = createRun(passports);

				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				addDocumentInfo(basicInfoRun, passport);

			}
		} else {
			setNotPresentMessage(passports.createRun());
		}
	}

	private void getPassportsInfoParagraph(final XWPFParagraph passports) {
		XWPFRun basicInfoRun = createBoldTitleRun("Паспорт: ", passports);
		basicInfoRun.addBreak();

		if (!user.getPassports().isEmpty()) {
			for (final Passport passport : user.getPassports()) {
				basicInfoRun = createRun(passports);
				basicInfoRun.setText("Номер: " + passport.getPassportNumber() + DOT_WHITE_SPACE);

				addDocumentInfo(basicInfoRun, passport);
			}
		} else {
			setNotPresentMessage(passports.createRun());
		}
	}

	private void getBirthCertificateInfoParagraph(final XWPFParagraph bithInfo) {
		XWPFRun basicInfoRun = createBoldTitleRun("Свідотство про народження: ", bithInfo);
		basicInfoRun.addBreak();

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun = createRun(bithInfo);

			if (certificate.getNumber() != null) {
				basicInfoRun.setText(certificate.getNumber() + DOT_WHITE_SPACE);
			}
			addDocumentInfo(basicInfoRun, certificate);

		} else {
			setNotPresentMessage(bithInfo.createRun());
		}
	}

	private void getIndividualEntrepreneurAddressesInfoParagraph(final XWPFParagraph addresses){
		final XWPFRun basicInfoRun = createTitleRun("ФОП Адреси: ", addresses);

		if (!user.getAddresses().isEmpty()) {
			for (final Address address : user.getAddresses()) {
				addAddressPart(address, basicInfoRun);
			}
		} else {
			setNotPresentMessage(addresses.createRun());
		}
	}

	private void getBirthAddressInfoParagraph(final XWPFParagraph addressInfo) {
		XWPFRun basicInfoRun = createBoldTitleRun("Місце народження: ", addressInfo);
		basicInfoRun.addBreak();
		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();

			if (certificate.getBirthAddress() != null) {
				final Address address = certificate.getBirthAddress();
				basicInfoRun = createRun(addressInfo);

				addAddressPart(address, basicInfoRun);
			}
		} else {
			setNotPresentMessage(addressInfo.createRun());
		}
	}

	private void addAddressPart(final Address address,final XWPFRun run ){
		if (address.getRegion() != null) {
			run.setText("  обл." + address.getRegion() + DOT);
		}
		if (address.getCity() != null) {
			run.setText("  нп." + address.getCity() + DOT);
		}
		if (address.getDistrict() != null) {
			run.setText("  р-н." + address.getDistrict() + DOT);
		}
		if (address.getStreet() != null) {
			run.setText(" вул." + address.getStreet() + DOT);
		}
		if (address.getCorpus() != null) {
			run.setText("  кор." + address.getCorpus() + DOT);
		}
		if (address.getOther() != null) {
			run.setText("  інше." + address.getOther());
			if (address.getOtherNum() != null){
				run.setText(" ." + address.getOtherNum());
			}
			run.setText(DOT);
		}
		if (address.getHomeNumber() != null) {
			run.setText(" буд." + address.getHomeNumber() + DOT);
		}
		if (address.getFlatNumber() != null) {
			run.setText(" кв." + address.getFlatNumber() + DOT);
		}
	}

	private void getAddressesInfoParagraph(final XWPFParagraph addresses) {
		XWPFRun basicInfoRun = createBoldTitleRun("Місце проживання/перебування: ", addresses);

		if (!user.getAddresses().isEmpty()) {
			for (final Address address : user.getAddresses()) {
				basicInfoRun.addBreak();
				basicInfoRun = createRun(addresses);
				addAddressPart(address, basicInfoRun);
			}
		} else {
			setNotPresentMessage(addresses.createRun());
		}
	}

	private void getPhonesInfoParagraph(final XWPFParagraph phones) {
		XWPFRun basicInfoRun = createBoldTitleRun("Телефони: ", phones);
		basicInfoRun.addBreak();

		if (!user.getPhones().isEmpty()) {
			for (final Phone phone : user.getPhones()) {
				basicInfoRun = createRun(phones);
				basicInfoRun.setText("38" + phone.getNumber() + DOT_WHITE_SPACE);
			}
		} else {
			setNotPresentMessage(phones.createRun());
		}
	}

	private void getBasicInfoParagraph(final XWPFParagraph basicInfoParagraph) {
		basicInfoParagraph.setSpacingBetween(1.0);

		XWPFRun basicInfoRun = createRun(basicInfoParagraph);

		if (!user.getSurNames().isEmpty()) {
			user.getSurNames().forEach(n -> n.setValue(n.getValue().toUpperCase(Locale.ROOT)));
			addNames(basicInfoRun, user.getSurNames());
			basicInfoRun.addBreak();
		}

		if (!user.getFirstNames().isEmpty()) {
			addNames(basicInfoRun, user.getFirstNames());
			basicInfoRun.addBreak();
		}
		if (!user.getMiddleNames().isEmpty()) {
			addNames(basicInfoRun, user.getMiddleNames());
			basicInfoRun.addBreak();
		}

		if (user.getBirthCertificate() != null) {
			final BirthCertificate certificate = user.getBirthCertificate();
			basicInfoRun.setText(
				certificate.getDay() + DOT + certificate.getMonth() + DOT + certificate.getYear()
					+ " р.н."
			);
		}

		basicInfoRun = createBoldTitleRun("РНОКПП: ", basicInfoParagraph);
		basicInfoRun.addBreak();

		if (user.getRnokpp() != null) {
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getRnokpp() + DOT);
		} else {
			setNotPresentMessage(basicInfoParagraph.createRun());
		}
	}

	private void setShortUserInfo(final XWPFRun infoRun, final User user) {
		if (!user.getSurNames().isEmpty()) {
			infoRun.addBreak();
			addNames(infoRun, user.getSurNames());
		}
		if (!user.getFirstNames().isEmpty()) {
			infoRun.addBreak();
			addNames(infoRun, user.getFirstNames());
		}
		if (!user.getMiddleNames().isEmpty()) {
			infoRun.addBreak();
			addNames(infoRun, user.getMiddleNames());
		}
		if (user.getRnokpp() != null) {
			infoRun.addBreak();
			infoRun.setText("РНОКПП:" + user.getRnokpp() + COMA_WHITE_SPACE);
		}
		if (!user.getPhones().isEmpty())
			infoRun.addBreak();{
			infoRun.setText("телефон: " + user.getPhones().stream().findFirst().map(Phone::getNumber).orElse("") + COMA_WHITE_SPACE);
		}
		if (user.getBirthCertificate() != null) {
			infoRun.addBreak();
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

	private <T extends DocumentEntity> void addDocumentInfo(final XWPFRun run, final T document){
		if (document.getIssueDate() != null) {
			run.setText("Дата видачі: " +
				document.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
			);
		}
		if (document.getUnlimited().equals(BooleanType.YES)) {
			run.setText("Дійсний до: необмежений" + DOT_WHITE_SPACE);
		} else if (document.getUnlimited().equals(BooleanType.NO)) {
			run.setText("Дійсний до: " +
				document.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
			);
		} else if (document.getValidUntil() != null) {
			run.setText("Дійсний до: " +
				document.getIssueDate().format(DATE_FORMATTER) + DOT_WHITE_SPACE
			);
		}
		if (document.getAuthority() != null) {
			run.addBreak();
			run.setText("Орган видачі:" + document.getAuthority() + DOT_WHITE_SPACE);
		}
	}

	private <T extends BaseNameEntity> void addNames(final XWPFRun infoRun, final Set<T> names) {
		final int n = names.size();
		int i = 1;

		for (final T name : names) {
			if(i == n){
				infoRun.setText(name.getValue() + WHITE_SPACE);
			}else{
				infoRun.setText(name.getValue() + COMA_WHITE_SPACE);
				i++;
			}
		}
	}
}
