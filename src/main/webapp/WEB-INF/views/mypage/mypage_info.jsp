<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- jstl -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- header -->
<%@include file="../includes/header.jsp"%>

<!-- mypage_info 시작 -->
<div id="mypage_info_content1">
    <div class="content_title">회 원 정 보
        <div class="myInfo">
            <table>
                <tr>
                    <th scope="row">아이디</th>
                    <td><sec:authentication property="principal.user.id"/></td>
                    <th scope="row">이메일</th>
                    <td><sec:authentication property="principal.user.email"/></td>
                </tr>
                <tr>
                    <th scope="row">이름</th>
                    <td><sec:authentication property="principal.user.name"/></td>
                    <th scope="row">휴대폰번호</th>
                    <td><sec:authentication property="principal.user.phoneNo"/></td>
                </tr>
            </table>
        </div>
    </div>
</div>
<!-- mypage_info1 끝 -->

<!-- JS 부트스트랩 적용 -->
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

<!-- footer -->
<%@include file="../includes/footer.jsp"%>