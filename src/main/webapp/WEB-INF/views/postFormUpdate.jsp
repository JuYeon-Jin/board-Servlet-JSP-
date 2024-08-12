<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/css/postForm.css">
</head>
<body>

<form action="/board/update" method="POST" id="updateForm" enctype="multipart/form-data" class="container">
    <hr class="line">
    <div class="category-container">
        <div class="subject-container">
            <span class="subject-item">카테고리*</span>
        </div>
        <div class="input-container">
            <select name="categoryId" id="category" class="category">
                <c:forEach var="category" items="${categories}">
                    <option value='<c:out value="${category.id}" />'
                         <c:if test='${category.name == post.categoryName}'>selected</c:if>
                        ><c:out value='${category.name}' /></option>
                </c:forEach>
            </select>
        </div>
    </div>

    <hr class="line">
    <div class="writer-container">
        <div class="subject-container">
            <span class="subject-item">등록 일시</span>
        </div>
        <div class="input-container">
            <span class="font13"><c:out value='${post.createdAt}' /></span>
        </div>
    </div>
    <hr class="line">

    <div class="writer-container">
        <div class="subject-container">
            <span class="subject-item">수정 일시</span>
        </div>
        <div class="input-container">
            <span class="font13">
            <c:choose>
                <c:when test="${post.updatedAt != null}">
                    <c:out value='${post.updatedAt}' />
                </c:when>
                <c:otherwise>
                    -
                </c:otherwise>
            </c:choose>
            </span>
        </div>
    </div>
    <hr class="line">

    <div class="writer-container">
        <div class="subject-container">
            <span class="subject-item">조회수</span>
        </div>
        <div class="input-container">
            <span class="font13"><c:out value='${post.views}' /></span>
        </div>
    </div>
    <hr class="line">

    <div class="writer-container">
        <div class="subject-container">
            <span class="subject-item">작성자*</span>
        </div>
        <div class="input-container">
            <input type="text" name="writer" class="writer" value="${post.writer}" required>
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
        </div>
    </div>
    <hr class="line">

    <div class="title-container">
        <div class="subject-container">
            <span class="subject-item">제목*</span>
        </div>
        <div class="input-container">
            <input type="text" name="title" id="title" class="title" value="${post.title}">
        </div>
    </div>
    <hr class="line">

    <div class="content-container">
        <div class="subject-container">
            <span class="subject-item">내용*</span>
        </div>
        <div class="input-container">
            <textarea name="content" id="content" class="content"><c:out value="${post.content}"/></textarea>
        </div>
    </div>
    <hr class="line">

    <div class="files-container">
        <div class="subject-container">
            <span class="subject-item">파일 첨부</span>
        </div>
        <div class="file-upload-container">
            <c:forEach var="file" items="${files}">
            <div class="file-item previous-file-list">
                <span><c:out value="${file.fileName}" /></span>
                <input type="hidden" name="deleteFileId">
                <button class="file-button" type="button"
                        onclick="removePreviousFile(this, '<c:out value="${file.id}"/>')">x
                </button>
            </div>
            </c:forEach>
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
        <input type="hidden" name="id" value="${post.id}">
        <button type="button" onclick="cancelForm()">취소</button>
        <button type="submit" onclick="submitForm()">저장</button>
    </div>
</form>

<script>

    function removePreviousFile(button, fileId) {
        // 해당 버튼의 부모 요소인 div 를 찾음
        const fileItemDiv = button.closest('.file-item');

        // div 안에 있는 hidden input을 찾음
        const hiddenInput = fileItemDiv.querySelector('input[name="deleteFileId"]');

        // hidden input의 value를 fileId로 설정
        hiddenInput.value = fileId;

        // 현재 파일 항목을 비활성화 (혹은 다른 처리를 원할 경우 여기에 추가)
        button.disabled = true; // 버튼 비활성화
        fileItemDiv.style.opacity = '0.5'; // 시각적으로 비활성화된 느낌을 주기 위해 투명도 조정
    };

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


        // 4. 제목 필수 입력
        const title = document.getElementById('title');
        const titleValue = document.getElementById('title').value.trim();
        if (titleValue.length < 4 || titleValue.length > 100) {
            alertBox.textContent = "제목은 4글자 이상 100글자 미만으로 입력해야 합니다.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {


        // 5. 내용 필수 입력
        const content = document.getElementById('content');
        const contentValue = document.getElementById('content').value;
        if (contentValue.length < 4 || contentValue.length > 100) {
            alertBox.textContent = "내용은 4글자 이상 1000글자 미만으로 입력해야 합니다.";
            alertBox.style.display = 'inline';
            initAlert(alertBox);
        } else {
            flag = true;
        }}}}}


        // 6-1. 서버 검증을 위한 데이터 객체 생성
        const form = document.getElementById('insertForm');
        const params = new FormData(form);

        const formData = new URLSearchParams();
        params.forEach((value, key) => {
            if (typeof value === 'string') {
                formData.append(key, value);
            }
        });

        flag = true;
        // 6-2. 서버에서 유효성 검증
        if (flag) {
            // fetch('', {}).then().then().catch();
            fetch('/validation/update', {
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

</script>

</body>
</html>
