$(function(){
//====================================================================================================================================================================================
    // validation
    $('.delete_confirmed').on('click', function () {
        $('.form-delete').submit();
    });

    $.validator.addMethod( "alphanumeric", function( value, element ) {
        return this.optional( element ) || /^\w+$/i.test( value );
    }, "Letters, numbers, and underscores only please" );

    $.validator.addMethod( "lettersonly", function( value, element ) {
        return this.optional( element ) || /^[a-z]+$/i.test( value );
    }, "Letters only please" );

    $(".submit").on('click', function (e) {
        e.preventDefault();

        if($(".passwd").val() == $(".cpasswd").val()){
            e.submit();
        }else {
            alert("Password field must match the confirmed password field.");
        }
    });
//====================================================================================================================================================================================
    //timeline

    $(function () {
        $('.timeline').hide();
    });

    $('.timelineBtn').on('click', function (e) {
        $('.timeline').toggle();
        $('.from').removeAttr("readonly");
        $('.from').addClass('from');
    });

    $('.extendBtn').on('click', function (e) {
        $('.timeline').toggle();
        var currentDate = $('.currentDate').text();
        console.log(currentDate);
        // $('.fromRow').remove();
        $('.from').replaceWith($("<input>", {
            type: 'text',
            name : 'from',
            value: currentDate,
            readonly: true,
            class : 'form-control from'
        }));
        $('.from').attr("readonly");


    });

    $(function () {

        var dateFormat = 'mm/dd/yy',
            from = $('.from').datepicker({
                minDate : 0,
                maxDate : '+1M',
                changeMonth : true,
                numberOfMonth : 3
            })
            .on('change', function () {
                to.datepicker("option", 'minDate', getDate(this));
            }),
            to = $('.to').datepicker({
                defaultDate : '+1w',
                changeMonth : true,
                numberOfMonth : 3
            })
                .on('focus', function () {
                    from.datepicker('option', 'maxDate', getDate(this));
                });
        function getDate(element) {
            var date;
            try {
                date = $.datepicker.parseDate(dateFormat, element.value)
            }catch (error){
                date = null;
            }

            return date;
        }
    });

//====================================================================================================================================================================================
    // triggering modals
    $('.ebtn').on('click', function(e){
        e.preventDefault();
        var href = $(this).attr('href');
        console.log(href);
        var text = $(this).text();
        console.log(text);

        if(text == "Edit"){
            $.get(href, function(app, status){
                console.log("get executed");
                console.log(app);
                $('#appId').val(app.applicationId);
                $('#comName').val(app.companyApplied.company.companyName);
                $('#townId').val(app.companyApplied.townId);
                $('#region').val(app.companyApplied.region);
                $('#loc').val(app.companyApplied.location);
                $('#address').val(app.companyApplied.address);
                $('#email').val(app.companyApplied.email);
            });

            $('#editModal').modal({keyboard: false});

        }else if(text == "Delete"){
            $('.delRef').attr('href', href);
            $('#deleteModal').modal({keyboard: false});

        }
    });

//====================================================================================================================================================================================
    //registering events
    $('form').submit(function (event) {
        $(".alert").html("").hide();
        $(".error-list").html("");
    });

//====================================================================================================================================================================================
    // updating department options
    $('.fac').change(function () {
        var fac = $('.fac').val();
        var depHref = "/getDepartments?id="+fac;
        console.log(depHref);
        $.get(depHref, function (data, success) {
            console.log(data);
            $('.updateDepartmentOptions').empty();
            var i;
            for(i=0; i<data.length; i++){
                $('.updateDepartmentOptions').append($('<option>',
                    {
                        value: data[i].id,
                        text: data[i].departmentName
                    }));
            }
        });
    });

//====================================================================================================================================================================================
    //loading departments
    $(function(){
        var fac = $('.fac').val();
        var depHref = "/getDepartments?id="+fac;
        console.log(depHref);
        $.get(depHref, function (data, success) {
            console.log(data);
            $('.updateDepartmentOptions').empty();
            var i;
            for(i=0; i<data.length; i++){
                $('.updateDepartmentOptions').append($('<option>',
                    {
                        value: data[i].id,
                        text: data[i].departmentName
                    }));
            };
        });
    });

//====================================================================================================================================================================================
    //validation for company registration
    $('myform').validate({
        rules :{
            comNAme : {
                required: true,
                minlength : 3,
                lettersonly : true
            },
            loc : {
                required: true,
                minlength : 3,
                lettersonly : true
            },
            address:{
                required: true,
                minlength : 3,
                alphanumeric : true
            },
            email : {
                required: true,
                email : true
            }
        },
        message : {
            comName : {
                required : "company name cannot be null",
                minlength : "company name should be more than three letter",
                lettersonly : 'only letters allowed'
            },
            loc : {
                required : "location cannot be null",
                minlength : "location should be more than three letter",
                lettersonly : 'only letters allowed'
            },
            address : {
                required : "address cannot be null",
                minlength : "address should be more than three letter",
                alphanumeric : 'only letters allowed, numbers and underscore allowed'
            },
            email : {
                required : "email cannot be null",
                email : "Enter a valid email"
            },
        }
    });

//====================================================================================================================================================================================
    //go back to previous page
    $('.back').click(function (e) {
        e.preventDefault();
        history.go(-1);
    });

//====================================================================================================================================================================================
    //getStudent for a particular application
    $('.getStudent').on('click', function (e) {
       e.preventDefault();

       var href = $(this).attr('href');
       console.log(href);

        $.get(href, function (data, success) {
            console.log(data);
            $('.showApps').empty();
            if($.isEmptyObject(data)){
                $('.showApps').append("<tr>").append("<td colspan='6'>No applications has been made by this student</td>");
            }else{
                var i;
                for(i=0; i<data.length; i++){
                    console.log(data[i]);
                    if(data[i].coordinatorApproval == "PENDING"){
                        $('.showApps').append("<tr>").append("<td>"+(i+1)+"</td>" +
                            "<td>"+data[i].companyApplied.company.companyName+"</td>" +
                            "<td>"+data[i].companyApplied.region+"</td>" +
                            "<td>"+data[i].companyApplied.location+"</td>" +
                            "<td>"+data[i].coordinatorApproval+"</td>" +
                            "<td>" +
                            "<a class='btn btn-sm btn-primary' href='/staff/approve?appId="+data[i].applicationId+"' name='appId' value='"+data[i].applicationId+"'>Approve</a>" +
                            " | <a class='btn btn-sm btn-primary disapprove' href='/staff/disapprove?appId="+data[i].applicationId+"' >Disapprove</a>"+
                            "</td>");
                        $('#appId').val(data[i].applicationId);
                        $('.disapprove').on('click',function (e) {
                            e.preventDefault();
                            var href = $(this).attr('href');
                            console.log(href);
                            $('#diapprovalModal').modal({keyboard: false});
                        });
                    }else{
                        $('.showApps').append("<tr>").append("<td>"+(i+1)+"</td>" +
                            "<td>"+data[i].companyApplied.company.companyName+"</td>" +
                            "<td>"+data[i].companyApplied.region+"</td>" +
                            "<td>"+data[i].companyApplied.location+"</td>" +
                            "<td>"+data[i].coordinatorApproval+"</td>" +
                            "<td>Previous Application</td>");
                    }
                }
            };
        });
    });

//====================================================================================================================================================================================
//date filtering for bugs

    $(function () {

        var dateFormat = 'mm/dd/yy',
            bugsFrom = $('.bugsFrom').datepicker({
                changeMonth : true,
                numberOfMonth : 2
            })
                .on('change', function () {
                    bugsTo.datepicker("option", 'minDate', getDate(this));
                }),
            bugsTo = $('.bugsTo').datepicker({
                changeMonth : true,
                numberOfMonth : 3
            })
                .on('change', function () {
                    bugsFrom.datepicker('option', 'maxDate', getDate(this));
                });
        function getDate(element) {
            var date;
            try {
                date = $.datepicker.parseDate(dateFormat, element.value)
            }catch (error){
                date = null;
            }

            return date;
        }
    });
});

//====================================================================================================================================================================================
