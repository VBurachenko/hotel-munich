function validateSignIn(form) {
    return testEmail(form) && testPassword(form);

}

function validateRegistration(form){
    return testEmail(form) && testPassword(form) && testPasswords(form) && testName(form) &&
        testSurname(form) && testTelephoneNumber(form) && testBirthday();
}

function testEmail(form) {
    var email = form.email.value;
    const emailRegExp = /\S+@\S+\.\S+/;
    return checkCorresponding(email, emailRegExp, "wrongEmail");
}

function testPassword(form) {
    var password = form.passwordFirst.value;
    const passwordRegExp = /^(?=^.{6,20}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[0-z])(?=.*[0-9])(?=.*[A-Z]).*$/;
    return checkCorresponding(password, passwordRegExp, "wrongPassword");
}

function testPasswords(form) {
    var pass_f = form.passwordFirst.value;
    var pass_s = form.passwordSecond.value;
    const warnLabel = document.getElementById("passwordsNotEquals");
    if (pass_f !== pass_s){
        warnLabel.style.display = "block";
        return false;
    }
    warnLabel.style.display = "none";
    return true;
}

function testName(form) {
    var name = form.firstName.value;
    const nameRegExp = /(?=^.{2,30}$)(?=[A-Za-z ']+)/;
    return checkCorresponding(name, nameRegExp, "name-not-correspond");
}

function testSurname(form) {
    var surname = form.lastName.value;
    const surnameRegExp = /(?=^.{2,30}$)(?=[A-Za-z ']+)/;
    return checkCorresponding(surname, surnameRegExp, "surname-not-correspond");
}

function testTelephoneNumber(form) {
    var telNum = form.telephoneNumber.value;
    const telNumRegExp = /(?=^.{7,20}$)(?=\+.*\d)/;
    return checkCorresponding(telNum, telNumRegExp, "wrong-number-format");
}

function testBirthday(form) {
    var bDay = form.birthday.value;
    const bDayRegExp = /^([0-9]{4})-([0-9]{2})-([0-9]{2})$/;
    return checkCorresponding(bDay, bDayRegExp, "wrong-date-format");
}

function checkCorresponding(inputvalue, regExp, warnLabelId) {
    var warnLabel = document.getElementById(warnLabelId);
    if (!regExp.test(inputvalue)) {
        warnLabel.style.display = "block";
        return false;
    }
    warnLabel.style.display = "none";
    return true;
}