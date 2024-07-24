package ua.jarvis.facade.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.facade.StrategyFacade;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.strategy.ExecutorStrategy;

import java.io.IOException;
import java.util.List;

@Component
public class StrategyFacadeImpl implements StrategyFacade {

	private final List<ExecutorStrategy<?>> strategies;

	public StrategyFacadeImpl(final List<ExecutorStrategy<?>> strategies) {
		this.strategies = strategies;
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		CommandExecutorService executer = null;
		for(ExecutorStrategy<?> strategy : strategies){
			final boolean isExecutor = strategy.isExecutorInstance(text);
			if(isExecutor){
				executer = (CommandExecutorService) strategy.getExecutor();
			}
		}

		if(executer != null){
			executer.execute(text, chatId);
		}else{
			throw new IllegalArgumentException(Constants.UAMessages.COMMAND_NOT_FOUND_MESSAGE);
		}
	}
}
