angular.module('marvelFiltres', [])
.filter('datetranslate', function() {
  return function(input) {
	  str = input;
	  switch (input) {
	case 'onsaleDate':
		str = 'Publish';
		break;
	case 'focDate':
		str = 'FOC Date';
		break;
	}
    return str;
  };
});