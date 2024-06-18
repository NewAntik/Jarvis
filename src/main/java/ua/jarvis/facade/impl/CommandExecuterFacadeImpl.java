package ua.jarvis.facade.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.facade.CommandExecuterFacade;
import ua.jarvis.service.CommandExecuter;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandExecuterFacadeImpl implements CommandExecuterFacade {

	private final List<CommandExecuter> executers;

	private final Map<String, CommandExecuter> executerRegistry = new HashMap<>();

	public CommandExecuterFacadeImpl(final List<CommandExecuter> executers){
		this.executers = executers;
	}

	@PostConstruct
	protected void populateExecuterRegistry() {
		for (CommandExecuter executer : executers) {
			final CommandExecuter alreadyRegistered = executerRegistry.put(executer.getType(), executer);

			if (alreadyRegistered != null) {
				throw new IllegalArgumentException(
					"Duplicate executer found: {}" + alreadyRegistered.getType()
				);
			}
		}
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		final Optional<CommandExecuter> executerOpt = getExecuterFor(text);
		if(executerOpt.isPresent()){
			final CommandExecuter executer = executerOpt.get();
			executer.execute(text, chatId);
		} else {
			throw new IllegalArgumentException(Constants.UAMessages.COMMAND_NOT_FOUND_MESSAGE);
		}
	}

	private Optional<CommandExecuter> getExecuterFor(final String text){
		if(MessageChecker.isInfo(text)){
			return getFromRegistry(Constants.ExecuterType.INFO);
		} else if(MessageChecker.isRnokpp(text)){
			return getFromRegistry(Constants.ExecuterType.RNOKPP);
		} else if(MessageChecker.isPhoneNumber(text)){
			return getFromRegistry(Constants.ExecuterType.PHONE_NUMBER);
		} else if(MessageChecker.isPassport(text)){
			return getFromRegistry(Constants.ExecuterType.PASSPORT);
		} else if(MessageChecker.isForeignPassport(text)){
			return getFromRegistry(Constants.ExecuterType.FOREIGN_PASSPORT);
		}

		return Optional.empty();
	}

	private Optional<CommandExecuter> getFromRegistry(final String text){
		return Optional.ofNullable(executerRegistry.get(text));
	}
}
