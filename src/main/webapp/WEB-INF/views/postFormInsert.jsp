<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/postForm.css">
</head>
<body>

<form action="/board/insert" method="POST" id="insertForm" enctype="multipart/form-data" class="container">
    <hr class="line">
    <div class="category-container">
        <div class="subject-container">
            <span class="subject-item">카테고리*</span>
        </div>
        <div class="input-container">
            <select name="categoryId" id="category" class="category">
                <option value="0" selected>카테고리 선택</option>
                <c:forEach var="category" items="${categories}">
                    <option value='<c:out value="${category.id}" />'><c:out value='${category.name}' /></option>
                </c:forEach>
            </select>
        </div>
    </div>
    <hr class="line">

    <div class="writer-container">
        <div class="subject-container">
            <span class="subject-item">작성자*</span>
        </div>
        <div class="input-container">
            <input type="text" id="writer" name="writer" class="writer">
        </div>
    </div>
    <hr class="line">

    <div class="password-container">
        <div class="subject-container">
            <span class="subject-item">비밀번호*</span>
        </div>
        <div class="password-input-container">
            <input type="password" name="password" id="password" class="password"
                   placeholder="비밀번호">
            <input type="password" name="passwordConfirm" id="passwordConfirm" class="password"
                   placeholder="비밀번호 확인">
        </div>
    </div>
    <hr class="line">

    <div class="title-container">
        <div class="subject-container">
            <span class="subject-item">제목*</span>
        </div>
        <div class="input-container">
            <input type="text" name="title" id="title" class="title">
        </div>
    </div>
    <hr class="line">

    <div class="content-container">
        <div class="subject-container">
            <span class="subject-item">내용*</span>
        </div>
        <div class="input-container">
            <textarea name="content" id="content" class="content"></textarea>
        </div>
    </div>
    <hr class="line">

    <div class="files-container">
        <div class="subject-container">
            <span class="subject-item">파일 첨부</span>
        </div>
        <div class="file-upload-container" id="fileUploadContainer">
            <div id="fileNameList" class="file-item file-list">

            </div>
            <div class="file-item">
                <input type="file" name="files" class="file-name" onchange="showChooseFiles(this)">
            </div>
        </div>
    </div>
    <hr class="line">

    <span id="warnAlert" class="warning-alert"></span>

    <div class="button-container">
        <button type="button" onclick="cancelForm()">취소</button>
        <button type="button" onclick="submitForm()">저장</button>
    </div>
</form>

