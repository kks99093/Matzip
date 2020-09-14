package com.koreait.matzip.restaurant;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.koreait.matzip.CommonDAO;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.Const;
import com.koreait.matzip.SeacurityUtils;
import com.koreait.matzip.ViewRef;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantController {
	
	private RestaurantService service;
	
	public RestaurantController() {
		service = new RestaurantService();
	}
	
	public String restMap(HttpServletRequest request) {
			request.setAttribute(Const.TITLE, "지도 보기");
			request.setAttribute(Const.VIEW, "restaurant/restMap");
		return ViewRef.TEMP_MENU;
	}
	
	public String restReg(HttpServletRequest request) {

		final int I_M = 1; //카테고리 코드
		request.setAttribute("categoryList", CommonDAO.selCodeList(I_M));		
		request.setAttribute(Const.TITLE, "가게 등록");
		request.setAttribute(Const.VIEW, "restaurant/restReg");
	return ViewRef.TEMP_MENU;
	}
	
	//리스트
	public String restProc(HttpServletRequest request) {
		
		int category = CommonUtils.getIntParameter(request, "cd_category");
		double lat = CommonUtils.getDoubleParameter(request, "lat");
		double lng = CommonUtils.getDoubleParameter(request, "lng");
		RestaurantVO param = new RestaurantVO();
		request.getParameter("cd_category");
		param.setNm(request.getParameter("nm"));
		param.setCd_category(category);
		param.setAddr(request.getParameter("addr"));
		param.setLat(lat);
		param.setLng(lng);
		param.setI_user(SeacurityUtils.getLoginUser(request).getI_user());
		
		int result = service.restProc(param);	
		return "redirect:/restaurant/restMap";
	}
	//리스트 jax
	public String ajaxGetList(HttpServletRequest request) {
		return "ajax:" + service.getRestList();//아작스로 리턴해줌
	}
	
	//디테일
	public String restDetail(HttpServletRequest request) {
		int i_rest = CommonUtils.getIntParameter(request, "i_rest");

		RestaurantVO param = new RestaurantVO();
		param.setI_rest(i_rest);

		request.setAttribute("data", service.restDetail(param));
		request.setAttribute(Const.TITLE, "디테일");
		request.setAttribute(Const.VIEW, "restaurant/restDetail");
		return ViewRef.TEMP_MENU;
	}
	//메뉴 추가
	public String addRecMenusProc(HttpServletRequest request) {
		String uploads = request.getRealPath("/res/img");
		MultipartRequest multi = null;
		String strI_rest = null;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		
		try {
			multi = new MultipartRequest(request, uploads,5*1024*1024,"UTF-8", new DefaultFileRenamePolicy());
			
			strI_rest = multi.getParameter("i_rest");
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(menu_nmArr != null && menu_priceArr != null) {
			for(int i=0; i<menu_nmArr.length; i++) {
				System.out.println(i + ":" + menu_nmArr[i] + ", " + menu_priceArr[i]);
			}	
		}
			
		return "redirect:/restaurant/restDetail?i_rest"+strI_rest;
	}
}
