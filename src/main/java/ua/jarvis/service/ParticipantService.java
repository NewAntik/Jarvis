package ua.jarvis.service;

import ua.jarvis.core.model.Participant;

public interface ParticipantService {

	Participant findById(Long id);

	Participant findByTelegramId(Long id);

	Participant saveNew(Participant participant);

	void deleteByTelegramId(Long telegramId);
}
