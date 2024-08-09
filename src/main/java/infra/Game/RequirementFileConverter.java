package infra.Game;

import java.util.ArrayList;
import java.util.Collection;

import fileSystem.FileConvert;
import model.dto.Game.RequirementDto;

public class RequirementFileConverter implements FileConvert<RequirementDto> {

	@Override
	public RequirementDto generate(String... args) {
		return new RequirementDto().fromString(args);
	}

	@Override
	public Collection<RequirementDto> all(Collection<String[]> dataset) {
		Collection<RequirementDto> requirementsDto = new ArrayList<>();
		dataset.forEach( requirement ->  requirementsDto.add(generate(requirement)));
		return requirementsDto;
	}

}
