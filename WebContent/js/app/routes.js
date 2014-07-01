marvelApp.config(['$routeProvider',
               function($routeProvider){
	$routeProvider.when('/search', {
		templateUrl: 'psearch',
		controller: 'marvelSearchCtrl'
	}).
	when('/comic/:comicId', {
		templateUrl: 'pcomic',
	    controller: 'marvelComicDetailCtrl'
	}).
	when('/creator/:creatorId', {
		templateUrl: 'pcreator',
	    controller: 'marvelCreatorDetailCtrl'
	}).otherwise({
        redirectTo: '/search'
    });
}]);