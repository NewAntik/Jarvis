package ua.jarvis.service;

import org.springframework.transaction.annotation.Transactional;
import ua.jarvis.core.model.Participant;

public interface ParticipantService {

	@Transactional(readOnly = true)
	Participant findById(final Long id);

	@Transactional(readOnly = true)
	Participant findByName(final String name);
}
