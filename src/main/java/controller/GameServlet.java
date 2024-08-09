package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entities.Category.Category;
import model.entities.Game.Game;
import repository.impl.CategoryRepository;
import repository.impl.GameRepository;

/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/game")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final GameRepository gameRepository;
	private final CategoryRepository categoryRepository;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        this.gameRepository = new GameRepository();
        this.categoryRepository = new CategoryRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("gameId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()) {
			Collection<Game> games = gameRepository.findAll();
			games.forEach(game -> pw.write(game.toString() + "\n"));
		}else {
			Game game = gameRepository.findById(Long.parseLong(id));
			if(game == null) {
				pw.write("NO CONTENT");
			}else {
				pw.write(game.getId() + "\n");
				pw.write(game.getTitle() + "\n");
				pw.write(game.getPublisher() + "\n");
				pw.write(game.getRelease() + "\n");
				pw.write(game.getSynopsis() + "\n");
				for(Map.Entry<String, String> entry : game.getRequirement().entrySet()) {
					pw.write(entry.getKey() + ":" + entry.getValue() + "\n");
				}
				for(String plat : game.getPlatform()) {
					pw.write(plat + "\n");
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String publisher = request.getParameter("publisher");
		String release = request.getParameter("release");
		String synopsis = request.getParameter("synopsis");
		String categoryId = request.getParameter("categoryId");
		String requirementNotMapped = request.getParameter("requirement");
		String platformNotMapped = request.getParameter("platform");

		PrintWriter pw = response.getWriter();

		if(title.isEmpty() || publisher.isEmpty() || release.isEmpty() || synopsis.isEmpty() || categoryId.isEmpty() || requirementNotMapped.isEmpty() || platformNotMapped.isEmpty()) {
			pw.write("FIELDS CAN'T BE EMPTY");
		}else {
			Game game = new Game();
			game.setTitle(title);
			game.setPublisher(publisher);
			game.setRelease(LocalDateTime.parse(release));
			game.setSynopsis(synopsis);

			Map<String, String> requirement = new HashMap<>();
			String[] lines = requirementNotMapped.split("\n");
			for(String line : lines) {
				String[] tuples = line.split(";");
				requirement.put(tuples[0], tuples[1]);
			}

			game.setRequirement(requirement);

			Collection<String> platform = new ArrayList<>();
			String[] values = platformNotMapped.split(";");
			for(String value : values) {
				platform.add(value);
			}

			game.setPlatform(platform);

			Category category = categoryRepository.findById(Long.parseLong(categoryId));

			if(category != null) {
				game.setCategory(category);
			}

			gameRepository.save(game);
			pw.write("CREATED");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("gameId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()){
			pw.write("ID CANT BE EMPTY");
		}else {
			gameRepository.deleteById(Long.parseLong(id));
		}
	}



}
