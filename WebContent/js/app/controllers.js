marvelApp
	.controller('marvelSearchCtrl', ['$scope', 'Comic', '$location', function($scope, Comic, $location){
		
		/*Comic.find().then(function(response){
			$scope.results = response.data.results;
		});
		*/
		$scope.radioModel = 1;
		$scope.searchType = 1;
		$scope.selected = undefined;
		
		$scope.onSelectAutoCompleteItem = function($item, $model, $label){
			var pathPrefix = '';
			switch ($scope.radioModel) {
			case 2 :
				pathPrefix = 'creator';
				break;
			case 1: // Comic
			default:
				pathPrefix = 'comic';
				break;
			}
			if($item && $item.id > 0){
				$location.path('/'+pathPrefix+'/'+$item.id);
			}
		};
		
		$scope.getItems = function(value){
			var result = [];
			switch ($scope.radioModel) {
			case 2: // Creator
				return Comic.autoCompleteSearchCreator(value).then(function(resp){
					angular.forEach(resp.data.results, function(item){
				        result.push({id:item.id, label: item.fullName, image: item.thumbnail});
				      });
					return result;
				});
				break;
			case 1: // Comic
			default:
				return Comic.autoCompleteSearchComic(value).then(function(resp){
					angular.forEach(resp.data.results, function(item){
				        result.push({id:item.id, label: item.title, image: item.thumbnail});
				      });
					return result;
				});
				break;
			}
			return result;
		};
		
		//recherche filtre
		$scope.errorDataSearch = false;
		$scope.pageSizes = [
			{count: 5, label: '5'},
			{count: 10, label: '10 '},
			{count: 20, label: '20 '},
			{count: 50, label: '50 '}
		];
		$scope.pageSize = $scope.pageSizes[1]; // 10
		$scope.maxSize = 5;
        $scope.currentPage = 1;
		$scope.pageChanged = function (page) {
			//$scope.currentPage = page;
            $scope.search();
        };
		$scope.isAvailableResults = function () {
			return $scope.searchResp && $scope.countItems>0 ? true : false;
		};
		$scope.searchText = function(){
			$scope.currentPage = 1;
			 $scope.search();
		};
		$scope.search = function(){
			if($scope.searchAutoCompleteModel != ''){
				$scope.loadingSearchItem = true;
				Comic.find($scope.radioModel ,$scope.pageSize.count, ($scope.currentPage-1)*$scope.pageSize.count, $scope.searchAutoCompleteModel).then(function(response){
					$scope.searchResp = response.data.results;
					$scope.searchType = $scope.radioModel;
					$scope.totalItems = response.data.total;
					$scope.countItems = response.data.count;
					$scope.loadingSearchItem = false;
					$scope.errorDataSearch = false;
				}, function(){
					$scope.searchResp = null;
					$scope.errorDataSearch = true;
					$scope.loadingSearchItem = false;
				});
			}else{
				$scope.searchResp = null;
			}
		};
	}])
	.controller('marvelComicDetailCtrl', ['$scope', 'Comic', '$routeParams', '$window', '$location', function($scope, Comic, $routeParams, $window, $location) {
		prepareCreators = function(creatorsItems){
			var result = {};
			//TODO pas extra je crais un tableau intermetdiaire améliorer l'algo.
			angular.forEach(creatorsItems, function(value, key) {
				if(!result[value.role]){
					result[value.role] = [];
				}
				result[value.role].push({'name': value.name, 'resourceURI': value.resourceURI});
			});
			var creators = [];
			angular.forEach(result, function(value, key) {
				creators.push({role:key, items: value});
			});
			return creators;
		};
		
		$scope.exctractCreatorId = function(uri){
			return uri.substring(uri.lastIndexOf('/')+1, uri.length);
		};
		$scope.viewImage = function(img){
			$window.open(img.path + '.' + img.extension);
		};
		$scope.openInNewWindow = function(img){
	        $window.open(img.path + '.' + img.extension);
	    };
		
	    Comic.detailComic($routeParams.comicId).then(function(response){
	    	if(response.data.total == 1){
				$scope.comic = response.data.results[0];
				$scope.creators = prepareCreators(response.data.results[0].creators.items);
			}
		}, function(){
			$location.path("/search");
		});
	}])
	.controller('marvelCreatorDetailCtrl', ['$scope', 'Comic', '$routeParams', '$http', function($scope, Comic, $routeParams, $http) {
		//ouvre les comics au chargement.
		$scope.comics = {open:true};
		$scope.zoneImageHover = {
				'series': '', 
				'stories': '', 
				'comics': ''};
		//TODO on peut surement mieux faire
		//liste permettant un cache des images déjà recupérées evitant de recupérer le detail a chaque hover 
		$scope.tabCacheItem = [];
		$scope.tabCacheItemId = [];
		
		Comic.detailCreator($routeParams.creatorId).then(function(response){
			if(response.data.total == 1){
				$scope.creator = response.data.results[0];
			}
		}, function(){
			$location.path("/search");
		});
		
		$scope.getDetail = function(uri){
			return Comic.itemDetail(uri).then(function(resp){
				return resp.data;
			});
		};
		$scope.getImg = function(zoneKey){
			return $scope.zoneImageHover[zoneKey];
		};
		/*TODO tentative pour mettre un lien vers le comic dans la page creator (id) 
		 * <a ng-href="#/comic/{{getLink(comic.resourceURI)}}">
		$scope.getLink = function(uri, zoneKey){
			if($scope.tabCacheItemId[uri]){
				return $scope.tabCacheItemId[uri].id;
			}else{
				return Comic.itemDetail(uri).then(function(resp){
					if(resp.data.results[0]){
						$scope.tabCacheItemId[uri] = {'id': resp.data.results[0].id};
						return $scope.tabCacheItemId[uri].id;
					}
				});
			}
		};
		$scope.buildInternalURL = function(itemId, zoneKey){
			var uri = '';
			if(itemId > 0){
				switch (zoneKey) {
				case 'comics':
					uri = '#/comic/'+itemId;
					break;
	
				default:
					break;
				}
			}
			return uri;
		};
		*/
		
		$scope.makeLinkComic = function(resourceURI){
			if(resourceURI)
				return '#/comic/'+resourceURI.substring(resourceURI.lastIndexOf('/')+1, resourceURI.length);
			else
				return '';
		};
		
		$scope.hoverItem = function(uri, zoneKey){
			if($scope.tabCacheItem[uri]){
				$scope.zoneImageHover[zoneKey] = $scope.tabCacheItem[uri].img;
			}else{
				Comic.itemDetail(uri).then(function(resp){
					if(resp.data.results[0].thumbnail){
						$scope.tabCacheItem[uri] = {
								'id': resp.data.results[0].id, 
								'img':$scope.urlImageSmall(resp.data.results[0].thumbnail)
							};
						$scope.zoneImageHover[zoneKey] = $scope.tabCacheItem[uri].img;
					}
				});
			}
			
		};
	}]);