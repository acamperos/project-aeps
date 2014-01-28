
function setPropertyVal(url, nameData, valData, valName, valId, title, width, height) {
    var valAdd = $("#" + valData).val();
    if (valAdd == "") {
        alert("Debe tener un productor seleccionado previamente");
    } else {
        listInfo(url, nameData, valData, valName, valId, title, width, height);
// 			$("#"+fieldFill).val(valAdd);
//       alert(1)
//       $("#"+fieldFill).html(valAdd);

        // $.ajax({
        // type: "POST",
        // url: url,
        // data: "productor="+valAdd,
        // success: function(information) {
        // $('.ajaxgif').hide();
        // var obj = jQuery.parseJSON(information);
        // alert(obj.state);
        // if (obj.state == 'failure') {
        // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
        // } else if (obj.state == 'success') { 
        // }									

        // }
        // });
    }
}

function setLotVal(url, optSel, nameProd, valProd, nameProp, valProp, valName, valId, title, width, height) {
    var valAdd = $("#" + optSel).val();
    // if(valAdd == "") {
    // alert("Complete la opcion 'El lote es'");
    // } else 
// 		if(valAdd == 1) {
    if ($("#" + valProp).val() == "") {
        alert("Debe tener una finca seleccionada previamente");
    } else {
        listInfo(url, nameProp, valProp, valName, valId, title, width, height);
    }
// 		} else {
//       if($("#"+valProd).val() == "") {
//         alert("Debe tener un productor seleccionado previamente");  
//       } else { 
//         listInfo(url, nameProd, valProd, valName, valId, title, width, height);
//       }  
//     }
}

function getListVal(url, nameData, valData, title, width, height) {
    $.ajax({
        type: "POST",
        url: url,
        data: nameData + '=' + valData,
        success: function(information) {
            // $('.ajaxgif').hide();
            var obj = jQuery.parseJSON(information);
            if (obj.state == 'failure') {
                $('#' + message).html(obj.info);
                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
            } else if (obj.state == 'success') {
                // alert(obj.info);
                showWindow(title, width, height, obj.info);
            }
        }
    });
}

function selLot(valSel, divShow) {
//     var valAdd = $("#"+field).val();
    if (valSel == 1) {
        $("#" + divShow).removeClass("hide");
    } else {
        $("#" + divShow).addClass("hide");
    }
}

function setLabelsObj(valSel, labSetObj, labSetNew) {
    if (valSel == 1) {
        $("#" + labSetObj).html("Plaga que mas lo afecta");
        $("#" + labSetNew).html("Nueva plaga que mas lo afecta");
    } else if (valSel == 2) {
        $("#" + labSetObj).html("Maleza que mas lo afecta");
        $("#" + labSetNew).html("Nueva maleza que mas lo afecta");
    } else if (valSel == 3) {
        $("#" + labSetObj).html("Enfermedad que mas lo afecta");
        $("#" + labSetNew).html("Nueva enfermedad que mas lo afecta");
    }
}


function showElement(valSel, divShow) {
    if (valSel != '' && valSel != '1000000') {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showInfoRiego(valSel, divShow) {
    if (valSel == 1) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showSelSemilla(valSel, divShow) {
    if (valSel == 5) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showOtherElement(valSel, divShow) {
    if (valSel == 1000000) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showProductUse(valSel, divShow) {
    if (valSel == true) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showNumCycles(valSel, divShow) {
    if (valSel == 2) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function selConf(valSel, inputId) {
    // $.mask.definitions['h'] = "[A-Fa-f0-9]";
    if (valSel == 'CC') {
        $("#" + inputId).mask("999999?9999", {placeholder: ""});
    } else if (valSel == 'CE') {
        $("#" + inputId).mask("999999?9999", {placeholder: ""});
    } else if (valSel == 'NIT') {
        $("#" + inputId).mask("9999999999", {placeholder: ""});
    } else if (valSel == 'PS') {
        //$("#"+inputId).mask("999999?9999",{placeholder:""});
    } else if (valSel == 'RC') {
        //$("#"+inputId).mask("999999?9999",{placeholder:""});
    }
}


function showControl(valSel, divShowA, divShowB, divShowC) {
    if (valSel == 2) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    } else if (valSel == 1) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
    } else if (valSel == 3) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).show();
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    }
}

function showEnabled(valSel, divShowA, divShowB) {
    // alert($(valSel).val())
    if ($(valSel).prop('checked')) {
        $("#" + divShowA).prop("disabled", false);
        $("#" + divShowB).show();
    } else {
        $("#" + divShowA).prop("disabled", true);
        $("#" + divShowB).hide();
    }
}

function showTipoAbono(valSel, divShowA, divShowB, divShowC) {
    if (valSel == 1) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    } else if (valSel == 2) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
    } else if (valSel == 3) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).show();
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    }
}

