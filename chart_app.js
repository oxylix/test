$(document).ready(function() {

	var month = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec'];
	
	$('#jsonptest').html("test");
	
	Morris.Donut({
		element: $('.ld-widget-side__chart'),
		data: [
			{label: "Light", value: 30},
			{label: "Pro", value: 20},
			{label: "Free", value: 45}
		],
		colors: ['#ed4949', '#FED42A', '#20c05c', '#1e59d9'],
		backgroundColor: '#30363c',
		labelColor: '#88939C',
		resize: true
	});
	
	$.getJSON("/filedoc/api/chart", function (json) {
		var acctregs = new Morris.Line({
		// ID of the element in which to draw the chart.
		element: $('.ld-widget-main__chart'),
		// Chart data records -- each entry in this array corresponds to a point on
		// the chart.
		data: json,
		// The name of the data record attribute that contains x-values.
		xkey: 'pingId',
		// A list of names of data record attributes that contain y-values.
		ykeys: ['icn1', 'icn2', 'icn3', 'icn4'],
		// Labels for the ykeys -- will be displayed when you hover over the
		// chart.
		colors: ['#ed4949', '#FED42A', '#20c05c', '#1e59d9'],
		labels: ['icn1', 'icn2', 'icn3', 'icn4'],
		dateFormat: function (x) {
		return new Date(x).toString().split("00:00:00")[0];
		}
		});
	});


	$('.selectpicker').selectpicker();
});

function ShowGrpah(chart, rest_data, el) {
	
	
	
	$.getJSON("/filedoc/chart/memoryncpu", function (json) {
			var acctregs = new Morris.Line({
			// ID of the element in which to draw the chart.
			element: $('.ld-widget-main__chart'),
			// Chart data records -- each entry in this array corresponds to a point on
			// the chart.
			data: json,
			// The name of the data record attribute that contains x-values.
			xkey: 'pingId',
			// A list of names of data record attributes that contain y-values.
			ykeys: ['usedMemory'],
			// Labels for the ykeys -- will be displayed when you hover over the
			// chart.
			labels: ['Value'],
			dateFormat: function (x) {
			return new Date(x).toString().split("00:00:00")[0];
			}
			});
		});
	
}

function timeConverter(UNIX_timestamp){
  var a = new Date(UNIX_timestamp * 1000);
  var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
  var year = a.getFullYear();
  var month = months[a.getMonth()];
  var date = a.getDate();
  var hour = a.getHours();
  var min = a.getMinutes();
  var sec = a.getSeconds();
  var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
  return time;
}
