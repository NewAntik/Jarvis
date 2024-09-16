package ua.jarvis.core.model.dto;

import jakarta.validation.constraints.NotNull;
import ua.jarvis.core.model.enums.ParticipantRole;

public class RequestDto {

	@NotNull
	private Long chatId;

	@NotNull
	private ParticipantRole role;

	private String messageText;

	public RequestDto() {}

	public RequestDto(final Long chatId, final ParticipantRole role, final String messageText) {
		this.chatId = chatId;
		this.role = role;
		this.messageText = messageText;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setChatId(final Long chatId) {
		this.chatId = chatId;
	}

	public ParticipantRole getRole() {
		return role;
	}

	public void setRole(final ParticipantRole role) {
		this.role = role;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(final String messageText) {
		this.messageText = messageText;
	}
}
