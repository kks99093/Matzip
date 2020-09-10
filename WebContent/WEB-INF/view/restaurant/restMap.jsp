<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sectionContainerCenter">
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=86737adefc889cff0763239e8b04a95a"></script>
	<div id="mapContainer" style="width:100%; height:100%;"></div>
	<script>
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.865552, 128.593393), //지도의 중심좌표.
			level: 5 //지도의 레벨(확대, 축소 정도)
		};
		
		const map = new kakao.maps.Map(mapContainer, options); //지도 생성 및 객체 리턴
								//여기 id값 적음
	</script>
</div>