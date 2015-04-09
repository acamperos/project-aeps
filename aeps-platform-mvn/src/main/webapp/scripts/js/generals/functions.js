
var countryCode;
function showOtherElement(valSel, divShow) {
    if (valSel == 1000000) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showApplicationProduct(idAppTyp, idSel, divShowApp, divShowOther, divShowPer) {
    var valAppTyp = $('#'+idAppTyp).val();
    if (valAppTyp==1) {
        $("#"+divShowApp).show();
        $("#"+divShowOther).hide().removeClass("hide");
        $("#"+divShowPer).show();
    } else if (valAppTyp==2) {
        $("#"+divShowApp).hide().removeClass("hide");
        $("#"+divShowOther).show();
        $("#"+divShowPer).hide().removeClass("hide");
        $('#'+idSel).val(1000000);
    } else {
        $("#"+divShowApp).hide().removeClass("hide");
        $("#"+divShowOther).hide().removeClass("hide");
        $("#"+divShowPer).hide().removeClass("hide");
    }
}

function showOtherElementChemical(idSel, idAppTyp, divShow) {
    var valSel = $('#'+idSel).val();
    var valAppTyp = $('#'+idAppTyp).val();
    if (valAppTyp==2) {
        
    } else if (valSel == 1000000 && valAppTyp==1) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showProductUse(valSel, divShow) {
    if (valSel=='true') {
        $("#" + divShow).show();
        $("#" + divShow).removeClass("hide");
    } else {
        $("#" + divShow).hide();
        $("#" + divShow).addClass("hide");
    }
}

function showSelectionRasta(valSel, divShow) {
    if (valSel=='true') {
        $("#" + divShow).show();
        $("#" + divShow).removeClass("hide");
    } else {
        $("#" + divShow).hide();
        $("#" + divShow).addClass("hide");
    }
}

function showOtherElementCarbonato(valSel, divShow) {
    if (valSel == 'no tiene' || valSel == -1) {
        $("#" + divShow).hide();
    } else {
        $("#" + divShow).show();
    }
}

function showOtherElementWorkType(valSel, divShow) {
    if (valSel == 3 || valSel == 4 || valSel == 5) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showOtherElementUser(valSel, divShowAg, divShowAss) {
    if (valSel == 1) {
        $("#" + divShowAg).show();
        $("#" + divShowAss).hide();
    } else if (valSel == 3) {
        $("#" + divShowAss).show();
        $("#" + divShowAg).hide();
    } else {
        $("#" + divShowAg).hide();
        $("#" + divShowAss).hide();
    }
}


function selConf(valSel, inputId) {
    // $.mask.definitions['h'] = "[A-Fa-f0-9]";
    if (valSel == 'CC') {
        $("#" + inputId).mask("999999?9999", {placeholder: ""});
    } else if (valSel == 'CE') {
        $("#" + inputId).mask("999999?9999", {placeholder: ""});
    } else if (valSel == 'NIT') {
        $("#" + inputId).mask("999999999?9", {placeholder: ""});
    } else if (valSel == 'PS') {
        //$("#"+inputId).mask("999999?9999",{placeholder:""});
    } else if (valSel == 'RC') {
        //$("#"+inputId).mask("999999?9999",{placeholder:""});
    }
}

function showTypeFertilizerSel(valSel, divShowA, divShowB, divShowC) {
    var valIng = $("#"+valSel).val();
    if (valIng == 1) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    } else if (valIng == 2) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
    } else if (valIng == 3) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).show();
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
    }
}

function showTypeFertilizerControl(valSel, divShowA, divShowB, divShowC, divShowD, divShowE) {
    var valIng = $("#"+valSel).val();
    if (valIng == 1) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 2) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 3) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).show();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 4) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).show();
        $("#" + divShowE).hide();
    } else if (valIng == 5) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).show();
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    }
}


function showElementRate(valSel, divShow) {
//    if (valSel == 1 || valSel == 4) {
    if (valSel == 1) {
        $("#" + divShow).show();
    } else {
        $("#" + divShow).hide();
    }
}

function showOtherElementPrep(valSel, divShowA, divShowB, lblDepth) {
    if (valSel == 1000000) {
        $("#" + divShowA).show();
        $("#" + divShowB).show();
        $("#" + lblDepth).addClass("req");
    } else if ((valSel >= 1 && valSel <= 5) || (valSel >= 12 && valSel <= 16)) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        if ((valSel >= 12 && valSel <= 16)) {
            $("#" + lblDepth).removeClass("req");
        } else {
            $("#" + lblDepth).addClass("req");
        }
    } else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
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


function viewForm(url, nameData, valData, title, width, height) {
//    showWindow(title, width, height, '<label>Entreee</label>');
    $.ajax({
        type: "GET",
        url: url,
//        data: nameData + '=' + $("#" + valData).val(),
        data: nameData + '=' + valData,
        success: function(information) {
//            var obj = jQuery.parseJSON(information);
//            if (obj.state == 'failure') {
//                $('#' + message).html(obj.info);
//            } else if (obj.state == 'success') {
                // alert(obj.info);
                showWindow(title, width, height, information);
//            }
        }
    });
}

