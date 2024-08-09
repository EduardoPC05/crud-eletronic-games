package model.dto.Game;

import fileSystem.ObjectConvert;

public class RequirementDto implements ObjectConvert<RequirementDto> {

	private String component;
	private String description;
	private Long gameId;

	public RequirementDto() {}

	public RequirementDto(String component, String description, Long gameId) {
		this.component = component;
		this.description = description;
		this.gameId = gameId;
	}

	public String getComponent() {
		return component;
	}

	public String getDescription() {
		return description;
	}

	public Long getGameId() {
		return gameId;
	}

	@Override
	public RequirementDto fromString(String... args) {
		return new RequirementDto(args[0], args[1], Long.parseLong(args[2]));
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%d", component, description, gameId);
	}

}
