package ua.jarvis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.jarvis.model.Participant;
import ua.jarvis.model.enums.ParticipantRole;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	Optional<Participant> findByName(final String name);
}
