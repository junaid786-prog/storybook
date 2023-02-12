<form id="signin_form" action="signin" method="post">
    <h2>SIGN IN</h2>
    <div class="input_combo">
        <p id="g_error" class="input_error"></p>
        <p>Enter Gmail</p>
        <input id="gmail_" type="email" name="gmail" placeholder="i.e junaid@gmail.com" required> </input>
    </div>
    <div class="input_combo">
        <p id="p_error" class="input_error"></p>
        <p>Enter Password</p>
        <input id="password_" type="password" name="password" required> </input>
    </div>
    <div class="button_wrapper">
        <button type="submit">Sign In</button>
    </div>
    <div class="bottom_register">
        <p>If you have no account. <a href="Signup.jsp">Sign up here</a></p>
    </div>
</form>