function listInfo(url, valName, valId, divShow, divHide) {
    $.ajax({
        type: "GET",
        url: url,
        data: '&valName=' + valName + '&valId=' + valId,
        success: function(information) {
//            var obj = jQuery.parseJSON(information);
//            if (obj.state == 'failure') {
//                $('#' + message).html(obj.info);
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
//    var valAdd = $("#" + valData).val();
//    if (valAdd == "") {
//        alert("Debe tener un productor seleccionado previamente");
//    } else {
        $.ajax({
            type: "POST",
            url: url,
            data: nameData + '=' + $("#"+valData).val() + '&valName=' + valName + '&valId=' + valId,
            success: function(information) {
    //            var obj = jQuery.parseJSON(information);
    //            if (obj.state == 'failure') {
    //                $('#' + message).html(obj.info);
    //            } else if (obj.state == 'success') {
    //                // alert(obj.info);
    //                showWindow(title, width, height, obj.info);
    //                // $("#window-productor")
    //                // $( "#dialog-form" ).removeClass("hide");
    //                // });                   
    //            }
                $("#"+divHide).hide();
                $("#"+divShow).show();   
    //                $('#'+divShow).html(obj.info);
                $('#'+divShow).html(information);
            }
        });
//    }
}


var requestSent = false;
function deleteItem(url, urlAction, divTable, divShow)
{
    $('#'+divTable).find("div.alert-success").remove();
    $('#'+divTable).find("div.error").removeClass("error");
    $('#'+divTable).find("span.s2_help_inline").remove();
    $('#'+divTable).find("div.s2_validation_errors").remove();
    if(!requestSent) {
        requestSent = true;
        $.ajax({
            type: "POST",
            url: url,
            complete: function() {
                requestSent = false;
            },
            success: function(obj) {
    //            var obj = jQuery.parseJSON(information);
                if (obj.state == 'failure') {
                    showMessError(divTable,obj.info);       
                } else if (obj.state == 'success') {
                    showMessInfo(divTable, obj.info);
                    setTimeout( function() {
                        showInfo(urlAction, divShow);
//                        if(requestSent) $.ajax().abort();
                    }, 3000);
//                    showInfo(urlAction, divShow);
    //                    $("#" + trSel).remove();
    //                    var numChild = $("#" + tblBody).children().size();
    //                    if (numChild <= 0) {
    //                        $("#" + tblPrincipal).hide();
    //                        $("#" + lblId).show();
    //                    } else {
    //                        $("#" + tblPrincipal).show();
    //                        $("#" + lblId).hide();
    //                    }
                }
            }
        });        
    }
}

function showMessInfo(idLoc, info)
{
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es') {
        titleMess = "Mensaje Exitoso";
    }
    
    if(navigator.language=='en-EN' || navigator.language=='en') {
        titleMess = "Success Message";
    }
    var infoDiv = $("<div class='alert alert-success messageAlerts'>");
    infoDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
    infoDiv.append('<i class=\"icon-info-sign\"></i> <strong>'+titleMess+'</strong>');
    infoDiv.append('<hr class=\"message-inner-separator\"></div>');
    $('#'+idLoc).prepend(infoDiv);    
    infoDiv.append('<p>' + info + '</p>\n');    
    $('#'+idLoc).focus();
    $('#'+idLoc).append(setTimerToMessage(20));
}

function showMessError(idLoc, info)
{
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es') {
        titleMess = "Mensaje de Error";
    }
    
    if(navigator.language=='en-EN' || navigator.language=='en') {
        titleMess = "Error Message";
    }
    var errorDiv = $("<div class='alert alert-error s2_validation_errors messageAlerts'>");
    errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
    errorDiv.append('<i class=\"icon-remove-sign\"></i> <strong>'+titleMess+'</strong>');
    errorDiv.append('<hr class=\"message-inner-separator\"></div>');
    $('#'+idLoc).prepend(errorDiv);    
    errorDiv.append('<p>' + info + '</p>\n');  
    $('#'+idLoc).focus();
    $('#'+idLoc).append(setTimerToMessage(20));
}


function showInfo(url, divShow)
{
    if(!requestSent) {
        requestSent = true;
        $.ajax({
            type: "GET",
            url: url,
            complete: function() {
                requestSent = false;
            },
            success: function(information) {
                $("#" + divShow).html(information);
            }
        });
    }
}

function viewInfo(url, title, divShow, divHide)
{
    $.ajax({
        type: "GET",
        url: url,
        success: function(information) {
            var obj = jQuery.parseJSON(information);
            if (obj.state == 'failure') {
                $("#" + message).html(obj.info);
            } else if (obj.state == 'success') {
                changeTitleWindow(title);
                $("#" + divShow).html(obj.info);
                $("#" + divShow).show();
                $("#" + divHide).hide();
            }

        }
    });
}


function showInfoPage(url, valFill)
{
    var data;
    $('#' + valFill).html('');
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {
            $('#' + valFill).html(information);
        }
    });
}

function chargeValues(url, valName, valSend, valFill, formId)
{
    var data;
    data = '&' + valName + '=' + valSend;
    $('#' + valFill).html('');
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {
            var json = jQuery.parseJSON(information);
            if (json.state == 'failure') {
                showMessError(formId, json.info);
//                $('#' + message).html(json.info);
//                $('#' + message).focus();
            } else if (json.state == 'success') {
                $('#' + valFill).html(json.info);
            }

        }
    });
}

