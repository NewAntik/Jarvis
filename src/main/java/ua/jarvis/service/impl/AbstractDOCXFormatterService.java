package ua.jarvis.service.impl;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import ua.jarvis.service.FileFormatterService;

import static ua.jarvis.core.constant.Constants.FileFormatter.FONT_SIZE;
import static ua.jarvis.core.constant.Constants.FileFormatter.RED_COLOR;
import static ua.jarvis.core.constant.Constants.FileFormatter.TIMES_NEW_ROMAN;
import static ua.jarvis.core.constant.Constants.UAMessages.INFO_NOT_PRESENT_MESSAGE;

public interface AbstractDOCXFormatterService extends FileFormatterService {

	default void setNotPresentMessage(final XWPFRun basicInfoRun) {
		basicInfoRun.setFontFamily(TIMES_NEW_ROMAN);
		basicInfoRun.setFontSize(FONT_SIZE);

		basicInfoRun.setText(INFO_NOT_PRESENT_MESSAGE);
		basicInfoRun.setColor(RED_COLOR);
	}

	default XWPFRun createTitleRun(final String title, final XWPFParagraph paragraph, final boolean isBold){
		paragraph.setSpacingBetween(1.0);
		final XWPFRun run = paragraph.createRun();
		run.setFontFamily(TIMES_NEW_ROMAN);
		run.setFontSize(FONT_SIZE);
		run.addBreak();
		run.setText(title);
		run.setBold(isBold);

		return run;
	}

	default XWPFRun createRun(final XWPFParagraph paragraph){
		paragraph.setSpacingBetween(1.0);
		final XWPFRun run = paragraph.createRun();
		run.setFontFamily(TIMES_NEW_ROMAN);
		run.setFontSize(FONT_SIZE);

		return run;
	}
}
