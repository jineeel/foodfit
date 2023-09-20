let account = { id: null, pw: null, pw2: null, name: null, phone: null, phoneNum: null,email: null };
let status;

const errMsg = {
    id: {
        invalid: "6~16자의 영문 소문자와 숫자를 조합해주세요",
        success: "사용 가능한 아이디입니다",
        fail: "이미 가입한 아이디입니다"
    },
    pw: "비밀번호는 6자 이상 16자 이하, 영문(대소문자),숫자,특수문자 2가지 포함하여 입력해주세요",
    pwCk: {
        success: "비밀번호가 일치합니다",
        fail: "비밀번호가 일치하지 않습니다"
    },
    name: "이름은 2자 이상 10자 이하, 한글,영문(대소문자),숫자로 입력해주세요",
    phone: {
        invalid: "'-' 제외 숫자만 입력해주세요",
        success: "사용 가능한 핸드폰 번호입니다",
        fail: "이미 가입한 핸드폰 번호입니다"
    },
    phoneNum: {
        success: "인증 되었습니다",
        fail: "인증 실패했습니다"
    },
    email: {
        invalid: "이메일 형식으로 입력해주세요",
        success: "사용 가능한 이메일입니다",
        fail: "이미 가입한 이메일입니다"
    }
}

/*** 아이디 정규식 체크 ***/
const idInputEl = document.querySelector('#info_id input');
const idErrorMsgEl = document.querySelector('#info_id .error-msg');

idInputEl.addEventListener('change', () => {
    const idRegExp = /^[a-zA-Z0-9]{6,16}$/;
    if (idRegExp.test(idInputEl.value)) {
        checkDataId(idInputEl.value);
    } else { // 유효성 검사 실패
        idErrorMsgEl.textContent = errMsg.id.invalid;
        idErrorMsgEl.style.color='red';
        account.id = null;
    }
});
/*** 아이디 중복 검사 함수 ***/
function checkDataId(userId) {
    $.ajax({
            url: '/user/checkId',
            type: 'POST', //POST 방식으로 전달
            data: {
                "userId": userId
            },
            async: false,
            success: function (result) {
                console.log(result)
                if (!result) {
                    idErrorMsgEl.textContent = errMsg.id.success;
                    idErrorMsgEl.style.color='green';
                    account.id = idInputEl.value;
                } else {
                    idErrorMsgEl.textContent = errMsg.id.fail;
                    idErrorMsgEl.style.color='red';
                    account.id = null;
                }
            },
            error: function (request, status, error) {
                const errorMsg = JSON.parse(request.responseText);
                alert("ERROR CODE: " + request.status + "\n" +
                    "ERROR MESSAGE: " + errorMsg.message + "\n" +
                    "상태가 지속될 경우 관리자에게 문의해주세요");
            }
        }
    );
}

/*** 비밀번호 정규식 체크 ***/
const pwInputEl = document.querySelector('#info_pw input');
const pwErrorMsgEl = document.querySelector('#info_pw .error-msg');

pwInputEl.addEventListener('keyup',() => {
    const pwRegExp = /^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*;()._-]{6,16}$/;
    if(pwRegExp.test(pwInputEl.value)){
        pwErrorMsgEl.textContent = "";
        account.pw = pwInputEl.value;
    }else{
        pwErrorMsgEl.textContent = errMsg.pw;
        account.pw = null;
    }
});

/*** 비밀번호 확인 체크 ***/
const pw2InputEl = document.querySelector('#info_pw2 input');
const pw2ErrorMsgEl = document.querySelector('#info_pw2 .error-msg');

pw2InputEl.addEventListener('keyup', () => {
    if(pwInputEl.value == pw2InputEl.value){
        pw2ErrorMsgEl.textContent = errMsg.pwCk.success;
        pw2ErrorMsgEl.style.color='green';
        account.pw2 = pw2InputEl.value;
    }else{
        pw2ErrorMsgEl.textContent = errMsg.pwCk.fail;
        pw2ErrorMsgEl.style.color='red';
        account.pw2 = null;
    }
});

/*** 이름 정규식 체크 ***/
const nameInputEl = document.querySelector('#info_name input');
const nameErrorMsgEl = document.querySelector('#info_name .error-msg');

nameInputEl.addEventListener('keyup', ()=> {
    const nameRegExp = /^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,10}$/;
    if(nameRegExp.test(nameInputEl.value)){
        nameErrorMsgEl.textContent = "";
        account.name = nameInputEl.value;
    }else{
        nameErrorMsgEl.textContent = errMsg.name;
        account.name = null;
    }
});


/*** 전화번호 정규식 + 중복 체크 ***/
const phoneInputEl = document.querySelector('#info_phone input');
const phoneErrorMsgEl = document.querySelector('#info_phone .error-msg');

/*** 전화번호 중복 / 정규식 체크 ***/
function setValidTel(phone) {
    const phoneRegExp = /^\d{3}\d{3,4}\d{4}$/;
    if (phoneRegExp.test(phoneInputEl.value)) {
        checkDataPhone(phone);
    } else {
        phoneErrorMsgEl.textContent = errMsg.phone.invalid;
        phoneErrorMsgEl.style.color = 'red';
        account.phone = null;
        status = false;
    }

    function checkDataPhone(phone) { //전화번호 중복 체크
        $.ajax({
                url: '/user/checkPhone',
                type: 'POST', //POST 방식으로 전달
                data: {
                    "phone": phone
                },
                async:false,
                success: function (result) {
                    if (!result) {
                        phoneErrorMsgEl.textContent = errMsg.phone.success;
                        phoneErrorMsgEl.style.color='green';
                        account.phone = phoneInputEl.value;
                        status = true;
                    } else {
                        phoneErrorMsgEl.textContent = errMsg.phone.fail;
                        phoneErrorMsgEl.style.color='red';
                        account.phone = null;
                        status = false;
                    }
                },
                error: function (request, status, error) {
                    const errorMsg = JSON.parse(request.responseText);
                    alert("ERROR CODE: " + request.status + "\n" +
                        "ERROR MESSAGE: " + errorMsg.message + "\n" +
                        "상태가 지속될 경우 관리자에게 문의해주세요");
                }
            }
        );}
    return status;
}