function showDialogDelete(divDialog, hRef, url, urlAction, divTable, divShow) {
//    $('#'+hRef).dialog({
//        autoOpen: true,
////        title: title,
//        height: 200,
//        width: 300,
//        modal: true,
//        open: function() {
//            $('.confirm_yes').click(function(e){
//                deleteItem(url, urlAction, divTable, divShow);
//                $('#'+hRef).dialog("close");
////                $('#'+hRef).close();
//            });
//            $('.confirm_no').click(function(e){
//                $('#'+hRef).dialog("close");
////                $('#'+hRef).close();
//            });
//            
//         },
//        close: function() {
//            // allFields.val( "" ).removeClass( "ui-state-error" );
//        }
//    });
    $(divDialog).colorbox({
        initialHeight: '0',
        initialWidth: '0',
        href: "#"+hRef,
        inline: true,        
        opacity: '0.3',        
        onComplete: function(){
            $('.confirm_yes').click(function(e){
                e.preventDefault();
                deleteItem(url, urlAction, divTable, divShow);
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
//                $.colorbox.remove();
            });
        }

    });       
}


function chargeValuesControls(url, valName, valSendId, valNameCon, valSenIdCon, valFillChe, valFillOrg, message)
{
    var data;    
//    var valSend    = $('#' + valSendId).val();
    var valSend    = $( "input[name='"+valSendId+"']:checked").val();
    var valSendCon = $('#' + valSenIdCon).val();
    if (valSend!==-1 && valSendCon!=-1) {
        data  = '&' + valName + '=' + valSend;
        data += '&' + valNameCon + '=' + valSendCon;
        $('#' + valFillChe).html('');
        $('#' + valFillOrg).html('');
        $.ajax({
            type: "GET",
            url: url,
            data: data,
            success: function(information) {
                var json = jQuery.parseJSON(information);
                if (json.state == 'failure') {
                    $('#' + message).html(json.info);
                    $('#' + message).focus();
                } else if (json.state == 'success') {
                    if (valSendCon==1){
                        $('#' + valFillOrg).html(json.info);
                    } else if (valSendCon==2){
                        $('#' + valFillChe).html(json.info);
                    }            
                }

            }
        });
    }
}

function hideInformationControls(divPes, divWee, divDis, divChe, divOrg) {
﻿  $('#'+divPes).removeClass('hide').addClass('hide');
﻿  $('#'+divWee).removeClass('hide').addClass('hide');
﻿  $('#'+divDis).removeClass('hide').addClass('hide');
﻿  $('#'+divChe).removeClass('hide').addClass('hide');
﻿  $('#'+divOrg).removeClass('hide').addClass('hide');
}


function changeOptionsHarvest(valSendId, divYield, divHumidity, divNumberSacks, lblNumberId, lblNumTextA, lblNumTextB, lblWeightId, lblWeightTextA, lblWeightTextB)
{
    var valSend = $('#' + valSendId).val();
    if (valSend==1 || valSend==2 || valSend==5){
        $('#' + divYield).removeClass("hide");
        $('#' + divHumidity).removeClass("hide");
        $('#' + divNumberSacks).addClass("hide");
    } else if (valSend==3){
        $('#' + divYield).addClass("hide");
        $('#' + divHumidity).addClass("hide");
        $('#' + divNumberSacks).removeClass("hide");
        $('#'+lblNumberId).text(lblNumTextA);
        $('#'+lblWeightId).text(lblWeightTextA);
    } else if (valSend==4){
        $('#' + divYield).removeClass("hide");
        $('#' + divHumidity).addClass("hide");
        $('#' + divNumberSacks).removeClass("hide");
        $('#'+lblNumberId).text(lblNumTextB);
        $('#'+lblWeightId).text(lblWeightTextB);
    } else {
        $('#' + divYield).addClass("hide");
        $('#' + divHumidity).addClass("hide");
        $('#' + divNumberSacks).addClass("hide");
    }
}

function chargeValuesObjective(valSendId, divPest, divWee, divDis)
{
    var valSend = $( "input[name='"+valSendId+"']:checked").val();
    if (valSend!=null) {
        $('#' + divPest).addClass("hide");
        $('#' + divWee).addClass("hide");
        $('#' + divDis).addClass("hide");
        if (valSend==1){
            $('#' + divPest).removeClass("hide");
        } else if (valSend==2){
            $('#' + divWee).removeClass("hide");
        } else if (valSend==3){
            $('#' + divDis).removeClass("hide");
        }
//        var data;
//        data = '&' + valName + '=' + valSend;
//        $('#' + valFillPest).html('');
//        $('#' + valFillWee).html('');
//        $('#' + valFillDis).html('');
//        $.ajax({
//            type: "POST",
//            url: url,
//            data: data,
//            success: function(information) {
//                var json = jQuery.parseJSON(information);
//                if (json.state == 'failure') {
//                    $('#' + message).html(json.info);
//                    $('#' + message).focus();
//                } else if (json.state == 'success') {
//                    $('#' + divPest).addClass("hide");
//                    $('#' + divWee).addClass("hide");
//                    $('#' + divDis).addClass("hide");
//                    if (valSend==1){
//                        $('#' + valFillPest).html(json.info);
//                        $('#' + divPest).removeClass("hide");
//                    } else if (valSend==2){
//                        $('#' + valFillWee).html(json.info);
//                        $('#' + divWee).removeClass("hide");
//                    } else if (valSend==3){
//                        $('#' + valFillDis).html(json.info);
//                        $('#' + divDis).removeClass("hide");
//                    }               
//                }
//
//            }
//        });
    }
}