function showElementRate(valSel, divShow) {
    if (valSel == 1 || valSel == 4) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showOtherElementPrep(valSel, divShowA, divShowB) {
    if (valSel == 1000000) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
    } else if (valSel >= 1 && valSel <= 5) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
    }
}

function setCropVal(url, field, fieldFill) {
    var valAdd = $("#" + field).val();
    if (valAdd == "") {
        alert("Debe tener un lote seleccionado previamente");
    } else {
        $("#" + fieldFill).val(valAdd);
        // $.ajax({
        // type: "POST",
        // url: url,
        // data: "lot="+valAdd,
        // success: function(information) {
        // $('.ajaxgif').hide();
        // var obj = jQuery.parseJSON(information);
        // alert(obj.state);
        // if (obj.state == 'failure') {
        // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
        // } else if (obj.state == 'success') { 
        // }									

        // }
        // });
    }
}

function showWindow(title, width, height, htmlInfo) {
    $("#dialog-form").html(htmlInfo);
    $("#dialog-form").dialog({
        autoOpen: true,
        title: title,
        height: height,
        width: width,
        modal: true,
        close: function() {
            // allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });
}

function closeWindow() {
    $("#dialog-form").dialog("close");
}

function changeTitleWindow(title) {
    $("#dialog-form").dialog("option", "title", title);
}


function viewInfoRasta(url, nameData, valData, title, width, height) {
    $.ajax({
        type: "POST",
        url: url,
        data: nameData + '=' + valData,
        success: function(information) {
            // $('.ajaxgif').hide();
            var obj = jQuery.parseJSON(information);
            if (obj.state == 'failure') {
                $('#' + message).html(obj.info);
                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
            } else if (obj.state == 'success') {
                // alert(obj.info);
                showWindow(title, width, height, obj.info);
            }

        }
    });
}

function viewForm(url, nameData, valData, title, width, height) {
//    showWindow(title, width, height, '<label>Entreee</label>');
    $.ajax({
        type: "POST",
        url: url,
//        data: nameData + '=' + $("#" + valData).val(),
        data: nameData + '=' + valData,
        success: function(information) {
            // $('.ajaxgif').hide();
//            var obj = jQuery.parseJSON(information);
//            if (obj.state == 'failure') {
//                $('#' + message).html(obj.info);
                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
//            } else if (obj.state == 'success') {
                // alert(obj.info);
                showWindow(title, width, height, information);
//            }
        }
    });
}

function listInfo(url, valName, valId, divShow, divHide) {
    $.ajax({
        type: "POST",
        url: url,
        data: '&valName=' + valName + '&valId=' + valId,
        success: function(information) {
            // $('.ajaxgif').hide();
//            var obj = jQuery.parseJSON(information);
//            if (obj.state == 'failure') {
//                $('#' + message).html(obj.info);
//                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
//            } else if (obj.state == 'success') {
                // alert(obj.info);
                $("#"+divHide).hide();
                $("#"+divShow).show();   
//                $('#'+divShow).html(obj.info);
                $('#'+divShow).html(information);
//            }
        }
    });
}

function setPropertyGeneral(url, nameData, valData, valName, valId, divShow, divHide) {
    var valAdd = $("#" + valData).val();
    if (valAdd == "") {
        alert("Debe tener un productor seleccionado previamente");
    } else {
        $.ajax({
            type: "POST",
            url: url,
            data: nameData + '=' + $("#"+valData).val() + '&valName=' + valName + '&valId=' + valId,
            success: function(information) {
                // $('.ajaxgif').hide();
    //            var obj = jQuery.parseJSON(information);
    //            if (obj.state == 'failure') {
    //                $('#' + message).html(obj.info);
    //                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
    //            } else if (obj.state == 'success') {
    //                // alert(obj.info);
    //                showWindow(title, width, height, obj.info);
    //                // $("#window-productor")
    //                // .button()
    //                // .click(function() {				
    //                // $( "#dialog-form" ).removeClass("hide");
    //                // });                   
    //            }
                $("#"+divHide).hide();
                $("#"+divShow).show();   
    //                $('#'+divShow).html(obj.info);
                $('#'+divShow).html(information);
            }
        });
    }
}


//function listInfo(url, nameData, valData, valName, valId, title, width, height) {
//    // $("#dialog-form").dialog({
//    // // autoOpen: true,
//    // title: title,
//    // height: height,
//    // width: width,
//    // modal: true,
//    // close: function() {
//    // // allFields.val( "" ).removeClass( "ui-state-error" );
//    // }
//    // });
//    // $('.msg').text('Hubo un error!').addClass('alert alert-error').fadeOut(5000);;
//    // alert($("#"+field).val());
//    $.ajax({
//        type: "POST",
//        url: url,
//        data: nameData + '=' + $("#" + valData).val() + '&valName=' + valName + '&valId=' + valId,
//        success: function(information) {
//            // $('.ajaxgif').hide();
//            var obj = jQuery.parseJSON(information);
//            if (obj.state == 'failure') {
//                $('#' + message).html(obj.info);
//                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
//            } else if (obj.state == 'success') {
//                // alert(obj.info);
//                showWindow(title, width, height, obj.info);
//                // $("#window-productor")
//                // .button()
//                // .click(function() {				
//                // $( "#dialog-form" ).removeClass("hide");
//                // });                   
//            }
//
//        }
//    });
//
//    // $("#window-productor")
//    // .button()
//    // .click(function() {				
//    // // $( "#dialog-form" ).removeClass("hide");
//    // // $( "#dialog-form" ).dialog( "open" );
//    // });
//    // $( "#window-productor" ).dialog({
//    // height: 300,
//    // width: 350,
//    // modal: true,
//    // buttons: [
//    // {
//    // text: "OK",
//    // click: function() {
//    // $( this ).dialog( "close" );
//    // }
//    // }
//    // ]
//    // });
//}


function listInfoLot(url, nameData, valData, valName, valId, title, width, height) {
    // $("#dialog-form").dialog({
    // // autoOpen: true,
    // title: title,
    // height: height,
    // width: width,
    // modal: true,
    // close: function() {
    // // allFields.val( "" ).removeClass( "ui-state-error" );
    // }
    // });
    // $('.msg').text('Hubo un error!').addClass('alert alert-error').fadeOut(5000);;
    // alert($("#"+field).val());
    $.ajax({
        type: "POST",
        url: url,
        data: nameData + '=' + $("#" + valData).val() + '&valName=' + valName + '&valId=' + valId,
        success: function(information) {
            // $('.ajaxgif').hide();
            var obj = jQuery.parseJSON(information);
            if (obj.state == 'failure') {
                $('#' + message).html(obj.info);
                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
            } else if (obj.state == 'success') {
                // alert(obj.info);
                showWindow(title, width, height, obj.info);
                // $("#window-productor")
                // .button()
                // .click(function() {        
                // $( "#dialog-form" ).removeClass("hide");
                // });                   
            }

        }
    });
}

function deleteItem(url, tblPrincipal, tblBody, trSel, lblId, messConfirm, divMessage)
{
    if (confirm(messConfirm)) {
        $.ajax({
            type: "POST",
            url: url,
            success: function(information) {
                var obj = jQuery.parseJSON(information);
                if (obj.state == 'failure') {
                    $('#' + divMessage).html(obj.info);
                } else if (obj.state == 'success') {
                    $("#" + trSel).remove();
                    var numChild = $("#" + tblBody).children().size();
                    if (numChild <= 0) {
                        $("#" + tblPrincipal).hide();
                        $("#" + lblId).show();
                    } else {
                        $("#" + tblPrincipal).show();
                        $("#" + lblId).hide();
                    }
                }
            }
        });
    }
}

function viewInfo(url, title, divShow, divHide)
{
    $.ajax({
        type: "POST",
        url: url,
        success: function(information) {
            // $('.ajaxgif').hide();
            var obj = jQuery.parseJSON(information);
            if (obj.state == 'failure') {
                $("#" + message).html(obj.info);
                // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);
            } else if (obj.state == 'success') {
                changeTitleWindow(title);
                $("#" + divShow).html(obj.info);
                $("#" + divShow).show();
                $("#" + divHide).hide();
            }

        }
    });
}


function getInfo(url, valName, valId, divShow, message)
{
    $('#' + message).html('');
    var datos = valName + '=' + $('#' + valId).val();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            var json = jQuery.parseJSON(information);
//         alert(event);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + divShow).html(json.info);
            }

        }
    });
}

