package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entities.Game.Game;
import model.entities.Rating.Rating;
import model.entities.User.User;
import repository.impl.GameRepository;
import repository.impl.RatingRepository;
import repository.impl.UserRepository;

/**
 * Servlet implementation class RatingServlet
 */
@WebServlet("/rating")
public class RatingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final RatingRepository ratingRepository;
	private final UserRepository userRepository;
	private final GameRepository gameRepository;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RatingServlet() {
        super();
        this.ratingRepository = new RatingRepository();
        this.userRepository = new UserRepository();
        this.gameRepository = new GameRepository();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ratingId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()) {
			Collection<Rating> ratings = ratingRepository.findAll();

			if(ratings.isEmpty()) {
				pw.write("[]");
			}else {
				ratings.forEach( rating -> pw.write(rating.toString()) );
			}

		}else {
			Rating rating = ratingRepository.findById(Long.parseLong(id));
			if(rating == null) {
				pw.write("NO CONTENT");
			}else {
				pw.write(rating.getId() + "\n");
				pw.write(rating.getDescription() + "\n");
				pw.write(rating.getStars() + "\n");
				pw.write(rating.getCreatedAt() + "\n");
				pw.write(rating.getUser().getEmail() + "\n");
				pw.write(rating.getGame().getTitle() + "\n");
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String description = request.getParameter("description");
		String stars = request.getParameter("stars");
		String createdAt = request.getParameter("createdAt");
		String userId = request.getParameter("userId");
		String gameId = request.getParameter("gameId");

		PrintWriter pw = response.getWriter();

		if(description.isEmpty() || stars.isEmpty() || createdAt.isEmpty() || userId.isEmpty() || gameId.isEmpty()) {
			pw.write("FIELDS CANT BE EMPTY");
		}else {
			Rating rating = new Rating();
			rating.setDescription(description);
			rating.setStars(Integer.parseInt(stars));
			rating.setCreatedAt(LocalDateTime.parse(createdAt));

			User user = userRepository.findById(Long.parseLong(userId));
			if(user != null) {
				rating.setUser(user);
			}

			Game game = gameRepository.findById(Long.parseLong(gameId));
			if(game != null) {
				rating.setGame(game);
			}

			ratingRepository.save(rating);
			pw.write("CREATED");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("ratingId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()){
			pw.write("ID CANT BE EMPTY");
		}else {
			ratingRepository.deleteById(Long.parseLong(id));
			pw.write("DELETED");
		}
	}

}