function selectItem(namField, idField, valName, valId, divShow, divHide)
{
    $("#" + namField).val(valName);
    $("#" + namField).focus();
//     $("#"+namField).html(valName);
    $("#" + idField).val(valId);
    toggleAndClean(divShow, divHide);
}

function resetForm(formId)
{
    $("#" + formId)[0].reset();
}

function setTimerToMessage(fade) 
{
    var html  = '<script type="text/javascript">';
        html += "  $(\".messageAlerts\").fadeOut("+(fade*1000)+");";
        html += '</script>';
    return html;    
}

function completeForm(dialogId, formId, information) 
{
//    bootstrapValidation(form, errors);
//    form.find("div.alert-success").remove();
    

//    if (errors.info && errors.errors.length > 0) {
//        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.errors, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//    }
    Recaptcha.reload();
    $.unblockUI();
    $('#'+formId).find("div.error").removeClass("error");
    $('#'+formId).find("span.s2_help_inline").remove();
    var json = jQuery.parseJSON(information);
    if (dialogId!='') {
        $('#'+dialogId).find("div.alert-success").remove();
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
        showMessError(formId, json.info);
//        $.each(json.infoR, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        }); // Estructura de arreglo
//        $(errorDiv).focus();        
        $('#'+formId).append(setTimerToMessage(8));
    } else if (json.state == 'success') {
        showMessInfo(formId, json.info);
        document.location='#'+formId;
        $('#'+formId)[0].reset();
    }
    if (dialogId!='') {
        $('#'+dialogId).dialog("close");
    }  
}

function completeFormChange(dialogId, formId, information) 
{
    $.unblockUI();
    $('#'+formId).find("div.error").removeClass("error");
    $('#'+formId).find("span.s2_help_inline").remove();
    var json = jQuery.parseJSON(information);
    if (dialogId!='') {
        $('#'+dialogId).find("div.alert-success").remove();
    }    
    if (json.state == 'failure') {
        showMessError(formId, json.info);
        $('#'+formId).append(setTimerToMessage(8));
    } else if (json.state == 'success') {
        showMessInfo(formId, json.info);
        document.location='#'+formId;
        $('#'+formId)[0].reset();
    }
    if (dialogId!='') {
        $('#'+dialogId).dialog("close");
    }  
}

function completeFormGetting(dialogId, formId, divId, information) 
{
    $('#'+divId).find("div.alert-success").remove();
    $('#'+divId).find("div.alert-error").remove();
    $.unblockUI();
    $('#'+formId).find("div.error").removeClass("error");
    $('#'+formId).find("span.s2_help_inline").remove();
    var json = jQuery.parseJSON(information);
//    if (dialogId!='') {
//        $('#'+dialogId).find("div.alert-success").remove();
//    }    
    if (json.state == 'failure') {
        showMessError(divId, json.info);
        $('#'+divId).append(setTimerToMessage(8));
    } else if (json.state == 'success') {
//        requestSent = false;
        $('#'+divId).find("div.alert-success").remove();
        showMessInfo(divId, json.info);
        document.location='#'+divId;
        $('#'+formId)[0].reset();
    }
    if (dialogId!='') {
        $('#'+dialogId).dialog("close");
    }  
}

function completeFormCrop(dialogId, formId, divId, information) 
{
    $('#'+divId).find("div.alert-success").remove();
    $('#'+divId).find("div.alert-error").remove();
    $.unblockUI();
    $('#'+formId).find("div.error").removeClass("error");
    $('#'+formId).find("span.s2_help_inline").remove();
    var json = jQuery.parseJSON(information);
//    if (dialogId!='') {
//        $('#'+dialogId).find("div.alert-success").remove();
//    }    
    if (json.state == 'failure') {
        showMessError(divId, json.info);
        $('#'+divId).append(setTimerToMessage(8));
    } else if (json.state == 'success') {
//        requestSent = false;
        $('#'+divId).find("div.alert-success").remove();
        showMessInfo(divId, json.info);
        document.location='#'+divId;
        restoreDecimalNumber(formId);
    }
    if (dialogId!='') {
        $('#'+dialogId).dialog("close");
    }  
}

function addMessageProcess() 
{
//    z-index: 1011; position: fixed; padding: 15px; margin: 0px; width: 30%; top: 40%; left: 35%; text-align: center; color: rgb(255, 255, 255); border: none; background-color: rgb(0, 0, 0); cursor: wait; border-top-left-radius: 10px; border-top-right-radius: 10px; border-bottom-right-radius: 10px; border-bottom-left-radius: 10px; opacity: 0.5;
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es') {
        titleMess = "Procesando";
    }
    
    if(navigator.language=='en-EN' || navigator.language=='en') {
        titleMess = "Processing";
    }
    $.blockUI({ css: { 
            'border-top-left-radius': '10px', 
            'border-top-right-radius': '10px', 
            'border-bottom-right-radius': '10px', 
            'border-bottom-left-radius': '10px',
            border: 'none', 
            padding: '15px', 
            backgroundColor: '#000', 
            '-webkit-border-radius': '10px', 
            '-moz-border-radius': '10px', 
            opacity: .5, 
            color: '#fff' 
        },
        message: '<div class="view-process"><div><h3><i style="font-size:30px" class="icon-spinner icon-spin"></i><br>'+titleMess+'....</h3></div></div>' 
    }); 
}