function viewInfoCropNew(url, elTiCul, idEvent, divShow, divShowB, message)
{
    $('#' + message).html('');
    var datos = 'tipoCul=' + $('#' + elTiCul).val() + '&idEvent=' + idEvent;
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            var json = jQuery.parseJSON(information);
//         alert(event);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + divShow).removeClass("hide");
                $('#' + divShow).html(json.info);
                $('#' + divShowB).html(json.infoData);
            }

        }
    });
}

function viewInfoCrop(url, elTiCul, idEvent, divShow, message)
{
    $('#' + message).html('');
    var datos = 'tipoCul=' + $('#' + elTiCul).val() + '&idEvent=' + idEvent;
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            var json = jQuery.parseJSON(information);
//         alert(event);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + divShow).removeClass("hide");
                $('#' + divShow).html(json.info);
            }

        }
    });
}

function viewInfoList(url, divShow, message)
{
    // $('#'+divShow).html('<h1>Hola Mundo</h1>');
    $('#' + message).html('');
    // var datos  = 'tipoCul='+$('#'+elTiCul).val()+'&idEvent='+idEvent;
    $.ajax({
        type: "POST",
        url: url,
        // data: datos,
        success: function(information) {
            var json = jQuery.parseJSON(information);
//         alert(event);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + divShow).removeClass("hide");
                $('#' + divShow).html(json.info);
            }

        }
    });
}

