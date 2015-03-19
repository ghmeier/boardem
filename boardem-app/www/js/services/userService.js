/*-----------------------------------------------------
								USER SERVICE
-----------------------------------------------------*/
appCtrl.service("UserService",['$http',function($http){

	var endpoint = "users/"
	this.getUser = function(base_url,userid){
		return $http.get(base_url+endpoint+userid);
	}

	this.getUsers = function(base_url){
		return $http.get(base_url+endpoint);
	}

	this.getUserDetail = function(base_url,skipId){
		var self = this;
		var userDetails = [];
		this.getUsers(base_url).success(function(res){
			var users = res.extra;
			
			for (id in users){
				if (users[id] != skipId){
					self.getUser(base_url,users[id]).success(function(res){
						userDetails.push(res.extra);
					});
				}
			}
		});
		return userDetails;
	}

	this.addFriend = function(base_url,user1,user2){
		var self =this;
		return $http.post(base_url+endpoint+user1 +"/contacts?fid="+user2);
	}
}]);