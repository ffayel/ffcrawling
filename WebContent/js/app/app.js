var marvelApp = angular.module('marvelApp', [ 'ngRoute', 'ngSanitize', 'ui.bootstrap', 'marvelFiltres' ]);

//create global Funtion
marvelApp.run(['$rootScope', '$location', '$anchorScroll', function($rootScope, $location, $anchorScroll) {
	$rootScope.urlImage = function(img, size){
		return img?img.path + '/' + size + '.' + img.extension:'';
	};
	
	$rootScope.urlImageSmall = function(img){
		return img?(img.path + '/portrait_xlarge.' + img.extension):'';
	};
	
	$rootScope.scrollTo = function(id){
		$location.hash(id);
		$anchorScroll();
	};
}]);