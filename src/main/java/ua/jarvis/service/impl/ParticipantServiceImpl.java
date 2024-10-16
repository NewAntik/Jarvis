package ua.jarvis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.jarvis.core.model.Participant;
import ua.jarvis.repository.ParticipantRepository;
import ua.jarvis.service.ParticipantService;

import static ua.jarvis.core.constant.Constants.UAMessages.HAVE_NO_ACCESS;

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
			.orElseThrow(() -> new IllegalArgumentException("Cannot find participant with id: " + id));
		LOG.debug("In findById: Participant: {} was successfully found.", participant);

		return participant;
	}

	@Override
	public Participant findByName(final String name) {
		return participantRepository.findByName(name)
			.orElseThrow(() -> new IllegalArgumentException(HAVE_NO_ACCESS));
	}
}
