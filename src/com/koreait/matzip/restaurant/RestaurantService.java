package com.koreait.matzip.restaurant;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.koreait.matzip.CommonUtils;
import com.koreait.matzip.FileUtils;
import com.koreait.matzip.vo.RestaurantDomain;
import com.koreait.matzip.vo.RestaurantRecommendMenuVO;
import com.koreait.matzip.vo.RestaurantVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class RestaurantService {
	
	private RestaurantDAO dao;
	
	public RestaurantService() {
		dao = new RestaurantDAO();
	}
	
	public int restProc(RestaurantVO param) {
		return dao.insRestaurant(param);
	}
	
	public String getRestList(){
		List<RestaurantDomain> list = dao.selRestList();
		Gson gson = new Gson();
		return gson.toJson(list);
	}
	//     디테일
	public RestaurantDomain restDetail(RestaurantVO param) {
		return dao.selRest(param);
	}
	// 추천메뉴 인설트
	public int addRecMenus(HttpServletRequest request) {
		String savePath = request.getServletContext().getRealPath("/res/img/restaurant");
		//getRealPath에는 (드라이브(C,E) ~ 프로젝트(Matzip)까지)톰켓이 작업하는공간까지의 주소가 담겨있다
		//그 뒤에 /res/img/restaurant 이경로를 추가로 붙여서 savePath에 저장
		String tempPath = savePath + "/temp";
		//temp에 임시로 저장한다음 원하는곳으로 이동시키면서 이름을 바꿀것
		FileUtils.makeFolder(tempPath);
		
		int maxFileSize = 10_485_760; //1024 * 1024 * 10 (10mb)
		int i_rest = 0;
		String[] menu_nmArr = null;
		String[] menu_priceArr = null;
		MultipartRequest multi = null;		
		List<RestaurantRecommendMenuVO> list = null;
		try {																		//중복된 이름이 있으면 알아서 바꿔서 만들어줌
			multi = new MultipartRequest(request, tempPath, maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			//form에서 enctype="multipart/form-data" 이렇게 보냈기때문에 파라미터를 request가 아니라
			//MultipartRequest로 받아와야 하기때문에 MultipartRequest를 객체화
			//경로값을 가져오려면 i_rest를 알아내야하고 i_rest를 알아내려면 MultipartRequest각 객체화되야하고 
			//MultipartRequest가 객체화 되려면 주소값이 필요하다 
			//그렇기 때문에 i_rest를 가져오기위해 MultipartRequest를 객체화할 경로(이미지를 잠시 넣어놓을곳)를 잠시 tempPath로 지정해놓음
			i_rest = CommonUtils.getIntParameter(multi, "i_rest");
			menu_nmArr = multi.getParameterValues("menu_nm");
			menu_priceArr = multi.getParameterValues("menu_price");
			
			if(menu_nmArr == null || menu_priceArr == null) {
				return i_rest;
			}
			
			list = new ArrayList(); 
			//이때 리스트에 담을것이 생기기에 리스트를 객체화 해줌
			for(int i=0; i<menu_nmArr.length; i++) {
				RestaurantRecommendMenuVO vo = new RestaurantRecommendMenuVO();
				vo.setI_rest(i_rest);
				vo.setMenu_nm(menu_nmArr[i]);
				vo.setMenu_price(CommonUtils.parseStrToInt(menu_priceArr[i]));
				list.add(vo);
			}
			
			String targetPath = savePath + "/" + i_rest;
			FileUtils.makeFolder(targetPath);
			
			String originFileNm = "";
			String saveFileNm = "";
			Enumeration files = multi.getFileNames();
			while(files.hasMoreElements()) {
			// 문제점 - 파일이름이 같으면 한번밖에 안돈다 , 방법 - jsp파일에서 이름뒤에 _0,_1이 붙게 바꿔준다
			//files.hasMoreElements() 엘리먼츠가 있는지 묻는것
			//들어간 엘리먼트가[ 0 -> 1 -> 2]순서면 [ 2-> 1 -> 0]순서로 물어본다
				String key = (String)files.nextElement();
				System.out.println("key : " + key);
				originFileNm = multi.getFilesystemName(key);
				System.out.println("fileNm : " + originFileNm);
				
				if(originFileNm != null) {
					//만약 파일이 없었다면 fileNm은 null이다 (이미지 업로드안함)
					String ext = FileUtils.getExt(originFileNm); //확장자 얻어옴
					saveFileNm = UUID.randomUUID() + ext; 
					//DB에 저장할 파일이름 UUID로 중복되지않는 랜덤한 파일이름을 얻어와서 확장자를 붙여줌 
					
					System.out.println("svaeFileNm: " + saveFileNm);
					//File 객체로 만듬
					File oldFile = new File(tempPath + "/" + originFileNm);	//tempPath에 	originFileNm이름으로 객체만듬
					File newFile = new File(targetPath + "/" + saveFileNm); //targetPath에  saveFileNm이름으로 객체만듬
					//객체생성한다는게 파일을 만든다는게 아님
					
					//객체경로에 객체명을가진 실제 파일이 있다면
					//이름을 바꾸면서 옮겨주는것
					oldFile.renameTo(newFile);
					//oldFile의 폴더(tempPath)에 파일(originFileNm)이 있는지 확인후 
					//newFile의 폴더(targetPath)로 이동시키며 이름(saveFileNm)도 바꾼다
					
					int idx = CommonUtils.parseStrToInt(key.substring(key.lastIndexOf("_") + 1));
					//어디다가 저장할지 _0 / _1 / _2 ....을 확인
					RestaurantRecommendMenuVO vo =list.get(idx);
					vo.setMenu_pic(saveFileNm);
				}
			}	
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for(RestaurantRecommendMenuVO param : list) {
			dao.insRecommendMenu(param);
		}
		
		return i_rest;
	}
	
	//추천메뉴 셀렉트
	public List<RestaurantRecommendMenuVO> getRecommendMenuList(int i_rest) {
		return dao.selRecommendMunuList(i_rest);
	}
	
	public int delRecMenu(RestaurantRecommendMenuVO param) {
		return dao.delRecommendMenu(param);
	}
	
}
