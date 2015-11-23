$(document).ready(function () {
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $(".button-collapse").sideNav();
    $('.modal-trigger').leanModal();

    $(function () {
        $("input:file").change(function () {
            var form = $(this).closest("form")[0];
            var formData = new FormData(form);

            $.ajax({
                url: '/load',
                type: 'POST',
                data: formData,
                cache: false,
                processData: false, // Don't process the files
                contentType: false, // Set content type to false as jQuery will tell the server its a query string request
                success: function (data, textStatus, jqXHR) {
                    window.location = "/scenario"
                }
            });
        });
    })
    $('select').material_select();
});