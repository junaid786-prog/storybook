<form id="Signin_form" action="signup" method="post">
    <h2>SIGN UP</h2>
    <div class="input_combo">
        <p id="f_error" class="input_error"></p>
        <p>Enter First Name</p>
        <input id="f_name" type="text" name="fname" placeholder="i.e junaid" required> </input>
    </div>
    <div class="input_combo">
        <p id="s_error" class="input_error"></p>
        <p>Enter Second Name</p>
        <input id="s_name" type="text" name="lname" placeholder="i.e khan" required> </input>
    </div>
    <div class="input_combo">
        <p id="g__error" class="input_error"></p>
        <p>Enter Gmail</p>
        <input id="gmail" type="email" name="gmail" placeholder="i.e junaid" required> </input>
    </div>
    <div class="input_combo">
        <p id="p__error" class="input_error"></p>
        <p>Enter Password</p>
        <input id="password" type="password" name="password" required> </input>
    </div>
    <div class="input_combo">
        <p id="c__error" class="input_error"></p>
        <p>Enter Confirm Password</p>
        <input id="cPassword" type="password" name="cpassword" required> </input>
    </div>
    <div class="button_wrapper">
        <button type="submit" id="signup_btn">Sign Up</button>
    </div>
    <div class="bottom_register">
        <p>If you already have an account. <a href="Signin.jsp">Sign in here</a></p>
    </div>
</form>