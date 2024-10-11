package ua.jarvis.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.User;

import java.io.IOException;
import java.util.List;

@Component
public class ShortDataDOCXFileFormatterServiceImpl implements FileFormatterService<XWPFParagraph, List<User>> {
	private static final int FONT_SIZE = 14;
	private static final String COMA_WHITE_SPACE = ", ";

	@Override
	public XWPFParagraph format(final List<User> users) throws IOException {
		final XWPFDocument document = new XWPFDocument();
		final XWPFParagraph basicInfoParagraph = document.createParagraph();
		basicInfoParagraph.setSpacingBetween(1.0);

		final XWPFRun basicInfoRun = basicInfoParagraph.createRun();
		basicInfoRun.setFontFamily("Times New Roman");
		basicInfoRun.setFontSize(FONT_SIZE);
		for(final User user : users){
			basicInfoRun.addBreak();
			basicInfoRun.setText(user.getName() == null ? "імʼя відсутнє" : user.getName() + COMA_WHITE_SPACE);
			basicInfoRun.setText(user.getSurName()  == null ? "прізвище відсутнє" : user.getSurName() + COMA_WHITE_SPACE );
			basicInfoRun.setText(user.getMiddleName() == null ? "по батькові відсутнє" : user.getMiddleName() + COMA_WHITE_SPACE);
			basicInfoRun.setText(user.getRnokpp() == null ? "РНОКПП відсутнє"  : user.getRnokpp());
			basicInfoRun.addBreak();
			basicInfoRun.addBreak();
		}

		document.close();

		return basicInfoParagraph;
	}
}
