/*
    상품 리스트에서 장바구니 추가
 */
const itemSellStatus = document.getElementById('itemSellStatus');
const itemIds = document.querySelectorAll('.itemId');
const counts = document.querySelectorAll('.count');
const cartBtns = document.querySelectorAll('.cartBtn');
const itemStatus = document.querySelectorAll('.itemStatus');
if(cartBtns){
    cartBtns.forEach((cartBtn, index) => {
            cartBtn.addEventListener('click', (event) => {
                event.stopPropagation();
                const formData = {
                    itemId: itemIds[index].value,
                    count: counts[index].value
                };

                function success(result) {
                    successConfirm();
                }

                function fail(response) {
                    cartErrorConfirm(response.url);
                }
                if(itemStatus[index].value==='SELL') {
                    cartRequest('POST', '/api/cart', JSON.stringify(formData), success, fail);
                }
            });
    });

}
/*
    상품 디테일에서 장바구니 추가
 */
const cartBtn = document.querySelector('.cartBtnDetail');
if(cartBtn){
    cartBtn.addEventListener('click', (event)=>{
        const formData = {
            itemId: document.getElementById('id').value,
            count: document.querySelector('.quantity_value').textContent
        }
        function success(result) {
            successConfirm();
        }
        function fail(response) {
            cartErrorConfirm(response.url);
        }
        cartRequest('POST', '/api/cart', JSON.stringify(formData), success, fail);
    })
}

function cartRequest(method, url, body, success, fail){
    fetch(url,{
        method: method,
        body: body,
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response=>response.json())
        .then(result=>success(result))
        .catch(error => {
            console.error("요청 실패 " + error);
            return fail(error);
        });
}

/*
    cart error Alert
 */
function cartErrorConfirm(response){
    Swal.fire({
        text: '상품을 장바구니에 추가할 수 없습니다',
        icon: 'error',
        showCancelButton: false,
        confirmButtonColor: '#8a8a8a',
        confirmButtonText: '확인',
        width: 400,

    }).then((result) => {
        if (result.value) {
            location.replace(response)
        }
    })
}
/*
    cart success Alert
 */
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