function chargeValues(url, valName, valSend, valFill, message)
{
//     alert(11)
    var datos;
    // if (valSend) {
    datos = '&' + valName + '=' + valSend;
    // }
    $('#' + valFill).html('');
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            var json = jQuery.parseJSON(information);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + valFill).html(json.info);
            }

        }
    });
}

function showDialogDelete(divDialog, hRef) {
    $(divDialog).colorbox({
        initialHeight: '0',
        initialWidth: '0',
        href: "#"+hRef,
        inline: true,
        opacity: '0.3',
        onComplete: function(){
            $('.confirm_yes').click(function(e){
                e.preventDefault();
//                $('input[name=row_sel]:checked', oTable.fnGetNodes()).closest('tr').fadeTo(300, 0, function () {
//                    $(this).remove();
//                    oTable.fnDeleteRow( this );
//                    $('.select_rows','#'+tableid).attr('checked',false);
//                });
                $.colorbox.close();
            });
            $('.confirm_no').click(function(e){
                e.preventDefault();
                $.colorbox.close(); 
            });
        }

    });       
}

function changePage(url, valName, valSend, valFill, message)
{
//     alert(11)
    var datos;
    // if (valSend) {
    datos = '&' + valName + '=' + valSend;
    // }
    $('#' + valFill).html('');
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
//            alert(information);
            $('#' + valFill).html(information);
//            var json = jQuery.parseJSON(information);
//            if (json.state == 'failure') {
//                $('#' + message).html(json.info);
//                $('#' + message).focus();
//            } else if (json.state == 'success') {
//                $('#' + valFill).html(json.info);
//            }

        }
    });
}

function chargeValuesMercado(url, valName, valSend, valFill, divShow, message)
{
//     alert(11)
    var datos;
    // if (valSend) {
    datos = '&' + valName + '=' + valSend;
    // }
    $('#' + valFill).html('');
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            // $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + valFill).html(json.info);
                if (valSend != '') {
                    $('#' + divShow).show();
                } else {
                    $('#' + divShow).hide();
                }
            }

        }
    });
}

function showInfoVende(valSel, divShow) {
    if (valSel == 3) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function chargeValuesObjective(url, valName, valSend, valFill, valFillAdd, message)
{
//     alert(11)
    var datos;
    // if (valSend) {
    datos = '&' + valName + '=' + valSend;
    // }
    $('#' + valFill).html('');
    $('#' + valFillAdd).html('');
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            // $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + valFill).html(json.info);
                $('#' + valFillAdd).html(json.info);
            }

        }
    });
}

function selValue(objSel, divShow)
{
    var valSel = $(objSel).val();
    if (valSel == 'NIT') {
        $("#" + divShow).removeClass("hide");
    } else {
        $("#" + divShow).addClass("hide");
    }
}

//function selectItem(namField, idField, valName, valId)
//{
//    $("#" + namField).val(valName);
//    $("#" + namField).focus();
////     $("#"+namField).html(valName);
//    $("#" + idField).val(valId);
//    closeWindow();
//}

