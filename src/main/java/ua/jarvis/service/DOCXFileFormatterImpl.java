package ua.jarvis.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;
import ua.jarvis.model.Address;
import ua.jarvis.model.Phone;
import ua.jarvis.model.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ua.jarvis.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;

@Service
public class DOCXFileFormatterImpl implements FileFormatter <List<XWPFParagraph>, User>{
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private static final String DOT_WHITE_SPACE = ". ";
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
		docxParagraphs.add(getAddressesInfoParagraph());

		document.close();

		return docxParagraphs;
	}

	private XWPFParagraph getAddressesInfoParagraph() {
		final XWPFParagraph addresses = document.createParagraph();
		XWPFRun basicInfoRun = addresses.createRun();
		basicInfoRun.setText("Адреси: ");
		basicInfoRun.setBold(true);

		if(!user.getAddresses().isEmpty()){
			for(Address address : user.getAddresses()){
				if(address.getCity() != null){
					basicInfoRun = addresses.createRun();
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
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return addresses;

	}

	private XWPFParagraph getPhonesInfoParagraph() {
		final XWPFParagraph phones = document.createParagraph();
		XWPFRun basicInfoRun = phones.createRun();
		basicInfoRun.setText("Телефони: ");
		basicInfoRun.setBold(true);

		if(user.getPhones().isEmpty()){
			for(Phone phone : user.getPhones()){
				basicInfoRun = phones.createRun();
				basicInfoRun.setText(phone.getNumber() + DOT_WHITE_SPACE);

			}
		}else{
			basicInfoRun = phones.createRun();
			basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
			basicInfoRun.setColor("FF0000");
		}

		return phones;
	}

	private XWPFParagraph getBasicInfoParagraph(){
		final XWPFParagraph basicInfoParagraph = document.createParagraph();
		XWPFRun basicInfoRun = basicInfoParagraph.createRun();

		if(user.getSurName() != null){
			basicInfoRun.setText("Прізвище: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getSurName() + DOT_WHITE_SPACE);
		}

		if(user.getName() != null){
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText("Імʼя: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getName() + DOT_WHITE_SPACE);
		}

		if(user.getName() != null){
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText("По батькові: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getMidlName() + DOT_WHITE_SPACE);
		}

		if(user.getName() != null){
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText("Дата народження: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getBirthday().format(DATE_FORMATTER) + DOT_WHITE_SPACE);
		}

		if(user.getName() != null){
			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText("РНОКПП: ");
			basicInfoRun.setBold(true);

			basicInfoRun = basicInfoParagraph.createRun();
			basicInfoRun.setText(user.getRnokpp() + DOT);
		}


		return basicInfoParagraph;
	}
}