function validationForm(form, errors) 
{
//    bootstrapValidation(form, errors);
    form.find("div.error").removeClass("error");
//    form.find("div.info").removeClass("error");
    form.find("div.alert-success").remove();
    form.find("div.alert-error").remove();
    form.find("span.s2_help_inline").remove();
    form.find("div.s2_validation_errors").remove();    
    $.unblockUI();
    
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es') {
        titleMess = "Mensaje de Error";
    }
    
    if(navigator.language=='en-EN' || navigator.language=='en') {
        titleMess = "Error Message";
    }
    if (errors.errors && errors.errors.length > 0) { 
//        Recaptcha.reload();//Recarga y genera un nuevo captcha a causa del error cometido
        var errorDiv = $("<div class='alert alert-error s2_validation_errors messageAlerts' name='messageUsers'>");
        errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
        errorDiv.append('<i class=\"icon-remove-sign\"></i> <strong>'+titleMess+'</strong>');
        errorDiv.append('<hr class=\"message-inner-separator\"></div>');
        form.prepend(errorDiv);
//        errorDiv.append('<button type="button" class="close" data-dismiss="alert">&times;</button>');
        $.each(errors.errors, function(index, value) {
            errorDiv.append('<p>' + value + '</p>\n');
        });
        restoreDecimalNumber(form.attr('id'));
//        $('[name=errorRasta]').;
//        document.location='#messageUsers';
//        alert(form.attr('id'));
        document.location='#'+form.attr('id');
        form.append(setTimerToMessage(20));
    }

    if (errors.fieldErrors) {
//        Recaptcha.reload();//Recarga y genera un nuevo captcha a causa del error cometido
        $.each(errors.fieldErrors, function(index, value) {
            var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
            if (element && element.length > 0) {
                element  = $(element[0]);
                controlGroup = element.closest("div.control-group");
                controlGroup.addClass('error');
                controls = controlGroup.find("div.controls");
                if (controls) {
                    if (value[0]!="") {
                        controls.append("<span class='help-inline s2_help_inline' style='display: block; margin-top:5px; font-size:10px;'>" + value[0] + "</span>");
                    }
                }
            }
        });
    }
//    form.find("div.alert-success").remove();
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
//        var errorDiv = $("<div class='alert alert-success s2_validation_info'></div>");
//        form.prepend(errorDiv);
//        $.each(errors.info, function(index, value) {
//            errorDiv.append('<p>' + value + '</p>\n');
//        });
//        $(form)[0].reset();
//    }
}

function searchDecimalNumber(formId) {
    
    $('#'+formId+' *').filter(':input').each(function(key, elem){
        var decimal=  /^[-+]?[0-9]+\.[0-9]+$/;   
        var valTemp = elem.value;
        if (elem.value!='' && valTemp.match(decimal)) elem.value = elem.value.replace('.',',');
    });
    
}

function restoreDecimalNumber(formId) {
    
    $('#'+formId+' *').filter(':input').each(function(key, elem){
        var decimal=  /^[-+]?[0-9]+\,[0-9]+$/;   
        var valTemp = elem.value;
        if (elem.value!='' && valTemp.match(decimal)) elem.value = elem.value.replace(',','.');
    });
    
}

function saveData(url, urlAction, formId, divShow)
{
    var data = $('#'+formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        complete: function(information) {
            validationForm($('#'+formId),information);
            completeFormGetting('dialog-form', formId, divShow, information);
            setTimeout( function() {
                showInfo(urlAction, divShow);
            }, 2000);
        }
    });
}


function sendFormRasta(url, formId, divTable, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var data = $('#' + formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(information) {
            var json = jQuery.parseJSON(information);
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
            }

        }
    });
}