function selectItem(namField, idField, valName, valId, divShow, divHide)
{
    $("#" + namField).val(valName);
    $("#" + namField).focus();
//     $("#"+namField).html(valName);
    $("#" + idField).val(valId);
    toggleAndClean(divShow, divHide);
//    closeWindow();
}

function resetForm(formId)
{
    $("#" + formId)[0].reset();
}

function changeValues(optSel, divOne, divSecond)
{

    if (optSel == 1) {
        $("#" + divOne).removeClass("hide");
        $("#" + divSecond).addClass("hide");
    } else if (optSel == 2) {
        $("#" + divOne).addClass("hide");
        $("#" + divSecond).removeClass("hide");
    }

}


function autenticateUser(url, formId, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
//         $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
//         alert(event);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.info);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));          
                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                document.location = json.url;
            }

        }
    });
}

function setTimerToMessage(fade) 
{
    var html  = '<script type="text/javascript">';
        html += "  $(\".s2_validation_errors\").fadeOut("+(fade*1000)+");";
        html += '</script>';
    return html;    
}

function completeForm(dialogId, formId, information) 
{
//    bootstrapValidation(form, errors);
//    form.find("div.alert-info").remove();
    

    //Handle non field errors
//    if (errors.info && errors.errors.length > 0) {
//        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.errors, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//    }
//    alert(123456)    
    var json = jQuery.parseJSON(information);
    if (dialogId!='') {
        $('#'+dialogId).find("div.alert-info").remove();
    }    
    // alert(message);
//    if (json.state == 'failure') {
//        $('#' + message).html(json.info);
//        $('#' + message).focus();
//    } else if (json.state == 'success') {
//        $('#' + message).html(json.info);
//        $('#' + message).focus();
//        $("#" + formId)[0].reset();
//    }       
    if (json.state == 'failure') {
        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
        $('#'+formId).prepend(errorDiv);
        errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
        errorDiv.append('<p>' + json.info + '</p>\n');
//        $.each(json.infoR, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        }); // Estructura de arreglo
        $(errorDiv).focus();        
        $('#'+formId).append(setTimerToMessage(4));
    } else if (json.state == 'success') {
        var errorDiv = $("<div class='alert alert-info'></div>");
        $('#'+formId).prepend(errorDiv);
        errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
        errorDiv.append('<p>' + json.info + '</p>\n');
        $('#'+formId).append(setTimerToMessage(4));
        $('#'+formId)[0].reset();
    }
}

function validationForm(form, errors) 
{
//    bootstrapValidation(form, errors);
    form.find("div.error").removeClass("error");
    form.find("span.s2_help_inline").remove();
    form.find("div.s2_validation_errors").remove();

    //Handle non field errors
    if (errors.errors && errors.errors.length > 0) {
        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
        form.prepend(errorDiv);
        errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
        $.each(errors.errors, function(index, value) {
            errorDiv.append('<p>' + value + '</p>\n');
        });
        form.append(setTimerToMessage(4));
    }

    //Handle field errors
    if (errors.fieldErrors) {
        $.each(errors.fieldErrors, function(index, value) {
            var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
            if (element && element.length > 0) {
                // select first element
                    element  = $(element[0]);
                controlGroup = element.closest("div.control-group");
                controlGroup.addClass('error');
                controls = controlGroup.find("div.controls");
                if (controls) {
                    controls.append("<span class='help-inline s2_help_inline'>" + value[0] + "</span>");
                }
            }
        });
    }
//    form.find("div.alert-info").remove();
    

    //Handle non field errors
//    if (errors.info && errors.errors.length > 0) {
//        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.errors, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//    }
//    if (errors.state == 'failure') {
//        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.info, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//        $(errorDiv).focus();
//    } else if (errors.state == 'success') {
//        var errorDiv = $("<div class='alert alert-info s2_validation_info'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.info, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//        $(form)[0].reset();
//    }
}


