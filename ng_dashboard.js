var dashapp = angular.module('dashboardApp', []);

dashapp.config(['$sceDelegateProvider', '$qProvider', function($sceDelegateProvider, $qProvider) {
    // We must whitelist the JSONP endpoint that we are using to show that we trust it
    $sceDelegateProvider.resourceUrlWhitelist([
      'self',
      'http://localhost:9090/**'
    ]);
    $qProvider.errorOnUnhandledRejections(false);
  }]);


dashapp.controller('dashCtrl',  function($scope, $http, $interval, getMetrics) {
	
	//$scope.Math = window.Math;
	
	$scope.main = {
            page: 1,
            size: 15             
      };
	$scope.srvname = 'all';
	$scope.selectedSrv = {
			pingId: '',
			name: ''
	};
	
	
	$scope.fetchLastResult = function(e) {
				
		$scope.title1 = "Historique";
		$scope.srvname = 'all';
		$scope.main.pages = 10;
			
		$http({
		method: 'GET',
		  url: '/api/history/all?page=1'
		}).
		then(function(response) {
			$scope.data = angular.fromJson(response.data);
		  })
		  .catch(function (err) {
			  console.log("erreur: "+data);
		});
	}
	
	$scope.fetch = function(e) {
		//$scope.title1 = "Historique";
		if(e != null){
			var srvname = e.target.getAttribute('data-value');
			if($scope.srvname != srvname){
				$scope.main.page = 0;
			}
			$scope.srvname = srvname;
		}			
		
		$http({
		method: 'GET',
		  url: '/api/history/'+$scope.srvname+'?page='+$scope.main.page+'&size='+$scope.main.size,
		  param : {}
		}).
		then(function(response) {
			$scope.main.pages = response.data.totalPages;
			$scope.data = angular.fromJson(response.data);
		  })
		  .catch(function (err) {
			  console.log("erreur: "+response.data);
		  });
	}
	
	
	$scope.showdetails = function(e) {
		 var pingdetails = e; // e.target.getAttribute('id_jvm');
		
		 $('.products-preview__cont').removeClass('hide');
		 $('.preview_jdbc_chart').html('');
		 $('.preview_mem_chart').html('');
		 $('.preview_threads_chart').html('');
		 
		$http({
		method: 'GET',
		  url: '/api/details/'+pingdetails,
		  param : {}
		}).
		then(function(response) {
				$scope.selectedSrv = angular.fromJson(response.data);
				$scope.memPercent = Math.round($scope.selectedSrv.usedMemory/$scope.selectedSrv.heapSize *100);
				$scope.pcubtnClass = $scope.memPercent > 75 ? 'danger': 'success';
								
				$scope.drawDonut('.preview_mem_chart', {'label': 'InUse', 'value': $scope.selectedSrv.usedMemory}, {'label': 'Free', 'value': ($scope.selectedSrv.heapSize - $scope.selectedSrv.usedMemory)});
				$scope.drawDonut('.preview_jdbc_chart', {'label': 'Waiting', 'value': $scope.selectedSrv.jdbc[0].waitingThreadCount}, {'label': 'PoolSize', 'value': $scope.selectedSrv.jdbc[0].poolSize});
				$scope.drawDonut('.preview_threads_chart', {'label': 'Active', 'value': $scope.selectedSrv.thPool[0].activeCount}, {'label': 'PoolSize', 'value': $scope.selectedSrv.thPool[0].poolSize});
				
		  })
		  .catch(function (err) {
			  console.log("erreur: "+data);
		  });
	}
	
	$scope.nextPage = function(e) {
		var srvname = e.target.getAttribute('data-value');
		$scope.srvname = srvname;
		if ($scope.main.page < $scope.main.pages) {
            $scope.main.page++;
            $scope.fetch(e);
        }
    };
    
    $scope.previousPage = function(e) {
    	var srvname = e.target.getAttribute('data-value');
		$scope.srvname = srvname;
    	if ($scope.main.page >= 1) {
            $scope.main.page--;
            $scope.fetch(e);
        }
    };
    
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
    };
    
    $scope.showThPoolModal = function(modal, thpList){
    	$scope.list_thPool = thpList;
    	$('#'+modal).modal('show');
    }
	
});




dashapp.service('getMetrics', function() {
    this.fetchJVMMetrics = function (x) {
         return x.toString(16);
    }
});
