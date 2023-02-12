function validateEmail(input) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (!re.test(input.value)) {
        g__error.innerText = "Please enter a valid email address"
        signupbtn.disabled = true;
        return false;
    } else {
        g__error.innerText = "";
        signupbtn.disabled = false;
        return true;
    }
}

function validatePasswords(input1, input2) {
    if (input1 !== input2) {
        c__error.innerText = "Passwords do not match";
        signupbtn.disabled = true;
        return false;
    } else {
        c__error.innerText ="";
        signupbtn.disabled = false;
        return true;
    }
}

function validatePassword(input) {
    var password = input.value;
    var pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$/;
    if (!pattern.test(password)) {
        p__error.innerText = "Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, one number, and one special character.";
        signupbtn.disabled = true;
        return false;
    } else {
        p__error.innerText = "";
        signupbtn.disabled = false;
        return true;
    }
}

function validateName(name, input){
    if(name.length <= 3){
        input.innerText = "Should be greater than 3 chars";
        signupbtn.disabled = true;
    }
    else{
        input.innerText = "";
        signupbtn.disabled = false;
    }
}
// Get the Signin form element
var signin_form = document.getElementById("signin_form");

let gmail_login = document.getElementById("gmail_");
let password_login = document.getElementById("password_");

// error fields
let g_error = document.getElementById("g_error");
let p_error = document.getElementById("p_error");


// gmail_login.addEventListener("blur", ()=>{
//     validateEmail(gmail_login)
// })

// password_login.addEventListener("blur", ()=>{
//     validatePassword(password_login)
// })

// Listen for the form's submit event
// signin_form.addEventListener("submit", function(event) {
//     let data = {
//         gmail: gmail_login.value,
//         password: password_login.value
//     }
//   event.preventDefault();
//   console.log(event)
//   var formData = new FormData(signin_form);
//   console.log(gmail_login.value)
//   formData.append("gmail", gmail_login.value);
//   formData.append("password", password_login.value);
//   console.log(formData)
//   // Use the fetch() method to send the data to the server
//   fetch("signin", {
//     method: "POST",
//     body: data
//   })
//   .then(response => response.text())
//   .then(data => {
//     console.log(data);
//   });
// });

// ====== signup ======= //
var signin_form = document.getElementById("signin_form");

let gmail = document.getElementById("gmail");
let password = document.getElementById("password");
let fname = document.getElementById("f_name");
let sname = document.getElementById("s_name");
let cpassword= document.getElementById("cPassword");
let signupbtn = document.getElementById("signup_btn")


// error fields
let g__error = document.getElementById("g__error");
let p__error = document.getElementById("p__error");
let c__error = document.getElementById("c__error");
let f__error = document.getElementById("f_error");
let s__error = document.getElementById("s_error");

gmail.addEventListener("blur", ()=>{
    validateEmail(gmail)
})

password.addEventListener("blur", ()=>{
    validatePassword(password)
})

cpassword.addEventListener("blur", ()=>{
    validatePasswords(password.value, cpassword.value)
})

fname.addEventListener("blur", ()=>{
    console.log(fname.value)
    validateName(fname.value, f__error);
})

sname.addEventListener("blur", ()=>{
    validateName(sname.value, s__error);
})