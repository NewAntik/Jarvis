package ua.jarvis.strategy.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;

import java.util.List;

@Component
public class SurNameNameDateRegionStrategyImpl extends AbstractExecutorStrategy {
	protected SurNameNameDateRegionStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isSurNameNameDataRegion(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(Constants.ExecutorType.SUR_NAME_NAME_UNDERSCORE_DATE_REGION);
	}
}
