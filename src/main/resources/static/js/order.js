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

const payBtn = document.getElementById('payBtn');
payBtn.addEventListener("click", (e)=>{
    if(inputNullCheck()){
        $.ajax({ //주문 저장하는 컨트롤러
            url:"/api/order",
            type:"POST",
            // data:,
            contentType:"application/json; charset=utf-8",
            success: function (){

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
