package ua.jarvis.strategy.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;

import java.util.List;

@Component
public class SurNameAndMidlNameStrategyImpl extends AbstractExecutorStrategy {
	protected SurNameAndMidlNameStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isSurNameAndMidlName(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(ExecutorType.SURNAME_AND_MIDL_NAME);
	}
}
