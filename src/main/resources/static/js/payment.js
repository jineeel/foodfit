const buyerName = document.getElementById('orderName').value;
const buyerEmail = document.getElementById('userEmail').value;
const itemNames = document.querySelectorAll('.itemName');
const lastItemName = itemNames[0].textContent;
const itemCount = (itemNames.length-1);

const buyButton = document.getElementById('payment')
buyButton.addEventListener('click', (e)=>{
    if(inputNullCheck()) {
        kakaoPay();
    }else{
        e.preventDefault();
    }
})

var IMP = window.IMP;
var today = new Date();
var hours = today.getHours(); // 시
var minutes = today.getMinutes();  // 분
var seconds = today.getSeconds();  // 초
var milliseconds = today.getMilliseconds();
var makeMerchantUid = `${hours}` + `${minutes}` + `${seconds}` + `${milliseconds}`;

function kakaoPay() {
    let name;
    if(itemCount>0){
        name = lastItemName + ' 외 ' + itemCount + '건';
    }else{
        name = lastItemName
    }

    const price = document.getElementById('totalPayment').textContent;

    IMP.init("imp32680210"); // 가맹점 식별코드
    IMP.request_pay({
        pg: 'kakaopay.TC0ONETIME', // PG사 코드표에서 선택
        pay_method: 'card', // 결제 방식
        merchant_uid: "IMP" + makeMerchantUid, // 결제 고유 번호
        name: name,
        amount: price,
        buyer_email: buyerEmail,
        buyer_name: buyerName,
    }, async function (rsp) {
        if (rsp.success) { //결제 성공시
            console.log(rsp);
            //결제 성공시 프로젝트 DB저장 요청
            paymentSave();
            if (response.status == 200) { // DB저장 성공시
                window.location.reload();
            } else { // 결제완료 후 DB저장 실패시
                alert(`error:[${response.status}]\n결제요청이 승인된 경우 관리자에게 문의바랍니다.`);
            }
        } else if (rsp.success == false) { // 결제 실패시
            alert(rsp.error_msg)
        }
    });
}
