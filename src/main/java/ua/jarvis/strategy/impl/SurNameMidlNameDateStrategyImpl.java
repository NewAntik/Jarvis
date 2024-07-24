package ua.jarvis.strategy.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;

import java.util.List;

@Component
public class SurNameMidlNameDateStrategyImpl extends AbstractExecutorStrategy {
	protected SurNameMidlNameDateStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isSurNameMidlNameAndDate(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(Constants.ExecutorType.SUR_NAME_UNDERSCORE_MIDL_NAME_DATE);
	}
}
