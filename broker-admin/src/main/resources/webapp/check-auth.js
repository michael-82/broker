var rest_base='../..';
function updateAuthStatus(onSuccess){
		$.ajax({ 
			type: 'GET', 
			url: rest_base+'/auth/status',
			success: function(data) {
				// TODO remember token expiration date
				onSuccess();
			},
			error: function(xhr, s, e){
				console.log('Session abgelaufen: '+s);
				window.location.replace('login.html');
			},
			dataType: "xml"
		});
}
$(document).ready(function(){
	// check if cookie is set
	var token = Cookies.get('token');
	if( token ){
		$.ajaxSetup({beforeSend: function (xhr){
			xhr.setRequestHeader("Authorization","Bearer "+token);        
		}});
		// cookie still valid?
		updateAuthStatus(init);
	}else{
		// no token cookie, redirect to login
		window.location.replace('login.html');
	}
});