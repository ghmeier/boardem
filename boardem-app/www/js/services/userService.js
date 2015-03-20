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

	this.getContacts = function(base_url,userId){
		var url = base_url+endpoint+userId+"/contacts";
		return $http.get(url);
	}

	this.parseUsers = function(base_url,users,userDetails,skipId,contact_ids){
		for (id in users){
			if (users[id] != skipId){
				var currentId = users[id];
				this.getUser(base_url,currentId,contact_ids).success(function(res){
					res.extra.friend = false;
					for (contact in contact_ids){
						res.extra.friend = contact_ids[contact] === res.extra.facebook_id;
						if (res.extra.friend){
							break;
						}
					}
					userDetails.push(res.extra);

				});
			}
		}
	}

	this.getUserDetail = function(base_url,skipId){
		var self = this;
		var userDetails = [];
		this.getUsers(base_url).success(function(res){
			var users = res.extra;
			self.getContacts(base_url,skipId).success(function(con){
				var contacts = con.extra; 
				self.parseUsers(base_url,users,userDetails,skipId,contacts);
			});			

		});
		return userDetails;
	}

	this.getUserContacts = function(base_url,userId){
		var userDetails = [];
		var self = this;
		this.getContacts(base_url,userId).success(function(res){
			self.parseUsers(base_url,res.extra,userDetails,userId,res.extra);

		});
		return userDetails;
	}

	this.addFriend = function(base_url,user1,user2){
		var self =this;
		return $http.post(base_url+endpoint+user1 +"/contacts?fid="+user2);
	}

	this.removeFriend = function(base_url,user1,user2){
		var self =this;
		return $http.delete(base_url+endpoint+user1 +"/contacts?fid="+user2);
	}
}]);