
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
const cartBtn = document.getElementById('cartBtn');
if(cartBtn){
    cartBtn.addEventListener('click', (event)=>{

        const formData = {
            itemId: document.getElementById('id').value,
            count: document.getElementById('quantity_value').textContent
        }
        function success() {
            successConfirm();
        }

        function fail() {
        }

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