package com.valdir.hp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valdir.hp.dtos.CredenciaisDTO;

/**
 * Quando eu crio uma classe que extende UsernamePasswordAuthenticationFilter
 * automaticamente o spring vai entender que esse filtro vai interceptar a
 * requisição POST no endpoint /login que inclusive é o um endpoint reservado do
 * Spring Security
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/**
	 * AuthenticationManager é a principal interface de estratégia para
	 * autenticação. Se o principal da autenticação de entrada for válido e
	 * verificado, o metodo authenticate retorna uma instância de Authentication com
	 * o sinalizador de autenticado definido como verdadeiro. Do contrário, se o
	 * principal não for válido, ele lançará uma AuthenticationException. Para o
	 * último caso, ele retorna nulo se não puder decidir.
	 */
	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Metodo que irá tentar autenticar
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		/**
		 * Vamos pegar os valores passados na requisição POST para o endpoint /login,
		 * convertê-los em CredenciaisDTO instanciar um objeto do tipo
		 * UsernamePasswordAuthenticationToken passá-lo como parâmetro para o metodo
		 * authenticate que irá tentar realizar a autenticação. O framework fará isso
		 * usando os contratos implementados em UserDetails, UserDetailsService de forma
		 * automática
		 */
		try {
			/*
			 * getInputStream() Recupera o corpo da solicitação como dados binários usando
			 * um ServletInputStream. Este método ou getReader pode ser chamado para ler o
			 * corpo
			 */
			CredenciaisDTO creds = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);

			/*
			 * Criando esse objeto para passar para o metodo autenticate do
			 * authenticationManager verificar se o usuario e senha passados na requisição
			 * são válidos
			 */
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					creds.getEmail(), creds.getSenha(), new ArrayList<>());

			/* Esse é o metodo que verifica se o usuario e senha passados são válidos */
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			return authentication;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Caso a tentativa de autenticação ocorrer com sucesso esse metodo será chamado
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param authResult
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		String username = ((UserSS) authResult.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		response.addHeader("Authorization", "Bearer " + token);
	}

	/**
	 * Esse metodo irá ser chamado caso não ocorra a autenticação
	 * 
	 * @param request
	 * @param response
	 * @param failed
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(401);
		response.setContentType("application/json");
		response.getWriter().append(json());

	}

	/**
	 * Metodo onde escrevemos um json para retornar como erro de credenciais para o
	 * usuario que digitar email e senha incorretos na autenticação. Esse metodo
	 * será usado pelo metodo unsuccessfulAuthentication acima.
	 * 
	 * @return
	 */
	private String json() {
		long date = new Date().getTime();
		return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
				+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
	}
}
