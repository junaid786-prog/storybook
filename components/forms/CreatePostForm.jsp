<form id="create_post_form" action="create" method="post" enctype='multipart/form-data'>
    <h1>Create New Post</h1>
    <div class="input_combo">
        <p id="t_error" class="input_error"></p>
        <p>Enter Title</p>
        <input id="post_title" type="text" name="postTitle" placeholder="spreading happiness is favour for humanity..."
            required> </input>
    </div>
    <div class="input_combo">
        <p id="s_error" class="input_error"></p>
        <p>Write Something</p>
        <textarea id="post_description" type="text" name="postDescription" placeholder="i.e khan" required> </textarea>
    </div>
    <div class="input_combo">
        <p id="c__error" class="input_error"></p>
        <p>Upload Picture</p>
        <input id="image" type="file" name="image" required accept="image/*"> </input>
    </div>
    <div class="button_wrapper">
        <button type="submit" id="create_post_btn" name = "action" value = "create_post">Upload Post</button>
    </div>
</form>