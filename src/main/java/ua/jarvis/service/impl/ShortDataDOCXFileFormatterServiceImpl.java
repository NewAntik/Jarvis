package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.BaseNameEntity;
import ua.jarvis.core.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
		basicInfoRun.setText(user.getSurNames()  == null ? SUR_NAME_NOT_PRESENT: getNameLine(user.getSurNames()));
		basicInfoRun.addBreak();
		basicInfoRun.setText(user.getFirstNames() == null ? NAME_NOT_PRESENT : getNameLine(user.getFirstNames()));
		basicInfoRun.addBreak();
		basicInfoRun.setText(user.getMiddleNames() == null ? MIDL_NAME_PRESENT : getNameLine(user.getMiddleNames()));
		basicInfoRun.addBreak();
		basicInfoRun.setText(user.getRnokpp() == null ? RNOKPP_NOT_PRESENT  : user.getRnokpp());
		basicInfoRun.addBreak();
		basicInfoRun.addBreak();

		document.close();

		return List.of(basicInfoParagraph);
	}

	private <T extends BaseNameEntity> String getNameLine(final Set<T> names){
		final StringBuilder builder = new StringBuilder();
		names.stream().forEach(n -> builder.append(n.getValue()).append(" "));

		return builder.toString();
	}
}
