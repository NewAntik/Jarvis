package ua.jarvis.strategy.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.core.constant.Constants;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;

import java.util.List;

@Component
public class ThreeNamesDateRegionStrategyImpl extends AbstractExecutorStrategy {
	protected ThreeNamesDateRegionStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isThreeNamesDateAndRegion(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(ExecutorType.THREE_NAMES_DATE_REGION.getValue());
	}
}
