package ua.jarvis.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.jarvis.model.Participant;
import ua.jarvis.model.enums.ParticipantRole;
import ua.jarvis.repository.ParticipantRepository;
import ua.jarvis.service.ParticipantService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

	@Mock
	private ParticipantRepository participantRepository;

	@InjectMocks
	private ParticipantServiceImpl participantServiceimpl;

	private Participant participant;

	@BeforeEach
	public void setUp() {
		participant = new Participant(1L, "@user_name", ParticipantRole.STAFF);
	}

	@Test
	void findById_ShouldThrowIllegalArgumentException() {
		when(participantRepository.findById(participant.getId())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> participantServiceimpl.findById(participant.getId()));

		verify(participantRepository).findById(participant.getId());
		verifyNoMoreInteractions(participantRepository);
	}
	@Test
	void findById_ShouldReturnParticipant() {
		when(participantRepository.findById(participant.getId())).thenReturn(Optional.of(participant));

		final Participant foundParticipant = participantServiceimpl.findById(participant.getId());

		assertEquals(participant, foundParticipant);
		verify(participantRepository).findById(participant.getId());
		verifyNoMoreInteractions(participantRepository);
	}

	@Test
	void findByName_ShouldThrowIllegalArgumentException() {
		when(participantRepository.findByName(participant.getName())).thenReturn(Optional.empty());

		assertThrows(IllegalArgumentException.class, () -> participantServiceimpl.findByName(participant.getName()));

		verify(participantRepository).findByName(participant.getName());
		verifyNoMoreInteractions(participantRepository);
	}
}