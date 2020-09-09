package com.koreait.matzip;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
//모든 요청은 여기로 온다
//단, web.xml에서 설정해놓은 /res/만 Container로 간다
public class Container extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HandlerMapper mapper;
	
	public Container() {
		//생성자기 때문에 Container가 생성될때 실행
		//Container의 생성은 톰켓이 알아서 해줌
		mapper = new HandlerMapper();
	}
    
	//최초의 request는 tomcatContainer가 준다
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proc(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		proc(request, response);
	}
	
	private void proc(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//몰아줬을때 장점 - 관리가 편하다
		String temp = mapper.nav(request);
		
		// 슬러쉬(/)가 없다면 무조건 -1을 리턴하기때문에 if문에 들어가지않음
		if(temp.indexOf(":") >= 0) {
			//substring - 인자1개 보낼떄 2개보낼때 차이=> 1개=(시작지점), 2개=(시작지점,종료지점)
			String prefix = temp.substring(0, temp.indexOf(":"));
			String value = temp.substring(temp.indexOf(":")+1);
			if("redirect".equals(prefix)) {		
				response.sendRedirect(value);
				return;
			} else if("ajax".equals(prefix)) {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json");
				PrintWriter out = response.getWriter();
				out.print(value);
				return;
			}
			
		}
		
		switch(temp) {
		case "405":
			temp= "/WEB-INF/view/error.jsp";
			break;
		case "404":
			temp= "/WEB-INF/view/notFound.jsp";
			break;
		}
		
		request.getRequestDispatcher(temp).forward(request, response);
	}

}