function sendFormProtection(url, formId, divHide, message)
{
    $('#' + message).html('');
    // $('#'+formId).toggleClass('error');

    $('div').removeClass("error");
    var data = $('#' + formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(information) {
            var json = jQuery.parseJSON(information);
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
    var data = $('#' + formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(information) {
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
    var data = $('#' + formId).serializeArray();
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function(information) {
            var json = jQuery.parseJSON(information);
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


function activeOption(ulId, classId) 
{
    if($('.'+classId)) {
        $('#'+ulId).find("li.active").removeClass("active");
        $('.'+classId).addClass("active");    
    }
}


function showInfoPassword(divId, fieldId) 
{
    var valAdd = $("#"+fieldId).val();
    if (valAdd=="false") {
        $('#'+divId).show();
        $("#"+fieldId).val("true");
    } else if (valAdd=="true") {
        $('#'+divId).hide();
        $("#"+fieldId).val("false");
    }
    
}

function showSearchAdvance(divSearchBasic, divSearchAdvance, valAsig, valSel)
{
    $("#"+valAsig).val(valSel);
    if (valSel==2) {
        $('#'+divSearchBasic).hide();
        $('#'+divSearchAdvance).show();
    } else {
        $('#'+divSearchBasic).show();
        $('#'+divSearchAdvance).hide();        
    }
    
}

function showRowAdditionalItem(url, divUpdate)
{
    var rows  = $('#'+divUpdate).children("tr");
    var child = $(rows)[rows.length-1];
//    alert($(child).attr("value"));
    var data  = '&numRows='+$(child).attr("value");
//    var data  = '&numRows='+rows.length;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {
            // responseContent = transport.responseText;
            // responseContent = information;
//            $('#'+divUpdate).insert({'bottom': information});
            $('#'+divUpdate).append(information);
        }
    });
}

function showRowAdditionalFert(url, idTypeApp, divUpdateChe, divUpdateOrg, divUpdateAme)
{
//    var valTypeApp  = $('#'+idTypeApp).val();
    var valTypeApp  = idTypeApp;
    var rows;
    if (valTypeApp==1) {
        rows  = $('#'+divUpdateChe).children("tr");
    } else if (valTypeApp==2) {
        rows  = $('#'+divUpdateOrg).children("tr");
    } else if (valTypeApp==3) {
        rows  = $('#'+divUpdateAme).children("tr");
    }
        
    
    var child = $(rows)[rows.length-1];
//    var data  = $('#'+formId).serialize();
    var data;
    data  += '&numRows='+$(child).attr("value")+'&appTyp='+valTypeApp;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {
            $('#'+idTypeApp).val(-1);
            if (valTypeApp==1) {
                rows  = $('#'+divUpdateChe).append(information);
            } else if (valTypeApp==2) {
                rows  = $('#'+divUpdateOrg).append(information);
            } else if (valTypeApp==3) {
                rows  = $('#'+divUpdateAme).append(information);
            }
        }
    });
}

function changeChemicalFoliar(typeAppId, lblProductId, lblText) {
    var valTypeApp  = $('#'+typeAppId).val();
    if (valTypeApp==2) {
        $('#'+lblProductId).text(lblText);
    }
    
}

function generateDecimals(valDec, valDegrees, valMinutes, valSeconds) {
    var valNumDegrees = parseFloat($('#'+valDegrees).val());
    var valNumMinutes = $('#'+valMinutes).val();
    var valNumSeconds = $('#'+valSeconds).val();
    if ($('#'+valDegrees).val()!=null && $('#'+valDegrees).val()!="" && valNumMinutes!=null && valNumMinutes!="" && valNumSeconds!=null && valNumSeconds!="") {
        var latLot = parseFloat((valNumMinutes/60) + (valNumSeconds/3600));
    //    alert(latLot)
        if (latLot===0) latLot = ".0";
        latLot = (valNumDegrees<0) ? ((Math.abs(valNumDegrees))+latLot)*-1 : (valNumDegrees+latLot);
        latLot = ""+latLot;
        latLot = parsePointSeparated(latLot);
    //    alert(latLot)
        $('#'+valDec).val(latLot); 
    } else {
        $('#'+valDec).val(""); 
    }
}

function generateDegrees(valDec, valDegrees, valMinutes, valSeconds) {
    
    var valNumDecimal = parseCommaSeparated($('#'+valDec).val());
    if ($('#'+valDec).val()!=null && $('#'+valDec).val()!="") {
        var d = Math.floor (valNumDecimal);
        var minfloat = (valNumDecimal-d)*60;
        var m = Math.floor(minfloat);
        var secfloat = (minfloat-m)*60;
        var s = Math.round(secfloat);   

        if (s==60) {
          m++;
          s=0;
        }
        if (m==60) {
          d++;
          m=0;
        }

        $('#'+valDegrees).val(d);
        $('#'+valMinutes).val(m);
        $('#'+valSeconds).val(s);
    } else {
        $('#'+valDegrees).val("");
        $('#'+valMinutes).val("");
        $('#'+valSeconds).val("");
    }
}

function parsePointSeparated( strVal ) {
//    alert(strVal)
    var decimal=  /^[-+]?[0-9]+\,[0-9]+$/;   
    if (strVal=="") {
        strVal = "";
//    } else if ((!isNaN(strVal)) && strVal.match(decimal)) {
    } else if (strVal!=null && strVal.match(decimal)) {
        return strVal.replace(',','.');
    }
    return strVal;
//    if (strVal!=null) {
//        if(navigator.language=='es-ES' || navigator.language=='es') {return strVal.replace('.',','); } // remove commas before parse
//        if(navigator.language=='en-EN' || navigator.language=='en') {return strVal.replace(',','.'); }// remove commas before parse
//    }
}

function parseCommaSeparated( strVal ) {
    if(navigator.language=='es-ES' || navigator.language=='es') return parseFloat(strVal.replace(',','.'));
    if(navigator.language=='en-EN' || navigator.language=='en') return parseFloat(strVal.replace('.',','));
}

function parseValueInt(strVal) {
    if (strVal!="") return parseInt(strVal);
}

var titleSelect = "";
if(navigator.language=='es-ES' || navigator.language=='es') {
    titleSelect = "Arriba";
}

if(navigator.language=='en-EN' || navigator.language=='en') {
    titleSelect = "Up";
}

beoro_scrollToTop = {
    init: function() {
        $('body').append('<div class="refTop"><a href="javascript:void(0)" class="scrollup refTop" style="display:none; font-size:80px; text-align:center;"><p style="font-size:14px;">'+titleSelect+'</p><i class="icon-chevron-sign-up icon-white"></i></a></div>');

        $(window).scroll(function(){
            if ($(this).scrollTop() > 100) {
                $('.scrollup').fadeIn();
            } else {
                $('.scrollup').fadeOut();
            }
        });

        $('.scrollup').click(function(e){
            $("html, body").animate({ scrollTop: 0 }, 600);
            e.preventDefault();
        });
    }
};

function checkValue(valSelId, limitVal) {
    var valSel = $("#" + valSelId).val();
    if (valSel>limitVal) {
       $("#" + valSelId).val('');         
    }
}

function checkValueSecond(valSelId, limitVal) {
    var valSel = $("#" + valSelId).val();
    if (valSel>limitVal || valSel==limitVal) {
       $("#" + valSelId).val('');         
    }
}

function showOtherTypeDocument(valSel, divCom, divPer) {
    if (valSel == 'NIT') {
        $("#" + divCom).show();
        $("#" + divPer).hide();
    } else if (valSel == -1) {
        $("#" + divCom).hide();
        $("#" + divPer).hide();
    } else {
        $("#" + divCom).hide();
        $("#" + divPer).show();
    }
}


function showDialogReport(divDialog, hRef, urlAction, nameData, valData, title, width, height) {
    $(divDialog).colorbox({
        initialHeight: '0',
        initialWidth: '0',
        href: "#"+hRef,
        inline: true,        
        opacity: '0.3',        
        onComplete: function(){
            $('.confirm_yes').click(function(e){
                e.preventDefault();
                viewForm(urlAction, nameData, valData, title, width, height);
                $.colorbox.close();
            });
            $('.confirm_no').click(function(e){
                e.preventDefault();
                $.colorbox.close(); 
            });
        }
    });       
}


function changeReport(valSelId, divRepA, divRepB) {
    var valSel = $("#" + valSelId).val();
    if (valSel==1) {
        $("#"+divRepA).removeClass("hide");
        $("#"+divRepB).addClass("hide");
    } else if (valSel==2) {
        $("#"+divRepA).addClass("hide");
        $("#"+divRepB).removeClass("hide");
    }
    
}

function changeRepYear(valSelId, divRepA, divRepB) {
    var valSel = $("#" + valSelId).val();         
    if (valSel==1) {
        $("#"+divRepA).removeClass("hide");
        $("#"+divRepB).addClass("hide");
    } else if (valSel==2) {
        $("#"+divRepA).removeClass("hide");
        $("#"+divRepB).removeClass("hide");
    } 
    
}


function viewPositionRasta(url, formId, valNameLat, valLatId, valNameLon, valLonId, divHide, divShow)
{
    var strValLat = $("#"+valLatId).val();
    strValLat = strValLat.replace('.',',');
    
    var strValLon = $("#"+valLonId).val();
    strValLon = strValLon.replace('.',',');
    
    $("#"+valLatId).val(strValLat);
    $("#"+valLonId).val(strValLon);
    var valLat  = $('#'+valLatId).val();
    var valLon  = $('#'+valLonId).val();
    var data    = '';
    data  += '&'+valNameLat+'='+valLat+'&'+valNameLon+'='+valLon;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {     
            var json = information;
            if (json.state == 'failure') {
                $.each(json.fieldError, function(index, value) {
                    var form = $("#"+formId);
                    var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
                    if (element && element.length > 0) {
                        element  = $(element[0]);
                        controlGroup = element.closest("div.control-group");
                        controlGroup.addClass('error');
                        controls = controlGroup.find("div.controls");
                        controls.find("span.s2_help_inline").remove();
                        if (controls) {
                            if (value[0]!="") {
                                controls.append("<span class='help-inline s2_help_inline' style='display: block; margin-top:5px; font-size:10px;'>" + value[0] + "</span>");
                            }
                        }
                    }
                });
            } else {
                $("#"+divHide).hide();
                $("#"+divShow).show();
                $("#"+divShow).html(information);       
            }
            
        }
    });
}

