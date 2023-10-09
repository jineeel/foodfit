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

        function success() {
            location.replace(`/item`);
        }

        function fail() {
            location.replace(`/item`);
        }

        itemRequest('POST',`api/item`, success,fail);
    });
}

const updateBtn = document.getElementById('updateBtn');
if(updateBtn){
    updateBtn.addEventListener('click', (event)=>{
        let id = document.getElementById('id').value;
        event.preventDefault(); // 폼 제출 동작 막기

        function success() {
            location.replace(`/item/detail/${id}`);
        }

        function fail() {
            location.replace(`/item/detail/${id}`);
        }

        itemRequest('PUT', `api/item/${id}`, success, fail);
    });
}

function itemRequest(method, url, success, fail){
    if(!inputNullCheck()){
        return;
    }
    const formData = new FormData(itemForm);

    fetch(url,{
        method: method,
        body : formData,
    })
        .then(response=>{
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
    }else{
        inputStatus = true;
    }
    return inputStatus;
}
