package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.User;

import java.io.IOException;
import java.util.List;

import static ua.jarvis.core.constant.Constants.FileFormatter.COMA_WHITE_SPACE;

@Component
public class ShortDataDOCXFileFormatterServiceImpl implements AbstractDOCXFormatterService {
	private static final String NAME_NOT_PRESENT = "імʼя відсутнє";
	private static final String SUR_NAME_NOT_PRESENT = "прізвище відсутнє";
	private static final String MIDL_NAME_PRESENT = "імʼя відсутнє";
	private static final String RNOKPP_NOT_PRESENT = "імʼя відсутнє";

	@Override
	public List<XWPFParagraph> format(final User user) throws IOException {
		final XWPFDocument document = new XWPFDocument();
		final XWPFParagraph basicInfoParagraph = document.createParagraph();

		final XWPFRun basicInfoRun = createRun(basicInfoParagraph);

		basicInfoRun.addBreak();
		basicInfoRun.setText(user.getName() == null ? NAME_NOT_PRESENT : user.getName() + COMA_WHITE_SPACE);
		basicInfoRun.setText(user.getSurName()  == null ? SUR_NAME_NOT_PRESENT: user.getSurName() + COMA_WHITE_SPACE );
		basicInfoRun.setText(user.getMiddleName() == null ? MIDL_NAME_PRESENT : user.getMiddleName() + COMA_WHITE_SPACE);
		basicInfoRun.setText(user.getRnokpp() == null ? RNOKPP_NOT_PRESENT  : user.getRnokpp());
		basicInfoRun.addBreak();
		basicInfoRun.addBreak();

		document.close();

		return List.of(basicInfoParagraph);
	}
}
