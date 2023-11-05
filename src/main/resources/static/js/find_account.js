const email = document.getElementById('email');
const username = document.getElementById('username');
const phone = document.getElementById('phone');
const username2 = document.getElementById('username2');
const resultFail = document.getElementById("resultFail");

const selectPhone = document.getElementById('selectPhone');
const selectEmail = document.getElementById('selectEmail');
const resultSuccess = document.getElementById('resultSuccess');

const id = document.getElementById('userId');
const phoneSendBtn = document.getElementById('phoneSendBtn');
const number = document.getElementById('number');

const confirm = document.getElementById('confirm');
const resendBtn = document.getElementById('resendBtn');

const authInputs = document.querySelectorAll("input[name='auth']");
document.addEventListener('DOMContentLoaded',()=>{
    selectPhone.style.display= 'none'; // 초깃값 설정

    authInputs.forEach(input =>{
        input.addEventListener('change', ()=>{
            console.log("input.value="+input.value)
            //아이디 찾기
            if(input.value === 'email' || input.value === 'phone') {
                checkedFindId(input.value);
            }
            //비밀번호 찾기
            if(input.value === 'email2' || input.value === 'phone2') {
                checkedFindPw(input.value)
            }
        })
    })
});
function checkedFindId(input){
    if (input === 'email'){
        selectPhone.style.display = 'none';
        selectEmail.style.display = 'block';
    }else if(input === 'phone'){
        selectPhone.style.display = 'block';
        selectEmail.style.display = 'none';
    }
    phone.value = "";
    username2.value = "";
    email.value="";
    username.value="";
    resultFail.textContent="";
    resultSuccess.style.display="none"
}
function checkedFindPw(input){
    if(input === 'email2'){
        selectPhone.style.display = 'none';
        selectEmail.style.display = 'block';
    }else if(input === 'phone2'){
        selectPhone.style.display = 'block';
        selectEmail.style.display = 'none';
    }
    phone.value = "";
    email.value="";
    id.value = "";
    number.value="";
    resultFail.textContent="";
}
function findId(name, email, phone) {
    let formData = {
        "username": name,
        "email": email,
        "phone": phone
    }
    function success(result) {
        if (result) {
            resultFail.textContent = "";
            resultSuccess.style.display = "";
            document.getElementById('resultId').textContent = "ID : " + result.userId;
        } else {
            resultFail.textContent = "해당 정보로 가입된 회원이 없습니다";
            resultSuccess.style.display = "none"
        }
    }
    function fail(request) {
        resultFail.textContent = "해당 정보로 가입된 회원이 없습니다";
        resultSuccess.style.display = "none"
    }

    findRequest('POST', '/api/user/findId', JSON.stringify(formData), success, fail);
}

const submitBtn = document.getElementById('submitBtn');
if(submitBtn) {
    submitBtn.addEventListener('click', (e) => {
        authInputs.forEach(input => {
            if (input.value === 'email') {
                if (!email.value || !username.value) {
                    resultFail.textContent = "인증할 정보를 입력해주세요";
                } else {
                    resultFail.textContent = "";
                    findId(username.value, email.value, phone.value);
                }
            } else if (input.value === 'phone') {
                if (!username2.value && !phone.value) {
                    resultFail.textContent = "인증할 정보를 입력해주세요";
                } else {
                    resultFail.textContent = "";
                    findId(username2.value, email.value, phone.value);
                }
            }
        });
    });
}
const emailSendBtn = document.getElementById('emailSendBtn');
if(emailSendBtn) {
    emailSendBtn.addEventListener('click', (e) => {
        if (!id.value || !email.value) {
            resultFail.textContent = "인증할 정보를 입력해주세요";
        } else {
            resultFail.textContent = "";
            findPw(id.value, email.value, phone.value)
        }
    });
}
const pwResetBtn = document.getElementById('pwResetBtn');

if(pwResetBtn) {
    pwResetBtn.addEventListener('click', (e) => {
        if (confirm.value === number.value) {
            alert("인증되었습니다. 비밀번호 재설정 페이지로 이동합니다");
            document.getElementById('form').submit();
        } else {
            resultFail.textContent = "인증 실패했습니다";
            resultFail.style.color = 'red';
            number.value = "";
            e.preventDefault();
        }
    });
}

if(resendBtn) {
    resendBtn.addEventListener('click', () => {
        number.value = "";
        resultFail.textContent = "";
        authInputs.forEach(index => {
            if (index.checked && index.value === 'email2') {
                sendEmail(email.value);
            } else if (index.checked && index.value === 'phone2') {
                sendSms(phone.value);
            }
        })
    });
}

if(phoneSendBtn) {
    phoneSendBtn.addEventListener('click', () => {
        if (!id.value || !phone.value) {
            resultFail.textContent = "인증할 정보를 입력해주세요";
        } else {
            resultFail.textContent = "";
            findPw(id.value, email.value, phone.value);
        }
    })
}

function findPw(id,email,phone){
    let formData = {
        "userId" : id,
        "email": email,
        "phone": phone
    }
    function success(result){
        if(phone === ""){
            sendEmail(email);
        }else if(email === ""){
            sendSms(phone);
        }
    }
    function fail(){
        resultFail.textContent = "해당 정보로 가입된 회원이 없습니다!";
    }

    findRequest('POST', '/api/user/findPassword', JSON.stringify(formData), success, fail);return status;
}

function sendEmail(email){
    function success(result){
        confirm.value=result;
        pwResetBtn.style.background="#67be38";
        pwResetBtn.disabled=false;
    }
    function fail(){}

    findRequest('POST', '/api/user/mail', email, success, fail);
}

function sendSms(phone){
    function success(result){
        confirm.value=result;
        pwResetBtn.style.background="#67be38";
        pwResetBtn.disabled=false;
    }
    function fail(){}

    findRequest('POST', '/api/user/sms', JSON.stringify({"to" : phone }), success, fail);
}


function findRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        body: body,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json())
        .then(result => success(result))
        .catch(error => {
            console.error("요청 실패 " + error);
            return fail();
        });
}
