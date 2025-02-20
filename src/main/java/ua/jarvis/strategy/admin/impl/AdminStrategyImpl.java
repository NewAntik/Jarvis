package ua.jarvis.strategy.admin.impl;

import org.springframework.stereotype.Component;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.service.utils.MessageChecker;
import ua.jarvis.strategy.impl.AbstractExecutorStrategy;

import java.util.List;

@Component
public class AdminStrategyImpl extends AbstractExecutorStrategy {

	protected AdminStrategyImpl(final List<CommandExecutorService> executors) {
		super(executors);
	}

	@Override
	public boolean isExecutorInstance(final String text) {
		return MessageChecker.isAdmin(text);
	}

	@Override
	public CommandExecutorService getExecutor() {
		return executorRegistry.get(ExecutorType.ADMIN);
	}
}
