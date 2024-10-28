package ua.jarvis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.jarvis.core.model.Participant;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	Optional<Participant> findByTelegramId(Long telegramId);
}
