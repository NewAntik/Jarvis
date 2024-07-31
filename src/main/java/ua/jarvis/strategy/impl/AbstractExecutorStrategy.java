package ua.jarvis.strategy.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.jarvis.core.model.enums.ExecutorType;
import ua.jarvis.service.executor.CommandExecutorService;
import ua.jarvis.strategy.ExecutorStrategy;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class AbstractExecutorStrategy implements ExecutorStrategy<CommandExecutorService> {

	private final List<CommandExecutorService> executors;

	protected final EnumMap<ExecutorType, CommandExecutorService> executorRegistry = new EnumMap<>(ExecutorType.class);

	protected AbstractExecutorStrategy(List<CommandExecutorService> executors) {
		this.executors = executors;
	}

	@PostConstruct
	protected void populateExecutorRegistry() {
		for (CommandExecutorService executor : executors) {
			final CommandExecutorService alreadyRegistered = executorRegistry.put(executor.getType(), executor);

			if (alreadyRegistered != null) {
				throw new IllegalArgumentException(
					"Duplicate executor found: {}" + alreadyRegistered.getType()
				);
			}
		}
	}
}