function viewPosition(url, formId, valNameLat, valLatId, valNameLon, valLonId, divHide, divShow)
{
    var valLat  = $('#'+valLatId).val();
    var valLon  = $('#'+valLonId).val();    
    var data    = '';
    data  += '&'+valNameLat+'='+valLat+'&'+valNameLon+'='+valLon;
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {     
//            alert(information.state)
//            var json = jQuery.parseJSON(information);
            var json = information;
            if (json.state == 'failure') {
                $.each(json.fieldError, function(index, value) {
                    var form = $("#"+formId);
                    var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
                    if (element && element.length > 0) {
                        element  = $(element[0]);
                        controlGroup = element.closest("div.control-group");
                        controlGroup.addClass('error');
                        controls = controlGroup.find("div.controls");
                        controls.find("span.s2_help_inline").remove();
                        if (controls) {
                            if (value[0]!="") {
                                controls.append("<span class='help-inline s2_help_inline' style='display: block; margin-top:5px; font-size:10px;'>" + value[0] + "</span>");
                            }
                        }
                    }
                });
                
//                showMessError(divHide, json.info);
//                $("#"+divHide).append(setTimerToMessage(8));
            } else {
                $("#"+divHide).hide();
                $("#"+divShow).show();
                $("#"+divShow).html(information);       
            }
            
        }
    });
}