const phoneSendBtn = document.getElementById('sendBtn');
const phoneNumInputEl = document.querySelector('#info_phone_number input');
const confirmBtn = document.getElementById('confirmBtn');
const confirm = document.getElementById('confirm');

/*** 전화번호 인증번호 보내기 ***/
phoneSendBtn.addEventListener('click', () => {
    if (setValidTel(phoneInputEl.value)) {
        sendTelNumber(phoneInputEl.value);
    }
});
function sendTelNumber(phone) {
    $.ajax({
        url: "/user/sms",
        type: "post",
        dataType: "json",
        data: JSON.stringify({
            "to": phone
        }),
        contentType: "application/json",
        success: function (data) {
            sendAuthNum();
            // $("#confirm").attr("value", data);
            confirm.value=data;
            document.getElementById('info_phone_num').style.display="";
            phoneNumInputEl.disabled=false;
            confirmBtn.disabled=false;
            phoneSendBtn.style.background="#a8d98e";
            confirmBtn.style.background="#a8d98e";
        },
    });
}
/*** 인증번호 발송 및 타이머 함수 실행 ***/
let timer;
let isRunning = false;
function sendAuthNum(){
    // 남은 시간(초)
    const leftSec = 180;
    const display = document.getElementById('sendBtn');
    // 이미 타이머가 작동중이면 중지
    if (isRunning){
        clearInterval(timer);
    }
    startTimer(leftSec, display);
    display.style.background="#dcdcdc";
    display.disabled=true;
}
function startTimer(count, display) {
    let minutes, seconds;
    timer = setInterval(function () {
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        display.value = minutes + ":" + seconds;
        // 타이머 종료
        if (--count < 0) {
            clearInterval(timer);
            // display.value = "";
            isRunning = false;
            confirmBtn.style.background="#dcdcdc";
            confirmBtn.disabled=true;
            phoneNumInputEl.disabled=true;
            phoneNumInputEl.value="";
        }
    }, 1000);
    isRunning = true;
}

/*** 휴대폰번호 인증번호 체크 ***/
const numErrorMsgEl = document.querySelector('#info_phone_number .error-msg');
confirmBtn.addEventListener('click', () => {

    if(confirm.value==phoneNumInputEl.value){
        numErrorMsgEl.textContent= errMsg.phoneNum.success;
        numErrorMsgEl.style.color='green';
        document.getElementById("info_phone_num").style.display="none";
        clearInterval(timer);
        account.phoneNum = confirm.value;
        phoneInputEl.readOnly = true;
    }else{
        numErrorMsgEl.textContent = errMsg.phoneNum.fail;
        numErrorMsgEl.style.color='red';
        account.phoneNum = null
    }
});

/*** 인증번호 다시 보내기 ***/
const resendBtn = document.getElementById('resendBtn');
resendBtn.addEventListener('click', () => {
    sendTelNumber(phoneInputEl.value);
    phoneNumInputEl.value="";
})

/*** 이메일 정규식 체크 ***/
const emailInputEl = document.querySelector('#info_email input');
const emailErrorMsgEl = document.querySelector('#info_email .error-msg');

emailInputEl.addEventListener('keyup', () => {
    const emailRegExp = /^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/;
    if(emailRegExp.test(emailInputEl.value)){
        checkDataEmail(emailInputEl.value)
    }else{
        emailErrorMsgEl.textContent = errMsg.email.invalid;
        emailErrorMsgEl.style.color='red';
        account.email = null;
    }

    function checkDataEmail(email) { //전화번호 중복 체크
        $.ajax({
                url: '/user/checkEmail',
                type: 'POST', //POST 방식으로 전달
                data: {
                    "email": email
                },
                async:false,
                success: function (result) {
                    if (!result) {
                        emailErrorMsgEl.textContent = errMsg.email.success;
                        emailErrorMsgEl.style.color='green';
                        account.email = emailInputEl.value;
                    } else {
                        emailErrorMsgEl.textContent = errMsg.email.fail;
                        emailErrorMsgEl.style.color='red';
                        account.email = null;
                    }
                },
                error: function (request, status, error) {
                    const errorMsg = JSON.parse(request.responseText);
                    alert("ERROR CODE: " + request.status + "\n" +
                        "ERROR MESSAGE: " + errorMsg.message + "\n" +
                        "상태가 지속될 경우 관리자에게 문의해주세요");
                }
            }
        );}
});

/*** SUBMIT ***/
const submitBtn = document.getElementById('submitBtn');
const resultFailEl = document.getElementById('resultFail');

submitBtn.addEventListener('click', (e) => {
    let isAllFilled = true;
    const word = {
        id: "아이디를",
        pw: "비밀번호를",
        pw2: "비밀번호가 일치하는지",
        name: "이름을",
        email: "이메일을",
        phone: "휴대폰 번호를",
        phoneNum: "휴대폰 인증번호를"
    }

    for(let element in account){
        if(!account[element]){
            resultFailEl.textContent = word[element]+" 다시 확인해주세요";
            isAllFilled = false;
            e.preventDefault();
            break;
        }
    }
    if(isAllFilled === true){
        resultFailEl.textContent = "";
    }
});
