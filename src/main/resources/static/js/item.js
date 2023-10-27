let inputStatus;

const itemId = document.getElementById('id');
const itemName = document.getElementById('itemName');
const price = document.getElementById('price');
const stockNumber = document.getElementById('stockNumber');
const itemDetail = document.getElementById('itemDetail');
const category2 = document.getElementById('category2');
const itemForm = document.getElementById('itemForm')

const createBtn = document.getElementById('createBtn');
if(createBtn){
    createBtn.addEventListener('click', (event)=>{
        event.preventDefault(); // 폼 제출 동작 막기

        const formData = new FormData(itemForm);

        function success() {
            location.replace(`/item`);
        }

        function fail(response) {
            itemErrorConfirm("상품 등록이 실패했습니다", response.url)
        }

        itemRequest('POST',`api/item`,formData, success,fail);
    });
}

const updateBtn = document.getElementById('updateBtn');
if(updateBtn){
    updateBtn.addEventListener('click', (event)=>{
        let id = document.getElementById('id').value;
        event.preventDefault(); // 폼 제출 동작 막기
        const formData = new FormData(itemForm);
        function success() {
            location.replace(`/item/detail/${id}`);
        }

        function fail(response) {
            itemErrorConfirm("상품 수정이 실패했습니다", response.url)
        }
        function fail(){
            location.replace(`/item/detail/${id}`);
        }

        itemRequest('PUT', `api/item/${id}`,formData, success, fail);
    });
}

const deleteBtn = document.getElementById('deleteBtn');
if(deleteBtn){
    deleteBtn.addEventListener('click', (event) => {

        let id= document.getElementById('id').value;
        let categoryCode = document.getElementById('categoryCode').value;

        function success(){
            location.replace(`/item/list/${categoryCode}`);
        }
        function fail(response) {
            itemErrorConfirm("상품 삭제가 실패했습니다", response.url)
        }
        function fail(){
            location.replace(`/item/detail/${id}`);
        }
        itemRequest('DELETE', `/api/item/${id}`,null, success, fail);
    })
}

function itemRequest(method, url, body, success, fail){
    if(method!="DELETE"){
        if(!inputNullCheck()){
            return;
        }
    }
    fetch(url,{
        method: method,
        body : body,
    }).then(response=>{
            if (response.status === 200 || response.status === 201) {
                if(response.redirected){
                    return fail(response);
                }else {
                    return success();
                }
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
// const customFileInput = document.querySelector('.custom-file-input');
const customFileLabel = document.querySelector('.custom-file-label');
function inputNullCheck(){
    inputStatus = false;
    if(!category2.value){
        resultFail.textContent="카테고리를 선택해주세요"
    }else if(!itemName.value){
        resultFail.textContent="상품명을 입력해주세요";
    }else if(!price.value){
        resultFail.textContent="가격을 입력해주세요";
    }else if(!stockNumber.value){
        resultFail.textContent="수량을 입력해주세요";
    }else if(!itemDetail.value){
        resultFail.textContent="상세정보를 입력해주세요"
    }else if(!customFileLabel.textContent){
        resultFail.textContent="대표 상품 이미지를 넣어주세요"
    }else{
        inputStatus = true;
    }
    return inputStatus;
}

const purchaseBtn = document.getElementById('purchaseBtn');
if(purchaseBtn){
    purchaseBtn.addEventListener('click', ()=>{
        const quantity = document.querySelector('.quantity_value').textContent;
        const url = '/order?id='+itemId.value+'&quantity='+quantity+'&orderType='+"item";
        window.location.href = url;
    })
}
/*
    item error Alert
 */
function itemErrorConfirm(text,response){
    Swal.fire({
        text: text,
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