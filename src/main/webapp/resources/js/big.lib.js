function testClick() {
    alert("Hello!!!");
}
function clickButtonDivision() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if (lastChar == "" || lastChar == "," || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {

    } else {
        display.textContent += "/";
    }
}
function clickButtonSum() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if (lastChar == "" || lastChar == "," || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {

    } else {
        display.textContent += "+";
    }
}
function clickButtonSubtract() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if (lastChar == "" || lastChar == "," || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {

    } else {
        display.textContent += "-";
    }
}
function clickButtonMultiply() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if (lastChar == "" || lastChar == "," || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {

    } else {
        display.textContent += "*";
    }
}
function clickButtonComa() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if (validateComa(textdisplay)) {
        return;
    }

    if (!isNaN(lastChar) && lastChar != "") {
        display.textContent += ".";
    }
    if (lastChar == "" || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {
        display.textContent += "0.";
    }
}
function validateComa(str) {
    var matchRe = /[0-9]+[\.][0-9]+$/;
//    if (matchRe.test(str)) {
//        alert("true")
//    }
    return matchRe.test(str);
}
function clickButton0() {
    var display = document.getElementById("display");
    var textdisplay = display.textContent;
    var lastChar = textdisplay.charAt(textdisplay.length - 1);
    if ((lastChar == "0" && textdisplay.length == 1) || lastChar == "/" || lastChar == "*" || lastChar == "-" || lastChar == "+") {
    } else {
        display.textContent += 0;
    }
}
function clickButton1() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "1";
        return;
    }
    display.textContent += 1;
}
function clickButton2() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "2";
        return;
    }
    display.textContent += 2;
}
function clickButton3() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "3";
        return;
    }
    display.textContent += 3;
}
function clickButton4() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "4";
        return;
    }
    display.textContent += 4;
}
function clickButton5() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "5";
        return;
    }
    display.textContent += 5;
}
function clickButton6() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "6";
        return;
    }
    display.textContent += 6;
}
function clickButton7() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "7";
        return;
    }
    display.textContent += 7;
}
function clickButton8() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "8";
        return;
    }
    display.textContent += 8;
}
function clickButton9() {
    var display = document.getElementById("display");
    if (display.textContent == "0" && display.textContent.length == 1) {
        display.textContent = "9";
        return;
    }
    display.textContent += 9;
}
function clickButtonC() {
    var display = document.getElementById("display");
    display.textContent = "0";
}
function clickButtonResult() {
    var display = document.getElementById("display");

    $.ajax({url: "result",
        data: {exp: display.textContent},
        
        success: function (data) {
            display.textContent = data;
        }});
}

window.onload = function () {

};