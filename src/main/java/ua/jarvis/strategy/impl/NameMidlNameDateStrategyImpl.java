package ua.jarvis.strategy.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;

import java.util.List;

@Component
public class NameMidlNameDateStrategyImpl extends AbstractExecutorStrategy {
	protected NameMidlNameDateStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isUnderscoreNameMidlNameAndDate(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(Constants.ExecutorType.UNDERSCORE_NAME_MIDL_NAME_DATE);
	}
}
