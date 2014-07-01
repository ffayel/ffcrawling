angular.module('marvelApp')
.directive('searchResult', ['Comic', function(MarvelService) {
    return {
      restrict: 'E', //AEC
      scope: {
        searchData: '=data',
        link: '@link',
        type: '@type'
      },
      link: function($scope, element, attrs) {
        $scope.getThumbnail = function(data, format){
            return data.path + '/' + format + '.' + data.extension;
        };
      },
      templateUrl: 'psearchresult'
    };
}]);

angular.module('marvelApp')
.directive('itemData', ['Comic', function(MarvelService){
	return {
	      restrict: 'E', //AEC
	      scope: {
	        item: '=data',
	        uri: '@uri'
	      },
	      link: function($scope, element, attrs) {
	    	  $scope.getThumbnail = function(data, format){
	    		  if(data){
	    			  return data.path + '/' + format + '.' + data.extension;
	    		  }else{return '';}
	          };
	    	  MarvelService.itemDetail($scope.uri).then(function(response){
	  			if(response.data.total == 1){
					$scope.items = response.data.results[0];
				}
			}, function(){
				//$location.path("/search");
			});
	      },
	      templateUrl: 'pdataitem'
	    };
}]);