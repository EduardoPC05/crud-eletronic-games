package model.dto.Game;

import fileSystem.ObjectConvert;

public class PlatformDto implements ObjectConvert<PlatformDto> {

	private String platform;
	private Long gameId;

	public PlatformDto() {}

	public PlatformDto(String platform, Long gameId) {
		this.platform = platform;
		this.gameId = gameId;
	}

	public String getPlatform() {
		return platform;
	}

	public Long getGameId() {
		return gameId;
	}

	@Override
	public PlatformDto fromString(String... args) {
		return new PlatformDto(args[0], Long.parseLong(args[1]));
	}

	@Override
	public String toString() {
		return String.format("%s;%s", platform, gameId);
	}

}
