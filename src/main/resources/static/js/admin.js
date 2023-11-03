const adminOrderForm = document.getElementById('adminOrderForm');
const adminItemForm = document.getElementById('adminItemForm');
const contentsForm = document.getElementById('contentsForm');

$(document).ready(function () {
    getForm('/order/list');
    adminOrderForm.style.color = 'green';
});

if(adminOrderForm) {
    adminOrderForm.addEventListener('click', () => {
        getForm('/order/list');
        adminOrderForm.style.color = 'green';
        adminItemForm.style.color = '';
    })
}
if(adminItemForm) {
    adminItemForm.addEventListener('click', () => {
        getForm('/item');
        adminItemForm.style.color = 'green';
        adminOrderForm.style.color = '';
    })
}

function getForm(url){
    function success(result){
        $(contentsForm).html(result);
    }
    function fail(error){
        console.error("에러",error);
    }
    formRequest('GET', url,null, success, fail);
}

function formRequest(method, url, body, success, fail){
    fetch(url,{
        method: method,
        body : body,
    }).then(function(response) {
        response.text().then(function (text) {
            success(text);
        })
        .catch(error => {
            console.error("요청 실패 " + error);
            return fail(error);
        });
    })
}

