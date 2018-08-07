function showPassword(){
    var input = document.getElementById("pwd");
    if (input.type === "password"){
        input.type = "text";
    } else {
        input.type = "password";
    }
}

function showInsertedPasswords(){
    var input_first = document.getElementById("pwd1");
    var input_second = document.getElementById("pwd2");
    if (input_first.type === "password" && input_second.type === "password"){
        input_first.type = "text";
        input_second.type = "text";
    } else {
        input_first.type = "password";
        input_second.type = "password";
    }
}