function changePage(formId, page){
    var currentForm = document.getElementById(formId);
    currentForm.page.value = page;
    currentForm.submit();
}