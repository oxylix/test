var realFormapp = angular.module('realtimeFormApp', []);

realFormapp.config(['$sceDelegateProvider', '$qProvider', function($sceDelegateProvider, $qProvider) {
    // We must whitelist the JSONP endpoint that we are using to show that we trust it
    $sceDelegateProvider.resourceUrlWhitelist([
      'self',
      'http://localhost:9090/**'
    ]);
    $qProvider.errorOnUnhandledRejections(false);
  }]);


realFormapp.controller('realTimeCtrl',  function($scope, $http, $interval, getMetrics) {
    
	$scope.main = {
            page: 1,
            size: 15             
      };
	$scope.srvname = 'all';
	$scope.selectedSrv = {
			pingId: '',
			name: 'Selectionner un serveur'
	};
	
	
	
	$interval(function(){
		var liveurl = $scope.urltomonitor;
		if( liveurl != "" && liveurl != null)	
				$scope.fetchRealTime(liveurl);
	},100000);
			
	$scope.fetchRealTime = function(url) {
		// $scope.title1 = "Real time";
		$http({
		method: 'GET',
		  url: '/api/realtime?hosttofetch='+url,
		  param : {'hosttofetch': url}
		}).
		then(function(response) {
			$scope.data = angular.fromJson(response.data);
		  })
		  .catch(function (err) {
			  console.log("erreur: "+data);
		});
	}
	
	
	$scope.showInfoChart = function( um, hs, jtc, jps, thac, thps) {
			
				 $('.products-preview__cont').removeClass('hide');
				 $('.preview_jdbc_chart').html('');
				 $('.preview_mem_chart').html('');
				 $('.preview_threads_chart').html('');
				
				 $scope.drawDonut('.preview_mem_chart', {'label': 'InUse', 'value': um}, {'label': 'Free', 'value': (hs - um)});
				 $scope.drawDonut('.preview_jdbc_chart', {'label': 'Waiting', 'value': jtc}, {'label': 'PoolSize', 'value': jps});
				 $scope.drawDonut('.preview_threads_chart', {'label': 'Active', 'value': thac}, {'label': 'PoolSize', 'value': thps});
			}
	
	    
    $scope.validate = function(e){
    	var url = $scope.urltomonitor;
    	
    	this.fetchRealTime(url);
    }
    
 $scope.drawDonut = function(a,b,c) {
    	Morris.Donut({
			 element: $(a),
			data: [
				{label: b.label, value: b.value},		
				{label: c.label, value:  c.value},
			],
			colors: ['#ed4949', '#20c05c'],
			backgroundColor: '#30363c',
			labelColor: '#fff',
			resize: true
		});
    	
    }
	
});




realFormapp.service('getMetrics', function() {
    this.fetchJVMMetrics = function (x) {
         return x.toString(16);
    }
});
