$(document).ready(function () {
    var term;
    $(function () {
        $("#companyName").autocomplete(
            {
                source: function (request, response) {
                    $.ajax({
                        url: "/company",
                        delay: 500,
                        dataType: "json",
                        data: {
                            term: request.term,

                        },
                        success: function (data) {
                            response($.map(data, function (item) {
                                return {
                                    label: item.companyName,
                                    value: item.companyId
                                }
                            }));
                        }
                    });
                },
                minLength: 0,
                focus: function (event, ui) {
                    $('#companyName').val(ui.item.companyName);
                    return false;
                },

                select: function (event, ui) {
                    event.preventDefault();
                    $(this).val(ui.item.label);
                    $('#id').val(ui.item.value);
                }
            });
    });

    $(function () {
        $("#location").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "/company/" + $("#id").val(),
                    delay: 500,
                    dataType: "json",
                    data: {
                        term: request.term,
                    },
                    success: function (data) {
                        console.log($("#id").val());
                        response($.map(data, function (item) {

                            return {
                                label: item.location,
                                value: item.address,
                                id: item.email
                            }
                        }));
                    }
                });
            },
            minLength: 0,
            focus: function (event, ui) {
                $('#location').val(ui.item.label);
                return false;
            },

            select: function (event, ui) {
                event.preventDefault();
                $(this).val(ui.item.label)
                $('#address').val(ui.item.value)
                $('#email').val(ui.item.id)

            }
        });
    });

    $("#companyName").focus(function (e) {
        $('#location').val("");
        $('#address').val("");
        $('#email').val("");
    });

    $(function () {
        $(".companyName").autocomplete(
            {
                source: function (request, response) {
                    $.ajax({
                        url: "/company",
                        delay: 500,
                        dataType: "json",
                        data: {
                            term: request.term,

                        },
                        success: function (data) {
                            response($.map(data, function (item) {
                                return {
                                    label: item.companyName,
                                    value: item.companyId
                                }
                            }));
                        }
                    });
                },
                minLength: 0,
                focus: function (event, ui) {
                    $('.companyName').val(ui.item.companyName);
                    return false;
                },

                select: function (event, ui) {
                    event.preventDefault();
                    $(this).val(ui.item.label);
                    $('.id').val(ui.item.value);
                }
            });
    });

    $(function () {
        $(".location").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: "/company/" + $(".id").val(),
                    delay: 500,
                    dataType: "json",
                    data: {
                        term: request.term,
                    },
                    success: function (data) {
                        console.log($(".id").val());
                        response($.map(data, function (item) {

                            return {
                                label: item.location,
                                value: item.address,
                                id: item.email
                            }
                        }));
                    }
                });
            },
            minLength: 0,
            focus: function (event, ui) {
                $('.location').val(ui.item.label);
                return false;
            },

            select: function (event, ui) {
                event.preventDefault();
                $(this).val(ui.item.label)
                $('.address').val(ui.item.value)
                $('.email').val(ui.item.id)

            }
        });
    });

    $('.companyName').focus(function (e) {
        $('.location').val("");
        $('.address').val("");
        $('.email').val("");
    });

});
