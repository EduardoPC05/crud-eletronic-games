package model.dto.Rating;

import java.time.LocalDateTime;

import fileSystem.ObjectConvert;

public class RatingDto implements ObjectConvert<RatingDto> {

	private Long id;
	private String description;
	private int stars;
	private LocalDateTime createdAt;
	private Long userId;
	private Long gameId;

	public RatingDto() {
	}

	public RatingDto(Long id, String description, int stars, LocalDateTime createdAt, Long userId, Long gameId) {
		this.id = id;
		this.description = description;
		this.stars = stars;
		this.createdAt = createdAt;
		this.userId = userId;
		this.gameId = gameId;
	}

	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public int getStars() {
		return stars;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getGameId() {
		return gameId;
	}

	@Override
	public RatingDto fromString(String... args) {
		return new RatingDto(Long.parseLong(args[0]), args[1], Integer.parseInt(args[2]), LocalDateTime.parse(args[3]), Long.parseLong(args[4]), Long.parseLong(args[5]));
	}

	@Override
	public String toString() {
		return String.format("%d;%s;%d;%s;%d;%d", id, description, stars, createdAt, userId, gameId);
	}

}
