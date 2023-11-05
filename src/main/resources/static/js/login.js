let inputStatus;
const idInputEl = document.getElementById("floatingInput");
const pwInputEl = document.getElementById("floatingPassword");

const validId = document.getElementById('validId');
const validPw = document.getElementById('validPassword');

const loginBtn = document.getElementById('loginBtn');
loginBtn.addEventListener('click', (e) => {
    if(!inputNullCheck()){
        e.preventDefault();
    }
});

function inputNullCheck(){
    inputStatus = false;
    if(!idInputEl.value){
        validId.textContent = "아이디를 입력해주세요";
    }else if(!pwInputEl.value){
        validPw.textContent = "비밀번호를 입력해주세요";
    } else{
        validId.textContent = "";
        validPw.textContent = "";
        inputStatus = true;
    }
    return inputStatus;
}

const idSaveCheck = document.getElementById('idSaveCheck');
document.addEventListener("DOMContentLoaded", ()=>{
    let key = getCookie("idChk"); //user1
    if(key!=""){
        idInputEl.value=key;
    }
    if(idInputEl.value != ""){
        idSaveCheck.checked = true;
    }
    idSaveCheck.addEventListener('change', ()=>{
        if(idSaveCheck.checked){
            setCookie("idChk", idInputEl.value, 7);
        }else{
            deleteCookie("idChk");
        }
    })
    idInputEl.addEventListener('keyup', ()=>{
        if(idSaveCheck.checked){
            setCookie("idChk", idInputEl.value, 7);
        }
    })
});

function setCookie(cookieName, value, exdays){
    let exdate = new Date();
    exdate.setDate(exdate.getDate() + exdays);
    let cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
    document.cookie = cookieName + "=" + cookieValue;
}

function deleteCookie(cookieName){
    let expireDate = new Date();
    expireDate.setDate(expireDate.getDate() - 1);
    document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}

function getCookie(cookieName) {
    cookieName = cookieName + '=';
    let cookieData = document.cookie;
    let start = cookieData.indexOf(cookieName);
    let cookieValue = '';
    if(start != -1){
        start += cookieName.length;
        let end = cookieData.indexOf(';', start);
        if(end == -1)end = cookieData.length;
        cookieValue = cookieData.substring(start, end);
    }
    return unescape(cookieValue);
}