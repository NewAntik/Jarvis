package ua.jarvis.facade.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.jarvis.constant.Constants;
import ua.jarvis.facade.CommandExecuterFacade;
import ua.jarvis.service.executer.CommandExecuterService;
import ua.jarvis.service.utils.MessageChecker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandExecuterFacadeImpl implements CommandExecuterFacade {

	private final List<CommandExecuterService> executers;

	private static final Map<String, CommandExecuterService> executerRegistry = new HashMap<>();

	public CommandExecuterFacadeImpl(final List<CommandExecuterService> executers){
		this.executers = executers;
	}

	@PostConstruct
	protected void populateExecuterRegistry() {
		for (CommandExecuterService executer : executers) {
			final CommandExecuterService alreadyRegistered = executerRegistry.put(executer.getType(), executer);

			if (alreadyRegistered != null) {
				throw new IllegalArgumentException(
					"Duplicate executer found: {}" + alreadyRegistered.getType()
				);
			}
		}
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
		final Optional<CommandExecuterService> executerOpt = ExecuteProvider.getExecuterFor(text);
		if(executerOpt.isPresent()){
			final CommandExecuterService executer = executerOpt.get();
			executer.execute(text, chatId);
		} else {
			throw new IllegalArgumentException(Constants.UAMessages.COMMAND_NOT_FOUND_MESSAGE);
		}
	}

	private static class ExecuteProvider {

		public static Optional<CommandExecuterService> getExecuterFor(final String text){
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
			} else if(MessageChecker.isNameSurNameMidlName(text)){
				return getFromRegistry(Constants.ExecuterType.NAME_SUR_NAME_MIDL_NAME);
			} else if(MessageChecker.isNameAndSurName(text)){
				return getFromRegistry(Constants.ExecuterType.SUR_NAME_NAME);
			} else if(MessageChecker.isSurNameAndMidlName(text)){
				return getFromRegistry(Constants.ExecuterType.SUR_NAME_MIDL_NAME);
			} else if(MessageChecker.isNameSurNameMidlNameDate(text)){
				return getFromRegistry(Constants.ExecuterType.NAME_SUR_NAME_MIDL_NAME_DATE);
			}

			return Optional.empty();
		}

		private static Optional<CommandExecuterService> getFromRegistry(final String text){
			return Optional.ofNullable(executerRegistry.get(text));
		}
	}
}