<script>

    // 선택한 첨부파일 명 목록 반영 (선택한 파일 this = input)
    function showChooseFiles(input) {

        // 선택된 파일들을 표시할 컨테이너
        const fileNamesContainer = document.getElementById('fileNameList');

        if (input.files.length > 0) {
            // div 생성, div 안에 span 과 button 생성
            const chooseFileDiv = document.createElement('div');
            chooseFileDiv.classList.add('choose-file');

            const fileNameSpan = document.createElement('span');
            fileNameSpan.textContent = input.files[0].name;

            const removeButton = document.createElement('button');
            removeButton.textContent = 'x';
            // x 버튼 클릭 → 선택한 <input type="file"> 을 disable /
            removeButton.onclick = function() {
                fileNamesContainer.removeChild(chooseFileDiv);
                input.disabled = true;
            };

            chooseFileDiv.appendChild(fileNameSpan);
            chooseFileDiv.appendChild(removeButton);
            fileNamesContainer.appendChild(chooseFileDiv);

            // 현재 <input type="file"> 의 부모 div 를 숨김
            input.closest('.file-item').style.display = 'none';

            // 새로운 <input type="file"> 을 위한 div 생성 및 삽입
            const newFileItemDiv = document.createElement('div');
            newFileItemDiv.classList.add('file-item');

            const newFileInput = document.createElement('input');
            newFileInput.type = 'file';
            newFileInput.name = 'files';
            newFileInput.classList.add('file-name');
            newFileInput.onchange = function() {
                showChooseFiles(newFileInput);
            };

            newFileItemDiv.appendChild(newFileInput);
            fileNamesContainer.parentElement.appendChild(newFileItemDiv);
        }
    }

    // 취소 - 게시물 목록 으로 이동
    function cancelForm() {
        location.href = "/board/posts";
    }

    // 저장 - 폼 submit
    function submitForm() {
        let flag = false;
        const fileNamesContainer = document.getElementById('warnAlert');

        // 1. 카테고리 필수 선택
        const alertBox = document.getElementById('warnAlert');
        if (document.getElementById('category').value == "0") {
            alertBox.textContent = "카테고리를 선택해주세요.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 2. 작성자 필수 입력 (3~5자)
        const writer = document.getElementById('writer');
        const writerValue = document.getElementById('writer').value.trim();
        if (writerValue.length < 3 || writerValue.length > 5 || validateWriter(writerValue)) {
            alertBox.textContent = "작성자는 3글자 이상 5글자 이하의 영문자, 숫자, 한글로 입력해주세요. (공백문자 불가)";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 3. 비밀번호 필수 입력
        const password = document.getElementById('password');
        const passwordValue = document.getElementById('password').value.trim();
        if (passwordValue.length < 4 || passwordValue.length > 16 || validatePassword(passwordValue)) {
            alertBox.textContent = "비밀번호는 4글자 이상 16글자 이하의 영문자, 숫자, 한글로 입력해주세요. (공백문자 불가)";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 4. 비밀번호 재확인
        const passwordConfirm = document.getElementById('passwordConfirm');
        const confirmValue = document.getElementById('passwordConfirm').value.trim();
        if (passwordValue !== confirmValue) {
            alertBox.textContent = "비밀번호와 비밀번호 확인이 일치하지 않습니다.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 5. 제목 필수 입력
        const title = document.getElementById('title');
        const titleValue = document.getElementById('title').value.trim();
        if (titleValue.length < 4 || titleValue.length > 100) {
            alertBox.textContent = "제목은 4글자 이상 100글자 미만으로 입력해야 합니다.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 6. 내용 필수 입력
        const content = document.getElementById('content');
        const contentValue = document.getElementById('content').value;
        if (contentValue.length < 4 || contentValue.length > 100) {
            alertBox.textContent = "내용은 4글자 이상 1000글자 미만으로 입력해야 합니다.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {
            flag = true;
        }}}}}}


        // 7-1. 서버 검증을 위한 데이터 객체 생성
        const form = document.getElementById('insertForm');
        const params = new FormData(form);

        const formData = new URLSearchParams();
        params.forEach((value, key) => {
            if (typeof value === 'string') {
                formData.append(key, value);
            }
        });

        // 7-2. 서버에서 유효성 검증
        if (flag) {
            // fetch('', {}).then().then().catch();
            fetch('/validation/insert', {
                method: 'POST',
                body: formData,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(response  => {
                    if (response.ok) {
                        alert("서버 검증 성공");
                        form.submit();
                    } else {
                        alertBox.textContent = "서버 검증에 실패했습니다.";
                        alertBox.style.display = 'inline';
                        initAlert(alertBox);
                    }
                }).catch(
                error => {
                    console.error('Error:', error);
                    alert("요청 중 에러가 발생했습니다.");
                }
            );
        }
    }

    // 조건 안내 문구 초기화
    function initAlert(element) {
        setTimeout(function() {
            element.textContent = "";
            element.style.display = 'none';
        }, 5000);
    }

    // 작성자 검증 로직
    function validateWriter(writer) {
        const isValidChars = /^[a-zA-Z0-9가-힣]+$/.test(writer);
        return !isValidChars;
    }

    // 비밀번호 검증 로직
    function validatePassword(password) {
        const hasLetter = /[a-zA-Z]/.test(password);                                    // 영문자
        const hasDigit = /\d/.test(password);                                           // 숫자(0-9)
        const hasSpecialChar = /[!"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]/.test(password);  // 특수문자
        // 이스케이프 -→\-   [→\[   \→\\  ]→\]   {→\{   |→\|   }→\}

        const isValidChars = /^[a-zA-Z0-9!"#$%&'()*+,\-./:;<=>?@[\\\]^_`{|}~]+$/.test(password);

        return !(hasLetter && hasDigit && hasSpecialChar && isValidChars);
    }

</script>


</body>
</html>
