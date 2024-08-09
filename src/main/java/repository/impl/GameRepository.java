package repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fileSystem.FileInterpreter;
import fileSystem.FileManagement;
import infra.Game.GameFileConverter;
import infra.Game.PlatformFileConverter;
import infra.Game.RequirementFileConverter;
import model.dto.Game.GameDto;
import model.dto.Game.PlatformDto;
import model.dto.Game.RequirementDto;
import model.entities.Category.Category;
import model.entities.Game.Game;
import repository.IRepository;

public class GameRepository implements IRepository<Game, Long> {

	private static Long SEQUENCE = 0L;

	private String gameFileName = "game.csv";
	private String requirementFileName = "requirement.csv";
	private String platformFileName = "platform.csv";

	private final FileManagement fileManagement;
    private final FileInterpreter fileInterpreter;
    private final GameFileConverter gameFileConverter;

    private final CategoryRepository categoryRepository;

    private final RequirementFileConverter requirementFileConverter;
    private final PlatformFileConverter platformFileConverter;

    public GameRepository() {
    	this.fileManagement = new FileManagement();
        this.fileInterpreter = new FileInterpreter();

        this.gameFileConverter = new GameFileConverter();

        this.categoryRepository = new CategoryRepository();

        this.requirementFileConverter = new RequirementFileConverter();
      
        this.platformFileConverter = new PlatformFileConverter();
    }

	@Override
	public void save(Game game) {
		if(game.getId() == null) {
			game.setId(++SEQUENCE);
		}

		deleteById(game.getId());

		GameDto gameDto = new GameDto(game.getId(), game.getTitle(), game.getPublisher(), game.getRelease(), game.getSynopsis(), game.getCategory().getId());

		for(Map.Entry<String, String> entry : game.getRequirement().entrySet()) {
			RequirementDto requirementDto = new RequirementDto(entry.getKey(), entry.getValue(), game.getId());
			fileManagement.write(requirementDto, requirementFileName);
		}

		for(String plat : game.getPlatform()) {
			PlatformDto platformDto = new PlatformDto(plat, game.getId());
			fileManagement.write(platformDto, platformFileName);
		}

		fileManagement.write(gameDto, gameFileName);
	}

	@Override
	public void saveAll(Collection<Game> collection) {
		for(Game game : collection) {
			save(game);
		}

	}

	@Override
	public Game findById(Long identifier) {
		Collection<Game> games = findAll();
        for (Game game : games) {
            if(game.getId().equals(identifier)){
                return game;
            }
        }
        return null;
	}

	@Override
	public Collection<Game> findAll() {
		Collection<GameDto> gamesDto = gameFileConverter.all(fileInterpreter.interpret(fileManagement.read(gameFileName), GameDto.class));

		Collection<RequirementDto> requirementsDto = requirementFileConverter.all(fileInterpreter.interpret(fileManagement.read(requirementFileName), RequirementDto.class));

		Collection<PlatformDto> platformsDto = platformFileConverter.all(fileInterpreter.interpret(fileManagement.read(platformFileName), PlatformDto.class));

		Collection<Game> games = new ArrayList<>();

		gamesDto.forEach( dto -> games.add(this.generate(dto, requirementsDto, platformsDto)) );

		return games;
	}

	@Override
	public void deleteById(Long identifier) {
		Collection<Game> games = findAll();
		Collection<RequirementDto> requirementsDto = requirementFileConverter.all(fileInterpreter.interpret(fileManagement.read(requirementFileName), RequirementDto.class));
		Collection<PlatformDto> platformsDto = platformFileConverter.all(fileInterpreter.interpret(fileManagement.read(platformFileName), PlatformDto.class));

        fileManagement.clear(gameFileName);
        fileManagement.clear(requirementFileName);
        fileManagement.clear(platformFileName);

		platformsDto.removeIf( plat -> plat.getGameId().equals(identifier) );
		requirementsDto.removeIf( req -> req.getGameId().equals(identifier) );
        games.removeIf( game -> game.getId().equals(identifier) );

        games.forEach( game -> fileManagement.write(new GameDto(game.getId(), game.getTitle(), game.getPublisher(), game.getRelease(), game.getSynopsis(), game.getCategory().getId()), gameFileName));
        requirementsDto.forEach(req -> fileManagement.write(req, requirementFileName));
        platformsDto.forEach( plat -> fileManagement.write(plat, platformFileName));
	}

	private Game generate(GameDto gameDto, Collection<RequirementDto> requirementsDto, Collection<PlatformDto> platformsDto) {
		Category category = categoryRepository.findById(gameDto.getCategoryId());

		Map<String, String> requirement = new HashMap<>();
		for(RequirementDto req : requirementsDto) {
			if(req.getGameId().equals(gameDto.getId())) {
				requirement.put(req.getComponent(), req.getDescription());
			}
		}

		Collection<String> platform = new ArrayList<>();
		for(PlatformDto plat : platformsDto) {
			if(plat.getGameId().equals(gameDto.getId())) {
				platform.add(plat.getPlatform());
			}
		}

		return new Game(gameDto.getId(), gameDto.getTitle(), gameDto.getPublisher(), gameDto.getRelease(), gameDto.getSynopsis(), category, requirement, platform);
	}

}
