$(document).ready(function(){
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $(".button-collapse").sideNav();
    $('.modal-trigger').leanModal();

    $(function() {
        $("input:file").change(function (){
            alert("Tdodo, upload file")
        });
        $('select').material_select();
    });
});