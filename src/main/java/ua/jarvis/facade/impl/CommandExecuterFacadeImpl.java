package ua.jarvis.facade.impl;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ua.jarvis.facade.CommandExecuterFacade;
import ua.jarvis.service.executor.CommandExecutorService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommandExecuterFacadeImpl implements CommandExecuterFacade {

	private final List<CommandExecutorService> executers;

	private static final Map<String, CommandExecutorService> executerRegistry = new HashMap<>();

	public CommandExecuterFacadeImpl(final List<CommandExecutorService> executers){
		this.executers = executers;
	}

	@PostConstruct
	protected void populateExecuterRegistry() {
		for (CommandExecutorService executer : executers) {
			final CommandExecutorService alreadyRegistered = executerRegistry.put(executer.getType(), executer);

			if (alreadyRegistered != null) {
				throw new IllegalArgumentException(
					"Duplicate executer found: {}" + alreadyRegistered.getType()
				);
			}
		}
	}

	@Override
	public void execute(final String text, final Long chatId) throws IOException {
//		final Optional<CommandExecutorService> executerOpt = ExecuteProvider.getExecuterFor(text);
//		if(executerOpt.isPresent()){
//			final CommandExecutorService executer = executerOpt.get();
//			executer.execute(text, chatId);
//		} else {
//			throw new IllegalArgumentException(Constants.UAMessages.COMMAND_NOT_FOUND_MESSAGE);
//		}
	}

//	private static class ExecuteProvider {
//
//		public static Optional<CommandExecutorService> getExecuterFor(final String text){
//			if(MessageChecker.isInfo(text)){
//				return getFromRegistry(Constants.ExecutorType.INFO);
//			} else if(MessageChecker.isRnokpp(text)){
//				return getFromRegistry(Constants.ExecutorType.RNOKPP);
//			} else if(MessageChecker.isPhoneNumber(text)){
//				return getFromRegistry(Constants.ExecutorType.PHONE_NUMBER);
//			} else if(MessageChecker.isPassport(text)){
//				return getFromRegistry(Constants.ExecutorType.PASSPORT);
//			} else if(MessageChecker.isForeignPassport(text)){
//				return getFromRegistry(Constants.ExecutorType.FOREIGN_PASSPORT);
//			} else if(MessageChecker.isNameSurNameMidlName(text)){
//				return getFromRegistry(Constants.ExecutorType.NAME_SUR_NAME_MIDL_NAME);
//			} else if(MessageChecker.isNameAndSurName(text)){
//				return getFromRegistry(Constants.ExecutorType.SUR_NAME_AND_NAME);
//			} else if(MessageChecker.isSurNameAndMidlName(text)){
//				return getFromRegistry(Constants.ExecutorType.SUR_NAME_AND_MIDL_NAME);
//			} else if(MessageChecker.isNameSurNameMidlNameDate(text)){
//				return getFromRegistry(Constants.ExecutorType.NAME_SUR_NAME_MIDL_NAME_DATE);
//			} else if(MessageChecker.isUnderscoreNameMidlNameAndDate(text)){
//				return getFromRegistry(Constants.ExecutorType.UNDERSCORE_NAME_MIDL_NAME_DATE);
//			} else if(MessageChecker.isSurNameMidlNameAndDate(text)){
//				return getFromRegistry(Constants.ExecutorType.SUR_NAME_UNDERSCORE_MIDL_NAME_DATE);
//			} else if(MessageChecker.isSurNameNameAndDate(text)){
//				return getFromRegistry(Constants.ExecutorType.SUR_NAME_NAME_UNDERSCORE_DATE);
//			} else if(MessageChecker.isThreeNamesDateAndRegion(text)){
//				return getFromRegistry(Constants.ExecutorType.THREE_NAMES_DATE_REGION);
//			} else if(MessageChecker.isSurNameNameDataRegion(text)){
//				return getFromRegistry(Constants.ExecutorType.SUR_NAME_NAME_UNDERSCORE_DATE_REGION);
//			} else if(MessageChecker.isCarPlateNumber(text)){
//				return getFromRegistry(Constants.ExecutorType.CAR_PLATE_NUMBER);
//			}
//
//			return Optional.empty();
//		}
//
//		private static Optional<CommandExecutorService> getFromRegistry(final String text){
//			return Optional.ofNullable(executerRegistry.get(text));
//		}
//	}
}
