/*-----------------------------------------------------
								REST SERVICE
-----------------------------------------------------*/
appCtrl.service('RestService', ['$http', function ($http) {
	
        var endpoint = 'event';

        this.getEvents = function (base_url) {

						return $http.get(base_url+endpoint);

        };
}]);