package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.Participant;
import ua.jarvis.repository.ParticipantRepository;
import ua.jarvis.service.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {
	private static final Logger LOG = LoggerFactory.getLogger(ParticipantServiceImpl.class);

	private final ParticipantRepository participantRepository;

	public ParticipantServiceImpl(final ParticipantRepository participantRepository) {
		this.participantRepository = participantRepository;
	}

	@Override
	public Participant findById(final Long id) {
		final Participant participant = participantRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Can not find participant with id: " + id));
		LOG.debug("In findById: Participant: {} was successfully found.", participant);

		return participant;
	}

	@Override
	public Participant findByTelegramId(final Long id) {
		final Participant participant = participantRepository.findByTelegramId(id)
			.orElseThrow(() -> new IllegalArgumentException("Can not find participant with id: " + id));
		LOG.debug("In findById: Participant: {} was successfully found.", participant);

		return participant;
	}

	@Override
	public Participant saveNew(final Participant participant) {
		participantRepository.findByTelegramId(participant.getTelegramId()).ifPresent(p -> {
			throw new IllegalArgumentException(
				"Cannot save participant with telegram ID: " + participant.getTelegramId() + " as it has already been persisted!"
			);
		});

		return participantRepository.save(participant);
	}

	@Override
	public void deleteByTelegramId(final Long telegramId) {
		final Participant participant = findByTelegramId(telegramId);

		participantRepository.delete(participant);
	}
}
