const orderName = document.getElementById('orderName');
const phone = document.getElementById('phone');
const addrText = document.getElementById('addrText');
const zipcode = document.getElementById('zipcode');
const streetAdr = document.getElementById('streetAdr');
const detailAdr = document.getElementById('detailAdr');

$(document).ready(function(){
    $("input[name='addr']").change(function(){
        if($("input[name='addr']:checked").val() == 'addrEx'){
            orderName.value=document.getElementById('userName').value;
            phone.value=document.getElementById('userPhone').value;
            zipcode.value=document.getElementById('zipcodeEx').value
            streetAdr.value = document.getElementById('streetAdrEx').value;
            detailAdr.value=document.getElementById('detailAdrEx').value;
        }
        else if($("input[name='addr']:checked").val() == 'addrNew'){
            orderName.value="";
            phone.value="";
            zipcode.value="";
            streetAdr.value = "";
            detailAdr.value="";
        }
    });
});
const selectBox = document.getElementById('selectBox');
selectBox.addEventListener('change',()=>{
    const option = selectBox.options[selectBox.selectedIndex].text;
    if(option=="직접 입력") {
        addrText.value="";
    }else{
        addrText.value=option;
    }

})
const order_item_div = document.querySelectorAll('.order_item_div');
const payBtn = document.getElementById('payBtn');

const itemId= document.querySelectorAll('.itemId');
const orderPrice = document.querySelectorAll('.itemPrice');
const count = document.querySelectorAll('.count');
const orderType = document.querySelectorAll('.orderType');
payBtn.addEventListener("click", (e)=>{
    if(inputNullCheck()){
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
            "orderName" : document.getElementById('orderName').value,
            "orderPhone" : document.getElementById('phone').value,
            "orderZipcode" : document.getElementById('zipcode').value,
            "orderStreetAdr" : document.getElementById('streetAdr').value,
            "orderDetailAdr" : document.getElementById('detailAdr').value,
            "orderMessage" : document.getElementById('addrText').value
         }

        let addOrderRequest = {
            "orderItemRequests" : orderItemList,
            "orderUserRequest" : orderUserData
        }

        $.ajax({
            url:"/api/order",
            type:"POST",
            data: JSON.stringify(addOrderRequest),
            contentType: "application/json",
            success: function (result){
                let orderId = result.id;
                location.replace('/order/'+orderId);
            },error:function (error){
                console.error("에러",error);
            }
        })
    }else{
        e.preventDefault();
    }
})


const payUser = document.getElementById('payUser');
const checkBox1 = document.getElementById('checkBox1');
const checkBox2 = document.getElementById('checkBox2');
function inputNullCheck(){
    if(!orderName.value){
        resultFail.textContent="받는 사람 이름을 입력해주세요"
    }else if(!zipcode.value||!streetAdr.value||!detailAdr.value){
        resultFail.textContent="주소를 입력해주세요";
    }else if(!phone.value){
        resultFail.textContent="연락처를 입력해주세요";
    }else if(!payUser.value){
        resultFail.textContent="입금자명을 입력해주세요";
    }else if(!checkBox1.checked||!checkBox2.checked){
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
$(document).ready(function () {
    totalPay();
});

function totalPay(){
    let resultPrice= 0;
    orderItemDiv.forEach((orderItem, index)=>{
        resultPrice += parseInt(itemPrice[index].textContent);
    })
    if(totalAmount){
        let amount = totalAmount.textContent = resultPrice +"원"
        let shippingFee = totalShippingFee.textContent = parseInt(totalAmount.textContent) >= 40000 ? "무료" : "3500원";
        let payment= totalPayment.textContent = shippingFee =="무료" ? amount : parseInt(amount)+parseInt(shippingFee)+"원";
    }
}