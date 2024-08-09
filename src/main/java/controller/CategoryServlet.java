package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.entities.Category.Category;
import repository.impl.CategoryRepository;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final CategoryRepository categoryRepository;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryServlet() {
        super();
        this.categoryRepository = new CategoryRepository();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("categoryId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()) {
			Collection<Category> categories = categoryRepository.findAll();
			categories.forEach( category -> pw.write(category.toString() + "\n") );
		}else {
			Category category = categoryRepository.findById(Long.parseLong(id));
			if(category == null) {
				pw.write("NO CONTENT");
			}else {
				pw.write(category.toString());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");

		PrintWriter pw = response.getWriter();

		if(name.isEmpty()) {
			pw.write("FIELDS CANT BE EMPTY");
		}else {
			Category category = new Category();
			category.setName(name);
			categoryRepository.save(category);
			pw.write("CREATED");
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("categoryId");

		PrintWriter pw = response.getWriter();

		if(id == null || id.isEmpty()){
			pw.write("ID CANT BE EMPTY");
		}else {
			categoryRepository.deleteById(Long.parseLong(id));
		}
	}



}
