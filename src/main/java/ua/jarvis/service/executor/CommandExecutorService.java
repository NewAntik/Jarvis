package ua.jarvis.service.executor;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.core.model.enums.ExecutorType;

import java.io.IOException;

@Service
public interface CommandExecutorService {

	ExecutorType getType();

	void execute(RequestDto dto) throws IOException, InvalidFormatException;

}
