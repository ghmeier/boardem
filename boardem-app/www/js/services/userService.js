/*-----------------------------------------------------
								USER SERVICE
-----------------------------------------------------*/
appCtrl.service("UserService",['$http',function($http){

	var endpoint = "users/"
	this.getUser = function(base_url,userid){
		return $http.get(base_url+endpoint+userid);
	}
	this.getUsers = function(base_url){
		return $http.get(base_url+endpoint).success(function(res){
			return res.extra;
		});
	}
}]);