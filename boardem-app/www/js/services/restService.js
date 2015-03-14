/*-----------------------------------------------------
								REST SERVICE
-----------------------------------------------------*/
appCtrl.service('RestService', ['$http', function ($http) {
	
        var endpoint = 'event';
        var search = 'search/';
        this.getEvents = function (base_url) {
						return $http.get(base_url+endpoint);

        };

        this.getSearch = function(base_url,lat,lng,user_id,dateStr,dist){
        	return $http.get(base_url+search+"?user_lat="+lat+"&user_lng="+lng+"&date="+dateStr+"&dist="+dist+"&user_id="+user_id);
        }
}]);