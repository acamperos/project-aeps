
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
    } else if (valIng == 2 || valIng == 6) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 3 || valIng == 7) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).show();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 4 || valIng == 8) {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).show();
        $("#" + divShowE).hide();
    } else if (valIng == 5 || valIng == 9) {
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

//function de costos gravedad o aspersion

function showTypeIrrigations(valSel, divShowA, divShowB, divShowC, divShowD, divShowE) {
    var valIng = $("#"+valSel).val();
    if (valIng == 1) {
        $("#" + divShowA).show();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    } else if (valIng == 2 ) {
        $("#" + divShowA).hide();
        $("#" + divShowB).show();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    }  else {
        $("#" + divShowA).hide();
        $("#" + divShowB).hide();
        $("#" + divShowC).hide();
        $("#" + divShowD).hide();
        $("#" + divShowE).hide();
    }
}

//function de costos equipo alquilado o propio

function showRentedquestionIrrigations(valSendId, divShowA, divShowB) {
    var valSend = $( "input[name='"+valSendId+"']:checked").val();
    
    if (valSend!=null) {
        $('#' + divShowA).addClass("hide");
        $('#' + divShowB).addClass("hide");
        
        if (valSend=='false'){
            $('#' + divShowA).removeClass("hide");
        } else if (valSend=='true'){
            $('#' + divShowB).removeClass("hide");
        } 
    }else {$('#' + divShowA).addClass("hide");
        $('#' + divShowB).addClass("hide");}
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
                    } else if (valSendCon==2 || valSendCon==6){
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
    if (valSend==1 || valSend==2 || valSend==5 || valSend==6 || valSend==7){
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
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
//        if (elem.value!='' && valTemp.match(decimal)) elem.value = elem.value.replace('.',',');
        if (elem.value!='' && valTemp.match(decimal)) elem.value = elem.value.replace(',','.');
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
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
        
    //url = "/aeps"+url;
    var child = $(rows)[rows.length-1];
//    var data  = $('#'+formId).serialize();
    var data;
    data  += '&numRows='+$(child).attr("value")+'&appTyp='+valTypeApp;
    $.ajax({
        type: "POST",
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
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') return parseFloat(strVal.replace(',','.'));
    if(navigator.language=='en-EN' || navigator.language=='en') return parseFloat(strVal.replace('.','.'));
}

function parseValueInt(strVal) {
    if (strVal!="") return parseInt(strVal);
}

var titleSelect = "";
if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
    strValLat = strValLat.replace(',','.');
    
    var strValLon = $("#"+valLonId).val();
    strValLon = strValLon.replace(',','.');
//    alert(strValLat)
//    alert(strValLon)
    //url = "/aeps"+url;
    $("#"+valLatId).val(strValLat);
    $("#"+valLonId).val(strValLon);
    var valLat  = $('#'+valLatId).val();
    var valLon  = $('#'+valLonId).val();
    var data    = '';
    data  += '&'+valNameLat+'='+valLat+'&'+valNameLon+'='+valLon;
    $.ajax({
        type: "POST",
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
    //url = "/aeps"+url;
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
    //url = "/aeps"+url;
    var data = $('#'+formId).serializeArray();
    var xmlRequest = $.ajax({
        type: "POST",
        url: url,
        data: data
    });
    xmlRequest.done( function (response){        
        var uri = "data:text/csv;charset=ANSI," + encodeURI(response);
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

function getReportXls(url, selectAll, selectItem)
{
    //url = "/aeps"+url;
//    var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
//    var checkboxes = document.getElementsByName(''+selectAll);
//    var vals = "";
//    for (var i=0, n=checkboxes.length;i<n;i++) {
//      if (checkboxes[i].checked) 
//      {
//        vals += ","+checkboxes[i].value;
//      }
//    }
    
    $.unblockUI();
    var boxes = $('input[name='+selectAll+']:checked');    
    var boxesValue = [];
    $(boxes).each(function(){
        boxesValue.push($(this).val());
    });
    var valAll = boxesValue.join(",");
       
    boxes = $('input[name='+selectItem+']:checked');    
    boxesValue = [];
    $(boxes).each(function(){
        boxesValue.push($(this).val());
    });
    var valItem = boxesValue.join(",");
    
//    alert(url+"?"+selectAll+"="+valAll+"&"+selectItem+"="+valItem);
//    alert(valAll)
//    alert(valItem)
    
    var info = "";
    if ((valAll!=null && valAll!="") && (valItem!=null && valItem!="")) {
        info += "?"+selectAll+"="+valAll+"&"+selectItem+"="+valItem;
    } else if (valAll!=null && valAll!="") {
        info += "?"+selectAll+"="+valAll;
    } else if (valItem!=null && valItem!="") {
        info += "?"+selectItem+"="+valItem;
    }
    
    document.location = url+info;

//    var data = $('#'+formId).serializeArray();    
//    $.ajax({
//        type: "POST",
//        url: url,
//        data: data,
//        success: function(information) {
////            window.open(information);
////            alert(information)
//            $.unblockUI();
//            var base64data = "base64," + Base64.encode(information);
////            window.open("data:application/vnd.ms-excel;filename=cropsInfo.xls;"+base64data+"download=cropsInfo.xls");
//            var uri = "data:application/vnd.ms-excel;"+base64data;
//            var downloadLink  = document.createElement("a");
//            downloadLink.href = uri;
//            downloadLink.download = filename;
//
//            document.body.appendChild(downloadLink);
//            downloadLink.click();
//            document.body.removeChild(downloadLink);
//        }
//    });
    
//    var xmlRequest = $.ajax({
//        type: "POST",
//        url: url,
//        data: data
//    });
//    xmlRequest.done( function (response){               
////        var uri = "data:application/vnd.ms-excel;base64," + $.base64.encode(response);
////        var downloadLink  = document.createElement("a");
////        downloadLink.href = uri;
////        downloadLink.download = filename;
////
////        document.body.appendChild(downloadLink);
////        downloadLink.click();
////        document.body.removeChild(downloadLink);
//        $.unblockUI();
//        var base64data = "base64," + $.base64.encode(response);
//        $('<a style="display:none" href="data:application/vnd.ms-excel;filename='+filename+';'+base64data+'" download="'+defaults.tableName.toString()+'.xls"><span></span></a>').appendTo(document.body).find('span').trigger("click").parent().remove();
//        
////        window.open( "data:application/vnd.ms-excel;charset=utf-8," + escape(response));         
//    });
}

function seeDate(valSel, labChange) 
{
    var titleMess = "";
    if(navigator.language=='es-ES' || navigator.language=='es-CO' || navigator.language=='es-PE' || navigator.language=='es-NI' || navigator.language=='es') {
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
//    $.getJSON("http://freegeoip.net/json/", function(result){
//        countryCode = result.country_code;
        countryCode = "CO";
//                    alert('Country: ' + result.country_name + '\n' + 'Code: ' + result.country_code);
        deferred.resolve();
//                    doAction();
//    });
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


function changePage(url, valName, valSend, valFill, formId, message)
{
    //url = "/aeps"+url;
    var data = '&' + valName + '=' + valSend;
    var dataForm = $('#'+formId).serializeArray();
    $('#' + valFill).html('');
    $.ajax({
        type: "POST",
        url: url+data,
        data: dataForm,
        success: function(information) {
            $('#' + valFill).html(information);
        }
    });
}

function showSelectionIrrigation(valSel, divShowA, divShowB) {
    if (valSel==1) {
        $("#" + divShowA).show();
        $("#" + divShowA).removeClass("hide");
        $("#" + divShowB).hide();
        $("#" + divShowB).addClass("hide");
    } else if (valSel==2) {
        $("#" + divShowB).show();
        $("#" + divShowB).removeClass("hide");
        $("#" + divShowA).hide();
        $("#" + divShowA).addClass("hide");
    }
}

function returnListCrop() {
    var host = window.location.host;
    var protocol = window.location.protocol;
    document.location = protocol+'//'+host+'/crop/listCrop.action';
//    document.location='/aeps/crop/listCrop.action';    
    
}

function showImageToSend(actionName, idFile, imgId) 
{
    var data = new FormData();
    jQuery.each(jQuery('#'+idFile)[0].files, function(i, file) {
        data.append('archivo', file);
    });
    var opts = {
        type: "POST",
        url: actionName,
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        success: function(response) {
//            var base64_string = decodeURI(response.resultImage);
            var someText = response.resultImage;
//            someText = someText.replace(/(\r\n|\n|\r)/gm,"");
            var base64_string = encodeURI(someText);
//            alert(base64_string)
//            var img = new Image();
//            // added `width` , `height` properties to `img` attributes
//            img.width = "250px";
//            img.height = "250px";
//            img.src = "data:image/jpg;base64,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";
            document.getElementById('imagenDinamica').setAttribute('src',someText);
        }
    };
    if(data.fake) {
        opts.xhr = function() { var xhr = jQuery.ajaxSettings.xhr(); xhr.send = xhr.sendAsBinary; return xhr; }
        opts.contentType = "multipart/form-data; boundary="+data.boundary;
        opts.data = data.toString();
    }
    jQuery.ajax(opts);
}

function getTexture(idTexture, sandId, lemonId, clayId) {
    var sand  = $('#'+sandId).val();
    var lemon = $('#'+lemonId).val();
    var clay  = $('#'+clayId).val();
    if ((sand>=86 && sand<=100) && (lemon>=0 && lemon<=14) && (clay>=0 && clay<=10)) {
        $('#'+idTexture).val("1");
    } else if ((sand>=70 && sand<=86) && (lemon>=0 && lemon<=30) && (clay>=0 && clay<=15)) {
        $('#'+idTexture).val("6");
    } else if ((sand>=50 && sand<=70) && (lemon>=0 && lemon<=50) && (clay>=0 && clay<=20)) {
        $('#'+idTexture).val("5");
    } else if ((sand>=23 && sand<=52) && (lemon>=28 && lemon<=50) && (clay>=7 && clay<=27)) {
        $('#'+idTexture).val("4");
    } else if ((sand>=20 && sand<=50) && (lemon>=74 && lemon<=88) && (clay>=0 && clay<=27)) {
        $('#'+idTexture).val("3");
    } else if ((sand>=0 && sand<=20) && (lemon>=88 && lemon<=100) && (clay>=0 && clay<=12)) {
        $('#'+idTexture).val("2");
    } else if ((sand>=20 && sand<=45) && (lemon>=15 && lemon<=52) && (clay>=27 && clay<=40)) {
        $('#'+idTexture).val("7");
    } else if ((sand>=45 && sand<=80) && (lemon>=0 && lemon<=28) && (clay>=20 && clay<=35)) {
        $('#'+idTexture).val("11");
    } else if ((sand>=0 && sand<=20) && (lemon>=40 && lemon<=73) && (clay>=27 && clay<=40)) {
        $('#'+idTexture).val("12");
    } else if ((sand>=45 && sand<=65) && (lemon>=0 && lemon<=20) && (clay>=35 && clay<=55)) {
        $('#'+idTexture).val("8");
    } else if ((sand>=0 && sand<=20) && (lemon>=40 && lemon<=60) && (clay>=40 && clay<=60)) {
        $('#'+idTexture).val("9");
    } else if ((sand>=0 && sand<=45) && (lemon>=0 && lemon<=40) && (clay>=40 && clay<=100)) {
        $('#'+idTexture).val("10");
    } else {        
        $('#'+idTexture).val("-1");
    }
}

function clearForm(formId)
{
    $("#"+formId).trigger("reset");    
}

function selectItemCropcheck(namField, idField, valName, valId)
{
    $("#" + namField).val(valName);
    $("#" + namField).focus();
    $("#" + idField).val(valId);
    closeWindow();
}