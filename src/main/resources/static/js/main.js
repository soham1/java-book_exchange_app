$(function () {

	$('#requestBooks').click(function(){
		$('#selectBooksForm').submit();
	});
	
	$('#offerBooks').click(function(){
		$('#offerBooksForm').submit();
	});
	
	$('#selectBooksForm').on('submit', function(e){
		var none = true;
		$(".filled-in").each(function(i, e){
	    	if(e.checked) {
	    		none = false;
	    		return true;
	    	} 
	    });

		if(none){
	    	e.preventDefault();
	    	var $toastContent = $('<span>Please select one book!</span>');
	    	Materialize.toast($toastContent, 5000);
	    }
	});
	
	$('#offerBooksForm').on('submit', function(e){
		var none = true;
		$(".filled-in").each(function(i, e){
	    	if(e.checked) {
	    		none = false;
	    		return true;
	    	} 
	    });

		if(none){
	    	e.preventDefault();
	    	var $toastContent = $('<span>Please select one book!</span>');
	    	Materialize.toast($toastContent, 5000);
	    }
	});
	
	$('.slider').slider({indicators: false});
	
});