function selValPos(formLatId, valIdLat, formLonId, valIdLon) 
{
    $("#"+formLatId).val($("#"+valIdLat).val());
    $("#"+formLonId).val($("#"+valIdLon).val());    
}

function showDialogWarning(hRef) 
{
    $.colorbox({
        initialHeight: '0',
        initialWidth: '0',
        href: "#"+hRef,
        inline: true,        
        opacity: '0.3',        
        onComplete: function(){
            $('.confirm_ok').click(function(e){
                e.preventDefault();
                $.colorbox.close(); 
            });
        }
    });       
}

function optSel(idCheck, div) 
{
   var valSel = $("input[name='"+idCheck+"']:checked").val();
//   var valSel = $("#"+idCheck).val();
   if (valSel=='true') {
       $("#"+div).show();
       $("#"+div).removeClass("hide");       
   } else {
       $("#"+div).hide();
       $("#"+div).addClass("hide");       
   }
    
}

function getReportCsv(url, formId, filename)
{
    var data = $('#'+formId).serializeArray();
    var xmlRequest = $.ajax({
        type: "POST",
        url: url,
        data: data
    });
    xmlRequest.done( function (response){        
        var uri = "data:text/csv;charset=utf-8," + escape(response);
        var downloadLink  = document.createElement("a");
        downloadLink.href = uri;
        downloadLink.download = filename;

        document.body.appendChild(downloadLink);
        downloadLink.click();
        document.body.removeChild(downloadLink);
        $.unblockUI();
//        window.open( "data:text/csv;charset=utf-8," + escape(response));         
    });
}

function seeDate(valSel, labChange) 
{
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es') {
        titleMess = "la aplicacion del ";
    }
    
    if(navigator.language=='en-EN' || navigator.language=='en') {
        titleMess = "the application of ";
    }
    $("#"+labChange).html("&nbsp;"+titleMess+valSel);
}

function clickSelAll(classAll, classNum, btnId) 
{
    $('.'+classAll).click();
//    $('.'+classNum).prop('checked', $(this).is(':checked'));
    if ($('.'+classAll).prop('checked')) {
        $('#'+btnId).prop('disabled', false);
        $('#'+btnId).removeClass('disabled');
        $('.'+classNum).prop('checked', true);
    } else {
        $('#'+btnId).prop('disabled', true);
        $('#'+btnId).addClass('disabled');            
        $('.'+classNum).prop('checked', false);
    }        

}

function clickSelOne(classAll, classNum, btnId) 
{
    if ($('.'+classNum+':checked').length == $('.'+classNum).length) {
        $('.'+classAll).prop('checked', true);
    } else {
        $('.'+classAll).prop('checked', false);
    }
    
    var chkId = '';
    $('.'+classNum+':checked').each(function () {
        chkId += $(this).val() + "|";
    });

    if (chkId!='') {
        $('#'+btnId).prop('disabled', false);
        $('#'+btnId).removeClass('disabled');
    } else {
        $('#'+btnId).prop('disabled', true);
        $('#'+btnId).addClass('disabled');            
    }      
}

function showDialogDeleteAll(divDialog, classNum, hRef, url, urlAction, divTable, divShow) 
{
    if ($('.'+classNum+':checked').length) {
        var chkId = '';
        $('.'+classNum+':checked').each(function () {
            chkId += $(this).val() + ",";
        });
        chkId = chkId.slice(0, -1);
    }
    url += '?valSel='+chkId;
//    $('<input>').attr({type: 'hidden',id: 'valDel'}).appendTo('#divRasta');
    $(divDialog).colorbox({
        initialHeight: '0',
        initialWidth: '0',
        href: "#"+hRef,
        inline: true,        
        opacity: '0.3',     
        onComplete: function() {
            $('.confirm_yes').click(function(e){
//                $("#valDel").val('1');
                deleteItem(url, urlAction, divTable, divShow);            
                $.colorbox.close();
                e.preventDefault();
//                e.stopPropagation();
            });
            $('.confirm_no').click(function(e){    
                $.colorbox.close(); 
                e.preventDefault();
//                e.stopPropagation();
            });
        }
    }); 
}


function removeRowHorizon(rowId, tableId) 
{    
    $('#'+rowId).remove();
    var numRows = $('#'+tableId+' tr').length;
    if (numRows<1) {
        $('#'+tableId).append('<tr value="0"></tr>');
    }
    
}

function getCountry () {
    var deferred = $.Deferred();
    $.getJSON("http://freegeoip.net/json/", function(result){
        countryCode = result.country_code;
//                    alert('Country: ' + result.country_name + '\n' + 'Code: ' + result.country_code);
        deferred.resolve();
//                    doAction();
    });
    return deferred;
}

function sendCountry(url)
{
    getCountry();
//    alert(window.location.host)
    document.location = "http://"+window.location.host+"/"+url+"&countryCode="+countryCode;
}

function showInfoPageCountry(url, countryCode, valFill)
{
    var data = "countryCode="+countryCode;
    $('#' + valFill).html('');
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        success: function(information) {
            $('#' + valFill).html(information);
        }
    });
}