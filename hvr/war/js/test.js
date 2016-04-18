$(function() {
	var medicationMap = [];
	/*
	window.sayHello = function(jsonData, divId) {
		google.charts.load('current', {
			'packages' : [ 'table' ]
		});

		// google.load("jquery", "1");

		// google.load('visualization', '1', {packages:['table']});
		google.charts.setOnLoadCallback(drawTable);

		function drawTable() {
			var data = new google.visualization.DataTable();
			data.addColumn('string', '');
			data.addColumn('string', 'ID');
			data.addColumn('string', 'Name');
			data.addColumn('string', 'Reason for Taking');
			data.addColumn('string', 'Date Started');
			// data.addColumn('string', 'Date Stopped');
			data.addColumn('string', 'Dose');
			data.addColumn('string', 'Strength');
			data.addColumn('string', 'Notes');

			try {
				alert(jsonData);
				var dataArray = JSON.parse(jsonData);
				var rows_data = [];
				alert(dataArray.medicationList.length);
				for (i = 0; i < dataArray.medicationList.length; i++) {
					var row = [ "<input type=\"checkbox\">",
							dataArray.medicationList[i].id.toString(),
							dataArray.medicationList[i].name.toString(),
							dataArray.medicationList[i].reason,
							dataArray.medicationList[i].startDate.toString(),
							dataArray.medicationList[i].dosage.toString(),
							dataArray.medicationList[i].strength.toString(),
							dataArray.medicationList[i].note ];
					rows_data.push(row);
					
					medicationMap[dataArray.medicationList[i].id.toString()] = dataArray.medicationList[i];
				}
				data.addRows(rows_data);
				var table = new google.visualization.Table(document
						.getElementById(divId));
				var dataView = new google.visualization.DataView(data);
				dataView.hideColumns([ 1 ]);
				table.draw(dataView, {
					showRowNumber : false,
					width : '75%',
					height : '75%',
					allowHtml : true
				});
				// Add our selection handler.
				google.visualization.events.addListener(table, 'select',
						function(){
							  var selection = table.getSelection();
							  var message = '';
							  try {
								  if(selection.length == 1){
									  var item = selection[0];
									  if(item.row != null){
										  var id = data.getFormattedValue(item.row, 1);
										  alert('row with id ' + id + 'has been selected');
									  }
								  }


							} catch (e) {
								// TODO: handle exception
								alert('exception in selection part:' + str(e.message));
							}
						}
				);

			} catch (e) {
				alert("exception in large block:" + e.message);
			}

		}

	}*/
});