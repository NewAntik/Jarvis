package ua.jarvis.facade;

import ua.jarvis.core.model.dto.RequestDto;

import java.io.IOException;

public interface StrategyFacade {
	void execute(RequestDto dto) throws IOException;
}
