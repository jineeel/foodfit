/*
    장바구니 수량 버튼
 */
const cartItemStatus = document.querySelectorAll('.itemStatus');
const plus = document.querySelectorAll('.plus');
const minus = document.querySelectorAll('.minus');
const quantity_value = document.querySelectorAll('.quantity_value');
const quantity_selectors = document.querySelectorAll('.quantity_selector');
const stockNum = document.querySelectorAll('.stockNum');
if(quantity_selectors){
    quantity_selectors.forEach((quantity_selector, index) => {
        const plusButton = plus[index];
        const minusButton = minus[index];
        const valueElement = quantity_value[index];

        let quantity = parseInt(valueElement.textContent); // 현재 수량 가져오기
        let stockNumber = parseInt(stockNum[index].value); // 상품 재고 수량
        if(cartItemStatus[index].value==='SELL') {
            plusButton.addEventListener('click', function () {
                if (valueElement.textContent < stockNumber) {
                    quantity += 1;
                    valueElement.textContent = quantity;
                }
            });
            minusButton.addEventListener('click', function () {
                if (quantity > 1) {
                    quantity -= 1;
                    valueElement.textContent = quantity;
                }
            });
        }
    });
}
/*
    장바구니 상품 수량 변경
 */
const countBtns = document.querySelectorAll('.countBtn');
let prices = document.querySelectorAll('.price')
const originPrice = document.querySelectorAll('.originPrice')
const cartItemId = document.querySelectorAll('.cartItemId');
const shippingFee = document.querySelectorAll('.shippingFee');
if(countBtns){
    countBtns.forEach((countBtn, index)=>{
        countBtn.addEventListener('click', ()=>{
            const count=parseInt(quantity_value[index].textContent);

            function success(result){
                const resultPrice = parseInt(result.count * originPrice[index].value);
                prices[index].textContent = resultPrice+"원";
                if(resultPrice>=40000){
                    shippingFee[index].textContent="무료";
                }else{
                    shippingFee[index].textContent="3500원";
                }
                totalPay();
            }
            function fail(error){
                console.error("에러",error);
            }
            cartRequest('PUT', '/api/cart/'+cartItemId[index].value, JSON.stringify(count), success, fail );
        })
    })
}

/*
    총 결제 금액
 */
const cartItems = document.querySelectorAll('.cartItems');
const totalAmount = document.getElementById('totalAmount')
const totalShippingFee = document.getElementById('totalShippingFee');
const totalPayment = document.getElementById('totalPayment');
$(document).ready(function () {
    totalPay();
});
function totalPay(){
    let resultPrice= 0;
    cartItems.forEach((cartItem, index)=>{
        if(cartItemStatus[index].value==='SELL') {
            resultPrice += parseInt(prices[index].textContent);
        }
    })
    if(totalAmount){
        let amount = totalAmount.textContent = resultPrice +"원"
        let shippingFee = totalShippingFee.textContent = parseInt(totalAmount.textContent) >= 40000 ? "무료" : "3500원";
        let payment= totalPayment.textContent = shippingFee =="무료" ? amount : parseInt(amount)+parseInt(shippingFee)+"원";
    }
}
/*
    체크박스 전체 선택
 */

function selectAll(selectAll){
    const checkBoxes = document.querySelectorAll('.checkItem');
    checkBoxes.forEach((checkbox,index)=>{
        checkbox.checked = selectAll.checked;
        if(cartItemStatus[index].value!='SELL'){
            checkbox.checked = false
        }
    })
}
/*
    상품 하나씩 삭제
 */
const originItemId = document.querySelectorAll('.originItemId');
const deleteCartItems = document.querySelectorAll('.deleteCartItem');
deleteCartItems.forEach((deleteCartItem, index)=>{
    deleteCartItem.addEventListener('click', ()=>{
        deleteItem(originItemId[index].value);
    })
})
/*
    상품 여러개 삭제
 */
const cartForm = document.getElementById('cartForm');
const selectDelete = document.getElementById('selectDelete');
if(cartForm){
    selectDelete.addEventListener('click',()=>{
        const selectedItems = Array.from(document.querySelectorAll('.checkItem:checked'))
            .map(item => item.value)
            .join(",");
        deleteItem(selectedItems);
    })
}
/*
    상품 삭제
 */
function deleteItem(cartItemId){
    function success(result){
        location.reload();
    }
    function fail(error){
        console.error("에러",error);
    }
    deleteCartRequest('DELETE', `/api/cart/${cartItemId}`, null, success, fail)
}

function deleteCartRequest(method, url, body, success, fail){
    fetch(url,{
        method: method,
        body: body,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response=>success())
        .then(result=>success())
        .catch(error => {
            console.error("요청 실패 " + error);
            return fail(error);
        });
}
/*
    구매하기 버튼
 */
const payBtn = document.getElementById('payBtn');
if(payBtn){
    payBtn.addEventListener('click', ()=>{
        const cartItemCheckboxes = Array.from(document.querySelectorAll('input[name="checkItem"]:checked'));
        const cartItemId = cartItemCheckboxes.map(item => item.value).join(",");
        const quantity = cartItemCheckboxes.map(checkbox => {
            const quantityText = checkbox.closest('tr.cartItems').querySelector('.quantity_value').textContent;
            return quantityText.trim();
        })
          .join(",");

        if(cartItemCheckboxes.length>0){
            const url = '/order?id='+cartItemId+'&quantity='+quantity+'&orderType='+"cart";
            location.replace(url);
        }else{
            errorConfirm();
        }
    })
}

const cartItemInfo= document.querySelectorAll('.cart_item_info');
if(cartItemInfo){
    cartItemInfo.forEach((cartInfo,index)=>{
        cartItemInfo[index].addEventListener('click',()=>{
            if(cartItemStatus[index].value==='SELL'){
                const id = originItemId[index].value;
                location.replace('/item/detail/'+id)
            }
        })
    })
}
/*
    error Alert
 */
function errorConfirm(){
    Swal.fire({
        text: '구매할 상품을 선택해주세요',
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#8a8a8a',
        confirmButtonText: '확인',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(`/cart`)
        }
    })
}
