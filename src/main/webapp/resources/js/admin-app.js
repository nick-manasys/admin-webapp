$(function() {
	$(function() {
		$("#dialog-modal").dialog({
			height : 768,
			width : 1024,
			autoOpen : false,
			modal : true,
			buttons : {
				Close : function() {
					$(this).dialog("close");
				}
			}
		});
	});
});
function showDetails() {
	$("#dialog-modal").dialog("open");
}
