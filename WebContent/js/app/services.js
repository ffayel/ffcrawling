marvelApp
	.factory('Comic', function($http, $q){
	var publicKey = marvelApp.publicKey;
	var baseUrl = 'http://gateway.marvel.com/v1/';
	var limitAutoComplete = 10;
	
	var find = function(typeFind, limit, offset, text){
		switch (typeFind) {
		case 2: // creator
			return findCeator(limit, offset, text);
			break;

		case 1: //Comics
		default:
			return findComic(limit, offset, text);
			break;
		}
	};
	
	var findComic = function(limit, offset, text){
		var def = $q.defer();
		if('' == text){
			return def.reject;
		}else{
		$http({method: 'GET', 
			'url': baseUrl + 'public/comics', 
			params: {'limit':limit, 
				'offset': offset,
				'titleStartsWith': text,
				'apikey':publicKey}})
		.success(def.resolve).error(def.reject);
		return def.promise;
		}
	};
	
	var findCeator = function(limit, offset, text){
		var def = $q.defer();
		if('' == text){
			return def.reject;
		}else{
			$http({method: 'GET', 
				'url': baseUrl + 'public/creators', 
				params: {'limit':limit, 
					'offset': offset,
					'nameStartsWith': text,
					'apikey':publicKey}})
					.success(def.resolve).error(def.reject);
			return def.promise;
		}
	};
	var autoCompleteSearchComic = function(text){
		var def = $q.defer();
		$http({method: 'GET', 
			'url': baseUrl + 'public/comics', 
			params: {'titleStartsWith':text, 'limit':limitAutoComplete, 'apikey':publicKey}})
		.success(def.resolve).error(def.reject);
		return def.promise;
	};
	var autoCompleteSearchCreator = function(text){
		var def = $q.defer();
		$http({method: 'GET', 
			'url': baseUrl + 'public/creators', 
			params: {'nameStartsWith' : text, 'limit':limitAutoComplete, 'apikey':publicKey}})
			.success(def.resolve).error(def.reject);
		return def.promise;
	};
	
	var detailComic = function(comicId){
		var def = $q.defer();
		$http({method: 'GET', url: baseUrl + 'public/comics/'+comicId, params: {apikey:publicKey}})
		.success(def.resolve).error(def.reject);
		return def.promise;
	};
	
	var detailCreator = function(creatorId){
		var def = $q.defer();
		$http({method: 'GET', url: baseUrl + 'public/creators/'+creatorId, params: {apikey:publicKey}})
		.success(def.resolve).error(def.reject);
		return def.promise;
	};
	
	var itemDetail = function(uri){
		var def = $q.defer();
		$http({method: 'GET', url: uri, params: {apikey:publicKey}})
		.success(def.resolve).error(def.reject);
		return def.promise;
	};
	
    return {'find': find,
    	'autoCompleteSearchComic' : autoCompleteSearchComic,
    	'autoCompleteSearchCreator' : autoCompleteSearchCreator,
    	'detailComic' : detailComic,
    	'detailCreator' : detailCreator,
    	'itemDetail' : itemDetail};
  });




/*******

marvelAppServices.factory('MarvelService', function ($resource) {
  return $resource('http://gateway.marvel.com/v1/public/characters/:id', { id: '@id', "apikey": api.key }, {
    update: { method: 'PUT' }
  });
});
*/