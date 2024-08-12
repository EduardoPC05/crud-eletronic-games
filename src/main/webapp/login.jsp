<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="includes/header.jsp" />

<style>
.input::placeholder {
	color: #96969e;
}
</style>


<div style="height: calc(100dvh - 160px)"
	class="container position-relative">

	<div style="width: 400px"
		class="p-3 text-center position-absolute top-50 start-50 translate-middle">
		
		<div>
			<c:if test="${not empty msg}">
				<c:if test="${ msg == 'success'}">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert">
						Usuario cadastrado
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
				</c:if>
				<c:if test="${ msg != 'success' }">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						${msg}
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"></button>
					</div>
				</c:if>
			</c:if>
		</div>
		
		<span class="fw-bold fs-4 text-center">Acessar sua conta</span>
		<form action="${pageContext.request.contextPath}/login" method="POST">
			<div class="mb-3">
				<input
					style="background: #000; border-color: #27272a; color: #fafafa; font-size: 14px;"
					type="email" class="form-control me-2 input" name="email"
					placeholder="name@example.com">
			</div>
			<div class="mb-3">
				<input
					style="background: #000; border-color: #27272a; color: #fafafa; font-size: 14px;"
					type="password" class="form-control me-2 input" name="password"
					placeholder="your password">
			</div>
			<div class="mb-3">
				<button type="submit" class="btn btn-light btn-sm w-100">Entrar</button>
			</div>
			<div class="mb-3">
				Não possui uma conta? <a class="text-light"
					href="${pageContext.request.contextPath}/singup">Cadastre-se</a>
			</div>
		</form>
	</div>
</div>

<c:import url="includes/footer.jsp" />