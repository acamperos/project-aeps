
function validateProducer(url, formClass, divMessage) {
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
				$('.ajaxgif').removeClass('hide');
				var datos  = $('.'+formClass).serializeArray();
				// $('.error').fadeOut();
				// $('.msg').text('Hubo un error!').addClass('msg_error').animate({ 'right' : '130px' }, 300);
				$.ajax({
					type: "POST",
					url: url,
					data: datos,
					success: function(information) {
						$('.ajaxgif').hide();
						var obj = jQuery.parseJSON(information);
						alert(obj.state);
						if (obj.state == 'failure') {
							$('.msg').text('Error').addClass('msg_error').fadeOut(1000);
						} else if (obj.state == 'success') { 
						}									
							
					},
					error: function() {
						$('.ajaxgif').hide();
						$('.msg').text('Error').addClass('msg_error').fadeOut(1000);	
					}
				});
				// alert(1)
				// return false;
			// }
		// });
    	// $(".nombre, .asunto, .mensaje").bind('blur keyup', function(){  
        // if ($(this).val() != "") {  			
			// $('.error').fadeOut();
			// return false;  
		// }  
	// });	
	// $(".email").bind('blur keyup', function(){  
        // if ($(".email").val() != "" && emailreg.test($(".email").val())) {	
			// $('.error').fadeOut();  
			// return false;  
		// }  
	// });
    
	}	
