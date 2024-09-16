package ua.jarvis.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.dto.RequestDto;
import ua.jarvis.facade.StrategyFacade;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.strategy.ExecutorStrategy;

import java.io.IOException;
import java.util.List;

@Component
public class StrategyFacadeImpl implements StrategyFacade {
	private static final Logger LOG = LoggerFactory.getLogger(StrategyFacadeImpl.class);

	private final List<ExecutorStrategy<?>> strategies;

	public StrategyFacadeImpl(final List<ExecutorStrategy<?>> strategies) {
		this.strategies = strategies;
	}

	@Override
	public void execute(final RequestDto dto) throws IOException {
		LOG.info("execute method in StrategyFacadeImpl was called.");
		CommandExecutorService executer = null;

		for(ExecutorStrategy<?> strategy : strategies){
			final boolean isExecutor = strategy.isExecutorInstance(dto.getMessageText());
			if(isExecutor){
				executer = (CommandExecutorService) strategy.getExecutor();
			}
		}

		if(executer != null){
			LOG.info("execute method in {} was called.", executer);
			executer.execute(dto);
		} else {
			throw new IllegalArgumentException(Constants.UAMessages.COMMAND_NOT_FOUND_MESSAGE);
		}
	}
}