function sendForm(url, formId, message)
{
    // $(".bt_send").click(function() {		
    // var nombre = $(".nombre").val();
    // email = $(".email").val();
    // // validacion_email = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;
    // telefono = $(".telefono").val();
    // mensaje = $(".mensaje").val();

    // $(".error").remove();
    // if( $(".nombre").val() == "" ) {
    // $(".nombre").focus().after("<span class='error'>Ingrese su nombre</span>");
    // return false;
    // } else if( $(".email").val() == "" || !emailreg.test($(".email").val())) {
    // $(".email").focus().after("<span class='error'>Ingrese un email correcto</span>");
    // return false;
    // } else if( $(".asunto").val() == "") {
    // $(".asunto").focus().after("<span class='error'>Ingrese un asunto</span>");
    // return false;
    // } else if( $(".mensaje").val() == "" ) {
    // $(".mensaje").focus().after("<span class='error'>Ingrese un mensaje</span>");
    // return false;
    // } else {
    // $('.ajaxgif').removeClass('hide');
    // var datos  = $('.'+formClass).serializeArray();
    // $('#'+message).text('');
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    // var fieldErrors = $('#'+formId).getElementsByClassName('error');
    // for (var i=0, lenErrors=fieldErrors.length; i<lenErrors; i++) {
    // // $(fieldErrors[0]).removeClassName('error');
    // alert(fieldErrors[i])
    // // $(fieldErrors[i]).removeClass('error');
    // }
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    // $('.msg').text('Hubo un error!').addClass('msg_error').animate({ 'right' : '130px' }, 300);
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
                $("#" + formId)[0].reset();
            }

        }
        // , error: function() {
        // $('.ajaxgif').hide();
        // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);	
        // }
    });
    // alert(1)
    // return false;
    // }
    // });
}

function sendFormRasta(url, formId, divTable, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                window.parent.$('#' + divTable).html(json.infoTable);
                window.parent.closeWindow();
                // $('#'+message).html(json.info);
                // $('#'+message).focus();
                // $("#"+formId)[0].reset();									
            }

        }
        // , error: function() {
        // $('.ajaxgif').hide();
        // $('.msg').text('Error').addClass('msg_error').fadeOut(1000);	
        // }
    });
}

function copyYPaste()
{
    window.parent.$('#divDataRastas').html('<tr><td>5</td><td>2010-05-30</td><td>23</td><td>4</td><td>8</td><td>25</td></tr>');
    window.parent.$('#divDataRastas').append('<tr><td>5</td><td>2010-05-30</td><td>23</td><td>4</td><td>8</td><td>25</td></tr>');

}


function sendFormProtection(url, formId, divHide, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    // $('.msg').text('Hubo un error!').addClass('msg_error').animate({ 'right' : '130px' }, 300);
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
                $('.' + divHide).hide();
                $("#" + formId)[0].reset();
            }

        }
    });
}

function toggleAndClean(divShow, divHide)
{
  $('#'+divHide).hide();
  $('#'+divShow).show();
  $('#'+divHide).html('');
}


function sendFormCrop(url, formId, divHide, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
                $('#' + divHide).hide();
            }

        }
    });
}

function sendFormHarvest(url, formId, divShow, divHide, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
                $('#' + divShow).show();
                $('#' + divHide).hide();
            }

        }
    });
}

function sendFormHarvestChange(url, formId, divShowA, divShowB, divHide, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var datos = $('#' + formId).serializeArray();
    // $('.error').fadeOut();
    $.ajax({
        type: "POST",
        url: url,
        data: datos,
        success: function(information) {
            $('.ajaxgif').hide();
            var json = jQuery.parseJSON(information);
            // alert(message);
            if (json.state == 'invalid') {
                var fieldsFail = json.fails;
                $('#' + message).html(json.text);
                // var fieldsFail = jQuery.parseJSON(json.fails);
                // alert(fieldsFail.split(','));

                for (var i = 0, lenFails = fieldsFail.length; i < lenFails; i++) {
                    // $(fieldsFail[i]).up("div").addClassName('error');
                    $('#' + fieldsFail[i]).closest("div").addClass('control-group error');
                    // $('#'+fieldsFail[i]).offsetParent().toggleClass('control-group error');
                    // alert($(fieldsFail[i]).closest("div"));
                }
                $('#' + message).focus();
            } else if (json.state == 'failure') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + message).html(json.info);
                $('#' + message).focus();
                $('#' + divShowA).show();
                $('#' + divShowB).show();
                $('#' + divHide).hide();
            }

        }
    });
}

function activeOption(ulId) 
{
    $('#'+ulId).find("li.active").removeClass("active");
    $(document.activeElement).parent('li').addClass("active");
//    $('#'+ulId+' li').addClass(function(index, currentClass ) {
//        var addedClass;
//        alert(currentClass)
//        if (currentClass === "homeCls") {
//          addedClass = "active";
//        } else if (currentClass === "aboutCls") {
//          addedClass = "active";
//        } else if (currentClass === "contactCls") {
//          addedClass = "active";
//        } 
//        return addedClass;
//    });
    
}