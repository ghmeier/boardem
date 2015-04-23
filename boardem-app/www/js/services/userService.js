/*-----------------------------------------------------
								USER SERVICE
-----------------------------------------------------*/
appCtrl.service("UserService",['$http','GameService','BadgeService','UtilService',function($http,GameService,BadgeService,UtilService){

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

	this.getUserMessages = function(base_url,userId,messages){
		var self = this;
		UtilService.showLoad();
		this.getUser(base_url,userId).success(function(res){
			var messageIds = res.extra.messages;
			for (id in messageIds){
				self.getMessages(base_url,messageIds[id]).success(function(mes){
					var mes = mes.extra;
					mes.userDetails = [];
					messages.push(mes);
					self.parseUsers(base_url,mes.users,mes.userDetails,userId,[]);
				});
			}
			UtilService.hideLoad();
		});
	}

	this.getMessages = function(base_url,messageId){
		return $http.get(base_url+"messages/"+messageId);
	}

	this.getMessageDetails = function(base_url,messageId,messages,pics){
		var self = this;
		UtilService.showLoad();
		this.getMessages(base_url,messageId).success(function(res){
			var mes = res.extra.messages;

			for (id in mes){
				messages.push(mes[id]);
			}

			for (id in res.extra.users){
				self.getUser(base_url,res.extra.users[id]).success(function(use){
					pics[use.extra.facebook_id] = use.extra.picture_url;
				});
			}
			UtilService.hideLoad();
		});
	}

	this.postMessage = function(base_url,messageId,userId,comment){
		return $http.post(base_url+"messages/"+messageId+"/send",{from:userId,content:comment});
	}

	this.createConversation = function(base_url,users){
		return $http.post(base_url+"messages/create",users);
	}

	this.getUserDetail = function(base_url,skipId){
		var self = this;
		var userDetails = [];
		UtilService.showLoad();
		this.getUsers(base_url).success(function(res){
			var users = res.extra;
			self.getContacts(base_url,skipId).success(function(con){
				var contacts = con.extra;
				self.parseUsers(base_url,users,userDetails,skipId,contacts);
			});
			UtilService.hideLoad();
		});
		return userDetails;
	}

	this.getUserContacts = function(base_url,userId){
		var userDetails = [];
		var self = this;
		UtilService.showLoad();
		this.getContacts(base_url,userId).success(function(res){
			self.parseUsers(base_url,res.extra,userDetails,userId,res.extra);
			UtilService.hideLoad();
		});
		return userDetails;
	}

	this.getBadges = function(base_url,userId,badges){
		return this.getUser(base_url,userId).success(function(res){
			var badge_id = res.extra.earned_badges;
			for (id in badge_id){
				BadgeService.getBadgeDetail(base_url,badge_id[id]).success(function(r){
					badges.push(r.extra);
				});
			}
		});
	}

	this.addFriend = function(base_url,user1,user2){
		var self =this;
		return $http.post(base_url+endpoint+user1 +"/contacts?fid="+user2);
	}

	this.removeFriend = function(base_url,user1,user2){
		var self =this;
		return $http.delete(base_url+endpoint+user1 +"/contacts?fid="+user2);
	}

	this.getShelf = function(base_url,userId,shelf){
		var url = base_url+endpoint+userId+"/shelf";
		UtilService.showLoad();
		return $http.get(url).success(function(res){
			var shelfRaw = res.extra;
			for (id in shelfRaw){
				GameService.getSingleGame(base_url,shelfRaw[id]).success(function(game){
					if (game.extra && game.extra.image){
						game.extra.image = (game.extra.image).substr(2);
						game.extra.shelved = true;
						shelf.push(game.extra);
					}
				});
			}
			UtilService.hideLoad();
		});

		return shelf;
	}

	this.addToUserShelf = function(base_url, userid, game){

		return $http.get(base_url+userid+"/shelf?game"+game);

	}

	this.getCompleted = function(base_url,userid){
		return $http.get(base_url+endpoint+userid+"/completed");
	}
}]);