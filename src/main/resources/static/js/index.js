$(function() {
	var id = null;
	$(".txtIndex").val("").focus();
	$(".firstname").val("");
	$(".lastname").val("");
	$(".querying").hide();
	$(".successEmployee").hide();
	$(".saving").hide();
	$(".successSaved").hide();
	$(".errorEmployee").hide();
    $(".errorSaved").hide();
    
	$('.btnGetData').click(function() {

		var index = $(".txtIndex").val();
		$(".querying").show();
		$.ajax({
			type : 'GET',
			url : '/getData?employeeId=' + index,
			success : function(result) {
				id = index;
				if (result.length > 0) {
					$(".firstname").val(result[0].firstName);
					$(".lastname").val(result[0].lastName);
				} else {
					$(".firstname").val("No results!");
					$(".lastname").val("");
				}
				$(".querying").hide();
				$('.successEmployee').show();
				$('.successEmployee').delay(1000).fadeOut();
			},
			error : function() {				
				$(".firstname").val("");
				$(".lastname").val("");
				$(".querying").hide();
				$('.errorEmployee').show();
                $('.errorEmployee').delay(2000).fadeOut();
			}
		});
	});

	$('.btnUpdate').click(function() {

		if (id != null) {
			$(".saving").show();
			$.ajax({
				type : 'GET',
				url : '/saveData?employeeId=' + id + "&firstName=" + $(".firstname").val() + "&lastName=" + $(".lastname").val(),
				success : function() {
					$(".saving").hide();
					$('.successSaved').show();
					$('.successSaved').delay(1000).fadeOut();
				},
				error : function() {
					$(".saving").hide();
					$('.errorSaved').show();
                    $('.errorSaved').delay(2000).fadeOut();
				}
			});
		}
	});

	$('.txtIndex').keypress(function(e) {
		if (e.keyCode == 13)
			$('.btnGetData').click();
	});
	
	$('.firstname').keypress(function(e) {
		if (e.keyCode == 13)
			$('.btnUpdate').click();
	});
	
	$('.lastname').keypress(function(e) {
		if (e.keyCode == 13)
			$('.btnUpdate').click();
	});
});
