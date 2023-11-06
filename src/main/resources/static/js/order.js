const orderName = document.getElementById('orderName');
const phone = document.getElementById('phone');
const addrText = document.getElementById('addrText');
const zipcode = document.getElementById('zipcode');
const streetAdr = document.getElementById('streetAdr');
const detailAdr = document.getElementById('detailAdr');
/*
    배송지 선택 [주문자정보와 동일 or 새로운배송지]
 */
const addrInputs = document.querySelectorAll("input[name='addr']");
document.addEventListener("DOMContentLoaded", ()=>{
    if(addrInputs){
        addrInputs.forEach((input)=>{
            input.addEventListener('change',()=>{
                if (input.value === 'addrEx') {
                    orderName.value=document.getElementById('userName').value;
                    phone.value=document.getElementById('userPhone').value;
                    zipcode.value=document.getElementById('zipcodeEx').value
                    streetAdr.value = document.getElementById('streetAdrEx').value;
                    detailAdr.value=document.getElementById('detailAdrEx').value;
                }else if(input.value === 'addrNew'){
                    orderName.value="";
                    phone.value="";
                    zipcode.value="";
                    streetAdr.value = "";
                    detailAdr.value="";
                }
            })
        })
    }
    totalPay();
});
/*
    배송 요청 사항
 */
const selectBox = document.getElementById('selectBox');
if(selectBox){
    selectBox.addEventListener('change',()=>{
        const option = selectBox.options[selectBox.selectedIndex].text;
        if(option==="직접 입력") {
            addrText.value="";
        }else{
            addrText.value=option;
        }
    })
}
const order_item_div = document.querySelectorAll('.order_item_div');
const itemId= document.querySelectorAll('.itemId');
const orderPrice = document.querySelectorAll('.itemPrice');
const count = document.querySelectorAll('.count');
const orderType = document.querySelectorAll('.orderType');
/*
    주문
 */
function paymentSave(){
        let orderItemList = [];
        let orderItemData;

        order_item_div.forEach((orderItemDiv, index)=>{
            orderItemData = {
                "itemId" : itemId[index].value,
                "orderPrice" : orderPrice[index].textContent,
                "count" : count[index].textContent,
                "orderType" : orderType[index].value
            }
            orderItemList.push(orderItemData);
        });

        let orderUserData = {
            "orderName" : orderName.value,
            "orderPhone" : phone.value,
            "orderZipcode" : zipcode.value,
            "orderStreetAdr" :streetAdr.value,
            "orderDetailAdr" : detailAdr.value,
            "orderMessage" : document.getElementById('addrText').value
         }

        let addOrderRequest = {
            "orderItemRequests" : orderItemList,
            "orderUserRequest" : orderUserData
        }
        function success(result){
            let orderId = result.id;
            location.replace('/order/'+orderId);
        }
        function fail(error){
            console.error("에러",error);
        }
        orderRequest('POST', '/api/order', JSON.stringify(addOrderRequest), success, fail)
}

const checkBox1 = document.getElementById('checkBox1');
const checkBox2 = document.getElementById('checkBox2');
function inputNullCheck(){
    if(!orderName.value){
        resultFail.textContent="받는 사람 이름을 입력해주세요"
    }else if(!zipcode.value||!streetAdr.value||!detailAdr.value){
        resultFail.textContent="주소를 입력해주세요";
    }else if(!phone.value){
        resultFail.textContent="연락처를 입력해주세요";
    } else if(!checkBox1.checked||!checkBox2.checked){
        resultFail.textContent="주문 동의 사항을 확인 후 체크해주세요"
    }else{
        resultFail.textContent="";
        return true;
    }
    return false;
}
const orderItemDiv = document.querySelectorAll('.order_item_div');
const totalAmount = document.getElementById('totalAmount')
const totalShippingFee = document.getElementById('totalShippingFee');
const totalPayment = document.getElementById('totalPayment');
const itemPrice = document.querySelectorAll('.itemPrice');
/*
    주문 총 금액
 */
function totalPay(){
    let resultPrice= 0;
    orderItemDiv.forEach((orderItem, index)=>{
        resultPrice += parseInt(itemPrice[index].textContent);
    })
    if(totalAmount){
        let amount = totalAmount.textContent = resultPrice +"원"
        let shippingFee = totalShippingFee.textContent = parseInt(totalAmount.textContent) >= 40000 ? "무료" : "3500원";
        totalPayment.textContent = shippingFee =="무료" ? amount : parseInt(amount)+parseInt(shippingFee)+"원";
    }
}

/*
    주문 내역 삭제
*/
const orderDeleteBtns = document.querySelectorAll('.orderDeleteBtn');
const orderIds = document.querySelectorAll('.orderId');
if(orderDeleteBtns){
    orderDeleteBtns.forEach((orderDeleteBtn,index)=>{
        orderDeleteBtn.addEventListener('click', ()=>{
            console.log(orderIds[index].value);
            const id = orderIds[index].value;

            function success(){
                location.reload();
            }
            function fail(){
                orderErrorConfirm('주문을 삭제할 수 없습니다', '/mypage/order');
            }
            warningConfirm("주문을 삭제하시겠습니까?", 'POST', `/api/order/${id}`, null, success, fail);
        })
    })
}
/*
    주문 취소
 */
const orderCancelBtn = document.getElementById('orderCancelBtn');
if(orderCancelBtn){
    orderCancelBtn.addEventListener('click', ()=>{

        const id = document.getElementById('orderId').value;
        function success(){
            location.reload()
        }
        function fail(){
            orderErrorConfirm('주문을 취소할 수 없습니다', location.reload());
        }
        warningConfirm("주문을 취소하시겠습니까?",'PUT',`/api/order/${id}`,null, success,fail)
    })
}
/*
    fetch
 */
function orderRequest(method, url, body, success, fail) {
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
/*
    삭제 fetch
 */
function orderDeleteRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        body: body,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => success())
        .then(result => success())
        .catch(error => {
            console.error("요청 실패 " + error);
            return fail();
        });
}
/*
    order error confirm
 */
function orderErrorConfirm(text,url){
    Swal.fire({
        text: text,
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#8a8a8a',
        confirmButtonText: '확인',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(url)
        }
    })
}
/*
    주문삭제, 주문취소 확인 alert
 */
function warningConfirm(text,method,url,body,success,fail){
    Swal.fire({
        text: text,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#66bc39',
        cancelButtonColor: '#8a8a8a',
        confirmButtonText: "확인",
        cancelButtonText: "취소",
        width: 400,
    }).then(function(result){
        if (result.value) {
            orderDeleteRequest(method, url, body, success, fail)
        }
    })
}