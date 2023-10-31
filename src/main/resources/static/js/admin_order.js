/***********************
 *
 ***********************/
const paging = document.querySelectorAll('.paging');
if(paging){
    paging.forEach((page,index)=>{
        paging[index].addEventListener('click', ()=>{
            let data = paging[index].getAttribute('data-value');
            loadAdminPage(data)
        })
    })
}

function loadAdminPage(url) {

    function success(result) {
        $(contentsForm).html(result);
    }
    function fail(error) {
        console.error("에러", error);
    }
    formRequest('GET', url, null, success, fail);
}

function updateShipping(){
    const url = '/order/updateOrderStatus';
    function success(result) {
        $(contentsForm).html(result);
    }
    function fail(error) {
        console.error("에러", error);
    }
    formRequest('POST', url, null, success, fail);
}


const adminOrderStatus = document.querySelectorAll('.adminOrderStatus')
function selectAll(selectAll){
    const checkBoxes = document.querySelectorAll('.checkOrder');
    checkBoxes.forEach((checkbox,index)=>{
        checkbox.checked = selectAll.checked;
        if(adminOrderStatus[index].value=='CANCEL'){
            checkbox.checked = false
        }
    })
}

/*
주문 처리 버튼
 */
const orderStatusBtn = document.querySelectorAll('.orderStatusBtn');
const orderIds = document.querySelectorAll('.orderId')
if(orderStatusBtn){
    orderStatusBtn.forEach((orderStatus, index)=>{
        orderStatusBtn[index].addEventListener('click', ()=>{
            const orderId = Array.from(document.querySelectorAll('.checkOrder:checked'))
                .map(order => order.value)
                .join(",");

            let status = orderStatusBtn[index].getAttribute('data-value');
            function success(result) {
                location.reload();
            }
            function fail(error) {
                console.error("에러", error);
            }

            formRequest('PUT', '/order/updateStatus/'+orderId, status, success, fail);
        })
    })
}