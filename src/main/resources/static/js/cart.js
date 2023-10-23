const cartBtns = document.querySelectorAll('.cartBtn');
const itemIds = document.querySelectorAll('.itemId');
const counts = document.querySelectorAll('.count');

/** 리스트에서 장바구니 추가 **/
if(cartBtns){
cartBtns.forEach((cartBtn, index) => {
    cartBtn.addEventListener('click', (event) => {
        event.stopPropagation();
        const formData = {
            itemId: itemIds[index].value,
            count: counts[index].value
        };
        function success() {
            successConfirm();
        }
        function fail() {
        }
        cartRequest('POST', '/api/cart', formData, success, fail);
    });
});
}
/** 상품 디테일에서 장바구니 추가 **/
const cartBtn = document.querySelector('.cartBtnDetail');
if(cartBtn){
    cartBtn.addEventListener('click', (event)=>{
        const formData = {
            itemId: document.getElementById('id').value,
            count: document.querySelector('.quantity_value').textContent
        }
        function success() {
            successConfirm();
        }
        function fail() {}

        cartRequest('POST', '/api/cart', formData, success, fail);
    })
}

function cartRequest(method, url, body, success, fail){
    fetch(url,{
        method: method,
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response=>{
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        if(!response.ok){
            throw new Error('네트워크 응답이 실패했습니다');
        }
        return response.json();
    }).catch(error => {
        console.error("요청 실패 " + error);
        return fail();
    });
}

/** 장바구니 추가 확인창 **/
function successConfirm(){
    Swal.fire({
        text: '상품이 장바구니에 추가되었습니다',
        icon: 'success',
        showCancelButton: true,
        confirmButtonColor: '#66bc39',
        cancelButtonColor: '#8a8a8a',
        confirmButtonText: '장바구니로 이동',
        cancelButtonText: '계속 쇼핑',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(`/cart`)
        }
    })
}

const plus = document.querySelectorAll('.plus');
const minus = document.querySelectorAll('.minus');
const quantity_value = document.querySelectorAll('.quantity_value');
const quantity_selectors = document.querySelectorAll('.quantity_selector');
const stockNum = document.querySelectorAll('.stockNum');
/** 수량 **/
if(quantity_selectors){
    quantity_selectors.forEach((quantity_selector, index) => {
        const plusButton = plus[index];
        const minusButton = minus[index];
        const valueElement = quantity_value[index];

        let quantity = parseInt(valueElement.textContent); // 현재 수량 가져오기
        let stockNumber = parseInt(stockNum[index].value); // 상품 재고 수량
        plusButton.addEventListener('click', function () {
            if(valueElement.textContent < stockNumber){
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
    });
}

const countBtns = document.querySelectorAll('.countBtn');
let prices = document.querySelectorAll('.price')
const originPrice = document.querySelectorAll('.originPrice')
const cartItemId = document.querySelectorAll('.cartItemId');
const shippingFee = document.querySelectorAll('.shippingFee');
/** 장바구니 상품 수량 변경 **/
if(countBtns){
    countBtns.forEach((countBtn, index)=>{
        countBtn.addEventListener('click', ()=>{
            $.ajax({
                url: "/api/cart/"+cartItemId[index].value,
                type:"PUT",
                data: {
                    "count": quantity_value[index].textContent
                },
                success: function (result) {
                    const resultPrice = parseInt(result.count * originPrice[index].value);
                    prices[index].textContent = resultPrice+"원";
                    if(resultPrice>=40000){
                        shippingFee[index].textContent="무료";
                    }else{
                        shippingFee[index].textContent="3500원";
                    }
                    totalPay();
                },error:function (error){
                    console.error("에러",error);
                }
            })
        })
    })
}

/** 장바구니 상품 삭제 **/
const deleteCartItems = document.querySelectorAll('.deleteCartItem');
deleteCartItems.forEach((deleteCartItem, index)=>{
    deleteCartItem.addEventListener('click', ()=>{
        deleteItem(cartItemId[index].value);
    })
})
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
        resultPrice += parseInt(prices[index].textContent);
    })
    if(totalAmount){
        let amount = totalAmount.textContent = resultPrice +"원"
        let shippingFee = totalShippingFee.textContent = parseInt(totalAmount.textContent) >= 40000 ? "무료" : "3500원";
        let payment= totalPayment.textContent = shippingFee =="무료" ? amount : parseInt(amount)+parseInt(shippingFee)+"원";
    }
}

function selectAll(selectAll){
    const checkBoxes = document.querySelectorAll('input[type="checkbox"]');
    checkBoxes.forEach((checkbox)=>{
        checkbox.checked=selectAll.checked;
    })
}

const cartForm = document.getElementById('cartForm');
const selectDelete = document.getElementById('selectDelete');
if(cartForm){
    selectDelete.addEventListener('click',()=>{
        const selectedItems = Array.from(document.querySelectorAll('input[name="checkItem"]:checked'))
            .map(item => item.value)
            .join(",");
        deleteItem(selectedItems);
    })
}

function deleteItem(cartItemId){
    $.ajax({
        url: "/api/cart/"+cartItemId,
        type: "DELETE",
        success: function (result){
            location.reload();
        },error:function (error){
            console.error("에러",error);
        }
    })
}

const payBtn = document.getElementById('payBtn');
if(payBtn){
    payBtn.addEventListener('click', ()=>{
        const cartItemCheckboxes = Array.from(document.querySelectorAll('input[name="checkItem"]:checked'));
        const cartItemId = cartItemCheckboxes.map(item => item.value).join(",");

        const quantities = cartItemCheckboxes.map(checkbox => {
            const quantityText = checkbox.closest('tr.cartItems').querySelector('.quantity_value').textContent;
            return quantityText.trim(); // Trim to remove leading/trailing spaces
        }).join(",");

        console.log("++"+quantities)
        const url = '/order/'+cartItemId;
        window.location.href = url;
        // $.ajax({
        //     url: "/order/"+cartItemId,
        //     type:"GET",
        //     data: {
        //         "quantity": quantities
        //     },
        //     contentType:"application/json; charset=utf-8",
        //     success: function (result){
        //         alert("??");
        //     },error:function (error){
        //         console.error("에러",error);
        //     }
        // })
    })
}
